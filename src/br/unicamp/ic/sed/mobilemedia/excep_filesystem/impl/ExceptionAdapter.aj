package br.unicamp.ic.sed.mobilemedia.excep_filesystem.impl;

import br.unicamp.ic.sed.mobilemedia.exceptionhandler.spec.prov.IExceptionHandler;

public abstract aspect ExceptionAdapter {

	private IManager manager = (IManager) ComponentFactory.createInstance();
	
	public abstract pointcut handleException(  );
	
	Object around() : handleException(){
		try{
			return proceed();
		}catch( Exception e ){
			IExceptionHandler eh = (IExceptionHandler)manager.getRequiredInterface( "IExceptionHandler" );
			eh.handleException( e );
			return null;
		}
	}
	
}
