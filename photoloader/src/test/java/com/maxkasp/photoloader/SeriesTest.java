package com.maxkasp.photoloader;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

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
		Series series = new Series(1, length);
		
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
		Series series = new Series(1, length);
		
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
		Series series = new Series(1, length);
		
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
		int length = 12;
		int partNumber = 1;
		Series series = new Series(partNumber, length);
		String target = "test-save-target";
		File tar = new File(TestConfiguration.testDirectory + target);
		tar.mkdir();
		
		try {
			series.loadPhotos(TestConfiguration.testDirectory + "12-series");
			FilenamePattern pattern = new FilenamePattern("test_$pp$_$ii$.jpg");
			//series.saveSeriesToDisk(new File (TestConfiguration.testDirectory + target), pattern, 3);
			SeriesExporter exporter = new SaveSeriesExporter(new File (TestConfiguration.testDirectory + target), pattern, 3);
			series.exportSeries(exporter);
		} catch (SeriesException | IOException e){
			fail();
		}
		Series newSeries = new Series(partNumber, length);
		try {
			newSeries.loadPhotos(TestConfiguration.testDirectory + target + "/001");
			assertEquals("test_01_01.jpg", newSeries.getPhotoByTopic(1).getImageFile().getName());
			assertEquals("test_01_02.jpg", newSeries.getPhotoByTopic(2).getImageFile().getName());
			assertEquals("test_01_03.jpg", newSeries.getPhotoByTopic(3).getImageFile().getName());
			assertEquals("test_01_04.jpg", newSeries.getPhotoByTopic(4).getImageFile().getName());
			assertEquals("test_01_05.jpg", newSeries.getPhotoByTopic(5).getImageFile().getName());
			assertEquals("test_01_06.jpg", newSeries.getPhotoByTopic(6).getImageFile().getName());
			assertEquals("test_01_07.jpg", newSeries.getPhotoByTopic(7).getImageFile().getName());
			assertEquals("test_01_08.jpg", newSeries.getPhotoByTopic(8).getImageFile().getName());
			assertEquals("test_01_09.jpg", newSeries.getPhotoByTopic(9).getImageFile().getName());
			assertEquals("test_01_10.jpg", newSeries.getPhotoByTopic(10).getImageFile().getName());
			assertEquals("test_01_11.jpg", newSeries.getPhotoByTopic(11).getImageFile().getName());
			assertEquals("test_01_12.jpg", newSeries.getPhotoByTopic(12).getImageFile().getName());
		} catch (SeriesException e) {
			fail();
		}
		
		// delete dir and files
		Path directory = Paths.get(TestConfiguration.testDirectory + target + "/");
		try {
			Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
			   @Override
			   public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			       Files.delete(file);
			       return FileVisitResult.CONTINUE;
			   }

			   @Override
			   public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			       Files.delete(dir);
			       return FileVisitResult.CONTINUE;
			   }
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
