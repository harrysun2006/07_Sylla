package com.sylla.service;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sylla.Image;

public class Combiner {

	private static final long FOLDER_SIZE = 8192;

	private static final String FOLDER_NAME = "{0,number,0000000}/";

	private static final String IIMAGE_FILENAME = "i{0,number,0000000}.jpg";

	private static final String IMAGE_PATH = "./images/";

	private static final String IIMAGE_PATH = "./images/ii/";

	private List<Integer> errors = new ArrayList<Integer>();

	private BufferedImage image;

	public List<Integer> getErrors() {
		return errors;
	}

	public BufferedImage getImage() {
		return image;
	}

	public static String getName(Image image) {
		String name = MessageFormat.format(IIMAGE_FILENAME, image.getIndex());
		String path;
		if (Image.IS_STORED.equals(image.getStatus())) path = IMAGE_PATH;
		else path = IIMAGE_PATH + MessageFormat.format(FOLDER_NAME, image.getIndex() / FOLDER_SIZE * FOLDER_SIZE);
		return path + name;
	}

	public static Combiner combine(List<Image> images) {
		File[] files = new File[images.size()];
		Image image;
		for (int i = 0; i < files.length; i++) {
			image = images.get(i);
			files[i] = new File(getName(image));
		}
		return combine(files);
	}

	public static Combiner combine(File[] files) {
		return combine1(files);
	}

	private static Combiner combine1(File[] files) {
		Combiner r = new Combiner();
		if(files == null || files.length <= 0) return r;
		BufferedImage[] images = new BufferedImage[files.length];
		int w = 0, h = 0, y = 0;
		for (int i = 0; i < files.length; i++) {
			try {
				images[i] = ImageIO.read(files[i]);
				if (w < images[i].getWidth()) w = images[i].getWidth();
				h += images[i].getHeight();
			} catch(Exception e) {
				r.errors.add(i);
			}
		}
		r.image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = r.image.createGraphics();
		g2.setBackground(Color.WHITE);
		g2.clearRect(0, 0, w, h);
		for (int i = 0; i < images.length; i++) {
			g2.drawImage(images[i], null, 0, y);
			y += images[i].getHeight();
		}
		return r;
	}

	private static Combiner combine2(File[] files) {
		Combiner r = new Combiner();
		if(files == null || files.length <= 0) return r;
		BufferedImage[] images = new BufferedImage[files.length];
		int w = 0, h = 0, y = 0, d = 12;
		for (int i = 0; i < files.length; i++) {
			try {
				images[i] = ImageIO.read(files[i]);
				if (w < images[i].getWidth()) w = images[i].getWidth();
				h += images[i].getHeight() + d;
			} catch(Exception e) {
				r.errors.add(i);
			}
		}
		r.image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = r.image.createGraphics();
		g2.setBackground(Color.WHITE);
		g2.clearRect(0, 0, w, h);
		for (int i = 0; i < images.length; i++) {
			g2.drawImage(images[i], null, 0, y + d);
			y += images[i].getHeight() + d;
		}
		return r;
	}

	public static File save(BufferedImage image, String name) throws Exception {
		File bif = new File(name);
		OutputStream os = new FileOutputStream(bif);
		JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(os);
		enc.getDefaultJPEGEncodeParam(image).setQuality(100, true);
		enc.encode(image);
		os.flush();
		os.close();
		return bif;
	}
}
