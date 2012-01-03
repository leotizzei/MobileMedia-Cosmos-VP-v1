package br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IManager;

class Manager implements IManager{

	Hashtable requiredInterfaces = new Hashtable();
	Hashtable providedInterfaces = new Hashtable();

	public Manager() {

		IFilesystemFacade filesystem = new IFilesystemFacade();
		providedInterfaces.put("IFilesystem", filesystem );
		providedInterfaces.put("IFilesystemMedia", filesystem );

	}
	
	public String[] getProvidedInterfaces(){
	          
	   return convertListToArray(this.providedInterfaces.elements());
	}
	
	public String[] getRequiredInterfaces(){
	
		return convertListToArray(requiredInterfaces.keys());
	}
	
	public Object getProvidedInterface(String name){
	   
	   return this.providedInterfaces.get(name);
	}
	
	public void setRequiredInterface(String name, Object facade){
		requiredInterfaces.put(name,facade);
	}
	
	public Object getRequiredInterface(String name){
	   return requiredInterfaces.get(name);
	}
	
	private String[] convertListToArray(Enumeration stringEnum){
		Vector stringVector = new Vector();
		for (Enumeration iter = stringEnum; iter.hasMoreElements();) {
			String element = (String) iter.nextElement();
			stringVector.addElement(element);
		}
		
		String[] stringArray = new String[stringVector.size()];
		for (int i=0; i < stringVector.size(); i++){
			stringArray[i] = (String) stringVector.elementAt(i);
		}
		return stringArray;
	}
}



