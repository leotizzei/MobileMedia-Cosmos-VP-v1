package br.unicamp.ic.sed.mobilemedia.mainuimidlet_exceptionhandler.impl;

public interface IManager{

	public String[] getProvidedInterfaces();
	
	public String[] getRequiredInterfaces();
	
	public Object getProvidedInterface(String name);
	
	public void setRequiredInterface(String name, Object facade);
	
	public Object getRequiredInterface(String name);
	
	public void setProvidedInterface(String name, Object facade);
}