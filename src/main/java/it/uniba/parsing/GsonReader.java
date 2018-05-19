package it.uniba.parsing;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.uniba.workdata.Channel;
import it.uniba.workdata.Message;
import it.uniba.workdata.User;
import it.uniba.workdata.Message.GsonMessage;

public class GsonReader implements JsonParserInterface
{
    Gson gson = (new GsonBuilder()).create();

    @Override
    public HashMap<String, User> populateUsers(Reader reader)
    {
        HashMap<String, User> tempMap = new HashMap<String, User>();
        User[] tempUser = gson.fromJson(reader, User[].class);

        for (User x : tempUser)
        {
            // System.out.println(x.getId() + " " +
            // x.getDisplayNameNorm());
            tempMap.put(x.getId(), x);
        }
        return tempMap;
    }

    @Override
    public HashMap<String, Channel> populateChannels(Reader reader)
    {
        HashMap<String, Channel> tempMap = new HashMap<String, Channel>();
        Channel[] tempChannel = gson.fromJson(reader, Channel[].class);
        for (Channel x : tempChannel)
        {
            // System.out.println(x.getId());
            tempMap.put(x.getName(), x);
        }
        return tempMap;
    }

    @Override
    public HashMap<String, ArrayList<Message>> populateMessages(HashMap<String, ArrayList<Message>> tempMap, String currChannel, Reader reader)
    {
        GsonMessage[] tempMessage = gson.fromJson(reader, GsonMessage[].class);
        for (GsonMessage x : tempMessage)
        {
            Message tempMes = new Message(x);
            if (tempMap.containsKey(currChannel))
            {
                tempMap.get(currChannel).add(tempMes);
            }

            else
            {
                ArrayList<Message> msg = new ArrayList<Message>();
                msg.add(tempMes);
                tempMap.put(currChannel, msg);
            }
        }
        return tempMap;
    }
    
    
}
