package br.unicamp.ic.sed.mobilemedia.mainuimidlet_exceptionhandler.impl;

import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.exceptionhandler.spec.req.IMobileResources;


public class MobileResourcesEHAdapter implements IMobileResources {

	public MIDlet getMainMIDlet() {
		IManager manager = (IManager) ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.main.IMobileResources iMobileResources = (br.unicamp.ic.sed.mobilemedia.main.IMobileResources)manager.getRequiredInterface("IMobileResources");
		return iMobileResources.getMainMIDlet();
	}

}
