package com.sylla.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sylla.Image;
import com.sylla.Ocr;
import com.sylla.OcrImage;
import com.sylla.Samephore;

/**
 * Read, Combine & Recognize the images:
 * 1. read n image files(n >= 11, configurable, status = "S"), file is "./images/i\\d{7}\\.jpg" 
 * 2. combine these images to one big image (420 x (20*n)), best recognition
 * 3. put these images to IPACKER (move them to "./images/ii/")
 * 4. recoginze (JNI call MODI dll) the big image
 * 5. if confidence = 100, put the big image to SPACKER (move it to "./image/is/")
 *    otherwise put it to FPACKER (move it to "./image/if/")
 * 6. update db (text, accurate, status)
 * 7. notify Packer & Imager
 * Comments: 
 * 1. big image name: "b" + max(index) (7 digits) + ".jpg"
 * 
 * @author hsun
 * 
 */
public class Ocrer {

	private static final Log _log = LogFactory.getLog(Ocrer.class);

	private static final String IIMAGE_PATH = "./images/";

	public static final String IIMAGE_FILENAME = "./images/i{0,number,0000000}.jpg";

	public static final String BIMAGE_FILENAME = "./images/b{0,number,0000000}.jpg";

	private static final String[] IMAGE_STATUS = {Image.IS_STORED};

	private static final Samephore EMPTY_SAMEPHORE = new Samephore(0);

	private static final Samephore MUTEX_IDX = new Samephore(1);

	private static final long GROUP_SIZE = 11;

	private static boolean stop = false;

	private static boolean running = false;

	private static final byte[] RUN_LOCK = {};

	private static Long idx = 0L;

	private Ocrer() {
	}

	public static void v() {
		EMPTY_SAMEPHORE.v();
	}

	public static int size() {
		File f = new File(IIMAGE_PATH);
		String[] s = f.list();
		return (s == null) ? 0 : s.length;
	}

	public static void run(long count) {
		synchronized (RUN_LOCK) {
			if (running || count <= 0) return;
			else running = true;
		}
		for (int i = 0; i < count; i++) {
			// new Thread(new OcrThread()).start();
			new OcrThread().run();
		}
	}

	private static final class OcrThread implements Runnable {

		private void combineAndOcr(List<Image> images) throws Exception {
			Image image = null;
			int i = 0;
			File[] files = new File[images.size()];
			for (i = 0; i < files.length; i++) {
				image = images.get(i);
				files[i] = new File(MessageFormat.format(IIMAGE_FILENAME, image.getIndex()));
			}
			Combiner r = Combiner.combine(files); 
			BufferedImage bi = r.getImage();
			List<Integer> errors = r.getErrors();
			for (Iterator<Integer> it = errors.iterator(); it.hasNext();) {
				i = it.next();
				image = images.get(i);
				image.setStatus(Image.IS_INITIAL);
				SyllaServiceUtil.updateImageStatus(image);
			}

			if (bi == null) throw new Exception("Combine images failed!");
			String name = MessageFormat.format(BIMAGE_FILENAME, image.getIndex());
			File bif = Combiner.save(bi, name);

			// recognize
			OcrImage[] items = Ocr.winocr(bif.getAbsolutePath());
			double accurate = 0.0;

			if (items.length != images.size())
				throw new Exception("OCR failed: " + name);
			// update images' status, text, accurate to db
			for (i = 0; i < images.size(); i++) {
				image = images.get(i);
				image.setStatus(Image.IS_COMPLETE);
				image.setText(items[i].getText());
				image.setAccurate(items[i].getAccurate());
				accurate += items[i].getAccurate();
				SyllaServiceUtil.updateImageText(image);
				_log.debug("OCR image success: " + image.getCode());
			}

			// move image files to IPACKER
			for (i = 0; i < files.length; i++) {
				Packer.IPACKER.save(files[i]);
			}

			accurate /= items.length;
			// delete the big image
			bif.delete();
		}

		public void run() {
			List<Image> images = null;
			Image image = null;
			while (!stop) {
				MUTEX_IDX.p();
				try {
					images = SyllaServiceUtil.getImages(GROUP_SIZE, IMAGE_STATUS, idx);
				} catch (Throwable tt) {
				}
				if (images.size() < GROUP_SIZE) {
					if (idx == 0) EMPTY_SAMEPHORE.p();
					else idx = 0L;
				}
				if (images.size() > 0) {
					image = images.get(images.size() - 1);
					idx = (image == null) ? 0 : image.getIndex();
				}
				MUTEX_IDX.v();
				if (images.size() == 0) break;
				try {
					if (images.size() == GROUP_SIZE) combineAndOcr(images);
				} catch (Throwable tt) {
					if (_log.isErrorEnabled()) {
						StringBuffer sb = new StringBuffer();
						sb.append("OCR image failed: ");
						for (int i = 0; i < images.size(); i++) {
							image = images.get(i);
							sb.append(image.getIndex()).append(", ");
						}
						_log.error(sb.toString(), tt);
					}
				} finally {
					if (Ocrer.size() < Imager.FOLDER_MAXSIZE) Imager.vv();
				}
			} // end of while
		} // end of OcrThread.run
	} // end of class OcrThread

} // end of class Ocrer
