package com.sylla.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageUtil {

	/**
	 * if success return a BufferedImage
	 * otherwise return a list contains incorrect file indexes
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public static BufferedImage combine(File[] files) throws Exception {
		if(files == null || files.length <= 0) return null;
		BufferedImage[] images = new BufferedImage[files.length];
		int w = 0, h = 0, y = 0;
		for (int i = 0; i < files.length; i++) {
			images[i] = ImageIO.read(files[i]);
			if (w < images[i].getWidth()) w = images[i].getWidth();
			h += images[i].getHeight();
		}
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		g2.setBackground(Color.WHITE);
		g2.clearRect(0, 0, w, h);
		for (int i = 0; i < images.length; i++) {
			g2.drawImage(images[i], null, 0, y);
			y += images[i].getHeight();
		}
		return image;
	}

}
