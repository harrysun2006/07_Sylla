// MainDlg.h: interface for the CMainDlg class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(MAINDLG__INCLUDED_)
#define MAINDLG__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "resource.h"

class CMainDlg : public CAxDialogImpl<CMainDlg>  
{
public:
	enum {IDD = IDD_MAINDLG};

	BEGIN_MSG_MAP(CMainDlg)
		MESSAGE_HANDLER(WM_INITDIALOG, OnInitDialog)
		MESSAGE_HANDLER(WM_DESTROY, OnDestroy)
		COMMAND_ID_HANDLER(IDCANCEL, OnCancel)

		COMMAND_HANDLER(IDC_OPEN, BN_CLICKED, OnOpen)
		COMMAND_HANDLER(IDC_OCR, BN_CLICKED, OnOcr)
		COMMAND_HANDLER(IDC_CLEAR, BN_CLICKED, OnClear)
	END_MSG_MAP()

	void Log(LPCTSTR psz);
private:
	LRESULT OnInitDialog(UINT uMsg, WPARAM wParam, LPARAM lParam, BOOL& bHandled);
	LRESULT OnDestroy(UINT uMsg, WPARAM wParam, LPARAM lParam, BOOL& bHandled);
	LRESULT OnCancel(WORD wNotifyCode, WORD wID, HWND hWndCtl, BOOL& bHandled);
	LRESULT OnOpen(WORD wNotifyCode, WORD wID, HWND hWndCtl, BOOL& bHandled);
	LRESULT OnOcr(WORD wNotifyCode, WORD wID, HWND hWndCtl, BOOL& bHandled);
	LRESULT OnClear(WORD wNotifyCode, WORD wID, HWND hWndCtl, BOOL& bHandled);
};

extern CMainDlg* g_pMainWin;

#endif // !defined(MAINDLG__INCLUDED_)
