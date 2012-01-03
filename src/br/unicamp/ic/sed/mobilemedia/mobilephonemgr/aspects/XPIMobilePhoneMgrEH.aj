package br.unicamp.ic.sed.mobilemedia.mobilephonemgr.aspects;

import javax.microedition.lcdui.Command;

import br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.excep.UnavailablePhotoAlbumException;
import br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.req.IFilesystem;

public aspect XPIMobilePhoneMgrEH {

	/*****************************************************************************************************/
	//========= BaseController ============
	
	public pointcut handleCommand():(call(public void IFilesystem.deletePhotoAlbum(String))) 
	&& (withincode(public boolean br.unicamp.ic.sed.mobilemedia.mobilephonemgr.impl.BaseController.handleCommand(Command)));
	
	declare soft: PersistenceMechanismException : handleCommand();
	
	//===
	
	public pointcut resetImageData(): 
		 execution(private void br.unicamp.ic.sed.mobilemedia.mobilephonemgr.impl.BaseController.resetImageData());
	
	declare soft: PersistenceMechanismException :  execution(private void br.unicamp.ic.sed.mobilemedia.mobilephonemgr.impl.BaseController.resetImageData());
	
	
	public pointcut showImageList2() :  execution(public void br.unicamp.ic.sed.mobilemedia.mobilephonemgr.impl.BaseController.showImageList(String)); 
	
	declare soft: UnavailablePhotoAlbumException : showImageList2();
	 
	//=======
	public pointcut showImage(): 
		 execution(public void  br.unicamp.ic.sed.mobilemedia.mobilephonemgr.impl.BaseController.showImage());
	
	declare soft: PersistenceMechanismException : execution(public void  br.unicamp.ic.sed.mobilemedia.mobilephonemgr.impl.BaseController.showImage());
	declare soft: ImageNotFoundException : execution(public void  br.unicamp.ic.sed.mobilemedia.mobilephonemgr.impl.BaseController.showImage());
	
	//======= Original cannot aspectize
	/*public pointcut createNewPhotoAlbum(): call(public void IFilesystem.createNewPhotoAlbum(String))
	&& (withincode(public boolean br.unicamp.ic.sed.mobilemedia.mobilephonemgr.impl.BaseController.handleCommand(Command)));
	
	declare soft : PersistenceMechanismException : createNewPhotoAlbum();
	declare soft : InvalidPhotoAlbumNameException : createNewPhotoAlbum();
*/
}
