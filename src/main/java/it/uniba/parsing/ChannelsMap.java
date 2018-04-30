package it.uniba.parsing;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import it.uniba.workdata.Channel;

public class ChannelsMap implements Serializable {

	private static final long serialVersionUID = 1L; // bho 
	private Map<String,Channel> channels = new HashMap<>();

	public Set<Map.Entry<String, Channel>> entrySet() {
		return channels.entrySet();
	}

	public void put(String string, Channel canale) {
		channels.put(string, canale);
	}
	public Channel get(String id) {
		return channels.get(id);
	}
	
	public void save(String filePath)
	{
		System.out.println("Trying to save");
		try
		{
			ObjectOutputStream out = new ObjectOutputStream
					( new FileOutputStream(filePath,true));
			out.writeObject(this);
			out.close();
			System.out.printf("Serialized data is saved in "+filePath);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
    }
	// attenzione  a questo metodo 
	public static ChannelsMap Load(String filePath) {
        System.out.println("Trying to load");
        ChannelsMap channelsDict = null;
        try
        {
            ObjectInputStream in = new ObjectInputStream
                 (new FileInputStream(filePath));
            channelsDict = (ChannelsMap) in.readObject();
            in.close();
        }catch(IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return channelsDict;
    }
	
	public Map<String,Channel> getChannelsMap()
	{
		return channels;
	}
}
