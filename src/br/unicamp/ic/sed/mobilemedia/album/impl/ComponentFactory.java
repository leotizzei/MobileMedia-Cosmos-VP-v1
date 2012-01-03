package br.unicamp.ic.sed.mobilemedia.album.impl;

import br.unicamp.ic.sed.mobilemedia.album.spec.prov.IManager;

public class ComponentFactory {

	private static IManager manager = null;
	
	public static IManager createInstance(){
	
		if (manager==null)
			manager = new Manager( );
		
		return manager;
	}
}



