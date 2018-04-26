package it.uniba.parsing;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

public class CommandParser
{
    public class Args 
    {
        @Parameter
        private List<String> parameters = new ArrayList<String>();

        @Parameter(names = { "load", "-l" }, description = "Path of the zip file to load")
        private String zipFile;
        
        @Parameter(names = { "quit", "-q" }, description = "Quits")
        private boolean sigKill = false;
        
        @Parameter(names = { "drop", "-d" }, description = "Drops current workspace")
        private boolean toDrop = false;
        /*
        @Parameter(names = { "members", "-m" }, description = "Prints workspace's members")
        private String groups; //TEMP

        @Parameter(names = { "channels", "-c" }, description = "Prints workspace's channels")
        private boolean s = false; //TEMP
        
        @Parameter(names = { "workspace", "-w" }, description = "Prints the name of the workspace")
        private boolean debug = false; //TEMP
        */
        
        public String getZipFile()
        {
            return zipFile;
        }
        public boolean getSigKill()
        {
            return sigKill;
        }
        public boolean getDrop()
        {
            return toDrop;
        }
    }
    
    private Args arguments;
    
    public CommandParser(String[] parameters)
    {
        arguments = new Args();
        try 
        {
            //Passando un vettore di parametri, il JCommander prova ad interfacciarli con i membri privati della classe "Args" sopra elencati
            //Se un parametro è stato definito, il corrispettivo membro privato viene settato col giusto valore
            JCommander.newBuilder().addObject(arguments).build().parse(parameters);        
        }
        catch(ParameterException e)
        {System.out.println(e.getMessage());}

    }
    
    public Args getParsedArgs()
    {
        return arguments;
    }
}
