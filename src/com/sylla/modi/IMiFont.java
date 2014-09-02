package com.sylla.modi;

import com.develop.jawin.COMException;
import com.develop.jawin.DispatchPtr;
import com.develop.jawin.IUnknown;

public class IMiFont extends DispatchPtr {

  public IMiFont(IUnknown unk) throws COMException {
  	super(unk);
	}

  public int getFamily() throws COMException {
  	return (Integer) get("Family");
  }

  public int getFaceStyle() throws COMException {
  	return (Integer) get("FaceStyle");
  }

  public int getSerifStyle() throws COMException {
  	return (Integer) get("SerifStyle");
  }

  public int getFontSize() throws COMException {
  	return (Integer) get("FontSize");
  }

}
