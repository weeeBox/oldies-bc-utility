package bc.utils.runtime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class NativeLibLoader 
{
	private static final String LIB_EXT = ".dll";

	public static void loadLibrary(String libName) 
	{
		try {
			System.loadLibrary(libName);
		} catch (UnsatisfiedLinkError e) {
			loadLibraryFromJar(libName);
		}
	}
	
	/**
	 * When packaged into JAR extracts DLLs, places these into
	 * @param libName 
	 */
	private static void loadLibraryFromJar(String libName) 
	{
		try 
		{
			InputStream fis = null;
			try
			{
				String libResName = "/" + libName + LIB_EXT;
				fis = NativeLibLoader.class.getResourceAsStream(libResName);

				String prefix = libName + System.currentTimeMillis();
				File tempFile = File.createTempFile(prefix, LIB_EXT);
				String tempPath = tempFile.getAbsolutePath();
				
				OutputStream fos = null;
				try 
				{
					fos = new FileOutputStream(tempFile);
					
					// copy file
					byte[] buffer = new byte[1024];
					int bytesRead;
					while ((bytesRead = fis.read(buffer)) != -1)
					{
						fos.write(buffer, 0, bytesRead);
					}
				} 
				finally 
				{
					if (fos != null)
					{
						fos.close();
					}
				}
				
				System.load(tempPath);
			} 
			finally 
			{
				if (fis != null)
				{
					fis.close();
				}
			}
		} 
		catch (UnsatisfiedLinkError e)
		{
			throw e;
		}
		catch (Exception e) 
		{
			throw new NativeLibraryLoadException(e);
		}
	}
}
