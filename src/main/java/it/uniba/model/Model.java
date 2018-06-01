package it.uniba.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipException;

import it.uniba.parsing.ZipParser;
import it.uniba.workdata.Channel;
import it.uniba.workdata.Message;
import it.uniba.workdata.User;
import it.uniba.controller.ExceptionsHandler;

/**
 * <i>Model</i>: Data storage and manipulation
 */
public class Model {
	// WorkData
	/*
	 * Map of <i>Users</i>
	 */
	private Map<String, User> users = new HashMap<String, User>();
	/*
	 * Map of <i>Channels</i>
	 */
	private Map<String, Channel> channels = new HashMap<String, Channel>();
	/*
	 * Map of <i>Messages</i> group by channels
	 */
	private Map<String, ArrayList<Message>> messages = new HashMap<String, ArrayList<Message>>();

	/*
	 * Graph of all mentions present in the workspace
	 */
	private MentionGraph snagraph = new MentionGraph();

	/*
	 * ZipParser for loading data from zip file
	 */
	private final ZipParser fileParser = new ZipParser();

	/**
	 * Load all of the Model data by calling the load procedure from the ZipParser
	 * class
	 * 
	 * @param path
	 *            <i>String</i> Path of Zipfile
	 * 
	 * @throws ZipException
	 *             Exception of ZipParser
	 * 
	 * @throws IOException
	 *             Exception of IOFileException
	 */
	public void updateModel(final String path) throws ZipException, IOException {
		fileParser.load(path);

		users = (HashMap<String, User>) fileParser.getUsers();
		channels = (HashMap<String, Channel>) fileParser.getChannels();
		messages = (HashMap<String, ArrayList<Message>>) fileParser.getMessages();
	}

	/**
	 * Default costructor of Model
	 */
	public Model() {
		// This constructor is intentionally empty. Nothing special is needed here.
	}

	/**
	 * Returns a <i>HashMap</i> of Users.
	 * 
	 * @return <i>HashMap</i> of Users
	 */
	public Map<String, User> getUsers() {
		return users;
	}

	/**
	 * Returns a <i>HashMap</i> of Users.
	 * 
	 * @return <i>HashMap</i> of all Channels
	 */
	public Map<String, Channel> getChannels() {
		return channels;
	}

	/**
	 * Returns a <i>HashMap</i> of messages.
	 * 
	 * @return <i>HashMap</i> of all messages
	 */
	public Map<String, ArrayList<Message>> getMessages() {
		return messages;
	}

	/**
	 * Returns a <i>Collection</i> of edges where the the <b>user</b> specified it
	 * was mentioned.
	 * 
	 * @param userMention
	 *            <i>User</i> mentioned (<i>to</i>)
	 * @param inChannel
	 *            <i>String</i> name of the channel (<i>to</i>)
	 * @return <i>Collection</i> of Edges
	 * @throws ExceptionsHandler
	 *             used to handle exceptions
	 */
	public Collection<Edge> getEdgesInDegree(final User userMention, final String inChannel) throws ExceptionsHandler {
		if (snagraph.isEmpty()) {
			snagraph.generate(inChannel, messages, users);
		}
		return snagraph.edgesInDegree(userMention);
	}

	/**
	 * Returns a <i>Collection</i> of edges where the the <b>user</b> specified has
	 * mentioned someone.
	 * 
	 * @param userMention
	 *            <i>User</i> mentioned (<i>to</i>)
	 * @param inChannel
	 *            <i>String</i> name of the channel (<i>to</i>)
	 * @return <i>Collection</i> of Edges
	 * @throws ExceptionsHandler
	 *             used to handle exceptions
	 */
	public Collection<Edge> getEdgesOutDegree(final User userMention, final String inChannel) throws ExceptionsHandler {
		if (snagraph.isEmpty()) {
			snagraph.generate(inChannel, messages, users);
		}
		return snagraph.edgesOutDegree(userMention);
	}

	/**
	 * Returns a <i>boolean</i> true if the <b>channel</b> specified exists else
	 * false.
	 * 
	 * @param channel
	 *            <i>String</i> name of the channel
	 * @return <i>boolean</i> true if exists else false
	 * 
	 */
	public boolean containsChannel(final String channel) {
		return channels.containsKey(channel);
	}

	/**
	 * Returns a <i>boolean</i> true if the <b>user</b> with the id specified exists
	 * else false.
	 * 
	 * @param user
	 *            <i>String</i> id the user
	 * @return <i>boolean</i> true if exists else false
	 * 
	 */
	public boolean containsUser(final String user) {
		return users.containsKey(user);
	}

	/**
	 * Returns the <b>user</b> with the id specified.
	 * 
	 * @param user
	 *            <i>String</i> id the user
	 * @return <i>User</i> specified
	 * 
	 */
	public User getUser(final String user) {
		return users.get(user);
	}

}
