package br.unicamp.ic.sed.mobilemedia.mainuimidlet_exceptionhandler.impl;



public class ComponentFactory {

	private static IManager manager = null;

	public static IManager createInstance(){
	
		if (manager==null)
			manager = (IManager) new Manager();
		
		return manager;
	}
}



