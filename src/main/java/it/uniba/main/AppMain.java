package it.uniba.main;

import it.uniba.controller.FlowController;
import it.uniba.interpreting.CommandInterpreter;
import it.uniba.parsing.CommandParser;
import it.uniba.parsing.ZipParser;

public final class AppMain 
{
	// path zip : /home/phinkie/Downloads/ingsw.zip 
	public static void main(final String[] args) 
	{ 
		FlowController control = new FlowController("", false, new ZipParser());

		CommandParser commandParser = null;
		CommandInterpreter interpreter = null;

		for(String x : args)
		    System.out.print(x + " ");
		System.out.println(" ");
		
		try
		{
			commandParser = new CommandParser(args);
		}
		catch(Exception e)
		{
			System.out.println("Invalid syntax. Refer to 'help' command");
		}

		interpreter = new CommandInterpreter();
		control = interpreter.executeCommands(commandParser, control);
		
		// salvataggio dei dizionari e dei workspace creati 
		interpreter.getSysws().DictSerial(control.getCurrWorkspace(), control.getFileParser());
		interpreter.getSysws().saveWorkspace();
	}
}