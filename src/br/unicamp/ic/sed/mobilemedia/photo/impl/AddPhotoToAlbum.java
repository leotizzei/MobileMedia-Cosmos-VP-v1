package br.unicamp.ic.sed.mobilemedia.photo.impl;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

class AddPhotoToAlbum extends Form {
	
	TextField labeltxt = new TextField("Photo label", "", 15, TextField.ANY);
	TextField photopathtxt = new TextField("Path", "", 20, TextField.ANY);
	
	Command ok;
	Command cancel;

	protected AddPhotoToAlbum(String title) {
		super(title);
		this.append(labeltxt);
		this.append(photopathtxt);
		ok = new Command("Save Add Photo", Command.SCREEN, 0);
		cancel = new Command("Cancel", Command.EXIT, 1);
		this.addCommand(ok);
		this.addCommand(cancel);
	}
	
	protected String getPhotoName(){
		return labeltxt.getString();
	}
	
	protected String getPath(){
		return photopathtxt.getString();
	}
}
