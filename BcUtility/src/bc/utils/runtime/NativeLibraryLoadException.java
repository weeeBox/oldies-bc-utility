package bc.utils.runtime;

public class NativeLibraryLoadException extends RuntimeException {
	private static final long serialVersionUID = 3411763998033192142L;
	
	public NativeLibraryLoadException(Throwable cause) 
	{
		super(cause);
	}
}
