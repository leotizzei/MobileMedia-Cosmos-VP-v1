package br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl;

import java.io.InputStream;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.ImageData;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImagePathNotValidException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidArrayFormatException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageFormatException;
import br.unicamp.ic.sed.mobilemedia.main.spec.dt.IImageData;

/**
 * @author trevor This is a utility class. It performs conversions between Image
 *         objects and byte arrays, and Image metadata objects and byte arrays.
 *         Byte arrays are the main format for storing data in RMS, and for
 *         sending data over the wire.
 */
class ImageUtil {

	// Delimiter used in record store data to separate fields in a string.
	protected static final String DELIMITER = "*";

	/**
	 * Constructor
	 */
	public ImageUtil() {
		super();
	}

	/**
	 * This method reads an Image from an Input Stream and converts it from a
	 * standard image file format into a byte array, so that it can be
	 * transported over wireless protocols such as SMS
	 * 
	 * @throws ImagePathNotValidException
	 * @throws InvalidImageFormatException
	 */
	public byte[] readImageAsByteArray(String imageFile)
	throws ImagePathNotValidException, InvalidImageFormatException {

		byte bArray[] = new byte[1000];

		// Read an Image into a byte array
		// Required to transfer images over SMS
		InputStream is = null;
		is = (InputStream) this.getClass().getResourceAsStream(imageFile);

		int i, len = 0;
		byte bArray2[];
		byte b[] = new byte[1];
		while (is.read(b) != -1) {

			if (len + 1 >= bArray.length) {

				bArray2 = new byte[bArray.length];

				// Transfer all data from old array to temp array
				for (i = 0; i < len; i++)
					bArray2[i] = bArray[i];

				bArray = new byte[bArray2.length + 500];

				// Re-Copy contents back into new bigger array
				for (i = 0; i < len; i++)
					bArray[i] = bArray2[i];
			}

			// Set the size to be exact
			bArray[len] = b[0];
			len++;
		}

		is.close();

		return bArray;
	}

	/**
	 * 
	 * Convert the byte array from a retrieved RecordStore record into the
	 * ImageInfo ((renamed ImageData) object Order of the string will look like
	 * this: <recordId>*<foreignRecordId>*<albumName>*<imageLabel> Depending
	 * on the optional features, additional fields may be: <phoneNum>
	 * 
	 * @throws InvalidArrayFormatException
	 */
	//[cosmos][MD Sce. 2]add a new parameter to method to use in after aspect.
	public IImageData getImageInfoFromBytes(byte[] bytes )
	throws InvalidArrayFormatException {

		String iiString = new String(bytes);



		// Track our position in the String using delimiters
		// Ie. Get chars from beginning of String to first Delim
		int startIndex = 0;
		endIndex = iiString.indexOf(DELIMITER);

		// Get recordID int value as String - everything before first
		// delimeter
		String intString = iiString.substring(startIndex, endIndex);
		

		// Get 'foreign' record ID corresponding to the image table
		startIndex = endIndex + 1;
		endIndex = iiString.indexOf(DELIMITER, startIndex);
		String fidString = iiString.substring(startIndex, endIndex);
		

		// Get Album name (recordstore) - next delimeter
		startIndex = endIndex + 1;
		endIndex = iiString.indexOf(DELIMITER, startIndex);
		String albumLabel = iiString.substring(startIndex, endIndex);	
		
		startIndex = endIndex + 1;
		endIndex = iiString.indexOf(DELIMITER, startIndex);

		if (endIndex == -1)
			endIndex = iiString.length();

		String imageLabel = "";
		imageLabel = iiString.substring(startIndex, endIndex);
		//System.out.println("[rid]="+intString+"[fid]="+fidString+"[album]="+albumLabel+"[imageLabel]="+imageLabel);

		Integer x = Integer.valueOf(fidString);
		//ImageData ii = new ImageData(x.intValue(), albumLabel, imageLabel);
		
		
		
		IImageData ii = createImageData(x.intValue(), albumLabel, imageLabel,bytes , endIndex);
		
		System.out.println("[ImageUtil.getImageInfoFromBytes(..)] intString="+intString);
		x = Integer.valueOf(intString);
		ii.setRecordId(x.intValue());
		
		System.out.println("[ImageUtil.getImageInfoFromBytes(..)] before return");
		
		return ii;
	}
	
	/*****
	 * Method add just to expose some informations to aspects.
	 * @author Marcelo
	 * Scenario 2 - Sorting by View
	 * 
	 * Tags:[cosmos][add]
	 * */
	public IImageData createImageData( int foreignRecordId, String parentAlbumName,String imageLabel , byte[] bytes , int endIndex ){
		return new ImageData( foreignRecordId , parentAlbumName , imageLabel );
	}

	/**
	 * 
	 * Convert the ImageInfo (renamed ImageData) object into bytes so we can
	 * store it in RMS Order of the string will look like this: <recordId>*<foreignRecordId>*<albumName>*<imageLabel>
	 * Depending on the optional features, additional fields may be: <phoneNum>
	 * @throws InvalidImageDataException 
	 */
	public byte[] getBytesFromImageInfo(IImageData ii) throws InvalidImageDataException {

		// Take each String and get the bytes from it, separating fields with a
		// delimiter
		String byteString = new String();

		// Convert the record ID for this record
		int i = ii.getRecordId();
		Integer j = new Integer(i);
		byteString = byteString.concat(j.toString());
		byteString = byteString.concat(DELIMITER);

		// Convert the 'Foreign' Record ID field for the corresponding Image
		// record store
		int i2 = ii.getForeignRecordId();
		Integer j2 = new Integer(i2);
		byteString = byteString.concat(j2.toString());
		byteString = byteString.concat(DELIMITER);

		// Convert the album name field
		byteString = byteString.concat(ii.getParentAlbumName());
		byteString = byteString.concat(DELIMITER);
		
		// Convert the label (name) field
		byteString = byteString.concat(ii.getImageLabel());
		
		
		
		
		// Convert the phone number field
		return byteString.getBytes();

	}
	
	private int endIndex = 0;

	protected void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	protected int getEndIndex() {
		return endIndex;
	}
}