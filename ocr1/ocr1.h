// ocr1.h : main header file for the ocr1 DLL
//

#pragma once

#ifndef __AFXWIN_H__
	#error "include 'stdafx.h' before including this file for PCH"
#endif

#include "resource.h"		// main symbols


// Cocr1App
// See ocr1.cpp for the implementation of this class
//

class Cocr1App : public CWinApp
{
public:
	Cocr1App();

// Overrides
public:
	virtual BOOL InitInstance();

	DECLARE_MESSAGE_MAP()
};
