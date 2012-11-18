#include "bc_utils_filesystem_FileUtils.h"

#include <Windows.h>

JNIEXPORT void JNICALL Java_bc_utils_filesystem_FileUtils_deleteToRecycleBin
	(JNIEnv * env, jclass clazz, jstring fileName)
{
	LPCTSTR src;
	SHFILEOPSTRUCT opFile;

	src = (LPTSTR)env->GetStringUTFChars (fileName, NULL);

	HANDLE file = CreateFile (src, GENERIC_READ, FILE_SHARE_READ, NULL, 
		OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, NULL);

	// Checks if file exists
	if (file == INVALID_HANDLE_VALUE)
	{
		env->ReleaseStringUTFChars (fileName, src);
		env->ThrowNew (env->FindClass ("java/io/IOException"),
			"Error while opening file.");
		return;
	}

	CloseHandle (file);


	int len = strlen(src)+2;
	char* csrc = new char[len];
	strcpy(csrc, src);
	csrc[len-1] = csrc[len-2] = 0;

	ZeroMemory(&opFile, sizeof(opFile));
	opFile.wFunc = FO_DELETE;
	opFile.pFrom = csrc;
	opFile.fFlags = FOF_ALLOWUNDO | FOF_NOCONFIRMATION | FOF_SILENT;

	if (SHFileOperation (&opFile))
	{
		env->ReleaseStringUTFChars (fileName, src);
		delete[] csrc;
		env->ThrowNew (env->FindClass ("java/io/IOException"),
			"Error while opening file.");
	}

	env->ReleaseStringUTFChars (fileName, src);
	delete[] csrc;
}