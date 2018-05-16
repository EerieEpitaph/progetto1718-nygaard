package it.uniba.controller;

import it.uniba.view.View;

import it.uniba.workdata.User;

import it.uniba.model.Model;

import java.util.Collection;

import it.uniba.model.Edge;
import it.uniba.parsing.ZipParser;

public class DataController {
	Model mod;
	View view;

	public DataController(Model _mod, View _view) {
		mod = _mod;
		view = _view;
	}

	// o utenti e channel e messaggi?
	// public static void loadModel(zipParser)
	public void printMembers(ZipParser fileParser) {
		// model.getUsers()
		view.printMembers(fileParser.getUsers().values());
	}

	public void printChannels(ZipParser fileParser) {
		// View.printChannels(model.getChannel().values());
		view.printChannels(fileParser.getChannels().values());
	}

	public void printMembers4Channel(ZipParser fileParser) {
		// View.printMembers4Channel(model.getUsers.values(),
		view.printMembers4Channel(fileParser.getUsers(), fileParser.getChannels().values());
	}

	public void printChannelMembers(ZipParser fileParser, final String _nameChannel) {
		view.printChannelMembers(fileParser.getUsers(), fileParser.getChannels(), _nameChannel);
	}

	public void printMention(ZipParser fileParser, final String _inChannel) {
		if (_inChannel.equals("") || _inChannel == null) {// -m
			fileParser.getMentionGraph().parseMessages(fileParser.getMessages(), fileParser.getUsers(), "");
			view.printMention(fileParser.getMentionGraph().edgesOutDegree(null));
		} else { // validazione canale -m in _inChannel
			if (fileParser.getChannels().containsKey(_inChannel)) {
				fileParser.getMentionGraph().parseMessages(fileParser.getMessages(), fileParser.getUsers(), _inChannel);
				view.printMention(fileParser.getMentionGraph().edgesOutDegree(null));
			} else {
				View.missingChannel(_inChannel);
			}
		}
	}

	// #38
	void printMentionsFromToUser(ZipParser fileParser, final String _user, final String _inChannel,
			final boolean _from) {
		String idUser = getUserFromId(fileParser, _user);
		if (fileParser.getUsers().containsKey(idUser)) // l'utente esiste nel workspace
		{
			if ((_inChannel == null || _inChannel.equals("")) || fileParser.getChannels().containsKey(_inChannel)) {
				fileParser.getMentionGraph().parseMessages(fileParser.getMessages(), fileParser.getUsers(), _inChannel);
				Collection<Edge> edgesneeded;
				if (_from) {
					edgesneeded = fileParser.getMentionGraph().edgesOutDegree(fileParser.getUsers().get(idUser));
				} else {
					edgesneeded = fileParser.getMentionGraph().edgesInDegree(fileParser.getUsers().get(idUser));
				}
				view.printMention(edgesneeded);
			}
		} else {
			View.missingUser(_user);
		}
		if (!(_inChannel == null || (_inChannel.equals(""))) && (!fileParser.getChannels().containsKey(_inChannel))) {
			View.missingChannel(_inChannel);
		}
	}

	public void printMentionsFromUser(ZipParser fileParser, final String _user, final String _inChannel) {
		printMentionsFromToUser(fileParser, _user, null, true);
	}

	// #39
	public void printMentionsToUser(ZipParser fileParser, final String _user, final String _inChannel) {
		printMentionsFromToUser(fileParser, _user, _inChannel, false);
		/*
		 * String idUser = getUserFromId(fileParser, _user); if (_inChannel.equals(""))
		 * { if (fileParser.getUsers().containsKey(idUser)) // l'utente esiste nel
		 * workspace {
		 * fileParser.getMentionGraph().parseMessages(fileParser.getMessages(),
		 * fileParser.getUsers(), "");
		 * 
		 * } else { System.out.println("The user specified doesn't exist."); } } else {
		 * if (fileParser.getUsers().containsKey(idUser) &&
		 * fileParser.getChannels().containsKey(_inChannel)) {
		 * fileParser.getMentionGraph().parseMessages(fileParser.getMessages(),
		 * fileParser.getUsers(), _inChannel); // parse // dei // mention // sul //
		 * grafo //
		 * fileParser.getMentionGraph().printEdgesInDegree(fileParser.getUsers().get(
		 * idUser)); } else { if (!fileParser.getUsers().containsKey(idUser))
		 * System.out.println("The user specified doesn't exist."); if
		 * (!fileParser.getChannels().containsKey(_inChannel))
		 * System.out.println("The channel specified doesn't exist."); } }
		 */}

	public void printMentionsFromUserWeigthed(ZipParser fileParser, final String _user, final String _inChannel) {
		printMentionsFromToUser(fileParser, _user, _inChannel, true);
	}

	public void printMentionsToUserWeigthed(ZipParser fileParser, final String _user, final String _inChannel) {
		printMentionsFromToUser(fileParser, _user, _inChannel, false);
	}

	static String getUserFromId(ZipParser fileParser, String name) {
		// possiamo aggiunger eccezione
		for (User x : fileParser.getUsers().values()) {
			String disName = x.getDisplayNameNorm();
			String rn = x.getRealName();
			String _name = x.getName();

			if (disName != null)
				if (disName.equals(name))
					return x.getId();
			if (rn != null)
				if (rn.equals(name))
					return x.getId();
			if (_name != null)
				if (_name.equals(name))
					return x.getId();
		}
		return "";
	}
}
