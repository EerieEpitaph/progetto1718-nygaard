package it.uniba.model;
import it.uniba.workdata.Message;
import it.uniba.workdata.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;


public class MentionGraph {
	private MutableValueGraph<User, Integer>  snagraph = ValueGraphBuilder.directed().build();
	
	public MentionGraph(ArrayList<Message> message,HashMap<String, User> users )
	{
		
		parseMessages(message,users);
	}
	
	void parseMessages(ArrayList<Message> message,HashMap<String, User> users )
	{
		
		
		Message msg = new Message("message", "U9NF6NSU8", "<@U9NJ4EYM7> ciao saluta <@U9P18U17X>"); 
//		for(Message msg : message)
//		{	
			System.out.println("----- --------------------------");
			System.out.println("Testo grabbato: " + msg.getText());
			/* controlli esistenza dei nodi prima di inserirli */ 
			if(msg.getText().contains("<@"))
			{
	
				User utenteu = users.get(msg.getUser());
				
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

			    	if(!snagraph.nodes().contains(utentev))
			    	{
			    		System.out.println("UtenteV : " + utentev.getRealName() + " " +  utentev.getName());
			    		snagraph.addNode(utentev);
			    	}
			    	/* controllo se esiste gia un arco tra i due utenti: Se esiste aggiungo 
							 +1 al mention altrimenti se non esiste creo l'arco */
			    	if(!snagraph.hasEdgeConnecting(utenteu, utentev))
			    	{
			    		snagraph.putEdgeValue(utenteu, utentev, 0); //dobbiamo pescarlo dal grafo e poi inserire l'arco (pot ghess) 
			    	}
			    	else
			    	{
			    		int mentioncount = snagraph.edgeValue(utenteu, utentev).get() + 1;
			    		// cercare un modifica  del peso dell'arco tra due nodi 
			    		snagraph.removeEdge(utenteu, utentev);
			    		snagraph.putEdgeValue(utenteu, utentev, mentioncount);
			    	}
			    }

 
					
			}	
		//}
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
	}
	public MutableValueGraph<User, Integer>  getGraph()
	{
		return snagraph;
	}
	
	
	
}
