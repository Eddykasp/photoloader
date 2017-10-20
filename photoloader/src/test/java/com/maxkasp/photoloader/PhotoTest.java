package com.maxkasp.photoloader;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.drew.imaging.ImageProcessingException;

public class PhotoTest {
	
	@Test
	public void testEmptyConstructor(){
		Photo photo = new Photo();
		assertNull(photo.getImageFile());
		assertNull(photo.getImageData());
	}
	
	@Test
	public void testConstructorWithValidImage(){
		try{
			Photo photo = new Photo(TestConfiguration.testDirectory + "test-photo-1.jpg");
			assertTrue(photo.getImageFile().isFile());
		}
		catch(Exception e){
			fail();
		}
	}
	
	@Test(expected=IOException.class)
	public void testConstructorWithMissingFile() throws ImageProcessingException, IOException{
		@SuppressWarnings("unused")
		Photo photo = new Photo(TestConfiguration.testDirectory + "illegal-file");
	}
	
	@Test(expected=ImageProcessingException.class)
	public void testConstructorWithNonImageFile() throws ImageProcessingException, IOException{
		@SuppressWarnings("unused")
		Photo photo = new Photo(TestConfiguration.testDirectory + "text-file.txt");
	}
	
	@Test
	public void testIsJpegWithValidFile(){
		try{
			Photo photo = new Photo(TestConfiguration.testDirectory + "test-photo-1.jpg");
			assertTrue(photo.isJpeg());
		}
		catch(Exception e){
			fail();
		}
	}
	
	@Test
	public void testIsJpegWithInvalidFile(){
		try{
			Photo photo = new Photo(TestConfiguration.testDirectory + "test-png.png");
			assertFalse(photo.isJpeg());
		}
		catch(Exception e){
			fail();
		}
	}
	
	@Test
	public void testIfWithinTimeFrame(){
		try{
			Photo photo = new Photo(TestConfiguration.testDirectory + "test-photo-1.jpg");
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
			String startDateInString = "08-7-2017 00:00:00";
			Date start = sdf.parse(startDateInString);
			
			String endDateInString = "10-7-2017 00:00:00";
			Date end = sdf.parse(endDateInString);
			
			
			assertTrue(photo.wasTakenWithinTime(start, end));
		}
		catch(Exception e){
			fail();
		}
	}
	
	@Test
	public void testIfOutsideTimeFrame(){
		try{
			Photo photo = new Photo(TestConfiguration.testDirectory + "test-photo-1.jpg");
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
			String startDateInString = "08-7-2017 00:00:00";
			Date start = sdf.parse(startDateInString);
			
			String endDateInString = "08-7-2017 23:59:59";
			Date end = sdf.parse(endDateInString);
			
			
			assertFalse(photo.wasTakenWithinTime(start, end));
		}
		catch(Exception e){
			fail();
		}
	}
	
	@Test(expected=PhotoException.class)
	public void timeFrameMissingExifData() throws PhotoException, ImageProcessingException, IOException, ParseException{
		Photo photo = new Photo(TestConfiguration.testDirectory + "non-photo.jpg");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String startDateInString = "08-7-2017 00:00:00";
		Date start = sdf.parse(startDateInString);
		
		String endDateInString = "08-7-2017 23:59:59";
		Date end = sdf.parse(endDateInString);
		
		photo.wasTakenWithinTime(start, end);
	}

}
