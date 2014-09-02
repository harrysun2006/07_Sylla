package com.sylla;

import java.util.ArrayList;
import java.util.List;

import com.develop.jawin.COMException;
import com.develop.jawin.win32.Ole32;
import com.sylla.modi.Constants;
import com.sylla.modi.IDocument;
import com.sylla.modi.IImage;
import com.sylla.modi.IImages;
import com.sylla.modi.ILayout;
import com.sylla.modi.IMiRect;
import com.sylla.modi.IMiRects;
import com.sylla.modi.IWord;
import com.sylla.modi.IWords;

public class Ocr {

	private static final OcrImage[] EMPTY_OCR_IMAGE = {};

	public static native OcrImage[] winocr(String name);

	public static native double add(double x, double y);

	public static native double sub(double x, double y);

	public static native double mul(double x, double y);

	public static native double div(double x, double y);

	public static OcrImage[] ocr(String name) throws COMException {
		List<OcrImage> items = new ArrayList<OcrImage>();
		OcrImage item;
		try {
			Ole32.CoInitialize();
			IDocument document =  new IDocument();
			document.create(name);
			document.OCR(Constants.miLANG_CHINESE_SIMPLIFIED, false, false);
			IImages images = document.getImages();
			IImage image;
			ILayout layout;
			IWords words;
			IWord word;
			IMiRects rects;
			IMiRect rect;
			int iCount, wCount, rCount, count = 0;
			int top, bottom, minTop, maxBottom, savedMaxBottom = 0;
			int conf;
			double tConf = 0.0;
			StringBuffer text = new StringBuffer();
			iCount = images.getCount();
		  for (int i = 0; i < iCount; i++ ) {
		  	image = images.getItem(i);
		  	layout = image.getLayout();
		  	words = layout.getWords();
		  	wCount = words.getCount();
		  	for (int j = 0; j < wCount; j++) {
		  		word = words.getItem(j);
		  		rects = word.getRects();
		  		rCount = rects.getCount();
		  		minTop = maxBottom = 0;
		  		for (int k = 0; k < rCount; k++) {
		  			rect = rects.getItem(k);
		  			top = rect.getTop();
		  			bottom = rect.getBottom();
		  			if (minTop == 0 || minTop > top) minTop = top;
		  			if (maxBottom < bottom) maxBottom = bottom;
		  		}
		  		if (savedMaxBottom == 0) savedMaxBottom = maxBottom;
		  		if (minTop >= savedMaxBottom) {
		  			tConf /= count;
		  			item = new OcrImage(text.toString(), tConf);
		  			items.add(item);
		  			savedMaxBottom = maxBottom;
		  			text.delete(0, text.length());
		  			tConf = 0.0;
		  			count = 0;
		  		}
		  		text.append(word.getText());
		  		conf = word.getRecognitionConfidence();
		  		tConf += conf;
		  		count++;
		  	}
		  	if (text.length() > 0) {
		  		tConf /= count;
		  		item = new OcrImage(text.toString(), tConf);
		  		items.add(item);
	  			text.delete(0, text.length());
	  			tConf = 0.0;
	  			count = 0;
		  	}
		  }
		  document.close(false);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
	  	Ole32.CoUninitialize();
	  	System.gc();
	  }
		return (OcrImage[])items.toArray(EMPTY_OCR_IMAGE);
	}

}
/*
StringBuffer sb = new StringBuffer();
sb.append(i).append(".image: ")
	.append("bitsPerPixel = ").append(image.getBitsPerPixel())
	.append(", compression = ").append(image.getCompression())
	.append(", pixelHeight = ").append(image.getPixelHeight())
	.append(", pixelWidth = ").append(image.getPixelWidth())
	.append(", XDPI = ").append(image.getXDPI())
	.append(", YDPI = ").append(image.getYDPI()).append("\n");
sb.append(i).append(".layout: ")
	.append("language = ").append(layout.getLanguage())
	.append(", NumChars = ").append(layout.getNumChars())
	.append(", NumWords = ").append(layout.getNumWords())
	.append(", NumFonts = ").append(layout.getNumFonts())
	.append(", Text = ").append(layout.getText()).append("\n");
System.out.println(sb.toString());
*/
