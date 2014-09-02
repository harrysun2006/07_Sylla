package com.sylla.modi;

import com.develop.jawin.COMException;
import com.develop.jawin.DispatchPtr;
import com.develop.jawin.IUnknown;

public class ILayout extends DispatchPtr {

  public ILayout(IUnknown unk) throws COMException {
  	super(unk);
	}

  public int getLanguage() throws COMException {
  	return (Integer) get("Language");
  }

  public int getNumChars() throws COMException {
  	return (Integer) get("NumChars");
  }

  public int getNumWords() throws COMException {
  	return (Integer) get("NumWords");
  }

  public int getNumFonts() throws COMException {
  	return (Integer) get("NumFonts");
  }

  public String getText() throws COMException {
  	return (String) get("Text");
  }

  public IWords getWords() throws COMException {
  	DispatchPtr words = (DispatchPtr) get("Words");
  	return new IWords(words);
  }

}
