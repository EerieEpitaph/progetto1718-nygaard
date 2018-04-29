package it.uniba.interpreting;

import java.io.File;

import it.uniba.controller.DataController;
import it.uniba.controller.FlowController;
import it.uniba.model.WorkspaceSys;
import it.uniba.parsing.CommandParser;
import it.uniba.parsing.CommandParser.*;
import it.uniba.parsing.ZipParser;

public class CommandInterpreter
{
	WorkspaceSys worksys;
	
	 
	
    public FlowController executeCommands(CommandParser parser, FlowController control)
    {
        FlowController newControl = control;
        
        CommBaseArgs baseArgs = parser.getBaseArgs();
        CommLoad load = parser.getCommLoad();
        CommMembers members = parser.getCommMembers();
        CommChannels channels = parser.getCommChannels();
        
         
        //Argomenti singoli immessi
        if(baseArgs.isActive())
        {
            if(baseArgs.getQuitStatus())
                newControl.setQuitStatus(true);

            else if(baseArgs.getDropStatus())
                newControl = dropWorkspace(newControl);
        }
        
        //Load inserito
        if(load.isActive())
        {
            //Percorso valido
            if(load.getPathToZip() != null)
            {
                newControl = loadWorkspace(load.getPathToZip(), newControl);   
                worksys = new WorkspaceSys(); 
                
                //creo la directory nascosta su cui memorizzare a fine esecuzione users e channels 
                worksys.makedirArea(newControl.getCurrWorkspace()); 
            }	
        }
        
        //Members inserito
        else if(members.isActive())
        {
            if(control.getFileParser().hasLoaded())
            {
                if(members.getChannelFilter() == null)
                    DataController.printMembers(newControl.getFileParser());
                else
                    DataController.channelMembers(newControl.getFileParser(), members.getChannelFilter());
            }
            else
                System.out.println("No workspace loaded");
        }
        
        //Channels inserito
        else if(channels.isActive())
        {
            if(control.getFileParser().hasLoaded())
            {
                if(!channels.getExtendedStatus())
                    DataController.printChannels(newControl.getFileParser());
                else
                    DataController.members4Channel(newControl.getFileParser());
            }
            else
                System.out.println("No workspace loaded");
        }
        
        return newControl;
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
            // ## attenzione a questi scope 
            if(fileName != null)
                control.setCurrWorkspace(fileName);
            else
                control.setCurrWorkspace(path);
        }
        
        return control;
    }

	public WorkspaceSys getSysws() {
		// TODO Auto-generated method stub
		return worksys;
	}
}
