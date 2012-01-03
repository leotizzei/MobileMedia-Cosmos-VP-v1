/*
 * Created on Sep 28, 2004
 */
package br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl;

import java.util.Hashtable;

import javax.microedition.lcdui.Image;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidPhotoAlbumNameException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.UnavailablePhotoAlbumException;
import br.unicamp.ic.sed.mobilemedia.main.spec.dt.IImageData;

/**
 * @author tyoung
 * 
 * This class represents the data model for Photo Albums. A Photo Album object
 * is essentially a list of photos or images, stored in a Hashtable. Due to
 * constraints of the J2ME RecordStore implementation, the class stores a table
 * of the images, indexed by an identifier, and a second table of image metadata
 * (ie. labels, album name etc.)
 * 
 * This uses the ImageAccessor class to retrieve the image data from the
 * recordstore (and eventually file system etc.)
 */
class AlbumData{

	protected ImageAccessor imageAccessor;

	//imageInfo holds image metadata like label, album name and 'foreign key' index to
	// corresponding RMS entry that stores the actual Image object
	protected Hashtable imageInfoTable = new Hashtable();

	public boolean existingRecords = false; //If no records exist, try to reset

	/**
	 *  Constructor. Creates a new instance of ImageAccessor
	 */
	public AlbumData() {
		imageAccessor = new ImageAccessor();
	}

	/* (non-Javadoc)
	 * @see br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.IAlbumData#getAlbumNames()
	 */
	public String[] getAlbumNames(){

		//Shouldn't load all the albums each time
		//Add a check somewhere in ImageAccessor to see if they've been
		//loaded into memory already, and avoid the extra work...

		imageAccessor.loadAlbums();

		return imageAccessor.getAlbumNames();
	}

	/* (non-Javadoc)
	 * @see br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.IAlbumData#createNewPhotoAlbum(java.lang.String)
	 */
	public void createNewPhotoAlbum(String albumName) throws PersistenceMechanismException, InvalidPhotoAlbumNameException {
		imageAccessor.createNewPhotoAlbum(albumName);
	}

	/* (non-Javadoc)
	 * @see br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.IAlbumData#deletePhotoAlbum(java.lang.String)
	 */
	public void deletePhotoAlbum(String albumName) throws PersistenceMechanismException{
		imageAccessor.deletePhotoAlbum(albumName);
	}

	/**
	 *  Get the names of all images for a given Photo Album that exist in the Record Store.
	 * @throws InvalidImageDataException 
	 * @throws PersistenceMechanismException 
	 *  
	 */
	public String[] getImageNames(String recordName) throws UnavailablePhotoAlbumException  {
		
		IImageData[] result;
		result = imageAccessor.loadImageDataFromRMS(recordName);
			
		String[] imageNames = new String[ result.length ];
		for( int i=0 ; i<imageNames.length ; i++ )
			imageNames[ i ] = result[ i ].getImageLabel();
		
		return imageNames;

	}
	
	
	/* (non-Javadoc)
	 * @see br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.IAlbumData#getImageFromRecordStore(java.lang.String, java.lang.String)
	 */
	public Image getImageFromRecordStore(String recordStore, String imageName) throws ImageNotFoundException, PersistenceMechanismException {
		
		try{
			IImageData imageInfo = null;
			imageInfo = imageAccessor.getImageInfo(imageName);
		
			//Find the record ID and store name of the image to retrieve
			int imageId = imageInfo.getForeignRecordId();
			String album = imageInfo.getParentAlbumName();
			//Now, load the image (on demand) from RMS and cache it in the hashtable
			
			Image imageRec = imageAccessor.loadSingleImageFromRMS(album, imageName, imageId); //rs.getRecord(recordId);
			
			return imageRec;
		}catch( Exception e ){
			e.printStackTrace();
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.IAlbumData#addNewPhotoToAlbum(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void addNewPhotoToAlbum(String label, String path, String album) throws InvalidImageDataException, PersistenceMechanismException{
		imageAccessor.addImageData(label, path, album);
	}

	/* (non-Javadoc)
	 * @see br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.IAlbumData#deleteImage(java.lang.String, java.lang.String)
	 */
	public void deleteImage(String imageName, String storeName) throws PersistenceMechanismException, ImageNotFoundException {
		imageAccessor.deleteSingleImageFromRMS(storeName, imageName);
		 
	}

	/* (non-Javadoc)
	 * @see br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.IAlbumData#resetImageData()
	 */
	public void resetImageData() throws PersistenceMechanismException{
		
		imageAccessor.resetImageRecordStore();
		
	}

	/* (non-Javadoc)
	 * @see br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.IAlbumData#getImageInfoTable()
	 */
	public Hashtable getImageInfoTable() {
		return imageInfoTable;
	}

	/* (non-Javadoc)
	 * @see br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.IAlbumData#setImageInfoTable(java.util.Hashtable)
	 */
	public void setImageInfoTable(Hashtable imageInfoTable) {
		this.imageInfoTable = imageInfoTable;
	}
	
	/*
	 * Method added to MobileMedia-Cosmos-AO-v2
	 * */	
	protected ImageAccessor getImageAccessor() {
		return imageAccessor;
	}

	/*
	 * Method added to MobileMedia-Cosmos-AO-v2
	 * */
	protected void setImageAccessor(ImageAccessor imageAccessor) {
		this.imageAccessor = imageAccessor;
	}
	
}