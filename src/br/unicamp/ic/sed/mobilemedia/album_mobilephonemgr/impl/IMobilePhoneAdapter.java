   
package br.unicamp.ic.sed.mobilemedia.album_mobilephonemgr.impl;

import javax.microedition.lcdui.Command;

import br.unicamp.ic.sed.mobilemedia.album.spec.req.IMobilePhone;


class IMobilePhoneAdapter implements IMobilePhone{
	
	public void postCommand ( Command command ){
		br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.prov.IMobilePhone mobilePhone;
		
		IManager manager = ComponentFactory.createInstance();
		
		mobilePhone = (br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.prov.IMobilePhone)manager.getRequiredInterface("IMobilePhone");
		mobilePhone.postCommand(command);
	} 

}