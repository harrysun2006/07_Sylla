package com.sylla.modi;

import com.develop.jawin.COMException;
import com.develop.jawin.DispatchPtr;
import com.develop.jawin.IUnknown;

public class IWords extends DispatchPtr {

  public IWords(IUnknown unk) throws COMException {
  	super(unk);
	}

  public int getCount() throws COMException {
  	return (Integer) get("Count");
  }

  public DispatchPtr getNewEnum() throws COMException {
  	return (DispatchPtr) get("_NewEnum");
  }

  public IWord getItem(int index) throws COMException {
  	DispatchPtr word = (DispatchPtr) get("Item", index);
  	return new IWord(word);
  }

}
