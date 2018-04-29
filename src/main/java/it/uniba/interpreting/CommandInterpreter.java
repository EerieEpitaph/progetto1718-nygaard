package it.uniba.interpreting;

import it.uniba.controller.DataController;
import it.uniba.model.WorkspaceSys;
import it.uniba.parsing.CommandParser;
import it.uniba.parsing.CommandParser.*;
import it.uniba.parsing.ZipParser;

public class CommandInterpreter
{
	WorkspaceSys worksys;

    public void executeCommands(CommandParser parser, ZipParser fileParser)
    {
        CommBaseArgs baseArgs = parser.getBaseArgs();
        CommLoad load = parser.getCommLoad();
        CommWorkspace workspace = parser.getCommWorkspace();
        CommMembers members = parser.getCommMembers();
        CommChannels channels = parser.getCommChannels();
        
         
        //Argomenti singoli immessi
        if(baseArgs.isActive())
        {
            
        }
        
        //load inserito
        else if(load.isActive())
        {
            //Percorso valido
            if(load.getPathToZip() != null)
            {
                fileParser.load(load.getPathToZip());
                worksys = new WorkspaceSys(); 
                //creo la directory nascosta su cui memorizzare a fine esecuzione users e channels 
                worksys.makedirArea(fileParser.getWorkspaceName()); 
            }	
            else
                System.out.println("Invalid syntax. Refer to 'help' command");
        }
        
        //-w nomeWorkspace inserito
        if(workspace.isActive())
        {
            String workspaceName = workspace.getWorkspaceName();
            
            //Tutto tuo, Giova'!
        }
        
        //-m inserito
        if(members.isActive())
        {
            if(fileParser.hasLoaded())
            {
                //Nessun filtro inserito
                if(members.getChannelFilter() == null)
                    DataController.printMembers(fileParser);
                //Canale filtro inserito
                else
                    DataController.channelMembers(fileParser, members.getChannelFilter());
            }
            else
                System.out.println("No workspace loaded");
        }
        
        //-c inserito
        if(channels.isActive())
        {
            if(fileParser.hasLoaded())
            {
                //Channels estesi inseriti
                if(!channels.getExtendedStatus())
                    DataController.printChannels(fileParser);
                //Channels estesi non inseriti
                else
                    DataController.members4Channel(fileParser);
            }
            else
                System.out.println("No workspace loaded");
        }
    }

	public WorkspaceSys getSysws() {
		// TODO Auto-generated method stub
		return worksys;
	}
}
