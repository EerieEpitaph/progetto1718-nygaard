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

/**
 * <i>Model</i> storage and manipulate Data
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
	private final MentionGraph snagraph = new MentionGraph();

	/*
	 * ZipParser for loading data from zip file
	 */
	private final ZipParser fileParser = new ZipParser();

	/**
	 * Load all data of Model from procedure load of class ZipParser
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
	 * Check if fileParser has load data from zip file
	 * 
	 * @return <i>boolean</i> if fileParser has load data from zip file
	 */
	public boolean hasLoaded() {
		return fileParser.hasLoaded();
	}

	/**
	 * Default costructor of Model
	 */
	public Model() {
		// This constructor is intentionally empty. Nothing special is needed here.
	}

	// /**
	// * Initialize a generic model after load the data
	// * @param _users <i>HashMap</i> of all <b>user</b> present in the workspace
	// * @param _channels <i>HashMap</i> of all <b>channels</b> present in the
	// workspace
	// * @param _messages <i>HashMap</i> of all <b>Messages</b> present in the
	// workspace
	// */
	// public Model(HashMap<String, User> _users, HashMap<String, Channel>
	// _channels,
	// HashMap<String, ArrayList<Message>> _messages) {
	// users = _users;
	// channels = _channels;
	// messages = _messages;
	// }

	/**
	 * 
	 * @return <i>HashMap</i> of Users
	 */
	public Map<String, User> getUsers() {
		return users;
	}

	public Collection<User> getUsersList() {
		return users.values();
	}

	/**
	 * 
	 * @return <i>HashMap</i> of all Channels
	 */
	public Map<String, Channel> getChannels() {
		return channels;
	}

	/**
	 * 
	 * @return <i>HashMap</i> of all messagges
	 */
	public Map<String, ArrayList<Message>> getMessages() {
		return messages;
	}

	/**
	 * 
	 * @return <i>MentionGraph</i> an instance of MentionGraph witch contains all
	 *         mentions
	 */
	public MentionGraph getMentionGraph() {
		return snagraph;
	}

	public Collection<Edge> getEdgesInDegree(final User userMention, final String inChannel) {
		if (snagraph.isEmpty()) {
			snagraph.parseMessages(messages, users, inChannel);
		}
		return snagraph.edgesInDegree(userMention);
	}

	public Collection<Edge> getEdgesOutDegree(final User userMention, final String inChannel) {
		if (snagraph.isEmpty()) {
			snagraph.parseMessages(messages, users, inChannel);
		}
		return snagraph.edgesOutDegree(userMention);
	}

	public boolean containsChannel(final String channel) {
		return channels.containsKey(channel);
	}

	public boolean containsUser(final String user) {
		return users.containsKey(user);
	}

	public User getUser(final String user) {
		return users.get(user);
	}

	public Channel getChannel(final String channel) {
		return channels.get(channel);
	}

}
