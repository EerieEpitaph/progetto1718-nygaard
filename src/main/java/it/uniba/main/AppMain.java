package it.uniba.main;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import com.beust.jcommander.ParameterException;

import it.uniba.controller.Controller;
import it.uniba.parsing.CommandParser;
import it.uniba.parsing.ZipParser;

public final class AppMain 
{
	public static void main(final String[] args) 
	{ 
	    String currWorkspace = new String();
	    Scanner scanLine = new Scanner(System.in);
	    ZipParser fileParser = new ZipParser();
	    
	    //Main loop
	    do
	    {
	        //Printa il workspace caricato
	        System.out.print("(" + currWorkspace + ") >> ");
	        //Regex per ignorare gli spazi tra quotes ("Tipo questo")
	        String[] currParams = scanLine.nextLine().split("\\s(?=(?:[^'\"`]*(['\"`])[^'\"`]*\\1)*[^'\"`]*$)");
	        
	        CommandParser commander;
	        try
	        {
//	            for(String x : currParams)
//	                System.out.println(x);
	            
	            //Valida gli argomenti, riesegue il loop se trova discordanze
	            commander = new CommandParser(currParams);
                commander.validateArguments();
	        }
	        catch(ParameterException e)
	        {
	            System.out.println(e.getMessage());
	            continue;
	        }
	        catch(AssertionError e)
	        {
	            System.out.println("Invalid syntax. Refer to 'help' command");
	            continue;
	        }
	        
	        String path = commander.getSingleArgs().getZipFile();
	        Boolean sigKill = commander.getSingleArgs().getSigKill();
	        Boolean toDrop = commander.getSingleArgs().getDrop();
	        String channelFilter = commander.getCommandUsers().getFilterChannel();
	        
	        
	        //Comando "quit" invocato
            if(sigKill)
                break;
            
            //Abbiamo chiesto il drop del workspace
            if(toDrop)
            {
                if(!fileParser.hasLoaded())
                    System.out.println("No workspace to drop");
                else
                {
                    fileParser = new ZipParser();
                    currWorkspace = "";            
                }
            }
	        
            //Abbiamo effettivamente invocato il "load" da parametro
	        if(path != null) 
	        {
//	            System.out.println(path);
	            fileParser.load(path);    
	            
	            //Aggiorna il nome del workspace corrente if il comando "load" è andato a buon fine
	            if(fileParser.hasLoaded())
	            {
	                File tempFile = new File(path);
	                String fileName = tempFile.getName();
	                if(fileName != null)
	                    currWorkspace = fileName;
	                else
	                    currWorkspace = path;
	            }
	        }
	        
	        //Abbiamo richiesto il print dei canali
//	        if(channels)
//	        {
//	            Controller.printChannels(fileParser);
//	        }
	        
	    }
	    while(true);
	    
	    scanLine.close();
	}
}