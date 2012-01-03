package br.unicamp.ic.sed.mobilemedia.excep_mobilephone.impl;

public class ComponentFactory {

	private static IManager mgr = null;
	
	public static IManager createInstance(){
		if(mgr == null){
			mgr = (IManager) new Manager();
		}
		return mgr;
	}
	
}
