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

// 要创建一个jstring，可以用如下方式： 
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

// 将jstring对象转换为const wchar_t*
LPCWSTR JStringToWChar(JNIEnv* env, jstring jstr)
{
	int length = env->GetStringLength(jstr);
	const jchar* jcstr = env->GetStringChars(jstr, 0);
	return jcstr;
}

// 将jstring对象转换为const char*
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

// 将const wchar_t*转换为jstring对象
jstring WCharToJString(JNIEnv* env, LPCWSTR str)
{
	jstring rtn = 0;
	int slen = wcslen(str);
	wchar_t* buffer = 0;
	rtn = env->NewString(str, slen); 
	return rtn;
}

// 将const char*转换为jstring对象
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
JNI类型映射
Java类型		本地类型			描述 
boolean			jboolean			C/C++无符号8位整型
byte			jbyte				C/C++无符号8位整型
char			jchar				C/C++无符号16位整型
short			jshort				C/C++带符号16位整型
int				jint				C/C++带符号32位整型
long			jlong				C/C++带符号64位整型 
float			jfloat				C/C++32位浮点型
double			jdouble				C/C++64位浮点型
void			void				N/A
Object			jobject				任何Java对象，或者没有对应java类型的对象
Class			jclass				Class对象
String			jstring				字符串对象
Object[]		jobjectArray		任何对象的数组
boolean[]		jbooleanArray		布尔型数组
byte[]			jbyteArray			比特型数组
char[]			jcharArray			字符型数组
short[]			jshortArray			短整型数组
int[]			jintArray			整型数组
long[]			jlongArray			长整型数组
float[]			jfloatArray			浮点型数组
double[]		jdoubleArray		双浮点型数组

为了使用方便，特提供以下定义。
#define JNI_FALSE  0
#define JNI_TRUE   1

jsize 整数类型用于描述主要指数和大小:
typedef jint jsize;

使用数组:
函数						Java数组类型	本地类型
GetBooleanArrayElements		jbooleanArray	jboolean
GetByteArrayElements		jbyteArray		jbyte
GetCharArrayElements		jcharArray		jchar
GetShortArrayElements		jshortArray		jshort
GetIntArrayElements			jintArray		jint
GetLongArrayElements		jlongArray		jlong
GetFloatArrayElements		jfloatArray		jfloat
GetDoubleArrayElements		jdoubleArray	jdouble

使用对象:
函数				描述
GetFieldID			得到一个实例的域的ID
GetStaticFieldID	得到一个静态的域的ID
GetMethodID			得到一个实例的方法的ID
GetStaticMethodID	得到一个静态方法的ID

Java类型			符号
boolean				Z
byte				B
char				C
short				S
int					I
long				J
float				F
double				D
void				V
objects对象			Lfully-qualified-class-name;L类名
Arrays数组			[array-type [数组类型
methods方法			(argument-types)return-type(参数类型)返回类型

例如，Java 方法：long f (int n, String s, int[] arr);
具有以下类型签名：(ILjava/lang/String;[I)J
**/