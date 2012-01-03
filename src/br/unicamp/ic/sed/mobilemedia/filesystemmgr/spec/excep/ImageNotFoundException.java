package br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep;

public class ImageNotFoundException extends Exception {
	
	private Throwable cause;
	
	public ImageNotFoundException() {
	}

	public ImageNotFoundException(String arg0) {
		super(arg0);
	}
	
	public ImageNotFoundException(Throwable arg0) {
		cause = arg0;
	}
	
	public Throwable getCause(){
		return cause;
	}
}
