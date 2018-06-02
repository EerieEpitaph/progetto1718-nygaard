package it.uniba.model;

import it.uniba.controller.ExceptionsHandler;
import it.uniba.workdata.Message;
import it.uniba.workdata.User;
import it.uniba.wrapping.MentionGraphWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
//import java.util.HashMap;
import java.util.Map;
//import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MentionGraph manage graph of mentions;
 */
public final class MentionGraph extends AbstractGraph {
	// /*
	// * An instance of Model
	// */
	// private Model mod;

	/*
	 * Instance of <i>MutableValueGraph</i> type from <b>Guava</b> library which
	 * contains nodes (of User type) and Edge (Integer) representing the weight
	 * based on numbers of mentions from a user
	 */

	private final MentionGraphWrapper snagraph = new MentionGraphWrapper();
	// lista comandi che presentano un @Mention ma che non dovranno essere parsati
	// perche' non presentano la struttura del messaggio: "utente ---> @mention"
	/*
	 * Array of <i>String</i> containing all of Slack's commands
	 */

	private final String[] commignore = {"has joined the channel", "set the channel purpose", "cleared channel topic",
			"uploaded a file", "commented on", "was added to this conversation", "set the channel topic",
			"pinned a message to this channel", "pinned", "has renamed the channel", "un-archived the channel",
			"archived the channel", "cleared channel purpose", "has left the channel", "shared a file"};
	// aggiornare lista comandi da ignorare {deleted} trovare riferimento ufficiale

	/**
	 * Default constructor of MentionGraph
	 */
	public MentionGraph() {
		// This constructor is intentionally empty. Nothing special is needed here.
	}

	/**
	 * Check if command is present in the message
	 * 
	 * @param inputStr
	 *            Slack's commands to ignore
	 * @return <i>boolean</i> if commands is present in the current message
	 */
	boolean containsItems(final String inputStr) {
		return Arrays.stream(commignore).parallel().anyMatch(inputStr::contains);
	}

	/**
	 * Check if Node is present in the graph
	 * 
	 * @param node
	 *            User in the graph
	 * @throws ExceptionsHandler
	 *             when node is null
	 * @return <i>boolean</i> if <b>Node</b> is present in the graph
	 */
	public boolean containsNode(final User node) throws ExceptionsHandler {
		if (node == null) {
			throw new ExceptionsHandler("Node is null!");
		} else {
			return snagraph.contains(node);
		}
	}

	/**
	 * Parse all messages of specified channel in the graph
	 * 
	 * @param inChannel
	 *            <i>String</i> Parse message of a specified channel
	 * @param messages
	 *            <i>HashMap<i> of <b>Channels and Messages</b>
	 * @param users
	 *            <i>HashMap<i> of <b>Users</b>
	 * @throws ExceptionsHandler
	 *             used to handle exceptions
	 * 
	 */
	public void generate(final String inChannel, final Map<String, ArrayList<Message>> messages,
			final Map<String, User> users) throws ExceptionsHandler {
		if (messages == null || users == null) {
			throw new ExceptionsHandler("Messages or Users are  null");
		}
		if (messages.isEmpty() || users.isEmpty()) {
			throw new ExceptionsHandler("Messages or Users are empty");
		} else {
			parseMessages(messages, users, inChannel);
		}
	}

	/**
	 * Check if graph is empty
	 * 
	 * @return <i>boolean</i> if graph is Empty
	 */
	public boolean isEmpty() {
		return snagraph.isEmpty();
	}

	/**
	 * Call <i>parsing</i> for all workspace or specified channel
	 * 
	 * @param messages
	 *            <i>HashMap<i> of <b>Channels and Messages</b>
	 * @param users
	 *            <i>HashMap<i> of <b>Users</b>
	 * @param inChannel
	 *            <i>String</i> specified channel to parsing messages
	 */
	public void parseMessages(final Map<String, ArrayList<Message>> messages, final Map<String, User> users,
			final String inChannel) { // TODO _inChannel default = null || ""
		if (inChannel == null || "".equals(inChannel)) {
			for (final ArrayList<Message> mess : messages.values()) {
				parsing(mess, users);
			}
		} else if (messages.containsKey(inChannel)) {
			parsing(messages.get(inChannel), users);
		}
	}

	/**
	 * Find all mentions in the message, identifies: the user write
	 * mention(<i><b>From</b></i>), the user mentioned (<i><b>To</b></i>) and the
	 * number of mentions
	 * 
	 * @param mess
	 *            <i>HashMap<i> of <b>Channels and Messages</b>
	 * @param users
	 *            <i>HashMap<i> of <b>Users</b>
	 */
	void parsing(final Collection<Message> mess, final Map<String, User> users) {
		for (final Message msg : mess) {
			if (msg.contains("<@") && !containsItems(msg.getText())) {
				final User utenteu = users.get(msg.getUser());
				// Prima di inserire un nuovo utente p avviene un controllo se e' nullo o se
				// esiste gia'� nel grafo
				if ((utenteu != null) && !snagraph.contains(utenteu)) {
					snagraph.addNode(utenteu);
				}
				final Pattern pattern = Pattern.compile("\\<@.*?\\>");
				final Matcher matcher = pattern.matcher(msg.getText()); // msg.getText
				while (matcher.find()) {
					final String dataparse = matcher.group(0); // filtro l'id dal messaggio
					final String filterstring = dataparse.replaceAll("<@", " ").replaceAll(">", "").trim();
					// pulisco dai tag l'id e lo cerco tra gli users memorizzati
					final User utentev = users.get(filterstring);
					// Prima di inserire un nuovo utente q avviene un controllo se e' nullo o se
					// esiste gia'� nel grafo
					if ((utentev != null) && !utenteu.equals(utentev)) {
						if (!snagraph.contains(utentev)) {
							snagraph.addNode(utentev);
						}

						/*
						 * controllo se esiste gia un arco tra i due utenti: Se esiste aggiungo +1 al
						 * mention altrimenti se non esiste creo l'arco
						 */
						if (snagraph.hasEdgeConnecting(utenteu, utentev)) {
							final int mentioncount = snagraph.edgeValue(utenteu, utentev) + 1;
							// cercare un modifica del peso dell'arco tra due nodi
							snagraph.removeEdge(utenteu, utentev);
							snagraph.putEdgeValue(utenteu, utentev, mentioncount);
						} else {
							snagraph.putEdgeValue(utenteu, utentev, 1); // dobbiamo pescarlo dal grafo e poi inserire
						}
					}
				}
			}
		}
	}

	// issue#39
	/**
	 * Find all edges in degree of specified user
	 * 
	 * @param user
	 *            <b>User</b>
	 * @return <i>ArrayList</i> of Edge contains (<i>From,To,Weight</i>) for each
	 *         edge
	 * @throws ExceptionsHandler
	 */

	public Collection<Edge> edgesInDegree(final User user) {
		final ArrayList<Edge> edges = new ArrayList<Edge>();
		if (snagraph.inDegree(user) > 0) {
			int inEdges = snagraph.inDegree(user); // mi conto quanti nodi sono in entrata sull'utente preso in
													// analisi
			for (final User userTo : snagraph.nodes()) {
				if (snagraph.hasEdgeConnecting(userTo, user)) {
					// stampo l'arco tra i due utenti interessati
					edges.add(new Edge(userTo, user, (float) snagraph.edgeValue(userTo, user)));
					inEdges--; // diminuisco il grado di entrata del nodo per ottimizzare la ricerca a grado 0
					if (inEdges == 0) {
						break;
					}
				}
			}
		}
		return edges;
	}

	// issue37 && issue#38
	/**
	 * Find all edges out degree of specified user
	 * 
	 * @param user
	 *            <b>User</b>
	 * @return <i>ArrayList</i> of Edge contains (<i>From,To,Weight</i>) for each
	 *         edge
	 */
	public Collection<Edge> edgesOutDegree(final User user) {
		final ArrayList<Edge> edges = new ArrayList<Edge>();
		if (user == null) { // se non e' speficato restituisce tutti gli archi
			for (final User usNode : snagraph.nodes()) {
				for (final User adiacenti : snagraph.adjacentNodes(usNode)) {
					if (snagraph.hasEdgeConnecting(usNode, adiacenti)) {
						edges.add(new Edge(usNode, adiacenti, (float) snagraph.edgeValue(usNode, adiacenti)));
					}
				}
			}
		} else {
			if (snagraph.contains(user)) {
				for (final User adiacenti : snagraph.adjacentNodes(user)) {
					if (snagraph.hasEdgeConnecting(user, adiacenti)) {
						edges.add(new Edge(user, adiacenti, (float) snagraph.edgeValue(user, adiacenti)));
					}
				}
			}
		}
		return edges;
	}
}
