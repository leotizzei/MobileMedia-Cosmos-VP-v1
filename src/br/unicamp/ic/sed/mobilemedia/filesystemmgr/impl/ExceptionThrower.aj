package br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl;

import br.unicamp.ic.sed.mobilemedia.main.spec.dt.IImageData;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImagePathNotValidException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidArrayFormatException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;

import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;

import java.io.IOException;



aspect ExceptionThroMediawer {

	//ImageAccessor
	pointcut addImageData() : execution( protected void ImageAccessor.addImageData( String , String , String ))
								||execution(protected void br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.ImageAccessor.addImageData(String, IImageData, String))
								||execution(public void br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.ImageAccessor.addNewMediaToAlbum(String , String, String, byte[]));
	
	declare soft : RecordStoreException : addImageData();
	
	void around() throws PersistenceMechanismException : addImageData(){
		try{
			proceed();
		}catch( RecordStoreNotFoundException e ){
			throw new PersistenceMechanismException( "Error while add media to mobile database: invalid album name." );
		}catch( RecordStoreFullException e ){
			throw new PersistenceMechanismException( "Error while add media to mobile database: database is full." );
		}catch( RecordStoreException e ){
			throw new PersistenceMechanismException( "Error while add media to mobile database." );
		}
	}
	
	//---------------------------------
	pointcut loadImageDataFromRMS() : execution( protected IImageData[] ImageAccessor.loadImageDataFromRMS(String));
	
	declare soft : RecordStoreException : loadImageDataFromRMS();
	
	IImageData[] around() throws PersistenceMechanismException : loadImageDataFromRMS() {
		try{
			return proceed();
		}catch( RecordStoreException e ){
			throw new PersistenceMechanismException( "Error while loading media from mobile database." );
		}
	}
	
	//---------------------------------
	pointcut updateImageInfo() : execution( protected boolean updateImageInfo( IImageData , IImageData ));
	
	declare soft : RecordStoreException : updateImageInfo();
	
	boolean around() throws PersistenceMechanismException : updateImageInfo(){
		try{
			return proceed();
		}catch( RecordStoreException e ){
			throw new PersistenceMechanismException( "Error while updating media from mobile database." );
		}
	}
	
	//---------------------------------
	pointcut getImageInfo() : execution( protected IImageData ImageAccessor.getImageInfo( String ));
	
	IImageData around() throws ImageNotFoundException : getImageInfo(){
		try{
			return proceed();
		}catch( NullPointerException e ){
			throw new ImageNotFoundException( "Media name provided was not found in mobile database.");
		}
	}
	
	//----------------------------------
	pointcut loadImageBytesFromRMS(): 
		 execution(protected byte[] br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.ImageAccessor.loadImageBytesFromRMS(String, String,int));
	
	declare soft : RecordStoreException : loadImageBytesFromRMS();
	
	byte[] around() throws PersistenceMechanismException : loadImageBytesFromRMS(){
		try{
			return proceed();
		}catch( RecordStoreException e ){
			throw new PersistenceMechanismException( "Image not found in mobile database." );
		}
	}
	
	//------------------------------------
	pointcut deleteSingleImageFromRMS(): 
		 execution(protected boolean br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.ImageAccessor.deleteSingleImageFromRMS(String, String));
	
	declare soft: RecordStoreException : deleteSingleImageFromRMS();
	
	boolean around() throws PersistenceMechanismException : deleteSingleImageFromRMS(){
		try{
			return proceed();
		}catch( RecordStoreException e ){
			throw new PersistenceMechanismException( "Media not found in mobile database." );
		}
	}
	
	//--------------------------------------
	pointcut createNewMediaAlbum(): 
		 execution(protected void br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.ImageAccessor.createNewPhotoAlbum(String));
	
	declare soft: RecordStoreException : createNewMediaAlbum();

	void around() throws PersistenceMechanismException : createNewMediaAlbum(){
		try{
			proceed();
		}catch( RecordStoreException e ){
			throw new PersistenceMechanismException( "The mobile database is full." );
		}
	}
	
	//---------------------------------------
	pointcut deletePhotoAlbum(): 
		 execution(protected void br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.ImageAccessor.deletePhotoAlbum(String));
	
	declare soft : RecordStoreException : deletePhotoAlbum();
	
	void around() throws PersistenceMechanismException : deletePhotoAlbum(){
		try{
			proceed();
		}catch( RecordStoreException e ){
			throw new PersistenceMechanismException( "The mobile database is full." );
		}
	}
	
	//========================================
	//ImageUtil
	
	pointcut readImageByteArray() : execution(public byte[] br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.ImageUtil.readImageAsByteArray(String));
	
	declare soft : IOException : readImageByteArray();
	
	byte[] around() throws ImagePathNotValidException : readImageByteArray(){
		try{
			return proceed();
		}catch( IOException e ){
			throw new ImagePathNotValidException( "Path not valid for this media!" );
		}
	}
	
	//------------------------------------------
	pointcut getMediaInfoFromBytes(): 
		 execution(public IImageData ImageUtil.getImageInfoFromBytes(byte[]));
	
	after() throwing(Exception e) throws  InvalidArrayFormatException: getMediaInfoFromBytes(){
		throw new InvalidArrayFormatException( "Error while reading media's info." );
	}
	
	//------------------------------------------
	pointcut getBytesFromImageInfo(): 
		 execution(public byte[] ImageUtil.getBytesFromImageInfo(IImageData));
	
	after() throwing(Exception e) throws  InvalidImageDataException: getBytesFromImageInfo(){
		throw new InvalidImageDataException("The provided data are not valid");
	}
	
	//==========================================
	//AlbumData
	
	pointcut getAlbumNames(): 
		 call(protected void br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.ImageAccessor.loadAlbums())
		 && (withincode(public String[] br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.AlbumData.getAlbumNames()));
	
	declare soft:  InvalidImageDataException : getAlbumNames();
	declare soft:  PersistenceMechanismException : getAlbumNames();

	//------------------------------------------
	pointcut getImages(): 
		 execution(public IImageData[] br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.AlbumData.getImages(String));
	
	declare soft:   PersistenceMechanismException : getImages();
	declare soft:   InvalidImageDataException : getImages();
	
	//------------------------------------------

	pointcut resetImageData(): 
		execution(public void br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.AlbumData.resetImageData());
			
	declare soft:InvalidImageDataException: resetImageData();
}
