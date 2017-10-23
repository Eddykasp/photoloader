package com.maxkasp.photoloader;

import java.io.File;
import java.io.IOException;

public class SaveSeriesExporter implements SeriesExporter {
	
	private File directory;
	private FilenamePattern pattern;
	private int padding;
	
	public SaveSeriesExporter(File directory, FilenamePattern pattern, int padding){
		if(!directory.isDirectory()){
			throw new IllegalArgumentException("The file object passed must be a directory");
		}
		if(!directory.exists()){
			directory.mkdir();
		}
		if(padding < 1){
			throw new IllegalArgumentException("The participant number padding must be at least 1");
		}
		
		this.directory = directory;
		this.pattern = pattern;
		this.padding = padding;
	}

	@Override
	public void export(Series series) throws IOException{
		File participantFolder = new File(directory + "/" + String.format("%0" + padding + "d", series.getParticipantNumber()));
		participantFolder.mkdir();
		for (int i = 1; i < series.getLength(); i++){
			// save each photo with the name provided by the pattern
			com.maxkasp.photoloader.util.File.copyFileUsingStream(series.getPhotoByTopic(i).getImageFile().toString(), 
					participantFolder + "/" + pattern.getFilenameForIndex(i, series.getParticipantNumber()));
		}

	}

}
