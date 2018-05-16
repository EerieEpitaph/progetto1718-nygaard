package it.uniba.model;

import java.util.ArrayList;
import java.util.HashMap;

import it.uniba.workdata.Channel;
import it.uniba.workdata.Message;
import it.uniba.workdata.User;

public class Model {
	private HashMap<String, User> users = new HashMap<String, User>();
	private HashMap<String, Channel> channels = new HashMap<String, Channel>();
	private HashMap<String, ArrayList<Message>> messages = new HashMap<String, ArrayList<Message>>();

	MentionGraph snagraph = new MentionGraph();

	public Model() {}
	public Model(HashMap<String, User> _users, HashMap<String, Channel> _channels,
			HashMap<String, ArrayList<Message>> _messages) {
		users = _users;
		channels = _channels;
		messages = _messages;
	}

	public HashMap<String, User> getUser() {
		return users;
	}

	public HashMap<String, Channel> getChannel() {
		return channels;
	}

	public HashMap<String, ArrayList<Message>> getMessages() {
		return messages;
	}

	public void generateGraph() {
		snagraph.generate();
	}
}
