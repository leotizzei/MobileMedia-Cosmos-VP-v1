   
package br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl;

import javax.microedition.lcdui.Image;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem;
import br.unicamp.ic.sed.mobilemedia.main.spec.dt.IImageData;

class IFilesystemFacade implements IFilesystem{

	private AlbumData albumData =  new AlbumData(); 
	private ImageUtil imageUtil;
	
	public IFilesystemFacade(){
		this.imageUtil = new ImageUtil();
	}
	
	public String[] getImageNames(	String albumName ){
		return albumData.getImageNames(albumName);
	} 
	
	public void addNewPhotoToAlbum ( String imageName, String imagePath, String albumName ){
		albumData.addNewPhotoToAlbum(imageName, imagePath, albumName);
	} 
	
	public void deleteImage ( String imageName, String albumName ){
		System.out.println("[IFilesystemFacade:deleteImage("+imageName+","+albumName+")");
		albumData.deleteImage(imageName, albumName);
	} 
	
	public Image getImageFromRecordStore ( String albumName, String imageName ){
		return albumData.getImageFromRecordStore(albumName, imageName);
	} 
		
	public String[] getAlbumNames (  ){
		return albumData.getAlbumNames();
	} 
	
	public void resetImageData (  ) {
		albumData.resetImageData();	
	} 
	
	public void createNewPhotoAlbum ( String albumName ) {
		albumData.createNewPhotoAlbum(albumName);		
	} 
	
	public void deletePhotoAlbum ( String albumName ) {
		albumData.deletePhotoAlbum(albumName);	
	}
	
	public byte[] readImageAsByteArray(String imageFile) {
		return this.imageUtil.readImageAsByteArray(imageFile);
	}

		public byte[] getBytesFromImageInfo(IImageData ii) {
		return this.imageUtil.getBytesFromImageInfo(ii);
	}
}