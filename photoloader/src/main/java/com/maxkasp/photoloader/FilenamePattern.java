package com.maxkasp.photoloader;

import java.util.ArrayList;
import java.util.List;

public class FilenamePattern {
	
	private List<PatternExpansionCommand> commands;
	
	public FilenamePattern(String pattern){
		
		this.commands = new ArrayList<PatternExpansionCommand>();
		
		for(int i = 0; i < pattern.length(); i++){
			
			if(pattern.charAt(i) == '$'){
				// check code behind $
				// add commands to list
				char itr = pattern.charAt(i + 1);
				int count = 0;
				while(itr != '$'){
					count++;
					itr = pattern.charAt(i + count + 1);
				}
				
				if (pattern.charAt(i + 1) == 'i'){
					commands.add(new IndexCommand(count));
				} else if (pattern.charAt(i + 1) == 'p'){
					commands.add(new ParticipantNumberCommand(count));
				}
				
				// shift i by count + 1 to move past end delimiting $
				i += count + 1;
				
			} else {
				commands.add(new CopyCharCommand(pattern.substring(i, i+1)));
			}
		}
	}
	
	public String getFilenameForIndex(int index, int participantNumber){
		StringBuilder str = new StringBuilder();
		
		for(PatternExpansionCommand command : commands){
			str.append(command.expandPattern(index, participantNumber));
		}
		
		return str.toString();
	}

}
