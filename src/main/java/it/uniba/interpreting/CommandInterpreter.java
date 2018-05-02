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
            if(workspace.getWorkspaceName() != null)
            {
                String workspaceName = workspace.getWorkspaceName();
                fileParser.load(workspaceName);

                //Fileparser ha caricato qualcosa
                if(fileParser.hasLoaded())
                {
                    //-m inserito
                    if(workspace.getMembersStatus())
                        DataController.printMembers(fileParser);
                    
                    //-c inserito
                    else if(workspace.getChannelsStatus())
                        DataController.printChannels(fileParser);
                    
                    //-cm inserito
                    else if(workspace.getExtChannelsStatus())
                        DataController.members4Channel(fileParser);
                    
                    //-mc inserito
                    else if(workspace.getChannelFilter() != null)
                        DataController.channelMembers(fileParser, workspace.getChannelFilter());
                    else
                        throw new IllegalStateException();
                }
                else
                    throw new IllegalStateException();
            }
            else
                throw new IllegalStateException();
        }
    }
    
    public void showHelp()
    {
        System.out.println("Usage:");
        System.out.println("help - shows this help\n");
        System.out.println("-w \"\\path\\to\\file.zip\" ( -c | -m | -mc channelFilter | -cm )");
        System.out.println("-\tw parses a workspace");
        System.out.println("-\tc prints all the channels in the specified workspace");
        System.out.println("-\tm prints all the members in the specified workspace");
        System.out.println("-\tmc channelFilter prints all the members in the specified channel ");
        System.out.println("-\tcm prints all the channels with their members\n");
    }
}
