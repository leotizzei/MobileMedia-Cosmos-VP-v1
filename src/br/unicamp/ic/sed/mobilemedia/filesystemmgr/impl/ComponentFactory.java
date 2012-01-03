package br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IManager;

public class ComponentFactory {

	private static IManager manager = null;

	public static IManager createInstance(){
	
		if (manager==null)
			manager = new Manager();
		
		return manager;
	}
}



