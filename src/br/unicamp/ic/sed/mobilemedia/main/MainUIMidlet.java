package br.unicamp.ic.sed.mobilemedia.main;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.prov.IMobilePhone;
import br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.req.IAlbum;


//Following are pre-processor statements to include the required
//classes for device specific features. They must be commented out
//if they aren't used, otherwise it will throw exceptions trying to
//load classes that aren't available for a given platform.


/* 
 * 
 *
 * This is the main Midlet class for the core J2ME application
 * It contains all the basic functionality that should be executable
 * in any standard J2ME device that supports MIDP 1.0 or higher. 
 * Any additional J2ME features for this application that are dependent
 * upon a particular device (ie. optional or proprietary library) are
 * de-coupled from the core application so they can be conditionally included
 * depending on the target platform 
 * 
 * This Application provides a basic Photo Album interface that allows a user to view
 * images on their mobile device. 
 * */
public class MainUIMidlet extends MIDlet implements IMobileResources {

	//components
	br.unicamp.ic.sed.mobilemedia.album.spec.prov.IManager album;
	br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IManager filesystem;
	br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IManager photo;
	br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.prov.IManager mobilePhone;
	br.unicamp.ic.sed.mobilemedia.exceptionhandler.spec.prov.IManager exceptionHandler;
	
	
	

	//connectors
	br.unicamp.ic.sed.mobilemedia.album_mobilephonemgr.impl.IManager album_mobilePhone;
	br.unicamp.ic.sed.mobilemedia.filesystemmgr_mobilephonemgr.impl.IManager filesystem_mobilePhone;
	br.unicamp.ic.sed.mobilemedia.mobilephonemgr_photo.impl.IManager mobilephonemgr_photo;
	br.unicamp.ic.sed.mobilemedia.mainuimidlet_album.impl.IManager mainMidlet_album;
	//br.unicamp.ic.sed.mobilemedia.mainuimidlet_mobilephonemgr.impl.IManager mainMidlet_mobilePhone;
	br.unicamp.ic.sed.mobilemedia.mainuimidlet_exceptionhandler.impl.IManager mainMidlet_exceptionHandler;
	br.unicamp.ic.sed.mobilemedia.mainuimidlet_photo.impl.IManager mainMidlet_photo;
	
	br.unicamp.ic.sed.mobilemedia.excep_filesystem.impl.IManager eh_mobile;
	
	public MainUIMidlet() {
		//do nothing
	}

	/**
	 * Start the MIDlet by creating new model and controller classes, and
	 * initialize them as necessary
	 */
	public void startApp() throws MIDletStateChangeException {
				
		// create all imanagers
		filesystem = br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.ComponentFactory.createInstance();

		album_mobilePhone = br.unicamp.ic.sed.mobilemedia.album_mobilephonemgr.impl.ComponentFactory.createInstance();

		mobilePhone = br.unicamp.ic.sed.mobilemedia.mobilephonemgr.impl.ComponentFactory.createInstance();

		album = br.unicamp.ic.sed.mobilemedia.album.impl.ComponentFactory.createInstance();

		exceptionHandler = br.unicamp.ic.sed.mobilemedia.exceptionhandler.impl.ComponentFactory.createInstance();
		
		filesystem_mobilePhone = br.unicamp.ic.sed.mobilemedia.filesystemmgr_mobilephonemgr.impl.ComponentFactory.createInstance();
		
		mainMidlet_album = br.unicamp.ic.sed.mobilemedia.mainuimidlet_album.impl.ComponentFactory.createInstance();
		
		photo = br.unicamp.ic.sed.mobilemedia.photo.impl.ComponentFactory.createInstance();
		
		mobilephonemgr_photo = br.unicamp.ic.sed.mobilemedia.mobilephonemgr_photo.impl.ComponentFactory.createInstance();
		
		//mainMidlet_mobilePhone = br.unicamp.ic.sed.mobilemedia.mainuimidlet_mobilephonemgr.impl.ComponentFactory.createInstance();
		mainMidlet_photo = br.unicamp.ic.sed.mobilemedia.mainuimidlet_photo.impl.ComponentFactory.createInstance();
		mainMidlet_exceptionHandler = br.unicamp.ic.sed.mobilemedia.mainuimidlet_exceptionhandler.impl.ComponentFactory.createInstance();
		
		
		eh_mobile = br.unicamp.ic.sed.mobilemedia.excep_filesystem.impl.ComponentFactory.createInstance();
		
		/*********************************************************************************************/
		//setting required interfaces
	
		eh_mobile.setRequiredInterface("IExceptionHandler", exceptionHandler.getProvidedInterface("IExceptionHandler"));
		
		
		//component mobilePhoneMgr
		mobilePhone.setRequiredInterface("IFilesystem", filesystem_mobilePhone.getProvidedInterface("IFilesystem"));
	
	//	mobilePhone.setRequiredInterface("IMobileResources", mainMidlet_mobilePhone.getProvidedInterface("IMobileResources"));
	
		br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.req.IAlbum ialbum = (IAlbum) album_mobilePhone.getProvidedInterface("IAlbum");
	
		mobilePhone.setRequiredInterface("IAlbum", ialbum );
	
		
		//component album
		
		album.setRequiredInterface("IMobilePhone", album_mobilePhone.getProvidedInterface("IMobilePhone"));

		album.setRequiredInterface("IMobileResources", mainMidlet_album.getProvidedInterface("IMobileResources") );

		//album.setRequiredInterface("IExceptionHandler", album_exceptionhandler.getProvidedInterface("IExceptionHandler"));
		
		
		// component photo
		photo.setRequiredInterface("IMobilePhone", mobilephonemgr_photo.getProvidedInterface("IMobilePhone"));
		photo.setRequiredInterface("IMobileResources", mainMidlet_photo.getProvidedInterface("IMobileResources"));
		
		//mainMidlet_mobilePhone.setRequiredInterface("IMobileResources", this);
		mainMidlet_photo.setRequiredInterface("IMobileResources", this);
		
		filesystem_mobilePhone.setRequiredInterface("IFilesystem", filesystem.getProvidedInterface("IFilesystem"));
	
		album_mobilePhone.setRequiredInterface("IAlbum", album.getProvidedInterface("IAlbum"));
		
		album_mobilePhone.setRequiredInterface("IMobilePhone", mobilePhone.getProvidedInterface("IMobilePhone"));
		
		mobilephonemgr_photo.setRequiredInterface("IPhoto", photo.getProvidedInterface("IPhoto"));
		
		mobilephonemgr_photo.setRequiredInterface("IMobilePhone", mobilePhone.getProvidedInterface("IMobilePhone"));

		mobilePhone.setRequiredInterface("IPhoto", mobilephonemgr_photo.getProvidedInterface("IPhoto"));
		
		mainMidlet_album.setRequiredInterface("IMobileResources", this );

		mainMidlet_exceptionHandler.setRequiredInterface("IMobileResources",  this );
		
		exceptionHandler.setRequiredInterface("IMobileResources", mainMidlet_exceptionHandler.getProvidedInterface("IMobileResources"));
					
		IMobilePhone mobPhone = (IMobilePhone) mobilePhone.getProvidedInterface("IMobilePhone");
		
		mobPhone.startUp();
	}

	/**
	 * Pause the MIDlet
	 * This method does nothing at the moment.
	 */
	public void pauseApp() {
		//do nothing
	}

	/**
	 * Destroy the MIDlet
	 */
	public void destroyApp(boolean unconditional) {
		notifyDestroyed();
	}

	public MIDlet getMainMIDlet() {
		return this;
	}
	
}