package it.uniba.main;

import java.util.Scanner;

import it.uniba.controller.FlowController;
import it.uniba.interpreting.CommandInterpreter;
import it.uniba.parsing.CommandParser;
import it.uniba.parsing.ZipParser;

public final class AppMain 
{
	public static void main(final String[] args) 
	{ 
	    Scanner scanLine = new Scanner(System.in);
	    FlowController control = new FlowController("", false, new ZipParser());
	    
	    CommandParser commandParser = null;
        CommandInterpreter interpreter = null;
	    
	    //Main loop
	    do
	    {
	        //Printa il workspace caricato
	        System.out.print("(" + control.getCurrWorkspace() + ") >> ");
	        //Regex per ignorare gli spazi tra quotes ("Tipo questo")
	        String[] currParams = scanLine.nextLine().split("\\s(?=(?:[^'\"`]*(['\"`])[^'\"`]*\\1)*[^'\"`]*$)");
	        
	        try
	        {
	            //Valida gli argomenti, riesegue il loop se trova discordanze
	            commandParser = new CommandParser(currParams);
	        }
	        catch(Exception e)
	        {
	            System.out.println("Invalid syntax. Refer to 'help' command");
	            continue;
	        }
	        
	        interpreter = new CommandInterpreter();
	        control = interpreter.executeCommands(commandParser, control);
	    }
	    while(!control.getQuitStatus());
	    scanLine.close();
	}
}