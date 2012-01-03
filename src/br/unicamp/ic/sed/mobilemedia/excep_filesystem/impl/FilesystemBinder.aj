package br.unicamp.ic.sed.mobilemedia.excep_filesystem.impl;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.aspects.XPIIFilesystemEH;

public aspect FilesystemBinder extends ExceptionAdapter{
	
	public pointcut handleException():XPIIFilesystemEH.handleException();
	
}
