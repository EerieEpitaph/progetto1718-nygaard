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
         { 
             System.out.println(utente.getRealName() +"\t@"+  utente.getName());
         }
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
    
    public static void channelMembers(ZipParser fileParser,String channel)
    {
        if(channel != "" && fileParser.getChannels().containsKey(channel))
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
            System.out.println("There is no channel " + channel);
    }
}
