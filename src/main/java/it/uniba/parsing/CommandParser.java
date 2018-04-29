package it.uniba.parsing;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.uniba.parsing.CommandLine.*;

public class CommandParser
{
    //Comandi booleani senza parametri
    public class CommBaseArgs
    {
        private Boolean active = false;
        
        @Option(names = "show")
        private boolean showStatus;
        
        public Boolean isActive()
        {
            return active;
        }
        
        public Boolean getShowStatus()
        {
            return showStatus;
        }
    }
    
    public class CommLoad
    {
        private Boolean active = false;
        
        @Parameters(index = "0")    
        String pathToZip;

        public Boolean isActive()
        {
            return active;
        }
        
        public String getPathToZip()
        {
            return pathToZip;
        }
    }
    
    public class CommWorkspace
    {
        private Boolean active = false;
        
        @Parameters(index = "0")
        String workspaceName;
        
        public Boolean isActive()
        {
            return active;
        }
        
        public String getWorkspaceName()
        {
            return workspaceName;
        }
    }
    
    public class CommMembers
    {
        private Boolean active = false;
        
        @Option(names = "-c", arity = "0..1", description = "Optional filtering by channel")
        private String filterBy;

        public Boolean isActive()
        {
            return active;
        }
        
        public String getChannelFilter()
        {
            return filterBy;
        }
    }
    
    public class CommChannels
    {
        private Boolean active = false;
        
        @Option(names = "-m", arity = "0..1", description = "Optional printing of channels and associated members")
        private boolean extendedStatus;

        public Boolean isActive()
        {
            return active;
        }
        
        public Boolean getExtendedStatus()
        {
            return extendedStatus;
        }
    }
    
    private CommBaseArgs baseArgs;
    private CommLoad load;
    private CommWorkspace workspace;
    private CommMembers members;
    private CommChannels channels;
    
    public CommandParser(String[] args)
    {
        baseArgs = new CommBaseArgs();
        load = new CommLoad();
        workspace = new CommWorkspace();
        members = new CommMembers();
        channels = new CommChannels();
        
        CommandLine commandLine = new CommandLine(baseArgs)
                .addSubcommand("load", load)
                .addSubcommand("-w", workspace)
                .addSubcommand("-m", members)
                .addSubcommand("-c", channels);
        
        List<CommandLine> result = commandLine.parse(args);
        
        for(CommandLine x : result)
        {
            //Gli "argomenti base" sarebbero sempre true, per com'è strutturata la libreria.
            //In questo if setto la loro attività = true solo se, usando la riflessione, uno dei loro field è true
            if(x.getCommand().getClass() == CommBaseArgs.class)
            {
                baseArgs = (CommBaseArgs) x.getCommand();
                
                ArrayList<Field> baseFields = new ArrayList<Field>(Arrays.asList(CommBaseArgs.class.getDeclaredFields()));
                baseFields.remove(0);
                baseFields.remove(baseFields.size()-1);
                
                for(Field y : baseFields)
                {
//                    System.out.println(y.getName());
                    y.setAccessible(true);
                    try
                    {
                        if(y.get(baseArgs).toString().equals("true"))
                        {
                            baseArgs.active = true;
                            y.setAccessible(false);
                            break;
                        }
                        y.setAccessible(false);
                    } 
                    catch (IllegalArgumentException e)
                    {e.printStackTrace();} catch (IllegalAccessException e)
                    {e.printStackTrace();}
                }
            }
            
            //Il comando parsato è "load"
            else if(x.getCommand().getClass() == CommLoad.class)
            {
                load = (CommLoad) x.getCommand();
                load.active = true;
            }
            
            //Il comando parsato è "-w"
            else if(x.getCommand().getClass() == CommWorkspace.class)
            {
                workspace = (CommWorkspace) x.getCommand();
                workspace.active = true;
            }
            
            //Il comando parsato è "-m"
            else if(x.getCommand().getClass() == CommMembers.class)
            {
                members = (CommMembers) x.getCommand();
                members.active = true;
            }
            
            //Il comando parsato è "-c"
            else if(x.getCommand().getClass() == CommChannels.class)
            {
                channels = (CommChannels) x.getCommand();
                channels.active = true;
            }
        }
    }
    
    public CommBaseArgs getBaseArgs()
    {
        return baseArgs;
    }
    
    public CommLoad getCommLoad()
    {
        return load;
    }
    
    public CommWorkspace getCommWorkspace()
    {
        return workspace;
    }
    
    public CommMembers getCommMembers()
    {
        return members;
    }
    
    public CommChannels getCommChannels()
    {
        return channels;
    }
}
