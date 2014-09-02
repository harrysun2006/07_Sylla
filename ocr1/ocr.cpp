// ocr.cpp
//
#include <afxtempl.h>
#include <atlbase.h>
#include <iostream>

#include "stdafx.h"
#include "ocr.h"
#include "MDIVWCTL.tlh"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

using namespace std;

POCR_ITEMS ocr(LPCSTR pszName)
{
    USES_CONVERSION;

    POCR_ITEMS pItems = new OCR_ITEMS;
    POCR_ITEM pItem;
    HRESULT hr = NULL;
    LPVOID pReserved = NULL;
    IDocument  *pDoc = NULL;
    // CComPtr<IDispatch> pDisp;
    // CComPtr<IDocument> pDoc;
    IImages *pImages = NULL;
    IImage *pImage = NULL;
    ILayout *pLayout = NULL;
    IWords *pWords = NULL;
    IWord *pWord = NULL;
    IMiRects *pRects = NULL;
    IMiRect *pRect = NULL;
    long iCount = 0, wCount = 0, rCount = 0, count = 0;
    long left, top, right, bottom, minTop = 0, maxBottom = 0, savedMaxBottom = 0;
    short conf = 0;
    double tConf = 0.0;

    CComBSTR bstrName;
    BSTR bstrText;
    CString strText;

    hr = CoInitialize(0);
    // hr = CoInitializeEx(pReserved, COINIT_MULTITHREADED);
    // hr = CoInitializeEx(pReserved, COINIT_APARTMENTTHREADED);
    if (!SUCCEEDED(hr)) return pItems;

    hr = CoCreateInstance(CLSID_Document, NULL, CLSCTX_INPROC_SERVER, IID_IDocument, (void**) &pDoc);
    // hr = CoCreateInstance(CLSID_Document, NULL, CLSCTX_INPROC_SERVER, IID_IDocument, (void**) &pDisp);
    // hr = pDisp->QueryInterface(IID_IDocument, (void**) &pDoc);
    // hr = pDoc.CoCreateInstance(CLSID_Document);
    // hr = CoGetClassObject(CLSID_Document, CLSCTX_INPROC_SERVER, NULL, IID_IDocument, (void**) &pDoc);
    if (!SUCCEEDED(hr)) return pItems;

    /*
    IStream *pStream = NULL;
    IUnknown *pUnknown = NULL;
    pDoc->QueryInterface(IID_IUnknown, (void **)&pUnknown);
    if (pUnknown != NULL)
    {
    CoMarshalInterThreadInterfaceInStream(__uuidof(IDocument), pUnknown, &pStream);
    pUnknown->Release();
    }
    */

    bstrName = pszName;
    hr = pDoc->Create(bstrName);
    if (!SUCCEEDED(hr)) return pItems;

    hr = pDoc->OCR(miLANG_CHINESE_SIMPLIFIED, FALSE, FALSE);
    if (!SUCCEEDED(hr)) return pItems;

    pDoc->get_Images(&pImages);
    pImages->get_Count(&iCount);
    for (long i = 0; i < iCount; i++ )
    {
        pImages->get_Item(i, (IDispatch**)&pImage);
        pImage->get_Layout(&pLayout);
        pLayout->get_Words(&pWords);
        pWords->get_Count(&wCount);

        for (long j = 0; j < wCount; j++)
        {
            pWords->get_Item(j, (IDispatch**)&pWord);
            pWord->get_Rects(&pRects);
            pRects->get_Count(&rCount);
            minTop = maxBottom = 0;
            for (long k = 0; k < rCount; k++)
            {
                pRects->get_Item(k, (IDispatch**)&pRect);
                pRect->get_Left(&left);
                pRect->get_Top(&top);
                pRect->get_Right(&right);
                pRect->get_Bottom(&bottom);
                if (minTop == 0) minTop = top;
                if (minTop > top) minTop = top;
                if (maxBottom < bottom) maxBottom = bottom;
                if (pRect) pRect->Release();
            }
            if (pRects) pRects->Release();
            if (savedMaxBottom == 0) savedMaxBottom = maxBottom;
            if (minTop >= savedMaxBottom)
            {
                tConf /= count;
                pItem = new OCR_ITEM();
                pItem->text = strText;
                pItem->accurate = tConf;
                pItems->Add(pItem);
                savedMaxBottom = maxBottom;
                strText = "";
                tConf = 0.0;
                count = 0;
            }
            pWord->get_Text(&bstrText);
            pWord->get_RecognitionConfidence(&conf);
            tConf += conf;
            count++;
            strText += bstrText ? OLE2CT(bstrText) : _T("");
            if (pWord) pWord->Release();
        }
        if (strText.GetLength() > 0)
        {
            tConf /= count;
            pItem = new OCR_ITEM();
            pItem->text = strText;
            pItem->accurate = tConf;
            pItems->Add(pItem);
            strText = "";
            tConf = 0.0;
            count = 0;
        }
        if (pWords) pWords->Release();
        if (pLayout) pLayout->Release();
        if (pImage) pImage->Release();
    }
    if (pImages) pImages->Release();
    pDoc->Close(0);
    pDoc->Release();
    CoUninitialize();
    return pItems;
}
