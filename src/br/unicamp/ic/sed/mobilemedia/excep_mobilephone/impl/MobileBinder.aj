package br.unicamp.ic.sed.mobilemedia.excep_mobilephone.impl;

import br.unicamp.ic.sed.mobilemedia.mobilephonemgr.aspects.XPIMobilePhoneMgrEH;

public aspect MobileBinder extends ExceptionAdapter{
	
	public pointcut handleException(): XPIMobilePhoneMgrEH.handleCommand()
										|| XPIMobilePhoneMgrEH.resetImageData()
										|| XPIMobilePhoneMgrEH.showImageList2()
										|| XPIMobilePhoneMgrEH.showImage();
	
}
