package com.maxkasp.photoloader;

import static org.junit.Assert.*;

import org.junit.Test;

public class FilenamePatternTest {
	
	@Test
	public void testSimpleStringNoExpansions(){
		FilenamePattern pattern = new FilenamePattern("aaaaaa");
		assertEquals("aaaaaa", pattern.getFilenameForIndex(0, 1));
	}
	
	@Test
	public void testIndexPatternExpansion(){
		FilenamePattern pattern = new FilenamePattern("$iii$");
		assertEquals("003", pattern.getFilenameForIndex(3, 1));
	}
	
	@Test
	public void testIndexPlusSimplePatternExpansion(){
		FilenamePattern pattern = new FilenamePattern("part_$ii$_p");
		assertEquals("part_02_p", pattern.getFilenameForIndex(2, 1));
	}
	
	@Test
	public void testParticpantPatternExpansion(){
		FilenamePattern pattern = new FilenamePattern("$pp$");
		assertEquals("03", pattern.getFilenameForIndex(1, 3));
	}

}
