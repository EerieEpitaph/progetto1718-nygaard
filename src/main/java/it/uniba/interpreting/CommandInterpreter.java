package it.uniba.interpreting;

import java.io.IOException;
import java.util.zip.ZipException;

import it.uniba.controller.DataController;
import it.uniba.parsing.CommandParser;
import it.uniba.parsing.CommandParser.*;
import it.uniba.parsing.ZipParser;

public class CommandInterpreter
{
    public void executeCommands(CommandParser parser, ZipParser fileParser) throws ZipException, IOException
    {
        CommBaseArgs baseArgs = parser.getBaseArgs();
        CommWorkspace workspace = parser.getCommWorkspace();
         
        //Argomenti singoli immessi
        if(baseArgs.isActive())
        {
            //Ho chiesto help
            if(baseArgs.getHelpStatus())
                showHelp();
        }
        
        //-w nomeWorkspace inserito
        else if(workspace.isActive())
        {
            //nomeWorkspace valido
            if(workspace.isValidWorkspace())
            {
                String workspaceName = workspace.getWorkspaceName();
                fileParser.load(workspaceName);

                //Fileparser ha caricato qualcosa
                if(fileParser.hasLoaded())
                {
                    //-u inserito
                    if(workspace.getMembersStatus())
                        DataController.printMembers(fileParser);
                    
                    //-c inserito
                    else if(workspace.getChannelsStatus())
                        DataController.printChannels(fileParser);
                    
                    //-cu inserito
                    else if(workspace.getExtChannelsStatus())
                        DataController.members4Channel(fileParser);
                    
                    //-uc inserito
                    else if(workspace.isValidFilter())
                        DataController.channelMembers(fileParser, workspace.getChannelFilter());
                    
                    //-m riconosciuto
                    else if(workspace.getMentionParams() != null)
                    {
                        //-m
                        if(workspace.getMentionParams().length == 0)
                        {
                            //TODO
                        }
                        
                        //-m from x 
                        else if(wantsFrom(workspace.getMentionParams()))
                        {
                            String fromWho = workspace.getMentionParams()[1];
                            //TODO
                        }
                        
                        //-m to x
                        else if(wantsTo(workspace.getMentionParams()))
                        {
                            String toWho = workspace.getMentionParams()[1];
                            //TODO
                        }
                        
                        //-m in x
                        else if(wantsIn(workspace.getMentionParams()))
                        {
                            String inChannel = workspace.getMentionParams()[1];
                            //TODO
                        }
                        
                        //-m from x in y
                        else if(wantsFromIn(workspace.getMentionParams()))
                        {
                            String fromWho = workspace.getMentionParams()[1];
                            String inChannel = workspace.getMentionParams()[3];
                            //TODO
                        }
                        
                        //-m to x in y
                        else if(wantsToIn(workspace.getMentionParams()))
                        {
                            String toWho = workspace.getMentionParams()[1];
                            String inChannel = workspace.getMentionParams()[3];
                            //TODO
                        }
                        else
                            throw new IllegalStateException();
                    }
                    else
                        throw new IllegalStateException();
                }
                else throw new IllegalStateException();
            }
            else
                throw new IllegalStateException();
        }
        else
            throw new IllegalStateException();
    }
    
    
    public void showHelp()
    {
        System.out.println("Usage:");
        System.out.println("help - shows this help\n");
        System.out.println("-w \"path\\to\\file.zip\" ( -c | -u | -uc (channelFilter) | -cu )");
        System.out.println("-\tw parses a workspace");
        System.out.println("-\tc prints all the channels in the specified workspace");
        System.out.println("-\tu prints all the users in the specified workspace");
        System.out.println("-\tuc (channelFilter) prints all the users in the specified channel ");
        System.out.println("-\tcu prints all the channels with their users\n");
    }
    
    private Boolean wantsFrom(String[] mentionParams)
    {
        if( (mentionParams.length == 2) 
            && mentionParams[0].equals("from") && mentionParams[1] != null)
            return true;
        else
            return false;
    }
    
    private Boolean wantsTo(String[] mentionParams)
    {
        if( (mentionParams.length == 2)
                && mentionParams[0].equals("to") && mentionParams[1] != null)
            return true;
        else
            return false;
    }
    
    private Boolean wantsIn(String[] mentionParams)
    {
        if( (mentionParams.length == 2)
                && mentionParams[0].equals("in") && mentionParams[1] != null)
            return true;
        else
            return false;
    }
    
    private Boolean wantsFromIn(String[] mentionParams)
    {
        if((mentionParams.length == 4)     &&
           mentionParams[0].equals("from") && mentionParams[1] != null && 
           mentionParams[2].equals("in")   && mentionParams[1] != null)
            return true;
        else
            return false;
    }
    
    private Boolean wantsToIn(String[] mentionParams)
    {
        if((mentionParams.length == 4)   &&
           mentionParams[0].equals("to") && mentionParams[1] != null && 
           mentionParams[2].equals("in") && mentionParams[1] != null)
            return true;
        else
            return false;
    }
}
