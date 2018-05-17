package it.uniba.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipException;

import it.uniba.parsing.ZipParser;
import it.uniba.workdata.Channel;
import it.uniba.workdata.Message;
import it.uniba.workdata.User;

public class Model {
	// WorkData
	private HashMap<String, User> users = new HashMap<String, User>();
	private HashMap<String, Channel> channels = new HashMap<String, Channel>();
	private HashMap<String, ArrayList<Message>> messages = new HashMap<String, ArrayList<Message>>();

	// MentionGraph
	MentionGraph snagraph = new MentionGraph();

	ZipParser fileParser = new ZipParser();

	public void updateModel(String path) throws ZipException, IOException {
		fileParser.load(path);

		users = fileParser.getUsers();
		channels = fileParser.getChannels();
		messages = fileParser.getMessages();
	}

	public boolean hasLoaded() {
		return fileParser.hasLoaded();
	}

	public Model() {
	}

	public Model(HashMap<String, User> _users, HashMap<String, Channel> _channels,
			HashMap<String, ArrayList<Message>> _messages) {
		users = _users;
		channels = _channels;
		messages = _messages;
	}

	public HashMap<String, User> getUsers() {
		return users;
	}

	public HashMap<String, Channel> getChannels() {
		return channels;
	}

	public HashMap<String, ArrayList<Message>> getMessages() {
		return messages;
	}

	public MentionGraph getMentionGraph() {
		return snagraph;
	}
}
