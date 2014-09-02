#include <iostream>
#include "jni.h"
#include "ocr.h"

using namespace std;

/*
BOOL APIENTRY DllMain (HINSTANCE hInst, DWORD reason, LPVOID reserved)
{
	switch (reason)
	{
		case DLL_PROCESS_ATTACH:
			break;
			
		case DLL_PROCESS_DETACH:
			break;
			
		case DLL_THREAD_ATTACH:
			break;
			
		case DLL_THREAD_DETACH:
			break;
	}
	
	return TRUE;
}
*/

// Ҫ����һ��jstring�����������·�ʽ�� 
jstring NewJString(JNIEnv * env, LPCTSTR str)
{
	if (!env || !str) return 0;
	int slen = strlen(str);
	jchar * buffer = new jchar[slen];
	int len = MultiByteToWideChar(CP_ACP, 0, str, strlen(str), buffer, slen);
	if (len > 0 && len < slen) buffer[len] = 0;
	jstring js = env->NewString(buffer, len);
	delete [] buffer;
	return js;
}

// ��jstring����ת��Ϊconst wchar_t*
LPCWSTR JStringToWChar(JNIEnv* env, jstring jstr)
{
	int length = env->GetStringLength(jstr);
	const jchar* jcstr = env->GetStringChars(jstr, 0);
	return jcstr;
}

// ��jstring����ת��Ϊconst char*
LPCTSTR JStringToChar(JNIEnv* env, jstring jstr)
{
	int length = env->GetStringLength(jstr);
	const jchar* jcstr = env->GetStringChars(jstr, 0);
	char* rtn = (char*)malloc(length * 2 + 1);
	int size = 0;
	size = WideCharToMultiByte(CP_ACP, 0, (LPCWSTR)jcstr, length, rtn, (length * 2 + 1), NULL, NULL);
	if(size <= 0) return NULL;
	env->ReleaseStringChars(jstr, jcstr);
	rtn[size] = 0;
	return rtn;
} 

// ��const wchar_t*ת��Ϊjstring����
jstring WCharToJString(JNIEnv* env, LPCWSTR str)
{
	jstring rtn = 0;
	int slen = wcslen(str);
	wchar_t* buffer = 0;
	rtn = env->NewString(str, slen); 
	return rtn;
}

// ��const char*ת��Ϊjstring����
jstring CharToJString(JNIEnv* env, LPCTSTR str)
{
	jstring rtn = 0;
	int slen = strlen(str);
	wchar_t* buffer = 0;
	if( slen == 0 )
		rtn = env->NewStringUTF(str); //UTF ok since empty string
	else
	{
		int length = MultiByteToWideChar(CP_ACP, 0, (LPCSTR)str, slen, NULL, 0);
		buffer = (wchar_t*)malloc(length * 2 + 1 );
		if(MultiByteToWideChar(CP_ACP, 0, (LPCSTR)str, slen, (LPWSTR)buffer, length) > 0)
			rtn = env->NewString((jchar*)buffer, length);
	}
	if(buffer) free(buffer);
	return rtn;
}

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jobjectArray JNICALL Java_com_sylla_Ocr_winocr(JNIEnv *env, jobject obj, jstring name)
{
	const char * pszName = JStringToChar(env, name);
	POCR_ITEMS pItems = ocr(pszName);
	POCR_ITEM pItem;
	int count = pItems->GetSize();

  jobjectArray ocrImages = 0;
  jclass objClass = env->FindClass("java/lang/Object");
	ocrImages = env->NewObjectArray(count, objClass, 0);
	jclass clazz = env->FindClass("com/sylla/OcrImage");
	jmethodID text = env->GetMethodID(clazz, "setText", "(Ljava/lang/String;)V");
  jmethodID accurate = env->GetMethodID(clazz, "setAccurate", "(D)V");

  for(int i = 0; i < count; i++ )
  {
		pItem = pItems->GetAt(i);
		jobject ocrImage = env->AllocObject(clazz);
		env->CallVoidMethod(ocrImage, text, NewJString(env, pItem->text));
		env->CallVoidMethod(ocrImage, accurate, (jdouble) pItem->accurate);
		env->SetObjectArrayElement(ocrImages, i, ocrImage);
  }
  return ocrImages;
}

#ifdef __cplusplus
}
#endif

/**
JNI����ӳ��
Java����		��������			���� 
boolean			jboolean			C/C++�޷���8λ����
byte			jbyte				C/C++�޷���8λ����
char			jchar				C/C++�޷���16λ����
short			jshort				C/C++������16λ����
int				jint				C/C++������32λ����
long			jlong				C/C++������64λ���� 
float			jfloat				C/C++32λ������
double			jdouble				C/C++64λ������
void			void				N/A
Object			jobject				�κ�Java���󣬻���û�ж�Ӧjava���͵Ķ���
Class			jclass				Class����
String			jstring				�ַ�������
Object[]		jobjectArray		�κζ��������
boolean[]		jbooleanArray		����������
byte[]			jbyteArray			����������
char[]			jcharArray			�ַ�������
short[]			jshortArray			����������
int[]			jintArray			��������
long[]			jlongArray			����������
float[]			jfloatArray			����������
double[]		jdoubleArray		˫����������

Ϊ��ʹ�÷��㣬���ṩ���¶��塣
#define JNI_FALSE  0
#define JNI_TRUE   1

jsize ������������������Ҫָ���ʹ�С:
typedef jint jsize;

ʹ������:
����						Java��������	��������
GetBooleanArrayElements		jbooleanArray	jboolean
GetByteArrayElements		jbyteArray		jbyte
GetCharArrayElements		jcharArray		jchar
GetShortArrayElements		jshortArray		jshort
GetIntArrayElements			jintArray		jint
GetLongArrayElements		jlongArray		jlong
GetFloatArrayElements		jfloatArray		jfloat
GetDoubleArrayElements		jdoubleArray	jdouble

ʹ�ö���:
����				����
GetFieldID			�õ�һ��ʵ�������ID
GetStaticFieldID	�õ�һ����̬�����ID
GetMethodID			�õ�һ��ʵ���ķ�����ID
GetStaticMethodID	�õ�һ����̬������ID

Java����			����
boolean				Z
byte				B
char				C
short				S
int					I
long				J
float				F
double				D
void				V
objects����			Lfully-qualified-class-name;L����
Arrays����			[array-type [��������
methods����			(argument-types)return-type(��������)��������

���磬Java ������long f (int n, String s, int[] arr);
������������ǩ����(ILjava/lang/String;[I)J
**/