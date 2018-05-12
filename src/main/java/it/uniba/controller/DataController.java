package it.uniba.controller;

import it.uniba.workdata.*;
import it.uniba.parsing.ZipParser;

public class DataController
{
    public static void printChannels(ZipParser fileParser)
    {
        for(Channel x : fileParser.getChannels().values())
            System.out.println(x.getName());
    }
     
    public static void printMembers(ZipParser fileParser)
    {
         for( User utente : fileParser.getUsers().values())
             System.out.println(utente.getRealName() +"\t@"+  utente.getName());
    }

    public static void members4Channel(ZipParser fileParser)
    {
    	for (Channel canale : fileParser.getChannels().values())
    	{
    		System.out.print(" + " + canale.getName() + "\n\t");
    		for(String membro : canale.getMemberList())
    		{
    			User utente = fileParser.getUsers().get(membro);
    			System.out.print(" - " + utente.getRealName() + "\t@ " + utente.getName() + "\n\t");
    		}
    		System.out.println();
    	}
    }
    
    public static void channelMembers(ZipParser fileParser, String channel)
    {
        if(fileParser.getChannels().containsKey(channel))
        {
            System.out.print(" + " + channel + "\n");
            
            for(String key : fileParser.getChannels().get(channel).getMemberList()) // key from value 
            {
                User utente = fileParser.getUsers().get(key);
                System.out.print("\t -" + utente.getRealName() + " @" + utente.getName() + "\n");
            }
            System.out.println();
        }
        else
            System.out.println("There is no channel \""+ channel + "\"");
    }
    
    public static void printMention(ZipParser fileParser, String inChannel)
    {
    	// channel diverso da "" 
    	if(inChannel.equals(""))
    	{
    		fileParser.getMentionGraph().parseMessages(fileParser.getMessages(), fileParser.getUsers(),"");
    		fileParser.getMentionGraph().printEdges(null);
    	}
    	else
    	{ 	// validazione canale 
    		if(fileParser.getChannels().containsKey(inChannel))
    		{
    			fileParser.getMentionGraph().parseMessages(fileParser.getMessages(), fileParser.getUsers(), inChannel);
    			fileParser.getMentionGraph().printEdges(null);
    		}
    		else 
    		{
    			System.out.println("The channel specified doesn't exist.");
    		}
    	}
    } 

    // #38 
    public static void mentionsFromUser(ZipParser fileParser, String user, String inChannel)
    {
    	String idUser = getUserFromId(fileParser, user);
    	if(inChannel.equals(""))
    	{
    		if(fileParser.getUsers().containsKey(idUser)) // l'utente esiste nel workspace
    		{
    			fileParser.getMentionGraph().parseMessages(fileParser.getMessages(), fileParser.getUsers(), ""); // parse dei mention sul grafo
    			fileParser.getMentionGraph().printEdges(fileParser.getUsers().get(idUser));
    		}
    		else
    		{
				System.out.println("The user specified doesn't exist.");    			
    		}
    	}
    	else
    	{
    		if(fileParser.getUsers().containsKey(idUser) && fileParser.getChannels().containsKey(inChannel))
    		{
    			fileParser.getMentionGraph().parseMessages(fileParser.getMessages(), fileParser.getUsers(), inChannel); // parse dei mention sul grafo
    			fileParser.getMentionGraph().printEdges(fileParser.getUsers().get(idUser));
    		}
    		else 
    		{
    			if(!fileParser.getUsers().containsKey(idUser))
    				System.out.println("The user specified doesn't exist.");
				if(!fileParser.getChannels().containsKey(inChannel))
					System.out.println("The channel specified doesn't exist.");    			
    		}
    	}
    }
    
    // #39 
    public static void mentionsToUser(ZipParser fileParser, String user, String inChannel)
    {
    	String idUser = getUserFromId(fileParser, user);
    	if(inChannel.equals(""))
    	{
    		if(fileParser.getUsers().containsKey(idUser)) // l'utente esiste nel workspace
    		{
    			fileParser.getMentionGraph().parseMessages(fileParser.getMessages(), fileParser.getUsers(), ""); // parse dei mention sul grafo
     			fileParser.getMentionGraph().printEdgesInDegree(fileParser.getUsers().get(idUser));
    		}
    		else
    		{
				System.out.println("The user specified doesn't exist.");    			
    		}
    	}
    	else
    	{
    		if(fileParser.getUsers().containsKey(idUser) && fileParser.getChannels().containsKey(inChannel))
    		{
    			fileParser.getMentionGraph().parseMessages(fileParser.getMessages(), fileParser.getUsers(), inChannel); // parse dei mention sul grafo
    			fileParser.getMentionGraph().printEdgesInDegree(fileParser.getUsers().get(idUser));
    		}
    		else 
    		{
    			if(!fileParser.getUsers().containsKey(idUser))
    				System.out.println("The user specified doesn't exist.");
				if(!fileParser.getChannels().containsKey(inChannel))
					System.out.println("The channel specified doesn't exist.");    			
    		}
    	}
    }
    static String getUserFromId(ZipParser fileParser, String name)
    {
    	// possiamo aggiunger eccezione 
    	for(User x : fileParser.getUsers().values())
    	{ 
    		String disName = x.getDisplayNameNorm();
    		String rn = x.getRealName();
    		String _name = x.getName();
    		
    		if(disName != null)
    			if(disName.equals(name))
    				return x.getId();
    		if(rn != null)
    			if(rn.equals(name))
    				return x.getId();
    		if(_name != null)
    			if(_name.equals(name))
    				return x.getId(); 
    	}
    	return "";
    }
}
