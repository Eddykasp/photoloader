package com.maxkasp.photoloader;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;

public class Series {
	
	private ArrayList<Photo> photos;
	private int participantNumber;
	private int length;
	
	/**
	 * Creates a series with the length specified in the configuration.
	 * @param participantNumber the participant number of the creator of this series
	 */
	public Series(int participantNumber){
		this.participantNumber = participantNumber;
		this.photos = new ArrayList<Photo>();
		this.length = ProgramConfiguration.seriesLength;
	}
	
	/**
	 * Special constructor for creating a series with a length different from the config, useful for testing.
	 * @param participantNumber the participant number of the creator of this series
	 * @param length Config override
	 */
	public Series(int participantNumber, int length){
		this.participantNumber = participantNumber;
		this.photos = new ArrayList<Photo>();
		this.length = length;
	}
	
	/**
	 * Load photos from a specified directory
	 * @param folderpath The name of the directory containing the photos
	 * @throws SeriesException Thrown if the provided folderpath is not a valid directory
	 */
	public void loadPhotos(String folderpath) throws SeriesException{
		File folder = new File(folderpath);
		if(!folder.isDirectory()){
			throw new SeriesException("Path provided is not a directory");
		}
		String [] filenames = folder.list(new FilenameFilter(){

			public boolean accept(File arg0, String arg1) {
				return (arg1.toUpperCase().endsWith(".JPG") || arg1.toUpperCase().endsWith(".JPEG"));
			}
			
		});
		
		ArrayList<Photo> tempPhotos = new ArrayList<>();
		for(String filename : filenames){
			Photo photo;
			try {
				photo = new Photo(folder + "/" + filename);
				tempPhotos.add(photo);
			} catch (ImageProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempPhotos.sort(new Comparator<Photo>(){

				@Override
				public int compare(Photo arg0, Photo arg1) {
					Directory dir0 = arg0.getImageData().getFirstDirectoryOfType(ExifSubIFDDirectory.class);
					Date date0 = dir0.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
					
					Directory dir1 = arg1.getImageData().getFirstDirectoryOfType(ExifSubIFDDirectory.class);
					Date date1 = dir1.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
					return date0.compareTo(date1);
				}
				
			});
			
			photos = tempPhotos;
		}
	}
	
	/**
	 * Retrieve the photo for a certain topic.
	 * @param topicNumber
	 * @return photo for the specified topic
	 * @throws IndexOutOfBoundsException Thrown when an invalid topic number is passed in
	 */
	public Photo getPhotoByTopic(int topicNumber) throws IndexOutOfBoundsException{
		if(photos.size() == 0 || topicNumber <= 0 || topicNumber > length){
			throw new IndexOutOfBoundsException();
		}
		return photos.get(topicNumber - 1);
	}
	
	/**
	 * This method copies the photos from a series to a new location, renaming them according to a passed in pattern.
	 * @param directory the target directory for the series folders
	 * @param pattern the renaming pattern to be used for the files
	 * @param padding determines how many leading zeros should be used in the participant number of the folder names
	 * @throws IOException
	 */
	public void saveSeriesToDisk(File directory, FilenamePattern pattern, int padding) throws IOException{
		if(!directory.isDirectory()){
			throw new IllegalArgumentException("The file object passed must be a directory");
		}
		if(!directory.exists()){
			directory.mkdir();
		}
		if(padding < 1){
			throw new IllegalArgumentException("The participant number padding must be at least 1");
		}
		// create folder for participant
		File participantFolder = new File(directory + "/" + String.format("%0" + padding + "d", participantNumber));
		participantFolder.mkdir();
		for (int i = 0; i < photos.size(); i++){
			// save each photo with the name provided by the pattern
			com.maxkasp.photoloader.util.File.copyFileUsingStream(photos.get(i).getImageFile().toString(), 
					participantFolder + "/" + pattern.getFilenameForIndex(i + 1, participantNumber));
		}
	}

}
