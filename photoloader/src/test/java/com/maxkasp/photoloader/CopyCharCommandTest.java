package com.maxkasp.photoloader;

import static org.junit.Assert.*;

import org.junit.Test;

public class CopyCharCommandTest {
	
	@Test
	public void testCopyingStringOfLengthOne(){
		CopyCharCommand command = new CopyCharCommand("t");
		assertEquals("t", command.expandPattern(0, 1));
	}

}
