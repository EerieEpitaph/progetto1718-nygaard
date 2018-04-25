package it.uniba.main;

import java.io.File;
import java.util.Scanner;

import it.uniba.parsing.CommandParser;
import it.uniba.parsing.ZipParser;

public final class AppMain 
{
	public static void main(final String[] args) 
	{ 
	    String currtWorkspace = new String();
	    Scanner scanLine = new Scanner(System.in);
	    ZipParser fileParser = new ZipParser();
	    
	    //Main loop
	    do
	    {
	        //Printa il workspace caricato
	        System.out.print("(" + currtWorkspace + ") >> ");
	        String[] currParams = scanLine.nextLine().split(" ");
	        
	        CommandParser commander = new CommandParser(currParams);
	        String path = commander.getParsedArgs().getZipFile();
	        boolean sigKill = commander.getParsedArgs().getSigKill();
	        
	        //Comando "quit" invocato
            if(sigKill)
                break;
	        
            //Abbiamo effettivamente invocato il "load" da parametro
	        if(path != null) 
	        {
	            fileParser.load(path);    
	            
	            //Aggiorna il nome del workspace corrente se il comando "load" è andato a buon fine
	            if(fileParser.isSuccessful())
	            {
	                File tempFile = new File(path);
	                String fileName = tempFile.getName();
	                if(fileName != null)
	                    currtWorkspace = fileName;
	                else
	                    currtWorkspace = path;
	            }
	        }     
	    }
	    while(true);
	    
	    scanLine.close();
	}
}