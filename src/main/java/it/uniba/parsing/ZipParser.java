package it.uniba.parsing;

import java.util.Map;
import java.util.Set;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.nio.file.InvalidPathException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import com.google.gson.*;

import it.uniba.workdata.Channel;
import it.uniba.workdata.User;

public class ZipParser implements Serializable 
{
   
    private String workspaceLoaded = "";
    //I due dizionari users e channels 
    private UsersMap usersdict = new UsersMap();
    private ChannelsMap channelsdict = new ChannelsMap();
    
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
        return usersdict.getUsersMap();
    }
    public Map<String, Channel> getChannels()
    {
        return channelsdict.getChannelsMap();
    }
    
    public UsersMap getUsersMap()
    {
    	return usersdict;
    }
    
    public ChannelsMap getChannelsMap() 
    {
    	return channelsdict; 
    }
    
    public void load(String path) 
    {
        Boolean loadedSomething = false;
        
        
        try 
        {
//            int count = 0;
            ZipFile zip = new ZipFile(path);
            Enumeration <? extends ZipEntry> entries = zip.entries();

            while (entries.hasMoreElements()) 
            {
                ZipEntry entry = entries.nextElement();
                
                if (entry.getName().equals("channels.json") || entry.getName().equals("users.json")) 
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
//                            System.out.println(x.getId());
                        	 usersdict.put(x.getId(), x);
                        }     
                    } 
                    else 
                    {
                        Channel[] tempUser = gson.fromJson(lecturer, Channel[].class);
                        for(Channel x : tempUser)
                        {
//                            System.out.println(x.getId());
                            channelsdict.put(x.getName(), x);
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
        } 
        catch (NullPointerException e) 
        {System.out.println("Specify zip file to load");} 
        catch (ZipException e) 
        {System.out.println("Unable to analyze. Damaged or wrong file");} 
        catch (InvalidPathException e) 
        {System.out.println("Illegal char used in path");} 
        catch (IOException e) 
        {System.out.println("Invalid file. Usage: load \"path\\to\\file.zip\"");} 
        catch (JsonParseException e) 
        {System.out.println( e.toString() );} 
        catch (Exception e) 
        {System.out.println("Critical exception, I'm out!");}
    }
    
    
   
    
   
 
}