package it.uniba.main;

import it.uniba.parsing.ZipParser;

public final class AppMain 
{
	public static void main(final String[] args) 
	{
	    ZipParser test = new ZipParser();
	    test.load("C:\\Users\\Ottavio Lischio\\Desktop\\test.zip");
	    
	    for( it.uniba.workdata.User x : test.getUsers().values())
	    {
	        System.out.println(x.getId());
	        System.out.println(x.getName());
	        System.out.println(x.getRealName());
	        System.out.println(x.getTeamId());
	        System.out.println("================");
	    }
	    
       for( it.uniba.workdata.Channel x : test.getChannels().values())
       {
            System.out.println(x.getId());
            System.out.println(x.getName());
            System.out.println(x.getDateCreation());
            System.out.println(x.getCreator());
            System.out.println(x.getMemberList());
            System.out.println("================");
       }
	    System.out.println("Exited");
	}
}
