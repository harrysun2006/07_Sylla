// MainDlg.cpp: implementation of the CMainDlg class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "MainDlg.h"
#include "ocr.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

CMainDlg* g_pMainWin = 0;

LRESULT CMainDlg::OnInitDialog(
	UINT uMsg, WPARAM wParam, LPARAM lParam, BOOL& bHandled)
{
	CenterWindow();
	bHandled = FALSE;
	return S_OK;
}

LRESULT CMainDlg::OnDestroy(
	UINT uMsg, WPARAM wParam, LPARAM lParam, BOOL& bHandled)
{
	PostQuitMessage(0);
	return S_OK;
}

LRESULT CMainDlg::OnCancel(WORD wNotifyCode, WORD wID, HWND hWndCtl, BOOL& bHandled)
{
	DestroyWindow();
	return S_OK;
}

/*
void CMainDlg::SinkIE(HWND hWnd)
{
	CString strDebug;
	CIESink* pSink = new CMyIESink();
	HRESULT hr = CSpyer::Instance()->AddIESink(hWnd, pSink);
	if (SUCCEEDED(hr))
	{
		strDebug.Format("IESink: Add a sink to window[%08X] successfully!\n", hWnd);
	}
	else
	{
		strDebug.Format("IESink: Can NOT add sink to window[%08X]!\n", hWnd);
	}
	Log(strDebug);
}

LRESULT CMainDlg::OnShell(UINT uMsg, WPARAM wParam, LPARAM lParam, BOOL& bHandled)
{
	CString strDebug, strTitle;
	CWnd* pwnd;
	if(::IsWindow(HWND(lParam)))
	{
		pwnd = CWnd::FromHandle(HWND(lParam));
		pwnd->GetWindowText(strTitle);
	}
	switch (wParam)
	{
	case HSHELL_WINDOWCREATED:
		SinkIE(HWND(lParam));
		strDebug.Format("ShellHook: Window[%08X] - %s created!\n", lParam, strTitle);
		break;
	case HSHELL_WINDOWDESTROYED:
		strDebug.Format("ShellHook: Window[%08X] - %s destroyed!\n", lParam, strTitle);
		break;
	case HSHELL_WINDOWACTIVATED:
		strDebug.Format("ShellHook: Window[%08X] - %s activated!\n", lParam, strTitle);
		break;
	default:
		strDebug.Format("%08X, %08X\n", wParam, lParam);
		break;
	}
	// Log(strDebug);
	return S_OK;
}
*/

LRESULT CMainDlg::OnOpen(WORD wNotifyCode, WORD wID, HWND hWndCtl, BOOL& bHandled)
{
	return S_OK;
}

LRESULT CMainDlg::OnOcr(WORD wNotifyCode, WORD wID, HWND hWndCtl, BOOL& bHandled)
{
	USES_CONVERSION;

	POCR_ITEMS pItems = ocr("E:\\Projects\\SZGSW\\Sylla\\tocr\\bi.jpg");
	POCR_ITEM pItem;
	CString strDebug;
	for (int i = 0; i < pItems->GetSize(); i++)
	{
		pItem = pItems->GetAt(i);
		strDebug.Format("%s\n", pItem->text);
		Log(strDebug);
	}
	return S_OK;
}

LRESULT CMainDlg::OnClear(WORD wNotifyCode, WORD wID, HWND hWndCtl, BOOL& bHandled)
{
	CWindow wndEdit = GetDlgItem(IDC_LOG);
	int len = wndEdit.GetWindowTextLength();
	wndEdit.SendMessage(EM_SETSEL, 0, len);
	wndEdit.SendMessage(EM_REPLACESEL, FALSE, NULL);
	return S_OK;
}

void CMainDlg::Log(LPCTSTR psz)
{
	//CString strDebug;
	//strDebug.Format("%s\n", psz);
	
	CWindow wndEdit = GetDlgItem(IDC_LOG);
	int len = wndEdit.GetWindowTextLength();
	wndEdit.SendMessage(EM_SETSEL, len, len);
	wndEdit.SendMessage(EM_REPLACESEL, FALSE, reinterpret_cast<LPARAM>(psz));
	//wndEdit.SendMessage(EM_REPLACESEL, FALSE, reinterpret_cast<LPARAM>(strDebug.GetBuffer(strDebug.GetLength())));
}
