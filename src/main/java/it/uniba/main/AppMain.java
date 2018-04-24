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
	        System.out.println(x.get_id());
	        System.out.println(x.get_name());
	        System.out.println(x.get_realname());
	        System.out.println(x.get_teamId());
	        System.out.println("================");
	    }
	    
       for( it.uniba.workdata.Channel x : test.getChannels().values())
       {
            System.out.println(x.get_id());
            System.out.println(x.get_name());
            System.out.println(x.get_date_creation());
            System.out.println(x.get_founder());
            System.out.println(x.get_member_list());
            System.out.println("================");
       }
	    System.out.println("Exited");
	}
}
