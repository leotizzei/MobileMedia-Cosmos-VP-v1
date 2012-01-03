   
package br.unicamp.ic.sed.mobilemedia.mobilephonemgr_photo.impl;

import javax.microedition.lcdui.Image;

import br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.req.IPhoto;


class IPhotoAdapter implements IPhoto{
	
	public void initPhotoListScreen ( String[] imageNames){

		br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto iphoto;
		
		IManager manager = ComponentFactory.createInstance();
		iphoto = (br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto)manager.getRequiredInterface("IPhoto");
		iphoto.initPhotoListScreen(imageNames);
		
	} 
	public void initPhotoViewScreen ( Image image ){
		
		br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto iphoto;
		
		IManager manager = ComponentFactory.createInstance();
		iphoto = (br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto)manager.getRequiredInterface("IPhoto");
		iphoto.initPhotoViewScreen(image);
	} 
	
	public void initAddPhotoToAlbum (String albumName){
		br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto iphoto;
		
		IManager manager = ComponentFactory.createInstance();
		iphoto = (br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto)manager.getRequiredInterface("IPhoto");
		
		iphoto.initAddPhotoToAlbum(albumName);
		
	} 
	public String getSelectedPhoto (  ){
		br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto iphoto;
		
		IManager manager = ComponentFactory.createInstance();
		iphoto = (br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto)manager.getRequiredInterface("IPhoto");
		
		return iphoto.getSelectedPhoto();
		
	} 
	public String getAddedPhotoPath (  ){
		br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto iphoto;
		
		IManager manager = ComponentFactory.createInstance();
		iphoto = (br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto)manager.getRequiredInterface("IPhoto");
		
		return iphoto.getAddedPhotoPath();
		
	} 
	public String getAddedPhotoName (  ){
		br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto iphoto;
		
		IManager manager = ComponentFactory.createInstance();
		iphoto = (br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto)manager.getRequiredInterface("IPhoto");
		
		return iphoto.getAddedPhotoName();
		
	}
}