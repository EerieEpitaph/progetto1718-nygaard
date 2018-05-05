package it.uniba.parsing;

import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import com.google.gson.*;

import it.uniba.workdata.Message;
import it.uniba.model.MentionGraph;
import it.uniba.workdata.Channel;
import it.uniba.workdata.User;

public class ZipParser
{
    private String workspaceLoaded = "";
    //I due dizionari users e channels 
    private HashMap<String, User> users = new HashMap<String, User>();
    private HashMap<String, Channel> channels = new HashMap<String, Channel>();
    private ArrayList<Message> messages = new ArrayList<Message>();
    
    private  MentionGraph grmention;
    
    
    public void setWorkspaceName(String _value)
    {
    	workspaceLoaded = _value; 
    }
    public String getWorkspaceName()
    {
        return workspaceLoaded;
    }
    public Boolean hasLoaded()
    {
        return (workspaceLoaded != "");
    }
    public Map<String, User> getUsers()
    {
        return users;
    }
    public Map<String, Channel> getChannels()
    {
        return channels;
    }
    
    public ArrayList<Message> getMessages()
    {
        return messages;
    }
    
    public void load(String path) throws ZipException, IOException
    {
        Boolean loadedSomething = false;
 
//            int count = 0;
        ZipFile zip = new ZipFile(path);
        Enumeration <? extends ZipEntry> entries = zip.entries();

        while (entries.hasMoreElements()) 
        {
            ZipEntry entry = entries.nextElement();
            
            if ( !entry.isDirectory() && !entry.getName().equals("integration_logs.json") ) 
            {
                loadedSomething = true;
                InputStream stream = zip.getInputStream(entry);
                Reader lecturer = new InputStreamReader(stream);
//                    System.out.println(entry.getName() + " " + ++count);

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                if (entry.getName().equals("users.json")) 
                {
                    User[] tempUser = gson.fromJson(lecturer, User[].class);
                    for(User x : tempUser)
                    {
//                        System.out.println(x.getId());
                    	users.put(x.getId(), x);
                    }     
                } 
                else if(entry.getName().equals("channels.json"))
                {
                    Channel[] tempChannel = gson.fromJson(lecturer, Channel[].class);
                    for(Channel x : tempChannel)
                    {
//                        System.out.println(x.getId());
                        channels.put(x.getName(), x);
                    }     
                } 
                else
                {
                    Message[] tempMessage = gson.fromJson(lecturer, Message[].class);
                    for(Message x : tempMessage)
                    {
//                        System.out.println(x.getType());
//                        System.out.println(x.getUser());
//                        System.out.println(x.getText());
//                        System.out.println("=====================");
                        messages.add(x);
                    }
                }

                lecturer.close();
                //Non ho trovato i file che ci servono
                if(!loadedSomething) throw new ZipException();
            }
        }
        File tempFile = new File(zip.getName());
        workspaceLoaded = tempFile.getName().replaceFirst("[.][^.]+$", "");
//            System.out.println(workspaceLoaded);
        zip.close();
        /* solo per testare il grafo */
        //grmention = new MentionGraph(messages,users);
    }
}