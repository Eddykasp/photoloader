package com.maxkasp.photoloader;

import static org.junit.Assert.*;

import org.junit.Test;

public class IndexCommandTest {
	
	@Test
	public void testTwoDigitsWithIndexThree(){
		IndexCommand command = new IndexCommand(2);
		assertEquals("03", command.expandPattern(3, 1));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testZeroDigits(){
		IndexCommand command = new IndexCommand(0);
	}
	
	@Test
	public void testIndexHigherThanDigits(){
		IndexCommand command = new IndexCommand(2);
		assertEquals("123", command.expandPattern(123, 1));
	}

}
