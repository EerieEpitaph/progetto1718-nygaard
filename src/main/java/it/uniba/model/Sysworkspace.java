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

// coppia dei dizionari members-channels 
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
	
	/*
	 * Comandi: 
	 * 			-l --load path dello zip |  carica e  crea i dizionari sul disco in una cartella temporanea 
	 * 		    -show | mostra i workspace attivi e creati nelle cartelle
	 * 			#Esempi user Story: 
	 * 			    -w "NomeWorkspace" -m			    |  mostro tutti membri del workspace
	 * 				-w "NomeWorkspace" -c 			    |  mostro tutti channels  del workspace
	 * 				-w "NomeWorkspace" -m -c            |  mostro i membri raggruppati per canali 
	 * 				-w "NomeWorkspace" -m -c "nygaard"  | mostro tutti i membri di un particolare channel	  
	 * 				
	 */
	

	private Map<String,String> workarea = new HashMap<String,String>();
	/* tutti i possibili workspace caricati identificati 
	come chiave,dal nome del workspace,  e come value, dal path assoluto del workspace */
	
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
	public void makedirArea(String namews) // da gestire 
	{
		try
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
	    catch(IOException ioe)
        {
       	 ioe.printStackTrace();
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
        	 FileOutputStream fos = new FileOutputStream(pathMember ); // prende un stream su cui scrivere il dizionario users
        	 ObjectOutputStream oos = new ObjectOutputStream(fos); // crea dallo stream output l'ObjectOutputStream che scriverà un oggetto sullo stream 
        	 oos.writeObject(fileParser.getUsers()); // scrive il dizionario users  
        	  
        	 
        	 String pathChannel = workarea.get(namews) +  "/channels.ser";
        	// pathChannel = pathChannel +  "/channels.ser";
        	 
        	 fos = new FileOutputStream(pathChannel); // imposta lo stream output su cui scrivere il dizionario channels
        	 oos = new ObjectOutputStream(fos);  // crea dallo stream output l'ObjectOutputStream che scriverà un oggetto sullo stream 
        	 oos.writeObject(fileParser.getChannels()); //scrive il dizionario channels  
        	 
        	 // Chiusura degli output streams
        	 oos.close(); 
        	 fos.close();
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
			String member = workarea.get(namews) + "/members.ser"; // prende il path del workspace e il percorso del dizionario users 
			FileInputStream fis = new FileInputStream(member); // imposta l'input stream sui cui leggere il dizionario users
			ObjectInputStream ois = new ObjectInputStream(fis); // crea dallo stream input l'ObjectInputStream che leggerà un oggetto sullo stream 
			worksdata.setDictUser((HashMap)ois.readObject()); // legge il  dizionario e lo iposta come campo della tupla pairDicts
			
			String channel = workarea.get(namews) + "/channels.ser";
			fis = new FileInputStream(channel);
			ois = new ObjectInputStream(fis);
			worksdata.setDictChannel((HashMap)ois.readObject());
			
			// Chiude gli stream 
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
		return worksdata;  // restituisce la tupla contenente il dizionario users e channels 
	}
	
}


