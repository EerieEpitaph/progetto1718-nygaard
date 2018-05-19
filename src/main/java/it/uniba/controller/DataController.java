package it.uniba.controller;

import it.uniba.view.View;

import it.uniba.workdata.User;

import it.uniba.model.Model;

import java.io.IOException;
import java.util.Collection;
import java.util.zip.ZipException;

import it.uniba.model.Edge;

public class DataController {
	Model mod;
	View view;

	public DataController(Model _mod, View _view) {
		mod = _mod;
		view = _view;
	}

	public void updateModel(String path) throws ZipException, IOException {
		mod.updateModel(path);
	}

	public boolean hasLoaded() {
		return mod.hasLoaded();
	}

	// o utenti e channel e messaggi?
	// public static void loadModel(zipParser)
	public void printMembers() {
		// model.getUsers()
		view.printMembers(mod.getUsers().values());
	}

	public void printChannels() {
		// View.printChannels(model.getChannel().values());
		view.printChannels(mod.getChannels().values());
	}

	public void printMembers4Channel() {
		// View.printMembers4Channel(model.getUsers.values(),
		view.printMembers4Channel(mod.getUsers(), mod.getChannels().values());
	}

	public void printChannelMembers(final String _nameChannel) {
		view.printChannelMembers(mod.getUsers(), mod.getChannels(), _nameChannel);
	}

	public void printMention(final String _inChannel) {
		if (_inChannel == null || _inChannel.equals("")) {// -m
			mod.getMentionGraph().parseMessages(mod.getMessages(), mod.getUsers(), "");
			view.printMention(mod.getMentionGraph().edgesOutDegree(null));
		} else { // validazione canale -m in _inChannel
			if (mod.getChannels().containsKey(_inChannel)) {
				mod.getMentionGraph().parseMessages(mod.getMessages(), mod.getUsers(), _inChannel);
				view.printMention(mod.getMentionGraph().edgesOutDegree(null));
			} else {
				View.missingChannel(_inChannel);
			}
		}
	}

	// #38
	void printMentionsFromToUser(final String _user, final String _inChannel, final boolean _from) {
		if (_user != null || _user.equals("")) {
			String idUser = getUserFromId(_user);
			if (mod.getUsers().containsKey(idUser)) // l'utente esiste nel workspace
			{
				if ((_inChannel == null || _inChannel.equals("")) || mod.getChannels().containsKey(_inChannel)) {
					mod.getMentionGraph().parseMessages(mod.getMessages(), mod.getUsers(), _inChannel);
					Collection<Edge> edgesneeded;
					if (_from) {
						edgesneeded = mod.getMentionGraph().edgesOutDegree(mod.getUsers().get(idUser));
					} else {
						edgesneeded = mod.getMentionGraph().edgesInDegree(mod.getUsers().get(idUser));
					}
					view.printMention(edgesneeded);
				}
			} else {
				View.missingUser(_user);
			}
			if (!(_inChannel == null || (_inChannel.equals(""))) && (!mod.getChannels().containsKey(_inChannel))) {
				View.missingChannel(_inChannel);
			}
		}
	}

	public void printMentionsFromUser(final String _user, final String _inChannel) {
		printMentionsFromToUser(_user, _inChannel, true);
	}

	// #39
	public void printMentionsToUser(final String _user, final String _inChannel) {
		printMentionsFromToUser(_user, _inChannel, false);
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

	public void printMentionsFromUserWeigthed(final String _user, final String _inChannel) {
		printMentionsFromToUser(_user, _inChannel, true);
	}

	public void printMentionsToUserWeigthed(final String _user, final String _inChannel) {
		printMentionsFromToUser(_user, _inChannel, false);
	}

	String getUserFromId(String name) {
		// possiamo aggiunger eccezione

		for (User x : mod.getUsers().values()) {
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
