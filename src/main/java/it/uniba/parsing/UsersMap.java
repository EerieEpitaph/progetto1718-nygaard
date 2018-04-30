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

import it.uniba.workdata.User;

public class UsersMap implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<String,User> users = new HashMap<>();

	public Set<Map.Entry<String, User>> entrySet() 
	{
		return users.entrySet();
	}

	public void put(String string, User utente) 
	{
		users.put(string, utente);
	}
	public User get(String id) {
		return users.get(id);
	}

	public void save(String filePath)
	{
		 
		try
		{
			ObjectOutputStream out = new ObjectOutputStream
					( new FileOutputStream(filePath,true));
			out.writeObject(this);
			out.close();
			//System.out.printf("Serialized data is saved in "+filePath);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	// attenzione  a questo metodo 
	public static UsersMap Load(String filePath) 
	{
		 
		UsersMap usersDict = null;
		try
		{
			ObjectInputStream in = new ObjectInputStream
					(new FileInputStream(filePath));
			usersDict = (UsersMap) in.readObject();
			in.close();
			 
		}catch(IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		return usersDict;
	}
	
	public Map<String,User> getUsersMap()
	{
		return users;
	}
}
