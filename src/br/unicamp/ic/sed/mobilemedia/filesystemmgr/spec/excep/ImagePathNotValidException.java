package br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep;

public class ImagePathNotValidException extends InvalidImageDataException {

	private Throwable cause;
	
	public ImagePathNotValidException() {
	}

	public ImagePathNotValidException(String arg0) {
		super(arg0);
	}

	public ImagePathNotValidException(Throwable arg0) {
		cause = arg0;
	}
	
	public Throwable getCause(){
		return cause;
	}
}
