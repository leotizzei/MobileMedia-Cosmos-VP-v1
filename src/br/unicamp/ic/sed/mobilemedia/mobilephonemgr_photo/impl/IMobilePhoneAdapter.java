   
package br.unicamp.ic.sed.mobilemedia.mobilephonemgr_photo.impl;

import javax.microedition.lcdui.Command;


import br.unicamp.ic.sed.mobilemedia.photo.spec.req.IMobilePhone;

class IMobilePhoneAdapter implements IMobilePhone{
	
	public void postCommand ( Command command ) {
		//debugging
		System.out.println("entering IMobilePhoneAdapter::postCommand("+command.getLabel());
		
		br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.prov.IMobilePhone mobilePhone;
		
		IManager manager = ComponentFactory.createInstance();
		mobilePhone = (br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.prov.IMobilePhone)manager.getRequiredInterface("IMobilePhone");
		
		
		mobilePhone.postCommand(command);
	} 

}