package br.unicamp.ic.sed.mobilemedia.filesystemmgr.aspects;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.main.spec.dt.IImageData;


privileged public aspect XPIAlbumData {

	//=========AlbumData===========
	public pointcut getAlbumNames(): 
		 call(protected void br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.ImageAccessor.loadAlbums())
		 && (withincode(public String[] br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.AlbumData.getAlbumNames()));
	
	declare soft:  InvalidImageDataException : getAlbumNames();
	declare soft:  PersistenceMechanismException : getAlbumNames();

	//================================================================
	public pointcut getImages(): 
		 execution(public String[] br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.AlbumData.getImageNames(String));
	
	declare soft:   PersistenceMechanismException : getImages();
	declare soft:   InvalidImageDataException : getImages();
	
	//================================================================
	
	public pointcut resetImageData(): 
		execution(public void br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.AlbumData.resetImageData());
			
	declare soft:InvalidImageDataException: resetImageData();
}
