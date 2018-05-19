package it.uniba.parsing;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

import it.uniba.workdata.*;

public interface JsonParserInterface
{
    public HashMap<String, User> populateUsers(Reader reader);
    
    public HashMap<String, Channel> populateChannels(Reader reader);
    
    public HashMap<String, ArrayList<Message>> populateMessages(HashMap<String, ArrayList<Message>> messages, String currChannel, Reader reader);
}
