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

// filepath load "/home/phinkie/Downloads/ingsw.zip" 


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
	public void DictSerial(String namews, ZipParser fileParser) 
	{
		//salvataggio degli utenti 
		if(namews != "")
		{
			String pathdicts = workarea.get(namews) + "/members.ser";
			fileParser.getUsersMap().save(pathdicts);

			// salvataggio dei canali 
			pathdicts = workarea.get(namews) + "/channels.ser";
			fileParser.getChannelsMap().save(pathdicts);
		}
	}
	
	public void lectureDicts(String namews, ZipParser fileParser)
	{
		// carico il dizionario dei membri 
		String pathdata = workarea.get(namews) + "/members.ser";	 
		fileParser.setUserDict(fileParser.getUsersMap().Load(pathdata));
		
		// carico il dizionario dei canali 
		pathdata =  workarea.get(namews) + "/channels.ser";
		fileParser.setChannelDict(fileParser.getChannelsMap().Load(pathdata));
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
	
	
	public void shoWorkspace()
	{
		if(!workarea.isEmpty())
		{
			System.out.println("WorkSpace Attivi: \n\tNome Workspace\t Path Workspace");
			for(String ws : workarea.keySet())
				System.out.println("\t" + ws  + "\t\t  " + workarea.get(ws));
		}
		else
			System.out.println("Non sono presenti workspace caricati su disco");
	}
	
	public void loadWorkspace()
	{
		pathws = pathws + "/.dictws.ser";
		File f = new File(pathws);
		
		if(workarea.isEmpty() && f.exists()) // leggo dal file solo se non ci sono workspace ed esite il path di salvataggio 
		{
			try
			{
				FileInputStream fis = new FileInputStream(pathws); 
				ObjectInputStream ois = new ObjectInputStream(fis); 
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
			 //System.out.println("Current path: " + pathws);
        	 FileOutputStream fos = new FileOutputStream(pathws); // prende un stream su cui scrivere il dizionario users
        	 ObjectOutputStream oos = new ObjectOutputStream(fos); // crea dallo stream output l'ObjectOutputStream che scriverà un oggetto sullo stream 
        	 oos.writeObject(workarea);
        	 
        	 fos.close();
        	 oos.close();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
}


