package br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.req;
import javax.microedition.lcdui.Image;

public interface IFilesystem{

	
	public String[] getImageNames ( String albumName ) ; 
	
	public void addNewPhotoToAlbum ( String imageName, String imagePath, String albumName ); 
	
	public void deleteImage ( String imageName, String albumName ); 
	
	public Image getImageFromRecordStore ( String albumName, String imageName ); 
	
	/*aspectized*/
	public String[] getAlbumNames (  ); 
	
	/*aspectized*/
	public void resetImageData (  ); 
	
	public void createNewPhotoAlbum ( String albumName ); 
	
	public void deletePhotoAlbum ( String albumName ) ; 

}