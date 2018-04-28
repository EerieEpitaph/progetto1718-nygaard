package it.uniba.interpreting;

import java.io.File;

import it.uniba.controller.DataController;
import it.uniba.controller.FlowController;
import it.uniba.parsing.CommandParser;
import it.uniba.parsing.CommandParser.*;
import it.uniba.parsing.ZipParser;

public class CommandInterpreter
{
    public FlowController executeCommands(CommandParser parser, FlowController control)
    {
        FlowController toReturn = control;
        
        CommBaseArgs baseArgs = parser.getBaseArgs();
        CommLoad load = parser.getCommLoad();
        CommMembers members = parser.getCommMembers();
        CommChannels channels = parser.getCommChannels();
        
        if(baseArgs.isActive())
        {
            if(baseArgs.getQuitStatus())
                toReturn.setQuitStatus(true);

            else if(baseArgs.getDropStatus())
                toReturn = dropWorkspace(toReturn);
        }
        
        if(load.isActive())
        {
            if(load.getPathToZip() != null)
                toReturn = loadWorkspace(load.getPathToZip(), toReturn);   
        }
        
        else if(members.isActive())
        {
            if(members.getChannelFilter() == null)
                DataController.printMembers(toReturn.getFileParser());
            else
                DataController.channelMembers(toReturn.getFileParser(), members.getChannelFilter());
        }
        
        else if(channels.isActive())
        {
            if(!channels.getExtendedStatus())
                DataController.printChannels(toReturn.getFileParser());
            else
                DataController.members4Channel(toReturn.getFileParser());
        }
        
        return toReturn;
    }
    
    private FlowController dropWorkspace(FlowController control)
    {
        if(!control.getFileParser().hasLoaded())
            System.out.println("No workspace to drop");
        else
        {
            control.setFileParser(new ZipParser());
            control.setCurrWorkspace("");
        }
        
        return control;
    }
    
    private FlowController loadWorkspace(String path, FlowController control)
    {
        control.getFileParser().load(path);    
        
        //Aggiorna il nome del workspace corrente if il comando "load" e' andato a buon fine
        if(control.getFileParser().hasLoaded())
        {
            File tempFile = new File(path);
            String fileName = tempFile.getName();
            if(fileName != null)
                control.setCurrWorkspace(fileName);
            else
                control.setCurrWorkspace(path);
        }
        
        return control;
    }
}
