package it.uniba.model;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import it.uniba.parsing.ZipParser;
import it.uniba.workdata.Channel;
import it.uniba.workdata.User;

// coppia dei dizionari memrbri-utenti 
class pairDict {
	private  Map<String, User> users = new HashMap<String, User>();
    private  Map<String, Channel> channels = new HashMap<String, Channel>();
    
    
    public void setDictUser(Map<String, User>  _deusers)
    {
    	users = _deusers;
    }
    
    public void setDictChannel(Map<String, Channel> _dechannels )
    {
    	channels = _dechannels;
    }
    /* da cancellare */ 
    public Map<String, User> getDictUser()
    {
    	return users;
    }
    
    public Map<String, Channel> getDictChannel()
    {
    	return channels;
    }
}
public class Sysworkspace {
	/* tutti i possibili workspace caricati identificati 
	come chiave,dal nome del workspace,  e come value, dal path assoluto del workspace */
	
	private Map<String,String> workarea = new HashMap<String,String>();
	
	private String currentPath; // path dell'applicazione
	private String currentOs;   // os corrente
	
	//il costruttore imposta il path corrente dell'applicativo e l'os su cui sta girando 
	public Sysworkspace()
	{
		currentPath = System.getProperty("user.dir");
		currentOs = System.getProperty("os.nio ame"); 
	}
	
	public Map<String,String> getWorkarea()
	{
		return workarea;
	}
	
	// inutle ma non si sa mai {possibilità di aggiunta manuale di un workspace senza creare nessuna cartella contemporanea}
	public void addWorkspace(String name, String path)
	{
		workarea.put(name, path);
	}
	
	// crea in base all'os la cartella nascosta e segna nel workarea i workspace attivi 
	public void makedirArea(String namews) throws IOException // da gestire 
	{
		 if(currentOs == "Windows") // da testare su os  
		 {
			 /// Runtime.getRuntime().exec("attrib +H " + currentPath); certezza sui file ma non sulle directory 
			 currentPath = currentPath + "/" + namews + "tmp";
			 Path path = Paths.get(currentPath);
			 Files.setAttribute(path, "dos:hidden", true);
			 Files.createDirectories(path);
			 workarea.put(namews, currentPath);
		 }
		 else // OS Linux or other linux distro based
		 {
			 currentPath = currentPath + "/." + namews + "tmp";
			 Path path = Paths.get(currentPath);
			 Files.createDirectories(path);
			 workarea.put(namews, currentPath);
		 }
		 
	}
	
	// Serializza i dizionari caricati in memoria su disco
	// dato un workspace e un fileParser serializza i due dizionari 
	public void DictSerial(String namews,ZipParser fileParser)
	{
		 try
         {
        	 String pathMember = workarea.get(namews) + "/members.ser";
        	 //pathMember = pathMember +  "/members.ser";
        	 FileOutputStream fos = new FileOutputStream(pathMember ); // prende un stream su cui scrivere l'ogetto 
        	 ObjectOutputStream oos = new ObjectOutputStream(fos); // crea daòlo stream output l'ObjectOutputStream che scriverà un oggetto sullo stream 
        	 oos.writeObject(fileParser.getUsers()); // scrive il dizionario 
        	 // chiude i due stream di output 
        	 
        	 String pathChannel = workarea.get(namews) +  "/channels.ser";
        	// pathChannel = pathChannel +  "/channels.ser";
        	 
        	 fos = new FileOutputStream(pathMember ); 
        	 oos = new ObjectOutputStream(fos); 
        	 oos.writeObject(fileParser.getChannels());
        	 
        	 oos.close(); 
        	 fos.close();
        	 //System.out.printf("Dizionario Seriliazzato");
         }
         catch(IOException ioe)
         {
        	 ioe.printStackTrace();
         }
	}
	
	// Dato un workspace deserializza i due dizionari e li restituisce  
	public pairDict getDicts(String namews)
	{
		
		pairDict worksdata = new pairDict();
		
		try
		{
			String member = workarea.get(namews) + "/members.ser";
			FileInputStream fis = new FileInputStream(member);
			ObjectInputStream ois = new ObjectInputStream(fis);
			worksdata.setDictUser((HashMap)ois.readObject());
			
			String channel = workarea.get(namews) + "/channels.ser";
			fis = new FileInputStream(channel);
			ois = new ObjectInputStream(fis);
			worksdata.setDictChannel((HashMap)ois.readObject());
			ois.close();
			fis.close();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			
		}
		catch(ClassNotFoundException c)
		{
			System.out.println("Class not found");
			c.printStackTrace();
			
		}
		return worksdata;
	}
	
}


