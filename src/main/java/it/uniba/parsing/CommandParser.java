package it.uniba.parsing;

import java.util.List;

import it.uniba.parsing.CommandLine.*;

public class CommandParser
{
    public class CommBaseArgs
    {
        private Boolean active = false;
        
        @Option(names = "drop")
        private boolean dropStatus;
        
        @Option(names = "quit")
        private boolean quitStatus;
        
        public Boolean isActive()
        {
            return active;
        }
        
        public Boolean getDropStatus()
        {
            return dropStatus;
        }
        
        public Boolean getQuitStatus()
        {
            return quitStatus;
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
    private CommMembers members;
    private CommChannels channels;
    
    public CommandParser(String[] args)
    {
        baseArgs = new CommBaseArgs();
        load = new CommLoad();
        members = new CommMembers();
        channels = new CommChannels();
        
        CommandLine commandLine = new CommandLine(baseArgs)
                .addSubcommand("load", load)
                .addSubcommand("members", members)
                .addSubcommand("channels", channels);
        
        List<CommandLine> result = commandLine.parse(args);
        
        for(CommandLine x : result)
        {
            if(x.getCommand().getClass() == CommBaseArgs.class)
            {
                baseArgs = (CommBaseArgs) x.getCommand();
                baseArgs.active = true;
            }
            else if(x.getCommand().getClass() == CommLoad.class)
            {
                load = (CommLoad) x.getCommand();
                load.active = true;
            }
            else if(x.getCommand().getClass() == CommMembers.class)
            {
                members = (CommMembers) x.getCommand();
                members.active = true;
            }
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
    
    public CommMembers getCommMembers()
    {
        return members;
    }
    
    public CommChannels getCommChannels()
    {
        return channels;
    }
}
