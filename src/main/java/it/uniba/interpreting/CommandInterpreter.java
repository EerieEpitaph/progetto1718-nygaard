package it.uniba.interpreting;

import it.uniba.controller.DataController;
import it.uniba.model.WorkspaceSys;
import it.uniba.parsing.CommandParser;
import it.uniba.parsing.CommandParser.*;
import it.uniba.parsing.ZipParser;

public class CommandInterpreter
{
	WorkspaceSys worksys; // Istanza del fileSystem corrente dell'applicativo #### 

    public void executeCommands(CommandParser parser, ZipParser fileParser)
    {
        CommBaseArgs baseArgs = parser.getBaseArgs();
        CommLoad load = parser.getCommLoad();
        CommDelete delete = parser.getCommDelete();
        CommWorkspace workspace = parser.getCommWorkspace();
         
        //Argomenti singoli immessi
        if(baseArgs.isActive())
        {
            //Ho chiesto lo show
            if(baseArgs.getShowStatus())
            {
            	worksys = new WorkspaceSys();
            	worksys.shoWorkspace();
            }
            
            //Ho chiesto help
            else if(baseArgs.getHelpStatus())
                showHelp();
        }
        
        //-l inserito
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
                throw new IllegalStateException();
        }
        
        //-d nomeWorkspace inserito
        else if(delete.isActive())
        {
            //Ho specificato un workspace
            if(delete.getWorkspaceName() != null)
            {
                //Elimina il workspace
            }
            else throw new IllegalStateException();
        }
        
        //-w nomeWorkspace inserito
        else if(workspace.isActive())
        {
            //nomeWorkspace valido
            if(workspace.getWorkspaceName() != null)
            {
                String workspaceName = workspace.getWorkspaceName();
                fileParser.setWorkspaceName(workspaceName);
                System.out.println("Nome workspace  " + workspaceName);
                worksys = new WorkspaceSys();
                worksys.lectureDicts(workspaceName, fileParser);

                //Fileparser ha caricato qualcosa
                if(fileParser.hasLoaded())
                {
                    //-m inserito
                    if(workspace.getMembersStatus())
                        DataController.printMembers(fileParser);
                    
                    //-c inserito
                    else if(workspace.getChannelsStatus())
                        DataController.printChannels(fileParser);
                    
                    //-m -c inserito
                    else if(workspace.getChannelFilter() != null)
                        DataController.channelMembers(fileParser, workspace.getChannelFilter());
                    
                    //-c -m inserito
                    else if(workspace.getExtChannelsStatus())
                        DataController.members4Channel(fileParser);
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
        System.out.println("show - shows all loaded workspaces\n");
        System.out.println("-l \"path\\to\\file.zip\" - loads and parses a zip file\n");
        System.out.println("-w \"workspaceName\" ( -c | -m | -mc \"channelFilter\" | -cm )");
        System.out.println("-\tw caches a previously loaded workspace");
        System.out.println("-\tc prints all the channels in the specified workspace");
        System.out.println("-\tm prints all the members in the specified workspace");
        System.out.println("-\tmc \"channelFilter\" prints all the members in the specified channel ");
        System.out.println("-\tcm prints all the channels with their members\n");
        System.out.println("-d \"workspaceName\" - deletes a loaded workspace");
    }

	public WorkspaceSys getSysws()
	{
		return worksys;
	}
}
