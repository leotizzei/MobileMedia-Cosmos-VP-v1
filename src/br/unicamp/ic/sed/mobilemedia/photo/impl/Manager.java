package br.unicamp.ic.sed.mobilemedia.photo.impl;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IManager;

class Manager implements IManager{
	
	Hashtable reqInterfaceMap = new Hashtable();
	private IPhotoFacade facade = null;
	
	
	public String[] getProvidedInterfaces(){
		Vector provInterfaceList = new Vector();
		provInterfaceList.addElement("IPhoto");
	     
	   
	   return convertListToArray(provInterfaceList.elements());
	}
	
	public String[] getRequiredInterfaces(){
	
		return convertListToArray(reqInterfaceMap.keys());
	}
	
	public void init(){}
	
	public Object getProvidedInterface(String name){

	   if (name.equals("IPhoto")){
		   if( facade == null )
			   facade = new IPhotoFacade( );
		   return this.facade;
	   }
	   
	   return null;
	}
	
	public void setRequiredInterface(String name, Object facade){
		reqInterfaceMap.put(name,facade);
	}
	
	public Object getRequiredInterface(String name){
	   return reqInterfaceMap.get(name);
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



