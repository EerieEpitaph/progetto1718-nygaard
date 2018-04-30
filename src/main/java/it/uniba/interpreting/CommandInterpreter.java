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
        CommWorkspace workspace = parser.getCommWorkspace();
        CommMembers members = parser.getCommMembers();
        CommChannels channels = parser.getCommChannels();
        
         
        //Argomenti singoli immessi
        if(baseArgs.isActive())
        {
            if(baseArgs.getShowStatus())
            {
            	worksys = new WorkspaceSys();
            	worksys.shoWorkspace();
            }
            else if(baseArgs.getHelpStatus())
                showHelp();
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
        else if(workspace.isActive())
        {
            String workspaceName = workspace.getWorkspaceName();
            fileParser.setWorkspaceName(workspaceName);
            System.out.println("Nome workspace	" + workspaceName);
            worksys = new WorkspaceSys();
            worksys.lectureDicts(workspaceName, fileParser);
            //Tutto tuo, Giova'!  lettura dei dizionari 
            
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
    
    public void showHelp()
    {
        System.out.println("Usage:");
        System.out.println("\thelp - Shows this help\n");
        System.out.println("\tload \"path\\to\\file.zip\" - Loads and parses a zip file\n");
        System.out.println("\t-w \"workspaceName\" (-c [-m] | -m [-c \"channelFilter\"])");
        System.out.println("\t-w caches a previously loaded workspace");
        System.out.println("\t-c prints all the channels in the specified workspace");
        System.out.println("\t\t [-m prints channels with their members] ");
        System.out.println("\t-m prints all the members in the specified workspace");
        System.out.println("\t\t [-c \"channelFilter\" prints members of a channel]");
    }

	public WorkspaceSys getSysws()
	{
		return worksys;
	}
}
