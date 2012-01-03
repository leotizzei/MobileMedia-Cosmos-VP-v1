package br.unicamp.ic.sed.mobilemedia.main.spec.excep;

public class VariabilityException extends RuntimeException {

	public VariabilityException( String variant ){
		super( "Variant optional not bound: " + variant );
	}
	
}
