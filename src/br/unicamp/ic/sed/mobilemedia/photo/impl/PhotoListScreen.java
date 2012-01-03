
package br.unicamp.ic.sed.mobilemedia.photo.impl;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;


/**
 * @author trevor
 *
 * This screen shows a listing of all photos for a selected photo album.
 * This is the screen that contains most of the feature menu items. 
 * From this screen, a user can choose to view photos, add or delete photos,
 * send photos to other users etc.
 * 
 */
public class PhotoListScreen extends List {
	
	//Add the core application commands always
	public static final Command viewCommand = new Command("View", Command.ITEM, 1);
	public static final Command addCommand = new Command("Add", Command.ITEM, 1);
	public static final Command deleteCommand = new Command("Delete", Command.ITEM, 1);
	public static final Command backCommand = new Command("Back", Command.BACK, 0);

	//[cosmos][add Sce 2. Edit Label]
	public static final Command editLabelCommand = new Command("Edit Label" , Command.ITEM , 1);

    /**
     * Constructor
     */
	public PhotoListScreen() {
		super("Choose Items", Choice.IMPLICIT);
		this.initMenu();
	}
	
	/**
	 * Constructor
	 * @param arg0
	 * @param arg1
	 */
	public PhotoListScreen(String arg0, int arg1) {
		super(arg0, arg1);
	}

	/**
	 * Constructor
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public PhotoListScreen(String arg0, int arg1, String[] arg2, Image[] arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	/**
	 * Initialize the menu items for this screen
	 */
	private void initMenu() {
		
		//Add the core application commands always
		this.addCommand(viewCommand);
		this.addCommand(addCommand);
		this.addCommand(deleteCommand);
		this.addCommand(backCommand);

		//[cosmos][add Sce 3. Edit Label]
		this.addCommand(editLabelCommand);
		
		
		//Add the optional feature menu items only if they are specified in 
		//the xxxBuild.properties file using the 'preprocessor.symbols' value
		
	
	}
	
}
