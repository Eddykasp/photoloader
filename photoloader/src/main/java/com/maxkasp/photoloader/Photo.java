package com.maxkasp.photoloader;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.jpeg.JpegDirectory;

public class Photo {
	
	private File imageFile;
	private Metadata imageData;
	
	public Photo(){
		imageFile = null;
		imageData = null;
	}
	
	public Photo(String filename) throws ImageProcessingException, IOException{
		this.imageFile = new File(filename);
		this.imageData = ImageMetadataReader.readMetadata(imageFile);
	}

	/**
	 * @return the imageFile
	 */
	public File getImageFile() {
		return imageFile;
	}

	/**
	 * @param imageFile the imageFile to set
	 */
	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	/**
	 * @return the imageData
	 */
	public Metadata getImageData() {
		return imageData;
	}

	/**
	 * @param imageData the imageData to set
	 */
	public void setImageData(Metadata imageData) {
		this.imageData = imageData;
	}
	
	/**
	 * Determines whether or not the loaded photo is a jpeg
	 * @return true if it is a jpeg, false if not
	 */
	public boolean isJpeg(){
		return imageData.containsDirectoryOfType(JpegDirectory.class);
	}
	
	/**
	 * Determines whether a photo was taken within a specified timeframe, throws an exception
	 * if image contains no exif data
	 * @param startTime time after which photo should be taken
	 * @param endTime time before photo should be taken
	 * @return true if taken within time
	 */
	public boolean wasTakenWithinTime(Date startTime, Date endTime) throws PhotoException{
		if(imageData.containsDirectoryOfType(ExifSubIFDDirectory.class)){
			
			Directory directory = imageData.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
			Date photoDate = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);

			if(photoDate.after(startTime) && photoDate.before(endTime)){
				return true;
			} else {
				return false;
			}
		} else {
			throw new PhotoException("Missing Exif data");
		}
	}

}
