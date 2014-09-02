package com.sylla.modi;

import com.develop.jawin.COMException;
import com.develop.jawin.DispatchPtr;
import com.develop.jawin.IUnknown;

public class IMiRect extends DispatchPtr {

  public IMiRect(IUnknown unk) throws COMException {
  	super(unk);
	}

  public int getTop() throws COMException {
  	return (Integer) get("Top");
  }

  public int getLeft() throws COMException {
  	return (Integer) get("Left");
  }

  public int getRight() throws COMException {
  	return (Integer) get("Right");
  }

  public int getBottom() throws COMException {
  	return (Integer) get("Bottom");
  }

}
