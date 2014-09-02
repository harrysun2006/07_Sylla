package com.sylla.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sylla.Image;
import com.sylla.Niffler;
import com.sylla.Samephore;

/**
 * Thread to download images from /CorpSel/InfoShowImg.aspx page
 * 1. save to files instead of db (deprecated)
 * 2. dump all downloaded images from db to files (optional)
 * 3. update the images to NULL those have been dumped to files
 * 4. threads will be blocked if too many files in the folder
 * 5. notify Ocrer
 * Comments:
 * 1. jpg file name: "i" + index(7 digits) + ".jpg"
 * 2. folder: ./images
 * @author hsun
 *
 */
public class Imager {

	private static final Log _log = LogFactory.getLog(Imager.class);

	public static final long FOLDER_MAXSIZE = 80000;

	private static final String[] IMAGE_STATUS = {/*Image.IS_DOWNLOADED, */Image.IS_INITIAL};

	private static final Samephore FULL_SAMEPHORE = new Samephore(0);

	private static final Samephore EMPTY_SAMEPHORE = new Samephore(0);

	private static final Samephore MUTEX_IDX = new Samephore(1);

	private static final long GROUP_SIZE = 20;

	private static boolean stop = false;

	private static boolean running = false;

	private static final byte[] RUN_LOCK = {};

	private static Long idx = 0L;

	private Imager() {
	}

	public static void vv() {
		FULL_SAMEPHORE.v();
	}

	public static void v() {
		EMPTY_SAMEPHORE.v();
	}

	public static void run(long count) {
		synchronized(RUN_LOCK) {
			if (running) return;
			else running = true; 
		}
		for (int i = 0; i < count; i ++) {
			new Thread(new ImageThread()).start();
		}
	}

	private static final class ImageThread implements Runnable {

		public void run() {
			List<Image> images = null;
			Image image = null;
			byte[] b;
			while(!stop) {
				MUTEX_IDX.p();
				try {
					images = SyllaServiceUtil.getImages(GROUP_SIZE, IMAGE_STATUS, idx);
				} catch(Throwable tt) {}
				if (images.size() == 0) {
					if (idx == 0) EMPTY_SAMEPHORE.p();
					else idx = 0L;
				}
				if (images.size() > 0) {
					image = images.get(images.size() - 1);
					idx = (image == null) ? 0 : image.getIndex();
				}
				MUTEX_IDX.v();
				if (Ocrer.size() > FOLDER_MAXSIZE) FULL_SAMEPHORE.p();
				for (Iterator<Image> it = images.iterator(); it.hasNext(); ) {
					image = it.next();
					try {
						b = Niffler.image(image);
						File f = new File(MessageFormat.format(Ocrer.IIMAGE_FILENAME, image.getIndex()));
						OutputStream os = new FileOutputStream(f);
						os.write(b);
						os.close();
						image.setStatus(Image.IS_STORED);
						// SyllaServiceUtil.updateImage(image);
						SyllaServiceUtil.updateImageStatus(image);
						_log.debug("Download image success: " + image.getIndex());
					} catch(Throwable tt) {
						_log.error("Download image failed: " + image.getIndex());
					} // end of try
				} // end of for
				Ocrer.v();
			} // end of while
		} // end of ImageThread.run
	} // end of class ImageThread

} // end of class Imager
