/*
 * Created on Sep 28, 2004
 *
 */
package br.unicamp.ic.sed.mobilemedia.mobilephonemgr.impl;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.rms.RecordStoreFullException;
import br.unicamp.ic.sed.mobilemedia.main.spec.dt.IImageData;

import br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.req.IAlbum;
import br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.req.IFilesystem;
import br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.req.IPhoto;


/**
 * @author tyoung
 *
 * This is the base controller class used in the MVC architecture.
 * It controls the flow of screens for the MobileMedia application.
 * Commands handled by this class should only be for the core application
 * that runs on any MIDP platform. Each device or class of devices that supports
 * optional features will extend this class to handle feature specific commands.
 * 
 */
class BaseController implements CommandListener{

	//Define a successor to implement the Chain of Responsibility design pattern
	private BaseController nextController;



	//Keep track of the navigation so we can go 'back' easily
	private String currentScreenName;


	//Keep track of the current photo album being viewed
	//Ie. name of the currently active J2ME record store
	private String currentStoreName = "My Photo Album";

	//[cosmos][add Sce 2. Edit Label]
	private String selectedImageName;

	/**
	 * Initialize the controller
	 */
	public void init() {
		System.out.println("BaseController.init()");
		IManager manager = ComponentFactory.createInstance();

		//Get all MobileMedia defined albums from the record store
		IFilesystem fileSystem = (IFilesystem)manager.getRequiredInterface("IFilesystem");
		
		
		String[] albumNames = fileSystem.getAlbumNames();

		

		//initialize all the screens
		IAlbum album = (IAlbum)manager.getRequiredInterface("IAlbum");
		album.initAlbumListScreen(albumNames);


		//Set the current screen to the photo album listing
		currentScreenName = Constants.ALBUMLIST_SCREEN;
	}
	
	private String getStoreName(){
		
		IManager manager = ComponentFactory.createInstance();
		IAlbum album = (IAlbum)manager.getRequiredInterface("IAlbum");
		this.currentStoreName  = album.getSelectedAlbum();
		
		return this.currentStoreName;
	}
	
	public boolean handleCommand(Command c){
		System.out.println("ENTROU---------HANDLECOMMAND: " + c.getLabel() );
		
		IManager manager = ComponentFactory.createInstance();


		IFilesystem fileSystem = (IFilesystem)manager.getRequiredInterface("IFilesystem");
		IAlbum album = (IAlbum)manager.getRequiredInterface("IAlbum");

		IPhoto photo = (IPhoto)manager.getRequiredInterface("IPhoto");
		//IExceptionsHandlerCtr excepHandler = (IExceptionsHandlerCtr)manager.getRequiredInterface("IExceptionsHandler");

		//Can this controller handle the desired action?
		//If yes, handleCommand will return true, and we're done
		//If no, handleCommand will return false, and postCommand
		//will pass the request to the next controller in the chain if one exists.

		String label = c.getLabel();
		
		String imageName = this.getSelectedPhoto(); 
		this.currentStoreName = this.getStoreName();

		/** Case: Exit Application **/
		if (label.equals("Exit")) {
			//TODO: Funcionalidade de destruir aplicação
			//midlet.destroyApp(true);
			return true;
			/** Case: Reset PhotoAlbum data **/
		} else if (label.equals("Reset")) {
			resetImageData();
			currentScreenName = Constants.ALBUMLIST_SCREEN;
			return true;
		}else if (label.equals("New Photo Album")) {
			System.out.println("Create new Photo Album here");			
			currentScreenName = Constants.NEWALBUM_SCREEN;
			album.initNewAlbumScreen();
			return true;
			/** Case: Delete Album Photo**/
		}else if (label.equals("Delete Album")) {
			System.out.println("Delete Photo Album here");
			currentScreenName = Constants.CONFIRMDELETEALBUM_SCREEN;
			currentStoreName = album.getSelectedAlbum( );
			album.initDeleteAlbumScreen( currentStoreName );
			return true;	
			/** Case: Yes delete Photo Album  **/
		}else if (label.equals("Yes - Delete")) {
			fileSystem.deletePhotoAlbum(currentStoreName);
			goToPreviousScreen();
			return true;	
			/** Case: Save new Photo Album  **/	
			/** Case: No delete Photo Album  **/
		}else if (label.equals("No - Delete")) {
			goToPreviousScreen();
			return true;	
			/** Case: Save new Photo Album  **/
		} else if (label.equals("Save")) {
			fileSystem.createNewPhotoAlbum( album.getNewAlbumName() );
			
			goToPreviousScreen();
			return true;
			
		/** Case: Select PhotoAlbum to view **/
		} else if (label.equals("Select")) {
			
			//Get the name of the PhotoAlbum selected, and load image list from RecordStore
			currentStoreName = album.getSelectedAlbum();
			
			System.out.println("ENTROU---SELECT: " + currentStoreName );
			     
			showImageList(currentStoreName);
			currentScreenName = Constants.IMAGELIST_SCREEN;
			return true;
			/** Case: View Image **/
		} else if (label.equals("View")) {
			showImage();
			currentScreenName = Constants.IMAGE_SCREEN;
			return true;

			/** Case: Add photo  **/
		} else if (label.equals("Add")) {
			currentScreenName = Constants.ADDPHOTOTOALBUM_SCREEN;
			photo.initAddPhotoToAlbum( currentStoreName );
			return true;
			/** Case: Save Add photo  **/
		} else if (label.equals("Save Add Photo")) {
			fileSystem.addNewPhotoToAlbum(photo.getAddedPhotoName(), photo.getAddedPhotoPath() , currentStoreName );
			
			goToPreviousScreen();
			return true;
		/** Case: Delete selected Photo from recordstore **/
		} else if (label.equals("Delete")) {
			String selectedImageName = getSelectedPhoto();
			
			showImageList(currentStoreName);
			currentScreenName = Constants.IMAGELIST_SCREEN;
			return true;

			/** Case: Go to the Previous or Fallback screen **/
		} else if (label.equals("Back")) {

			goToPreviousScreen();
			return true;

			/** Case: Cancel the current screen and go back one**/
		} else if (label.equals("Cancel")) {

			goToPreviousScreen();
			return true;

		}
			
		//If we couldn't handle the current command, return false
		return false;
	}

	protected void postCommand(Command c){
		//If the current controller cannot handle the command, pass it to the next
		//controller in the chain.
		if (handleCommand(c) == false) {
			BaseController next = getNextController();
			if (next != null) {
				System.out.println("Passing to next controller in chain: " + next.getClass().getName());
				next.postCommand(c);
			} else {
				System.out.println("BaseController::postCommand - Reached top of chain. No more handlers for command: " + c.getLabel());
			}
		}

	}

	/**
	 * Handle events. For now, this just passes control off to a 'wrapper'
	 * so we can ensure , in order to use it in the aspect advice
	 * @throws PersistenceMechanismException 
	 */
	public void commandAction(Command c, Displayable d)  {
		postCommand(c);
	}




	/**
	 * This option is mainly for testing purposes. If the record store
	 * on the device or emulator gets into an unstable state, or has too 
	 * many images, you can reset it, which clears the record stores and
	 * re-creates them with the default images bundled with the application 
	 */
	private void resetImageData() {
		System.out.println("Entering resetImageData...");
		
		
		//Get all required interfaces for this method
		IManager manager = ComponentFactory.createInstance();
		IFilesystem fileSystem = (IFilesystem)manager.getRequiredInterface("IFilesystem");
		IAlbum album = (IAlbum)manager.getRequiredInterface("IAlbum");

		fileSystem.resetImageData();
		
		String[] albumNames = fileSystem.getAlbumNames();
		album.initAlbumListScreen(albumNames);
	}
	
    /**
	 * Go to the previous screen
	 * TODO: Re-implement. Not working properly yet.
	 */
	private void goToPreviousScreen() {

		System.out.println("\n\n========" + this );

		//Get all required interfaces for this method
		IManager manager = ComponentFactory.createInstance();
		IFilesystem fileSystem = (IFilesystem)manager.getRequiredInterface("IFilesystem");
		IAlbum album = (IAlbum)manager.getRequiredInterface("IAlbum");
		
		if (currentScreenName.equals(Constants.ALBUMLIST_SCREEN)) {
			System.out.println("Can't go back here...Should never reach this spot");
		} else if (currentScreenName.equals(Constants.IMAGE_SCREEN)) {		    
			//Go to the image list here, not the main screen...
			showImageList(currentStoreName);
			currentScreenName = Constants.IMAGELIST_SCREEN;
		} else if (currentScreenName.equals(Constants.IMAGELIST_SCREEN)) {
			album.initAlbumListScreen( fileSystem.getAlbumNames() );
			currentScreenName = Constants.ALBUMLIST_SCREEN;
		}else if (currentScreenName.equals(Constants.NEWALBUM_SCREEN)) {
			album.initAlbumListScreen( fileSystem.getAlbumNames() );
			currentScreenName = Constants.ALBUMLIST_SCREEN;
		}else if (currentScreenName.equals(Constants.CONFIRMDELETEALBUM_SCREEN)) {
			album.initAlbumListScreen( fileSystem.getAlbumNames() );
			currentScreenName = Constants.ALBUMLIST_SCREEN;
		}else if (currentScreenName.equals(Constants.ADDPHOTOTOALBUM_SCREEN)) {
			showImageList(currentStoreName);
			currentScreenName = Constants.IMAGELIST_SCREEN;
		//[cosmos] [add][Edit Label] 
		}else if(currentScreenName.equals(Constants.NEWLABEL_SCREEN)){
			showImageList(currentStoreName);
			currentScreenName = Constants.IMAGELIST_SCREEN;
		}


	} 

	/**
	 * Show the current image that is selected
	 */
	public void showImage() {
		//Get all required interfaces for this method
		IManager manager = ComponentFactory.createInstance();
		IFilesystem fileSystem = (IFilesystem)manager.getRequiredInterface("IFilesystem");
		IPhoto photo = (IPhoto)manager.getRequiredInterface("IPhoto");

		String name = photo.getSelectedPhoto();

		Image storedImage = null;
		storedImage = fileSystem.getImageFromRecordStore(currentStoreName, name);

		//We can pass in the image directly here, or just the name/model pair and have it loaded
		photo.initPhotoViewScreen( storedImage );
	}

	/**
	 * Show the list of images in the record store
	 * TODO: Refactor - Move this to ImageList class
	 */
	public void showImageList(String recordName) {

		System.out.println( "ENTROU-----SHOWIMAGELIST");
		try{
		//Get all required interfaces for this method
		IManager manager = ComponentFactory.createInstance();
		IFilesystem fileSystem = (IFilesystem)manager.getRequiredInterface("IFilesystem");
		IPhoto photo = (IPhoto)manager.getRequiredInterface("IPhoto");

		if (recordName == null)
			recordName = currentStoreName;
				
		String[] labels = fileSystem.getImageNames(recordName);
		
				
		photo.initPhotoListScreen( labels );
		}catch(Exception e ){
			e.printStackTrace();
		}
	}

	/**
	 * Get the last selected image from the Photo List screen.
	 * TODO: This really only gets the selected List item. 
	 * So it is only an image name if you are on the PhotoList screen...
	 * Need to fix this
	 */
	public String getSelectedPhoto() {
		IManager manager = ComponentFactory.createInstance();
		IPhoto photo = (IPhoto)manager.getRequiredInterface("IPhoto");

		return photo.getSelectedPhoto();

	}

	/**
	 * @return Returns the currentStoreName.
	 */
	public String getCurrentStoreName() {
		return currentStoreName;
	}

	/**
	 * @param currentScreenName The currentScreenName to set.
	 */
	//public void setCurrentScreenName(String currentScreenName) {
	//	this.currentScreenName = currentScreenName;
	//}

	/**
	 * @return
	 */
	public BaseController getNextController() {
		return nextController;
	}

	/**
	 * @param nextController
	 */
	public void setNextController(BaseController nextController) {
		this.nextController = nextController;
	}
}
