package it.uniba.controller;

import it.uniba.workdata.*;
import it.uniba.parsing.ZipParser;

public class Controller
{
    public static void printChannels(ZipParser fileParser)
    {
        for(Channel x : fileParser.getChannels().values())
            System.out.println(x.getName());
    }
    
    /*
    public static void printUsers(ZipParser fileParser)
    {
        for(User x : fileParser.getUsers().values())
            System.out.println(x.getName());
    }
    */
}
