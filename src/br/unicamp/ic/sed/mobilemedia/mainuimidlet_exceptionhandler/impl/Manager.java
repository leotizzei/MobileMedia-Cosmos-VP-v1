package br.unicamp.ic.sed.mobilemedia.mainuimidlet_exceptionhandler.impl;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class Manager implements IManager{

	private Hashtable reqInterfaceMap = new Hashtable();
	private Hashtable provInterfaceMap = new Hashtable();

	public Manager(){
		this.setProvidedInterface("IMobileResources", new MobileResourcesEHAdapter());
		this.setProvidedInterface("IManager", this);
	}


	public String[] getProvidedInterfaces(){
		Vector provInterfaceList = new Vector();
		provInterfaceList.addElement("IMobileResources");


		return convertListToArray(provInterfaceList.elements());
	}

	public String[] getRequiredInterfaces(){

		return convertListToArray(reqInterfaceMap.keys());
	}

	public Object getProvidedInterface(String name){

		return this.provInterfaceMap.get( name );


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


	public void setProvidedInterface(String name, Object facade) {
		this.provInterfaceMap.put(name, facade);

	}
}



