package com.sylla.modi;

import com.develop.jawin.COMException;
import com.develop.jawin.COMPtr;
import com.develop.jawin.DispatchPtr;
import com.develop.jawin.win32.Ole32;

public class IDocument extends DispatchPtr {

  // public static final GUID proxyIID = Constants.IID_IDocument;
  // public static final int iidToken = IdentityManager.registerProxy(proxyIID, IDocument.class);

	public IDocument() throws COMException {
	  COMPtr cp = Ole32.CoCreateInstance(Constants.CLSID_Document, Ole32.CLSCTX_INPROC_SERVER, proxyIID);
	  stealUnknown(cp);
	}

  public void save() throws COMException {
  	invoke("Save");
  }

  public void close(boolean saveChanges) throws COMException {
  	invoke("Close", saveChanges);
  }

  public void saveAs(String fileName, int fileFormat, int compLevel) throws COMException {
  	invoke("SaveAs", new Object[]{fileName, fileFormat, compLevel});
  }

  public IImages getImages() throws COMException {
  	DispatchPtr images = (DispatchPtr) get("Images");
  	return new IImages(images);
  }

  public DispatchPtr getBuiltInDocumentProperties() throws COMException {
  	return (DispatchPtr) get("BuiltInDocumentProperties");
  }

  public DispatchPtr getCustomDocumentProperties() throws COMException {
  	return (DispatchPtr) get("CustomDocumentProperties");
  }

  public void create(String fileName) throws COMException {
  	invoke("Create", fileName);
  }

  public boolean getDirty() throws COMException {
  	return (Boolean) get("Dirty");
  }

  public void OCR(int langId, boolean OCROrientImage, boolean OCRStraightenImage) throws COMException {
  	invoke("OCR", langId, OCROrientImage, OCRStraightenImage);
  }

  public void printOut(long from, long to, long copies, String printerName, String printToFileName, boolean printAnnotation, int fitMode) throws COMException {
  	invoke("PrintOut", new Object[]{from, to, copies, printerName, printToFileName, printAnnotation, fitMode});
  }

}
