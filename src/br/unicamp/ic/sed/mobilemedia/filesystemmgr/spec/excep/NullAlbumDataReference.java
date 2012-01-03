package br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep;

public class NullAlbumDataReference extends Exception {

	private Throwable cause;

	public NullAlbumDataReference() {
	}

	public NullAlbumDataReference(String arg0) {
		super(arg0);
	}

	public NullAlbumDataReference(Throwable arg0) {
		cause = arg0;
	}
	
	public Throwable getCause(){
		return cause;
	}
}
