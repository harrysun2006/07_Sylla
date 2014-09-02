package com.sylla.modi;

import com.develop.jawin.COMException;
import com.develop.jawin.DispatchPtr;
import com.develop.jawin.IUnknown;

public class IImages extends DispatchPtr {

  public IImages(IUnknown unk) throws COMException {
  	super(unk);
	}

  public int getCount() throws COMException {
  	return (Integer) get("Count");
  }

  public void add(DispatchPtr page, DispatchPtr beforePage) throws COMException {
  	invoke("Add", new Object[]{page, beforePage});
  }

  public DispatchPtr getNewEnum() throws COMException {
  	return (DispatchPtr) get("_NewEnum");
  }

  public IImage getItem(int index) throws COMException {
  	DispatchPtr image = (DispatchPtr) get("Item", index);
  	return new IImage(image);
  }

  public void remove(DispatchPtr page) throws COMException {
  	invoke("Remove", page);
  }

}
