package br.unicamp.ic.sed.mobilemedia.exceptionhandler.impl;

import br.unicamp.ic.sed.mobilemedia.exceptionhandler.spec.prov.IExceptionHandler;

public class IExceptionHandlerFacade implements IExceptionHandler {

	public void handleException( Exception e ) {
		Handler handler = new Handler();
		handler.handleException( e );
	}
	
}
