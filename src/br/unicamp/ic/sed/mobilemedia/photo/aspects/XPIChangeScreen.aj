/**
 * Institute of Computing - University of Campinas - Brazil
 * Software Engineering and Dependability Group
 * 
 * Added in MobileMedia-Cosmos-AO-v2
 * Sorting aspect. 
 * 
 */

package br.unicamp.ic.sed.mobilemedia.photo.aspects;

public aspect XPIChangeScreen {

	public pointcut initMenu(br.unicamp.ic.sed.mobilemedia.photo.impl.PhotoListScreen photoListScreen):
		call(void br.unicamp.ic.sed.mobilemedia.photo.impl.PhotoListScreen.initMenu())
		&& target( photoListScreen ); 
	
}
