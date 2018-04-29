package it.uniba.main;

import it.uniba.interpreting.CommandInterpreter;
import it.uniba.parsing.CommandParser;
import it.uniba.parsing.ZipParser;

public final class AppMain 
{
	// path zip : /home/phinkie/Downloads/ingsw.zip 
	public static void main(final String[] args) 
	{ 
		ZipParser fileParser = new ZipParser();

		CommandParser commandParser = null;
		CommandInterpreter interpreter = null;

		for(String x : args)
		    System.out.println(x + " ");
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
		interpreter.executeCommands(commandParser, fileParser);
		
		// salvataggio dei dizionari e dei workspace creati 
		//interpreter.getSysws().DictSerial(fileParser.getWorkspaceName(), fileParser);
		//interpreter.getSysws().saveWorkspace();
	}
}