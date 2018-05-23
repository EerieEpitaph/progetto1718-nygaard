package it.uniba.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
	private HashMap<String, User> users = new HashMap<String, User>();
	/*
	 * Map of <i>Channels</i>
	 */
	private HashMap<String, Channel> channels = new HashMap<String, Channel>();
	/*
	 * Map of <i>Messages</i> group by channels
	 */
	private HashMap<String, ArrayList<Message>> messages = new HashMap<String, ArrayList<Message>>();

	/*
	 * Graph of all mentions present in the workspace
	 */
	private MentionGraph snagraph = new MentionGraph();

	/*
	 * ZipParser for loading data from zip file
	 */
	private ZipParser fileParser = new ZipParser();

	/**
	 * Load all data of Model from procedure load of class ZipParser
	 * 
	 * @param path <i>String</i> Path of Zipfile
	 * 
	 * @throws ZipException Exception of ZipParser
	 * 
	 * @throws IOException Exception of IOFileException
	 */
	public void updateModel(final String path) throws ZipException, IOException {
		fileParser.load(path);

		users = fileParser.getUsers();
		channels = fileParser.getChannels();
		messages = fileParser.getMessages();
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
	public HashMap<String, User> getUsers() {
		return users;
	}

	/**
	 * 
	 * @return <i>HashMap</i> of all Channels
	 */
	public HashMap<String, Channel> getChannels() {
		return channels;
	}

	/**
	 * 
	 * @return <i>HashMap</i> of all messagges
	 */
	public HashMap<String, ArrayList<Message>> getMessages() {
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
}
