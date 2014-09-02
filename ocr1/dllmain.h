#ifndef _DLLMAIN_H
#define _DLLMAIN_H

#ifdef	_EXPORTDLL
#define _LIBAPI __declspec(dllexport)
#else
#define _LIBAPI __declspec(dllimport)
#endif

#endif