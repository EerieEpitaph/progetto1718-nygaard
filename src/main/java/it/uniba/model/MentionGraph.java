package it.uniba.model;
import it.uniba.workdata.Message;
import it.uniba.workdata.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;


public class MentionGraph {
	private MutableValueGraph<User, Integer>  snagraph = ValueGraphBuilder.directed().build();
	
	String[] commignore = { "has joined the channel", "set the channel purpose", 
			"cleared channel topic", "uploaded a file", "commented on", "was added to this conversation", 
			"set the channel topic", "pinned a message to this channel", "pinned", "has renamed the channel",
			"un-archived the channel", "archived the channel", "cleared channel purpose"};
	
	public MentionGraph(ArrayList<Message> message,HashMap<String, User> users )
	{
		
		parseMessages(message,users);
	}
	
	public  boolean containsItems(String inputStr) 
	{
	    return Arrays.stream(commignore).parallel().anyMatch(inputStr::contains);
	} 
	
	void parseMessages(ArrayList<Message> message,HashMap<String, User> users )
	{
		/* Todo: pescare solo i  mention e non i join nei channel o l'invio dei file di un utente  */ 
		
		//Message msg = new Message("message", "U9NF6NSU8", "<@U9NJ4EYM7> ciao saluta <@U9P18U17X>");  // messaggio di test 
		for(Message msg : message)
		{	
			System.out.println("----- --------------------------");
			System.out.println("Testo grabbato: " + msg.getText());
			/* controlli esistenza dei nodi prima di inserirli */ 
			if(msg.getText().contains("<@") && !containsItems(msg.getText()))
			{
	
				User utenteu = users.get(msg.getUser());
				
				/* controlli esistenza dei nodi prima di inserirli */ 
				if(!snagraph.nodes().contains(utenteu))
				{
						snagraph.addNode(utenteu);
						System.out.println("UtenteU : " + utenteu.getRealName() + " " +  utenteu.getName());
				}
				
				Pattern pattern = Pattern.compile("\\<@.*?\\>");
			    Matcher matcher = pattern.matcher(msg.getText()); // msg.getText
			 
			    while (matcher.find()) 
			    {
			    	// attenzione qua kitestramu
			    	String dataparse = matcher.group(0);
			    	String filterstring  = dataparse.replaceAll("<@", " ").replaceAll(">", "").trim();
			    	User utentev = users.get(filterstring);
			    	if(utentev.equals(utenteu))
			    	{
			    		if(!snagraph.nodes().contains(utentev))
			    		{
			    			System.out.println("UtenteV : " + utentev.getRealName() + " " +  utentev.getName());
			    			snagraph.addNode(utentev);
			    		}
			    		/* controllo se esiste gia un arco tra i due utenti: Se esiste aggiungo 
						 +1 al mention altrimenti se non esiste creo l'arco */
			    		if(!snagraph.hasEdgeConnecting(utenteu, utentev))
			    			snagraph.putEdgeValue(utenteu, utentev, 0); //dobbiamo pescarlo dal grafo e poi inserire l'arco (pot ghess) 
			    		else
			    		{
			    			int mentioncount = snagraph.edgeValue(utenteu, utentev).get() + 1;
			    			// cercare un modifica  del peso dell'arco tra due nodi 
			    			snagraph.removeEdge(utenteu, utentev);
			    			snagraph.putEdgeValue(utenteu, utentev, mentioncount);
			    		}
			    	}
			    }
			}	
		}
		// print node 
		printNode();
	}
	void printNode()
	{
		System.out.println("#### Stampa nodi test ####");
		for(User x : snagraph.nodes())
		{
			System.out.println(x.getRealName() +  " " + x.getName());
		}
		
		System.out.println("#### Stampa adiacenti test ####");
		System.out.println("From\t\tTo");
		for(User x : snagraph.nodes())
		{
			for(User adiacenti : snagraph.adjacentNodes(x))
				System.out.println(x.getRealName() + "\t\t" + adiacenti.getRealName());
		}
	}
	public MutableValueGraph<User, Integer>  getGraph()
	{
		return snagraph;
	}
	
	
	
}
