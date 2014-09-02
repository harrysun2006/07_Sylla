package com.sylla.modi;

import com.develop.jawin.COMException;
import com.develop.jawin.DispatchPtr;
import com.develop.jawin.IUnknown;

public class IWord extends DispatchPtr {

  public IWord(IUnknown unk) throws COMException {
  	super(unk);
	}

  public int getRecognitionConfidence() throws COMException {
  	return (Short) get("RecognitionConfidence");
  }

  public int getFontId() throws COMException {
  	return (Integer) get("FontId");
  }

  public int getLineId() throws COMException {
  	return (Integer) get("LineId");
  }

  public int getRegionId() throws COMException {
  	return (Integer) get("RegionId");
  }

  public String getText() throws COMException {
  	return (String) get("Text");
  }

  public IMiFont getFont() throws COMException {
  	DispatchPtr font = (DispatchPtr) get("Font");
  	return new IMiFont(font);
  }

  public int getId() throws COMException {
  	return (Integer) get("Id");
  }

  public IMiRects getRects() throws COMException {
  	DispatchPtr rects = (DispatchPtr) get("Rects");
  	return new IMiRects(rects);
  }

}
