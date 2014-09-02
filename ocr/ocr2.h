#if !defined(OCR2__INCLUDED_)
#define OCR2__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <afxtempl.h>

typedef struct _OCR_ITEM
{
	CString	text;
	double	accurate;
} OCR_ITEM, *POCR_ITEM;

void ocr(LPCTSTR);
#endif // !defined(OCR2__INCLUDED_)