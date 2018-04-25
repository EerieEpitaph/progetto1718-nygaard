package it.uniba.parsing;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class CommandParser
{
    public class Args 
    {
        @Parameter
        private List<String> parameters = new ArrayList<String>();

        @Parameter(names = { "load", "-l" }, description = "Path of the zip file to load")
        private String zipFile;
        /*
        @Parameter(names = { "members", "-m" }, description = "Prints workspace's members")
        private String groups; //TEMP

        @Parameter(names = { "channels", "-c" }, description = "Prints workspace's channels")
        private boolean s = false; //TEMP
        
        @Parameter(names = { "workspace", "-w" }, description = "Prints the name of the workspace")
        private boolean debug = false; //TEMP
        
        @Parameter(names = { "quit", "-q" }, description = "Quits")
        private boolean sad = false; //TEMP
        */
        
        public String getZipFile()
        {
            return zipFile;
        }
    }
    
    private Args arguments;
    
    public CommandParser(String[] parameters)
    {
        arguments = new Args();
        JCommander.newBuilder().addObject(arguments).build().parse(parameters);
        System.out.println(arguments.zipFile);
    }
    
    public Args getParsedArgs()
    {
        return arguments;
    }
}
