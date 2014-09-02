#if !defined(OCR__INCLUDED_)
#define OCR__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <afxtempl.h>

typedef struct _OCR_ITEM
{
	CString	text;
	double	accurate;
} OCR_ITEM, *POCR_ITEM;

typedef CArray <POCR_ITEM, POCR_ITEM> OCR_ITEMS, *POCR_ITEMS;
POCR_ITEMS ocr(LPCSTR);
#endif // !defined(OCR__INCLUDED_)