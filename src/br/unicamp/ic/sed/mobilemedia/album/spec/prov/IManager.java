package br.unicamp.ic.sed.mobilemedia.album.spec.prov;

public interface IManager{

	public String[] getProvidedInterfaces();
	
	public String[] getRequiredInterfaces();
	
	public Object getProvidedInterface(String name);
	
	public void setRequiredInterface(String name, Object facade);
	
	public Object getRequiredInterface(String name);
}