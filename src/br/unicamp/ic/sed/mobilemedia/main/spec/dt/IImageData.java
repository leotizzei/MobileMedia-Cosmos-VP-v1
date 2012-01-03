package br.unicamp.ic.sed.mobilemedia.main.spec.dt;

public interface IImageData {

	/**
	 * @return Returns the recordId.
	 */
	public abstract int getRecordId();

	/**
	 * @param recordId The recordId to set.
	 */
	public abstract void setRecordId(int recordId);

	/**
	 * @return Returns the foreignRecordId.
	 */
	public abstract int getForeignRecordId();

	/**
	 * @param foreignRecordId The foreignRecordId to set.
	 */
	public abstract void setForeignRecordId(int foreignRecordId);

	/**
	 * @return Returns the imageLabel.
	 */
	public abstract String getImageLabel();

	/**
	 * @param imageLabel The imageLabel to set.
	 */
	public abstract void setImageLabel(String imageLabel);

	/**
	 * @return Returns the parentAlbumName.
	 */
	public abstract String getParentAlbumName();

	/**
	 * @param parentAlbumName The parentAlbumName to set.
	 */
	public abstract void setParentAlbumName(String parentAlbumName);
}