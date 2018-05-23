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

/**
 * MentionGraph manage graph of mentions, it use <b>Guava</b> Libraries, an
 * libraries of Google
 */
public class MentionGraph extends AbstractGraph {
	/*
	 * An instance of Model
	 */
	private Model mod;

	/*
	 * Instance of <i>MutableValueGraph</i> type of <b>Guava</b> libraries witch
	 * contains nodes(User) and Egde (Integer) weight base on numbers of mention
	 * from a user
	 */
	private MutableValueGraph<User, Integer> snagraph = ValueGraphBuilder.directed().build();
	// lista comandi che presentano un @Mention ma che non dovranno essere parsati
	// perchè non presentano la struttura del messaggio: "utente ---> @mention"
	/*
	 * Array of <i>String</i> contains all Slack's commands
	 */
	private String[] commignore = {"has joined the channel", "set the channel purpose", "cleared channel topic",
			"uploaded a file", "commented on", "was added to this conversation", "set the channel topic",
			"pinned a message to this channel", "pinned", "has renamed the channel", "un-archived the channel",
			"archived the channel", "cleared channel purpose", "has left the channel", "shared a file" };
	// aggiornare lista comandi da ignorare {deleted} trovare riferimento ufficiale

	/**
	 * Default costructor of MentionGraph
	 */
	public MentionGraph() {
	}

	/**
	 * @param model
	 *            <i>Model</i> witch contains Data such as <i>Users,Channel and
	 *            Messages</i>
	 */
	public MentionGraph(final Model model) {
		mod = model;
	}

	/**
	 * Check if command is present in the message
	 * 
	 * @param inputStr
	 *            Slack's commands to ignore
	 * @return <i>boolean</i> if commands is present in the current message
	 */
	public boolean containsItems(final String inputStr) {
		return Arrays.stream(commignore).parallel().anyMatch(inputStr::contains);
	}

	/**
	 * Check if Node is present in the graph
	 * 
	 * @param node
	 *            User in the graph
	 * @return <i>boolean</i> if <b>Node</b> is present in the graph
	 */
	public boolean containsNode(final User node) {
		return snagraph.nodes().contains(node);
	}

	/**
	 * Parse all messages of specified channel in the graph
	 * 
	 * @param inChannel
	 *            <i>String</i> Parse message of a specified channel
	 * 
	 */
	public void generate(final String inChannel) {
		parseMessages(mod.getMessages(), mod.getUsers(), inChannel);
	}

	/**
	 * Check if graph is empty
	 * 
	 * @return <i>boolean</i> if graph is Empty
	 */
	public boolean isEmpty() {
		return snagraph.nodes().isEmpty();
	}

	/**
	 * Call <i>parsing</i> for all workspace or specified channel
	 * 
	 * @param message
	 *            <i>HashMap<i> of <b>Channels and Messages</b>
	 * @param users
	 *            <i>HashMap<i> of <b>Users</b>
	 * @param inChannel
	 *            <i>String</i> specified channel to parsing messages
	 */
	public void parseMessages(final HashMap<String, ArrayList<Message>> message, final HashMap<String, User> users,
			final String inChannel) { // TODO _inChannel default = null || ""
		if (inChannel == null || inChannel.equals("")) {
			for (ArrayList<Message> mess : message.values()) {
				parsing(mess, users);
			}
		} else if (message.containsKey(inChannel)) {
			parsing(message.get(inChannel), users);
		}
	}

	/**
	 * Find all mention in the message, identifies: the user write
	 * mention(<i><b>From</b></i>), the user mentioned (<i><b>To</b></i>) and the
	 * number of mentions
	 * 
	 * @param mess
	 *            <i>HashMap<i> of <b>Channels and Messages</b>
	 * @param users
	 *            <i>HashMap<i> of <b>Users</b>
	 */
	void parsing(final ArrayList<Message> mess, final HashMap<String, User> users) {
		for (Message msg : mess) {
			if (msg.getText().contains("<@") && !containsItems(msg.getText())) {
				/*
				 * Attivare le due stampe in caso di null pointer per vedere eventuali stringhe
				 * di controllo usate da slack per gestire il singolo utente
				 */

				// System.out.println("----------- Testo Grabbato: \n" + msg.getText());
				// System.out.println("\t\t Scritto da: " + msg.getUser() + "\n #############
				// \n\n");
				User utenteu = users.get(msg.getUser());
				// Prima di inserire un nuovo utente p avviene un controllo se è nullo o se
				// esiste già nel grafo
				if ((utenteu != null) && !snagraph.nodes().contains(utenteu)) {
					snagraph.addNode(utenteu);
				}
				Pattern pattern = Pattern.compile("\\<@.*?\\>");
				Matcher matcher = pattern.matcher(msg.getText()); // msg.getText
				while (matcher.find()) {
					String dataparse = matcher.group(0); // filtro l'id dal messaggio
					String filterstring = dataparse.replaceAll("<@", " ").replaceAll(">", "").trim(); // pulisco dai tag
																										// l'id e lo
																										// cerco tra gli
																										// user
																										// memorizzati
					User utentev = users.get(filterstring);
					// Prima di inserire un nuovo utente q avviene un controllo se è nullo o se
					// esiste già nel grafo
					if ((utentev != null) && !utentev.equals(utenteu)) {
						if (!snagraph.nodes().contains(utentev)) {
							snagraph.addNode(utentev);
						}

						/*
						 * controllo se esiste gia un arco tra i due utenti: Se esiste aggiungo +1 al
						 * mention altrimenti se non esiste creo l'arco
						 */
						if (!snagraph.hasEdgeConnecting(utenteu, utentev)) {
							snagraph.putEdgeValue(utenteu, utentev, 1); // dobbiamo pescarlo dal grafo e poi inserire
						} else {
							int mentioncount = snagraph.edgeValue(utenteu, utentev).get() + 1;
							// cercare un modifica del peso dell'arco tra due nodi
							snagraph.removeEdge(utenteu, utentev);
							snagraph.putEdgeValue(utenteu, utentev, mentioncount);
						}
					}
				}
			}
		}
	}

	// issue#39
	/**
	 * Find all edge in degree of specified user
	 * 
	 * @param user
	 *            <b>User</b>
	 * @return <i>Arraylist</i> of Edge contains (<i>From,To,Weight</i>) for each
	 *         edge
	 */
	public ArrayList<Edge> edgesInDegree(final User user) {
		ArrayList<Edge> edges = new ArrayList<Edge>();
		if (snagraph.nodes().contains(user)) {
			if (snagraph.inDegree(user) > 0) {
				int inEdges = snagraph.inDegree(user); // mi conto quanti nodi sono in entrata sull'utente preso in
														// analisi
				// String nameUser = user.getRealName();
				for (User to : snagraph.nodes()) {
					if (snagraph.hasEdgeConnecting(to, user)) {
						// stampo l'arco tra i due utenti interessati
						// System.out.println("From: " + to.getRealName() + "\tTo: " + nameUser + "\t n.
						// mention: "
						// + snagraph.edgeValue(to, user).get());
						edges.add(new Edge(to, user, (float) snagraph.edgeValue(to, user).get()));
						inEdges--; // diminuisco il grado di entrata del nodo per ottimizzare la ricerca a grado 0
						if (inEdges == 0) {
							break;
						}
					}
				}
			}
		}
		return edges;
	}

	// issue37 && issue#38
	/**
	 * Find all edge out degree of specified user
	 * 
	 * @param user
	 *            <b>User</b>
	 * @return <i>Arraylist</i> of Edge contains (<i>From,To,Weight</i>) for each
	 *         edge
	 */
	public ArrayList<Edge> edgesOutDegree(final User user) {
		ArrayList<Edge> edges = new ArrayList<Edge>();
		int numNodesPrinted = 0;
		if (user == null) { // se non � speficato restituisce tutti gli archi
			for (User x : snagraph.nodes()) {
				for (User adiacenti : snagraph.adjacentNodes(x)) {
					if (snagraph.hasEdgeConnecting(x, adiacenti)) {
						edges.add(new Edge(x, adiacenti, (float) snagraph.edgeValue(x, adiacenti).get()));
						numNodesPrinted++;
					}
				}
			}
			if (numNodesPrinted == 0) { // eccezione: non ci sono mention nel workspace
				System.out.println("There aren't mention.");
			}
		} else {
			if (snagraph.nodes().contains(user)) {
				for (User adiacenti : snagraph.adjacentNodes(user)) {
					if (snagraph.hasEdgeConnecting(user, adiacenti)) {
						edges.add(new Edge(user, adiacenti, (float) snagraph.edgeValue(user, adiacenti).get()));
						numNodesPrinted++;
					}
				}
				// if (numNodesPrinted == 0) // eccezione: non ci sono mention nel channel
				// specificato
				// System.out.println("There aren't mention in the channel specified.");
			} // else {
				// eccezione : user non presente nel canale specificato
				// System.out.println("The user specified doesn't belong to this channel.");
				// }
		}
		return edges;
	}
}
