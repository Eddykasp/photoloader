package com.maxkasp.photoloader;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParticipantNumberCommandTest {
	
	@Test
	public void testNumberFourWithTwoDigits(){
		ParticipantNumberCommand commmand = new ParticipantNumberCommand(2);
		assertEquals("04", commmand.expandPattern(1, 4));
	}

}
