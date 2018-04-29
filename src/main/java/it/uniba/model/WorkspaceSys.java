package it.uniba.model;
import java.io.File;
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
public class WorkspaceSys {
	
	/*
	 * Comandi: 
	 * 			-l --load path dello zip |  carica e  crea i dizionari sul disco in una cartella temporanea 
	 * 		    -show | mostra i workspace attivi e creati nelle cartelle
	 * 			-d "NomeWorkspace" elimina il workspace caricato su disco con relativi dizionari user e channel
	 * 			#Esempi user Story: 
	 * 			    -w "NomeWorkspace" -m			    |  mostro tutti membri del workspace
	 * 				-w "NomeWorkspace" -c 			    |  mostro tutti channels  del workspace
	 * 				-w "NomeWorkspace" -c -m            |  mostro i canali raggruppati per membri 
	 * 				-w "NomeWorkspace" -m -c "nygaard"  | mostro tutti i membri di un particolare channel	  
	 * 				
	 */
	

	private Map<String,String> workarea = new HashMap<String,String>();
	/* tutti i possibili workspace caricati identificati 
	come chiave,dal nome del workspace,  e come value, dal path assoluto del workspace */
	
	private String currentPath; // path dell'applicazione
	private String currentOs;   // os corrente
	private String pathws; // path dove salvare tuti i workspace creati 
	
	//il costruttore imposta il path corrente dell'applicativo e l'os su cui sta girando 
	public WorkspaceSys()
	{
		currentPath = System.getProperty("user.dir");
		pathws = currentPath;
		currentOs = System.getProperty("os.nio ame"); 
		loadWorkspace();
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
	
	public void delWorkspace(String namews)
	{
		//dato un workspace elimino i dizionari creati e la sua cartella nascosta 
		
		String delPath = workarea.get(namews);  // prendo il path completo del workspace creato su disco 
		File pathFile = new File(delPath);
		String[] data = pathFile.list(); // predo tutti i file presenti nella cartella del workspace 
		for(String s: data) 
		{
		    File currentFile = new File(pathFile.getPath(),s);
		    currentFile.delete(); // elimino ogni file presente nella cartella 
		}
		pathFile.delete(); // eliminio infine la directory del workspace 
		workarea.remove(namews); // elimino il riferimento nel dizionario del workspace 
	}
	
	public void loadWorkspace()
	{
		pathws = pathws + "/.dictws.ser";
		File f = new File(pathws);
		
		if(workarea.isEmpty() && f.exists()) // leggo dal file solo se non ci sono workspace ed esite il path di salvataggio 
		{
			try
			{
				String member = workarea.get(pathws); // prende il path del workspace e il percorso del dizionario users 
				FileInputStream fis = new FileInputStream(member); // imposta l'input stream sui cui leggere il dizionario users
				ObjectInputStream ois = new ObjectInputStream(fis); // crea dallo stream input l'ObjectInputStream che leggerà un oggetto sullo stream 
				workarea = (HashMap)ois.readObject();

				fis.close();
				ois.close();
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
		}
	}
	
	// da richiamare ogni qualvolta che si esce dall'applicativo [da ottimizzare le scritture]
	public void saveWorkspace()
	{
		try
		{
			 pathws = pathws + "/.dictws.ser";
        	 FileOutputStream fos = new FileOutputStream(pathws); // prende un stream su cui scrivere il dizionario users
        	 ObjectOutputStream oos = new ObjectOutputStream(fos); // crea dallo stream output l'ObjectOutputStream che scriverà un oggetto sullo stream 
        	 oos.writeObject(workarea);
        	 
        	 fos.close();getClass();
        	 oos.close();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
}


