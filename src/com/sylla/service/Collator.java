package com.sylla.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sylla.Image;
import com.sylla.Ocr;
import com.sylla.OcrImage;
import com.sylla.Samephore;

public class Collator {

	private static final Log _log = LogFactory.getLog(Collator.class);

	// private static final String IMAGE_HSQL_CRITERIA = " im.type = 'CD2' and LEN(im.text) > 10";
	// private static final String IMAGE_HSQL_CRITERIA = " im.text not like '%-%-%' and im.type = 'CD2'";
	private static final String IMAGE_HSQL_CRITERIA = " im.type = 'T' and im.accurate = 300";

	private static final Samephore MUTEX_IDX = new Samephore(1);

	private static final long GROUP_SIZE = 6;

	private static boolean stop = false;

	private static boolean running = false;

	private static final byte[] RUN_LOCK = {};

	private static Long idx = 0L;

	private Collator() {
	}

	public static void run(long count) {
		synchronized (RUN_LOCK) {
			if (running || count <= 0) return;
			else running = true;
		}
		for (int i = 0; i < count; i++) {
			new Thread(new CollateThread()).start();
		}
	}


	private static byte[] readBytes(String file) throws Exception {
		File f = new File(file);
		InputStream is = new FileInputStream(f);
		byte[] b = new byte[is.available()];
		is.read(b);
		is.close();
		return b;
	}

	private static final class CollateThread implements Runnable {

		public void run() {
			run1();
		}

		/**
		 * manual collate, accurate = 1000
		 *
		 */
		private void run1() {
			List<Image> images = null;
			Image image = null;
			String text;
			while (!stop) {
				MUTEX_IDX.p();
				try {
					images = SyllaServiceUtil.getImages(GROUP_SIZE, IMAGE_HSQL_CRITERIA, idx);
				} catch (Throwable tt) {
				}
				if (images.size() > 0) {
					image = images.get(images.size() - 1);
					idx = (image == null) ? 0 : image.getIndex();
				} 
				MUTEX_IDX.v();
				if (images.size() == 0) break;
				try {
					for (Iterator<Image> it = images.iterator(); it.hasNext(); ) {
						image = it.next();
						text = OcrCollator.getCode(readBytes(Combiner.getName(image)), image.getText());
						image.setText(text);
						image.setAccurate(1000.0);
						SyllaServiceUtil.updateImageText(image);
					}
				} catch (Throwable tt) {
					_log.error(tt, tt);
				} 
			} // end of whil
		} // end of CollateThread.run1

		private final static int REPEAT_COUNT = 3;

		/**
		 * using ocr (420x32, bottom align) to recognize telephone numbers again
		 *
		 */
		private void run2() {
			List<Image> images = null;
			List<Image> tims = new ArrayList<Image>();
			Image image = null;
			String text;
			String criteria = " im.type = 'T' and im.accurate < 200";
			while (!stop) {
				MUTEX_IDX.p();
				try {
					images = SyllaServiceUtil.getImages(GROUP_SIZE, criteria, idx);
				} catch (Throwable tt) {
				}
				if (images.size() > 0) {
					image = images.get(images.size() - 1);
					idx = (image == null) ? 0 : image.getIndex();
				} 
				MUTEX_IDX.v();
				if (images.size() == 0) break;
				for (Iterator<Image> it = images.iterator(); it.hasNext(); ) {
					image = it.next();
					try {
						tims.clear();
						for (int i = 0; i < REPEAT_COUNT; i++) tims.add(image);
						combineAndOcr(tims);
					} catch (Throwable tt) {
						_log.error(tt, tt);
					} 
				}
			} // end of whil
		} // end of CollateThread.run2

		private void combineAndOcr(List<Image> images) throws Exception {
			Image image = null;
			int i = 0;
			File[] files = new File[images.size()];
			for (i = 0; i < files.length; i++) {
				image = images.get(i);
				files[i] = new File(Combiner.getName(image));
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
			String name = MessageFormat.format(Ocrer.BIMAGE_FILENAME, image.getIndex());
			File bif = null;
			try {
				bif = Combiner.save(bi, name);
				// recognize
				OcrImage[] items = Ocr.winocr(bif.getAbsolutePath());

				if (items.length != images.size()) throw new Exception("OCR failed: " + name);
				// update images' status, text, accurate to db
				
				i = REPEAT_COUNT / 2;
				double accurate;
				image = images.get(i);
				image.setStatus(Image.IS_COMPLETE);
				if (items[i].getText().equals(image.getText())) accurate = 300.0;
				else accurate = 200.0;
				image.setText(items[1].getText());
				image.setAccurate(accurate);
				SyllaServiceUtil.updateImageText(image);
				_log.debug("OCR image success: " + image.getCode());
			} catch(Throwable tt) {
			} finally {
				// delete the big image
				if (bif != null) bif.delete();
			}

		}

	} // end of class CollateThread
}
