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
		Pattern pattern = Pattern.compile("\\<@.*?\\>");
		 
		 
		for(Message msg : message)
		{	
			 
			/* controlli esistenza dei nodi prima di inserirli */ 
			if(msg.getText().contains("<@"))
			{
	
				User utenteu = users.get(msg.getUser());
				if(!snagraph.nodes().contains(utenteu))
						snagraph.addNode(utenteu);
				
			    Matcher matcher = pattern.matcher(msg.getText()); // msg.getText
				
				if(matcher.find())
				{
					while (matcher.find()) 
					{
						String filterstring  = matcher.group(0).replaceAll("<@", " ").replaceAll(">", "");
						User utentev = users.get(filterstring);
						if(!snagraph.nodes().contains(utentev))
							snagraph.addNode(utentev);
						/* controllo se esiste gia un arco tra i due utenti: Se esiste aggiungo 
							 +1 al mention altrimenti se non esiste creo l'arco */
						if(!snagraph.hasEdgeConnecting(utenteu, utentev))
							snagraph.putEdgeValue(utenteu, utentev, 0);
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
	}
	
	public MutableValueGraph<User, Integer>  getGraph()
	{
		return snagraph;
	}
	
	
	
}
