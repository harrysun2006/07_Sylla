package com.sylla.modi;

import com.develop.jawin.COMException;
import com.develop.jawin.DispatchPtr;
import com.develop.jawin.IUnknown;

public class IImage extends DispatchPtr {

  public IImage(IUnknown unk) throws COMException {
  	super(unk);
	}

  public ILayout getLayout() throws COMException {
  	DispatchPtr layout = (DispatchPtr) get("Layout");
  	return new ILayout(layout);
  }

  public int getPixelWidth() throws COMException {
  	return (Integer) get("PixelWidth");
  }

  public int getPixelHeight() throws COMException {
  	return (Integer) get("PixelHeight");
  }

  public int getXDPI() throws COMException {
  	return (Integer) get("XDPI");
  }

  public int getYDPI() throws COMException {
  	return (Integer) get("YDPI");
  }

  public int getBitsPerPixel() throws COMException {
  	return (Integer) get("BitsPerPixel");
  }

  public IPictureDisp getThumbnail(int thumbSize) throws COMException {
  	DispatchPtr picture = (DispatchPtr) get("Thumbnail", thumbSize);
  	return new IPictureDisp(picture);
  }

  public IPictureDisp getPicture() throws COMException {
  	DispatchPtr picture = (DispatchPtr) get("Picture");
  	return new IPictureDisp(picture);
  }

  public int getCompression() throws COMException {
  	return (Integer) get("Compression");
  }

  public void OCR(int langId, boolean OCROrientImage, boolean OCRStraightenImage) throws COMException {
  	invoke("OCR", langId, OCROrientImage, OCRStraightenImage);
  }

  public void rotate(long angle) throws COMException {
  	invoke("Rotate", angle);
  }

}
