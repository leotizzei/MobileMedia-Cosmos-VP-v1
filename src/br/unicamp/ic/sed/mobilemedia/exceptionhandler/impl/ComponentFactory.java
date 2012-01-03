package br.unicamp.ic.sed.mobilemedia.exceptionhandler.impl;

import br.unicamp.ic.sed.mobilemedia.exceptionhandler.spec.prov.IManager;

public class ComponentFactory {

	private static IManager mgr = null;
	
	public static IManager createInstance(){
		if(mgr == null){
			mgr = (IManager) new Manager();
		}
		return mgr;
	}
	
}
