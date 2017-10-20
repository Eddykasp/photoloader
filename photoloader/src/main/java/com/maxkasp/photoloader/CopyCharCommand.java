package com.maxkasp.photoloader;

public class CopyCharCommand implements PatternExpansionCommand {

	private String chr;
	
	public CopyCharCommand(String c){
		this.chr = c;
	}
	
	@Override
	public String expandPattern(int photoIndex, int participantNumber) {
		// TODO Auto-generated method stub
		return chr;
	}

}
