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
		CommandInterpreter interpreter = new CommandInterpreter();

//		for(String x : args)
//		    System.out.println(x + " ");
//		System.out.println(" ");
		
		try
		{
			commandParser = new CommandParser(args);
			if(args.length != 0)
			    interpreter.executeCommands(commandParser, fileParser);
			else
			    interpreter.showHelp();
		}
		catch(Exception e)
		{
		    System.out.println(e.getStackTrace());
			System.out.println("Invalid syntax. Refer to 'help' command");
		}	
		interpreter.getSysws().DictSerial(fileParser.getWorkspaceName(), fileParser);
		interpreter.getSysws().saveWorkspace();
	}
}