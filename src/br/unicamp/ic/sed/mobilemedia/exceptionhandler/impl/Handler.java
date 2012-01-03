/**
authors: Marcelo Dias and Leonardo Tizzei
**/
package br.unicamp.ic.sed.mobilemedia.exceptionhandler.impl;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.exceptionhandler.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.exceptionhandler.spec.req.IMobileResources;
import org.aspectj.lang.SoftException;

class Handler {
	public void handleException( Exception e ){
		IManager mgr = ComponentFactory.createInstance();
		IMobileResources mobileResources = (IMobileResources) mgr.getRequiredInterface("IMobileResources");
		
		String msg = null;
		if( e instanceof SoftException || e.getMessage() == null )
			msg = "Error trying to access mobile database.";
		else
			msg = e.getMessage();
		
		Alert alert = new Alert( "Error", msg, null , AlertType.ERROR );
		MIDlet midlet = mobileResources.getMainMIDlet();
		Display.getDisplay( midlet ).setCurrent(alert, Display.getDisplay( midlet ).getCurrent());
		Display.getDisplay( midlet ).vibrate( 10 );
		
		System.out.println("EXCEPTION CAUGHT:");
		e.printStackTrace();
	}
}
