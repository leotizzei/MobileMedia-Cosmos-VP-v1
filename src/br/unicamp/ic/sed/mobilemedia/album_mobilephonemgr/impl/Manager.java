package br.unicamp.ic.sed.mobilemedia.album_mobilephonemgr.impl;

import java.util.*;

class Manager implements IManager{

	Hashtable reqInterfaceMap = new Hashtable();

	public String[] getProvidedInterfaces(){
	   Vector provInterfaceList = new Vector();
	  provInterfaceList.addElement("IAlbum");
	  provInterfaceList.addElement("IMobilePhone");
	     
	   
	   return convertListToArray(provInterfaceList.elements());
	}
	
	public String[] getRequiredInterfaces(){
	
		return convertListToArray(reqInterfaceMap.keys());
	}
	
	public Object getProvidedInterface(String name){

	   if (name.equals("IAlbum")){
	   		return new IAlbumAdapter();
	   }else if (name.equals("IMobilePhone")){
		   return new IMobilePhoneAdapter();
	   }
	   return null;
	}
	
	public void setRequiredInterface(String name, Object adapter){
		reqInterfaceMap.put(name,adapter);
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



