package it.uniba.parsing;

import java.util.List;

import it.uniba.parsing.CommandLine.*;

public class CommandParser
{
    class BaseArgs
    {
        @Option(names = "quit")
        private boolean quitStatus;
    }
    
    class Members
    {
        @Option(names = "-c", arity = "0..1", description = "Optional filtering by channel")
        private String filterBy;
    }
    
    class Channels
    {
        @Option(names = "-m", arity = "0..1", description = "Optional printing of channels and associated members")
        private boolean extendedChannels;
    }
    
    
    public CommandParser(String[] args)
    {
        BaseArgs baseArgs = new BaseArgs();
        Members members = new Members();
        Channels channels = new Channels();
        CommandLine commandLine = new CommandLine(baseArgs)
                .addSubcommand("members", members)
                .addSubcommand("channels", channels);
        
        List<CommandLine> parsed = commandLine.parse(args);
        
        for(CommandLine x : parsed)
        System.out.println(x.getCommand().getClass());
        
    }
}
