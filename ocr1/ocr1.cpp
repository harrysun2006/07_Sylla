// ocr1.cpp : Defines the initialization routines for the DLL.
//
#include <iostream>
#include "stdafx.h"
#include "ocr1.h"
#include "jni.h"
#include "ocr.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif

//
//TODO: If this DLL is dynamically linked against the MFC DLLs,
//		any functions exported from this DLL which call into
//		MFC must have the AFX_MANAGE_STATE macro added at the
//		very beginning of the function.
//
//		For example:
//
//		extern "C" BOOL PASCAL EXPORT ExportedFunction()
//		{
//			AFX_MANAGE_STATE(AfxGetStaticModuleState());
//			// normal function body here
//		}
//
//		It is very important that this macro appear in each
//		function, prior to any calls into MFC.  This means that
//		it must appear as the first statement within the 
//		function, even before any object variable declarations
//		as their constructors may generate calls into the MFC
//		DLL.
//
//		Please see MFC Technical Notes 33 and 58 for additional
//		details.
//


// Cocr1App

BEGIN_MESSAGE_MAP(Cocr1App, CWinApp)
END_MESSAGE_MAP()


// Cocr1App construction

Cocr1App::Cocr1App()
{
    // TODO: add construction code here,
    // Place all significant initialization in InitInstance
}


// The one and only Cocr1App object

Cocr1App theApp;

const GUID CDECL BASED_CODE _tlid =
{ 0x6A50935, 0xC959, 0x41D0, { 0x8B, 0x79, 0xC0, 0xAE, 0xD8, 0x2F, 0x45, 0xD1 } };
const WORD _wVerMajor = 1;
const WORD _wVerMinor = 0;


// Cocr1App initialization

BOOL Cocr1App::InitInstance()
{
    CWinApp::InitInstance();

    // Register all OLE server (factories) as running.  This enables the
    //  OLE libraries to create objects from other applications.
    COleObjectFactory::RegisterAll();

    return TRUE;
}

// DllGetClassObject - Returns class factory

STDAPI DllGetClassObject(REFCLSID rclsid, REFIID riid, LPVOID* ppv)
{
    AFX_MANAGE_STATE(AfxGetStaticModuleState());
    return AfxDllGetClassObject(rclsid, riid, ppv);
}


// DllCanUnloadNow - Allows COM to unload DLL

STDAPI DllCanUnloadNow(void)
{
    AFX_MANAGE_STATE(AfxGetStaticModuleState());
    return AfxDllCanUnloadNow();
}


// DllRegisterServer - Adds entries to the system registry

STDAPI DllRegisterServer(void)
{
    AFX_MANAGE_STATE(AfxGetStaticModuleState());

    if (!AfxOleRegisterTypeLib(AfxGetInstanceHandle(), _tlid))
        return SELFREG_E_TYPELIB;

    if (!COleObjectFactory::UpdateRegistryAll())
        return SELFREG_E_CLASS;

    return S_OK;
}


// DllUnregisterServer - Removes entries from the system registry

STDAPI DllUnregisterServer(void)
{
    AFX_MANAGE_STATE(AfxGetStaticModuleState());

    if (!AfxOleUnregisterTypeLib(_tlid, _wVerMajor, _wVerMinor))
        return SELFREG_E_TYPELIB;

    if (!COleObjectFactory::UpdateRegistryAll(FALSE))
        return SELFREG_E_CLASS;

    return S_OK;
}

jstring NewJString(JNIEnv * env, LPCSTR str)
{
	if (!env || !str) return 0;
	int slen = strlen(str);
	jchar * buffer = new jchar[slen];
	int len = MultiByteToWideChar(CP_ACP, 0, str, strlen(str), (LPWSTR) buffer, slen);
	if (len > 0 && len < slen) buffer[len] = 0;
	jstring js = env->NewString(buffer, len);
	delete [] buffer;
	return js;
}

#ifdef __cplusplus
extern "C" {
#endif
JNIEXPORT jobjectArray JNICALL Java_com_sylla_Ocr_winocr(JNIEnv *env, jobject obj, jstring name)
{
    USES_CONVERSION; 

    jboolean isCopy = true;
    const char * pszName = env->GetStringUTFChars(name, &isCopy);
    POCR_ITEMS pItems = ocr(pszName);
    POCR_ITEM pItem;
    int count = pItems->GetSize();

    jobjectArray ocrImages = 0;
    jclass objClass = env->FindClass("java/lang/Object");
    ocrImages = env->NewObjectArray(count, objClass, 0);
    jclass clazz = env->FindClass("com/sylla/OcrImage");
    jmethodID text = env->GetMethodID(clazz, "setText", "(Ljava/lang/String;)V");
    jmethodID accurate = env->GetMethodID(clazz, "setAccurate", "(D)V");
    const char * pszText;
    
    for(int i = 0; i < count; i++ )
    {
        pItem = pItems->GetAt(i);
        pszText = W2A(pItem->text);
        jobject ocrImage = env->AllocObject(clazz);
        env->CallVoidMethod(ocrImage, text, NewJString(env, pszText));
        env->CallVoidMethod(ocrImage, accurate, (jdouble) pItem->accurate);
        env->SetObjectArrayElement(ocrImages, i, ocrImage);
    }
    env->ReleaseStringUTFChars(name, pszName);
    return ocrImages;
}
#ifdef __cplusplus
}
#endif