package br.unicamp.ic.sed.mobilemedia.exceptionhandler.impl;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import br.unicamp.ic.sed.mobilemedia.exceptionhandler.spec.prov.IManager;

class Manager implements IManager{

	private Hashtable reqInterfaceMap;
	private Hashtable providedInterface;

	Manager(){
		this.reqInterfaceMap = new Hashtable();
		this.providedInterface = new Hashtable();
		this.providedInterface.put("IManager", this);

		this.providedInterface.put("IExceptionHandler",new IExceptionHandlerFacade());
	}

	public String[] getProvidedInterfaces(){
		//TODO It is necessary to convert all the keys of a Hashtable into a String[]
		Vector provInterfaceList = new Vector();
		return convertListToArray(provInterfaceList.elements());
	}

	public String[] getRequiredInterfaces(){

		return convertListToArray(reqInterfaceMap.keys());
	}

	public Object getProvidedInterface(String name){


		return providedInterface.get(name);
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


	public void setProvidedInterface(String key, Object facade){
		providedInterface.put(key, facade);
	}
}



