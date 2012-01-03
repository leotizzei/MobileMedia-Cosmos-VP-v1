   
package br.unicamp.ic.sed.mobilemedia.mobilephonemgr.impl;

import javax.microedition.lcdui.Command;

import br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.prov.IMobilePhone;

class IMobilePhoneMgrFacade implements IMobilePhone{

	private BaseController controller;
	
	public IMobilePhoneMgrFacade() {
		controller = new BaseController();
		
	}
	
	public void postCommand ( Command command ) /*throws PersistenceMechanismException*/{
		System.out.println("IMobilePhoneMgrFacade::postCommand");
		controller.postCommand( command );
	}



	public void startUp() {
		BaseController controller = new BaseController();
		//controller.setManager(manager);
		controller.init();
		System.out.println("End of IMobilePhoneMgrFacade::startUp");
		
	}



	

}