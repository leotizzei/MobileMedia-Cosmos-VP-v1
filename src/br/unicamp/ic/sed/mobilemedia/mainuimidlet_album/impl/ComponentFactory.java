package br.unicamp.ic.sed.mobilemedia.mainuimidlet_album.impl;


import br.unicamp.ic.sed.mobilemedia.mainuimidlet_album.impl.IManager;

public class ComponentFactory {

	private static IManager manager = null;

	public static IManager createInstance(){
	
		if (manager==null)
			manager = new Manager();
		
		return manager;
	}
}



