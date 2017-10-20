package com.maxkasp.photoloader;

public class ParticipantNumberCommand implements PatternExpansionCommand {

	private int digits;
	
	public ParticipantNumberCommand(int d){
		if(d < 1){
			throw new IllegalArgumentException("Number of digits must be greater than 0");
		}
		digits = d;
	}
	
	@Override
	public String expandPattern(int photoIndex, int participantNumber) {
		return String.format("%0" + digits + "d", participantNumber);
	}

}
