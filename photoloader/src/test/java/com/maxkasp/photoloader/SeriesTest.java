package com.maxkasp.photoloader;

import static org.junit.Assert.*;

import org.junit.Test;

public class SeriesTest {
	
	@Test
	public void testConstructor(){
		Series series = new Series(1);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testEmptySeriesGetPhoto(){
		Series series = new Series(1);
		series.getPhotoByTopic(1);
	}
	
	@Test(expected=SeriesException.class)
	public void testLoadPhotosPassingFilepath() throws SeriesException{
		Series series = new Series(1);
		series.loadPhotos(TestConfiguration.testDirectory + "test-photo-1.jpg");
	}
	
	@Test
	public void testLoadTwelvePhotosFromDirectory(){
		int length = 12;
		Series series = new Series(length);
		
		try {
			series.loadPhotos(TestConfiguration.testDirectory + "12-identical");
			for(int i = 1; i <= length; i++){
				assertNotNull(series.getPhotoByTopic(i));
			}
		} catch (SeriesException e) {
			fail();
		}
	}
	
	@Test
	public void testLoadTwelvePhotosFromDirectoryCapitalFileEndings(){
		int length = 12;
		Series series = new Series(length);
		
		try {
			series.loadPhotos(TestConfiguration.testDirectory + "12-series");
			for(int i = 1; i <= length; i++){
				assertNotNull(series.getPhotoByTopic(i));
			}
		} catch (SeriesException e) {
			fail();
		}
	}
	
	@Test
	public void testLoadTwelvePhotosAndSort(){
		int length = 12;
		Series series = new Series(length);
		
		try {
			series.loadPhotos(TestConfiguration.testDirectory + "12-series");
			assertEquals("hall-1.JPG", series.getPhotoByTopic(1).getImageFile().getName());
			assertEquals("fog-2.JPG", series.getPhotoByTopic(2).getImageFile().getName());
			assertEquals("old-building-3.JPG", series.getPhotoByTopic(3).getImageFile().getName());
			assertEquals("statue-4.JPG", series.getPhotoByTopic(4).getImageFile().getName());
			assertEquals("forest-5.JPG", series.getPhotoByTopic(5).getImageFile().getName());
			assertEquals("inside-outside-6.JPG", series.getPhotoByTopic(6).getImageFile().getName());
			assertEquals("cars-7.JPG", series.getPhotoByTopic(7).getImageFile().getName());
			assertEquals("shops-8.JPG", series.getPhotoByTopic(8).getImageFile().getName());
			assertEquals("korean-bbq-9.JPG", series.getPhotoByTopic(9).getImageFile().getName());
			assertEquals("sauce-10.JPG", series.getPhotoByTopic(10).getImageFile().getName());
			assertEquals("thomas-11.JPG", series.getPhotoByTopic(11).getImageFile().getName());
			assertEquals("meat-12.JPG", series.getPhotoByTopic(12).getImageFile().getName());
		} catch (SeriesException e){
			fail();
		}
	}
	
	@Test
	public void testSaveTwelveSeries(){
		fail();
	}
	

}
