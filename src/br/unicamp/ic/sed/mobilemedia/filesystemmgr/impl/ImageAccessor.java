/*
 * Created on Sep 13, 2004
 *
 */
package br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl;

import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.lcdui.Image;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.ComponentFactory;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.ImageData;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImagePathNotValidException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageFormatException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidPhotoAlbumNameException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.main.spec.dt.IImageData;

/**
 * @author trevor
 * 
 * This is the main data access class. It handles all the connectivity with the
 * RMS record stores to fetch and save data associated with MobileMedia TODO:
 * Refactor into stable interface for future updates. We may want to access data
 * from RMS, or eventually direct from the 'file system' on devices that support
 * the FileConnection optional API.
 * 
 */
class ImageAccessor {

	// Note: Our midlet only ever has access to Record Stores it created
	// For now, use naming convention to create record stores used by
	// MobileMedia
	protected String album_label = "mpa-"; // "mpa- all album names
	// are prefixed with
	// this label
	protected String info_label = "mpi-"; // "mpi- all album info
	// stores are prefixed with
	// this label
	protected String default_album_name = "My Photo Album"; // default
	// album
	// name

	protected String image_label = "ImageList"; // RecordStore name
	// prefixed

	protected String[] albumNames; // User defined names of photo albums

	protected Hashtable imageInfoTable = new Hashtable();

	// Record Stores
	protected RecordStore imageRS = null;
	protected RecordStore imageInfoRS = null;

	/*
	 * Constructor
	 */
	public ImageAccessor(String album_label, String info_label, String default_album_name) {
		this.album_label = album_label; 
		this.info_label = info_label; 
		this.default_album_name = default_album_name; 
	}
	
	public ImageAccessor(){}

	/**
	 * Load all existing photo albums that are defined in the record store.
	 * 

	 * @throws InvalidImageDataException
	 * @throws PersistenceMechanismException
	 */
	protected void loadAlbums() throws InvalidImageDataException,
	PersistenceMechanismException {

		// Try to find any existing Albums (record stores)

		String[] currentStores = RecordStore.listRecordStores();

		if (currentStores != null) {
			System.out.println("ImageAccessor::loadAlbums: Found: "	+ currentStores.length + " existing record stores");
			String[] temp = new String[currentStores.length];
			int count = 0;

			// Only use record stores that follow the naming convention defined
			for (int i = 0; i < currentStores.length; i++) {
				String curr = currentStores[i];

				// If this record store is a photo album...
				if (curr.startsWith(album_label)) {

					// Strip out the mpa- identifier
					curr = curr.substring(4);
					// Add the album name to the array
					temp[i] = curr;
					count++;
				}
			}

			// Re-copy the contents into a smaller array now that we know the
			// size
			albumNames = new String[count];
			int count2 = 0;
			for (int i = 0; i < temp.length; i++) {
				if (temp[i] != null) {
					albumNames[count2] = temp[i];
					count2++;
				}
			}
		} else {
			System.out
			.println("ImageAccessor::loadAlbums: 0 record stores exist. Creating default one.");
			resetImageRecordStore();
			loadAlbums();
		}
	}

	/**
	 * Reset the album data for MobileMedia. This will delete all existing photo
	 * data from the record store and re-create the default album and photos.
	 * 
	 * @throws InvalidImageFormatException
	 * @throws ImagePathNotValidException
	 * @throws InvalidImageDataException
	 * @throws PersistenceMechanismException
	 * 
	 */
	public void resetImageRecordStore() throws InvalidImageDataException,
	PersistenceMechanismException {

		String storeName = null;
		String infoStoreName = null;



		// remove any existing album stores...
		if (albumNames != null) {
			for (int i = 0; i < albumNames.length; i++) {
				try {
					// Delete all existing stores containing Image objects as
					// well as the associated ImageInfo objects
					// Add the prefixes labels to the info store

					storeName = album_label + albumNames[i];
					infoStoreName = info_label + albumNames[i];

					RecordStore.deleteRecordStore(storeName);
					RecordStore.deleteRecordStore(infoStoreName);

				} catch (RecordStoreException e) {
					System.out.println("No record store named " + storeName
							+ " to delete.");
					System.out.println("...or...No record store named "
							+ infoStoreName + " to delete.");
					System.out.println("Ignoring Exception: " + e);
					// ignore any errors...
				}
			}
		} else {
			// Do nothing for now
			System.out
			.println("ImageAccessor::resetImageRecordStore: albumNames array was null. Nothing to delete.");
		}

	
		
		// Now, create a new default album for testing
		addImageData("Tucan Sam", "/images/Tucan.png",
				default_album_name);
		// Add Penguin
		addImageData("Linux Penguin", "/images/Penguin.png",
				default_album_name);
		// Add Duke
		addImageData("Duke (Sun)", "/images/Duke1.PNG",
				default_album_name);
		addImageData("UBC Logo", "/images/ubcLogo.PNG",
				default_album_name);
		// Add Gail
		addImageData("Gail", "/images/Gail1.PNG",
				default_album_name);
		// Add JG
		addImageData("J. Gosling", "/images/Gosling1.PNG",
				default_album_name);
		// Add GK
		addImageData("Gregor", "/images/Gregor1.PNG",
				default_album_name);
		// Add KDV
		addImageData("Kris", "/images/Kdvolder1.PNG",
				default_album_name);

	}

	protected void addImageData(String photoname, String path, String albumname)
	throws InvalidImageDataException, PersistenceMechanismException {

		//try {
		imageRS = RecordStore.openRecordStore(album_label + albumname, true);
		imageInfoRS = RecordStore.openRecordStore(info_label + albumname, true);

		int rid; // new record ID for Image (bytes)
		int rid2; // new record ID for ImageData (metadata)

		ImageUtil converter = new ImageUtil();

		// NOTE: For some Siemen's phone, all images have to be less than
		// 16K
		// May have to check for this, or try to convert to a lesser format
		// for display on Siemen's phones (Could put this in an Aspect)

		// Add Tucan
		byte[] data1 = converter.readImageAsByteArray(path);
		rid = imageRS.addRecord(data1, 0, data1.length);
		IImageData ii = new ImageData(rid, album_label	+ albumname, photoname);
		
		rid2 = imageInfoRS.getNextRecordID();
		ii.setRecordId(rid2);

		data1 = converter.getBytesFromImageInfo(ii);
		imageInfoRS.addRecord(data1, 0, data1.length);

		imageRS.closeRecordStore();

		imageInfoRS.closeRecordStore();
	}

	/**
	 * This will populate the imageInfo hashtable with the ImageInfo object,
	 * referenced by label name and populate the imageTable hashtable with Image
	 * objects referenced by the RMS record Id
	 * 
	 * @throws PersistenceMechanismException
	 */
	protected IImageData[] loadImageDataFromRMS(String recordName)
	throws PersistenceMechanismException, InvalidImageDataException {

		Vector imagesVector = new Vector();


		// [EF] not used			String storeName = ImageAccessor.ALBUM_LABEL + recordName;
		String infoStoreName = info_label + recordName;
		
		
		RecordStore infoStore = RecordStore.openRecordStore(infoStoreName,false);
		
		
		RecordEnumeration isEnum = infoStore.enumerateRecords(null, null, false);
		
		
		while (isEnum.hasNextElement()) {
			// Get next record
		
			int currentId = isEnum.nextRecordId();
		
			byte[] data = infoStore.getRecord(currentId);
			
			// Convert the data from a byte array into our ImageData
			// (metadata) object
			ImageUtil converter = new ImageUtil();
		
		
			IImageData iiObject = converter.getImageInfoFromBytes(data);
		
			//				System.out.println("<* ImageAccessor.loadImageDataFromRMS *> iiObject = "+iiObject.getImageLabel());

			// Add the info to the metadata hashtable, Integer start
		
			String label = iiObject.getImageLabel();
			
			
			//iiObject.setNumberOfViews(9);
			//System.out.println("setting # of views = 9");
			imagesVector.addElement(iiObject);
		
			imageInfoTable.put(label, iiObject);
			System.out.println("PUT: " + label + " : " + this + " : " + imageInfoTable );
			

		}
		
		infoStore.closeRecordStore();

		// Re-copy the contents into a smaller array
		IImageData[] labelArray = new IImageData[imagesVector.size()];
		imagesVector.copyInto(labelArray);

		return labelArray;
	}

	/**
	 * Update the Image metadata associated with this named photo
	 * @throws InvalidImageDataException 
	 * @throws PersistenceMechanismException 
	 */
	protected boolean updateImageInfo(IImageData oldData, IImageData newData) throws InvalidImageDataException, PersistenceMechanismException {
		boolean success = false;
		RecordStore infoStore = null;
		
		// Parse the Data store name to get the Info store name
		String infoStoreName = oldData.getParentAlbumName();
		infoStoreName = info_label + infoStoreName.substring(album_label.length());
		infoStore = RecordStore.openRecordStore(infoStoreName, false);
		
		ImageUtil converter = new ImageUtil();
		
	 	byte[] imageDataBytes = converter.getBytesFromImageInfo(newData);
		
	
		infoStore.setRecord(oldData.getRecordId(), imageDataBytes, 0, imageDataBytes.length);
			

		// Update the Hashtable 'cache'
		setImageInfo(oldData.getImageLabel(), newData);

		infoStore.closeRecordStore();
		success = true;

		return success;
	}

	/**
	 * Retrieve the metadata associated with a specified image (by name)
	 * @throws ImageNotFoundException 
	 * @throws NullAlbumDataReference 
	 */
	//[cosmos][MD Sce 2. Edit Label] turned to public
	protected IImageData getImageInfo(String imageName) throws ImageNotFoundException{
		IImageData ii = (IImageData) imageInfoTable.get(imageName);
		ii.getImageLabel();	
		
		return ii;

	}

	/**
	 * Update the hashtable with new ImageInfo data
	 */
	private void setImageInfo(String imageName, IImageData newData) {

		imageInfoTable.put(newData.getImageLabel(), newData);

	}

	/**
	 * Fetch a single image from the Record Store This should be used for
	 * loading images on-demand (only when they are viewed or sent via SMS etc.)
	 * to reduce startup time by loading them all at once.
	 * @throws PersistenceMechanismException 
	 */
	protected Image loadSingleImageFromRMS(String recordName, String imageName,
			int recordId) throws PersistenceMechanismException {

		Image img = null;
		byte[] imageData = loadImageBytesFromRMS(recordName, imageName,
				recordId);



		img = Image.createImage(imageData, 0, imageData.length);



		return img;
	}

	/**
	 * Get the data for an Image as a byte array. This is useful for sending
	 * images via SMS or HTTP
	 * @throws PersistenceMechanismException 
	 */
	protected byte[] loadImageBytesFromRMS(String recordName, String imageName,
			int recordId) throws PersistenceMechanismException {

		byte[] imageData = null;

		RecordStore albumStore = RecordStore.openRecordStore(recordName,false);
		imageData = albumStore.getRecord(recordId);
		albumStore.closeRecordStore();

		return imageData;
	}

	/**
	 * Delete a single (specified) image from the (specified) record store. This
	 * will permanently delete the image data and metadata from the device.
	 * @throws PersistenceMechanismException 
	 * @throws NullAlbumDataReference 
	 * @throws ImageNotFoundException 
	 */
	protected boolean deleteSingleImageFromRMS(String storeName, String imageName) throws PersistenceMechanismException, ImageNotFoundException{

		boolean success = false;

		// Verify storeName is name without pre-fix
		imageRS = RecordStore.openRecordStore(album_label + storeName, true);
		imageInfoRS = RecordStore.openRecordStore(info_label + storeName, true);
		IImageData imageData = getImageInfo(imageName);
		int rid = imageData.getForeignRecordId();
		imageRS.deleteRecord(rid);
		imageInfoRS.deleteRecord(rid);

		imageRS.closeRecordStore();
		imageInfoRS.closeRecordStore();

		return success;
	}

	/**
	 * Define a new photo album for mobile photo users. This creates a new
	 * record store to store photos for the album.
	 * @throws PersistenceMechanismException 
	 * @throws InvalidPhotoAlbumNameException 
	 */
	protected void createNewPhotoAlbum(String albumName) throws PersistenceMechanismException, InvalidPhotoAlbumNameException {

		RecordStore newAlbumRS = null;
		RecordStore newAlbumInfoRS = null;
		if (albumName.equals("")){
			throw new InvalidPhotoAlbumNameException();

		}
		String[] names  = getAlbumNames();
		for (int i = 0; i < names.length; i++) {
			if (names[i].equals(albumName))
				throw new InvalidPhotoAlbumNameException();
		}

		newAlbumRS = RecordStore.openRecordStore(album_label + albumName,true);
		newAlbumInfoRS = RecordStore.openRecordStore(info_label + albumName, true);
		newAlbumRS.closeRecordStore();
		newAlbumInfoRS.closeRecordStore();

	}

	protected void deletePhotoAlbum(String albumName) throws PersistenceMechanismException {
		RecordStore.deleteRecordStore(album_label + albumName);
		RecordStore.deleteRecordStore(info_label + albumName);
		
	}

	/**
	 * Get the list of photo album names currently loaded.
	 * 
	 * @return Returns the albumNames.
	 */
	protected String[] getAlbumNames() {
		return albumNames;
	}
}