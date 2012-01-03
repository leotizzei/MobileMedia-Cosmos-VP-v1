   
package br.unicamp.ic.sed.mobilemedia.album_mobilephonemgr.impl;

import br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.req.IAlbum;


class IAlbumAdapter implements IAlbum{
	
	public void initAlbumListScreen ( String[] albumNames ){
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.album.spec.prov.IAlbum iAlbum;
		iAlbum = (br.unicamp.ic.sed.mobilemedia.album.spec.prov.IAlbum) manager.getRequiredInterface("IAlbum");
		iAlbum.initAlbumListScreen(albumNames);
	} 
	
	public void initNewAlbumScreen (  ){
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.album.spec.prov.IAlbum iAlbum;
		iAlbum = (br.unicamp.ic.sed.mobilemedia.album.spec.prov.IAlbum) manager.getRequiredInterface("IAlbum");
		iAlbum.initNewAlbumScreen();
	} 
	
	public String getSelectedAlbum (  ){
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.album.spec.prov.IAlbum iAlbum;
		iAlbum = (br.unicamp.ic.sed.mobilemedia.album.spec.prov.IAlbum) manager.getRequiredInterface("IAlbum");
		return iAlbum.getSelectedAlbum();	
	} 
	
	public String getNewAlbumName (  ){
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.album.spec.prov.IAlbum iAlbum;
		iAlbum = (br.unicamp.ic.sed.mobilemedia.album.spec.prov.IAlbum) manager.getRequiredInterface("IAlbum");
		return iAlbum.getNewAlbumName();	
	}

	public void initDeleteAlbumScreen(String currentStoreName) {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.album.spec.prov.IAlbum iAlbum;
		iAlbum = (br.unicamp.ic.sed.mobilemedia.album.spec.prov.IAlbum) manager.getRequiredInterface("IAlbum");
		iAlbum.initDeleteAlbumScreen(currentStoreName);
	}
	
}