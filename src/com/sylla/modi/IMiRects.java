package com.sylla.modi;

import com.develop.jawin.COMException;
import com.develop.jawin.DispatchPtr;
import com.develop.jawin.IUnknown;

public class IMiRects extends DispatchPtr {

  public IMiRects(IUnknown unk) throws COMException {
  	super(unk);
	}

  public int getCount() throws COMException {
  	return (Integer) get("Count");
  }

  public DispatchPtr getNewEnum() throws COMException {
  	return (DispatchPtr) get("_NewEnum");
  }

  public IMiRect getItem(int index) throws COMException {
  	DispatchPtr rect = (DispatchPtr) get("Item", index);
  	return new IMiRect(rect);
  }

}
