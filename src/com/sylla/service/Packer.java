package com.sylla.service;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Actual implement: just move images to folders
 * Original design: compress images to archive
 * 1. compress images in "./images/ii" into zip file: ./images/ii.zip
 * 2. compress images in "./images/is" into zip file: ./images/is.zip
 * 3. compress images in "./images/if" into zip file: ./images/if.zip
 * 4. delete processed images
 * 5. notify Ocrer
 * Comments:
 * 1. zip file: 8192 files per folder, folder name is max(index) (7 digits)
 * 2. one thread per package, ZipOutputStream is not thread safe
 * @author hsun
 *
 */
public class Packer {

	// private static final Log _log = LogFactory.getLog(Packer.class);

	// public static final Packer IPACKER = new Packer("./images/ii/", "./images/ii.zip");
	
	public static final Packer IPACKER = new Packer("./images/ii/");

	public static final String FOLDER_NAME = "{0,number,0000000}/";
	
	public static final long FOLDER_SIZE = 8192;

	private static final Pattern INDEX_PATTERN = Pattern.compile("\\w(\\d{7}).*");

	private String path;

	private Packer(String path) {
		this.path = path;
	}

	private String getName(String name) {
		Matcher m = INDEX_PATTERN.matcher(name);
		long index;
		String s = m.find() ? m.group(1) : "";
		index = Long.parseLong(s);
		index = index / FOLDER_SIZE * FOLDER_SIZE;
		return path + MessageFormat.format(FOLDER_NAME, index);
	}

	public void save(File f) throws IOException {
		String newPath = getName(f.getName());
		File np = new File(newPath);
		if (!np.exists()) np.mkdirs();
		File nf = new File(newPath + f.getName());
		f.renameTo(nf);
		/*
		if (this.size() > Imager.FILE_MAXCOUNT) fullSame.p();
		byte data[] = new byte[BUFFER_SIZE];
    mutexSame.p();
		try {
	    FileInputStream fi = new FileInputStream(nf);
	    InputStream is = new BufferedInputStream(fi, BUFFER_SIZE);
	    ZipEntry entry = new ZipEntry(getName(nf.getName()));
	    int count;
	    zip.putNextEntry(entry);
	    while ((count = is.read(data, 0, BUFFER_SIZE)) != -1) {
	        zip.write(data, 0, count);
	    }
	    is.close();
	    nf.delete();
	    _log.debug("Compress image success: " + f.getName());
		} catch(IOException ioe) {
			_log.error("Compress image failed: " + f.getName());
			throw ioe;
		} finally {
	    mutexSame.v();
	    fullSame.v();
		}
		*/
	}

}
