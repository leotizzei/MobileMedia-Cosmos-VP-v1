package br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.req;


public interface IAlbum{

	public void initAlbumListScreen ( String[] albumNames ); 
	public void initNewAlbumScreen (  );
	public void initDeleteAlbumScreen( String albumName );
	public String getSelectedAlbum (  ); 
	public String getNewAlbumName (  ); 
}