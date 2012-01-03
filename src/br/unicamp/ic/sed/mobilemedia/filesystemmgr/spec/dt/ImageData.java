/*
 * Created on Nov 26, 2004
 *
 */
package br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt;

import br.unicamp.ic.sed.mobilemedia.main.spec.dt.IImageData;

/**
 * @author trevor
 *
 * This class holds meta data associated with a photo or image. There is a one-to-one
 * relationship between images and image metadata. (ie. Every photo in MobileMedia will
 * have a corresonding ImageData object). 
 * It stores the recordId of the image record in RMS, the recordID of the metadata record
 * the name of the photo album(s) it belongs to, the text label, associated phone numbers
 * etc.
 * 
 */
public class ImageData implements IImageData {
	
	private int recordId; //imageData recordId
	private int foreignRecordId; //image recordId
	private String parentAlbumName; //Should we allow single image to be part of multiple albums?
	private String imageLabel;	
	
	/**
	 * Constructor
	 * @param foreignRecordId
	 * @param parentAlbumName
	 * @param imageLabel
	 */
	public ImageData(int foreignRecordId, String parentAlbumName,String imageLabel) {
		super();
		this.foreignRecordId = foreignRecordId;
		this.parentAlbumName = parentAlbumName;
		this.imageLabel = imageLabel;
	}
	
	/* (non-Javadoc)
	 * @see br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.IImageData#getRecordId()
	 */
	public int getRecordId() {
		return recordId;
	}
	
	/* (non-Javadoc)
	 * @see br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.IImageData#setRecordId(int)
	 */
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	
	/* (non-Javadoc)
	 * @see br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.IImageData#getForeignRecordId()
	 */
	public int getForeignRecordId() {
		return foreignRecordId;
	}
	
	/* (non-Javadoc)
	 * @see br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.IImageData#setForeignRecordId(int)
	 */
	public void setForeignRecordId(int foreignRecordId) {
		this.foreignRecordId = foreignRecordId;
	}
	
	/* (non-Javadoc)
	 * @see br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.IImageData#getImageLabel()
	 */
	public String getImageLabel() {
		return imageLabel;
	}
	
	/* (non-Javadoc)
	 * @see br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.IImageData#setImageLabel(java.lang.String)
	 */
	public void setImageLabel(String imageLabel) {
		this.imageLabel = imageLabel;
	}
	
	/* (non-Javadoc)
	 * @see br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.IImageData#getParentAlbumName()
	 */
	public String getParentAlbumName() {
		return parentAlbumName;
	}
	
	/* (non-Javadoc)
	 * @see br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.IImageData#setParentAlbumName(java.lang.String)
	 */
	public void setParentAlbumName(String parentAlbumName) {
		this.parentAlbumName = parentAlbumName;
	}	
}
