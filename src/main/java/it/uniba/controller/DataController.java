package it.uniba.controller;

import it.uniba.view.View;

 
import it.uniba.workdata.User;
 

import it.uniba.model.Model;
import it.uniba.parsing.ZipParser;

public class DataController {
	Model mod;
	View view;
	
	public DataController() {
		
	}
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
		if (_inChannel.equals("")) {//-m
			fileParser.getMentionGraph().parseMessages(fileParser.getMessages(), fileParser.getUsers(), "");

			fileParser.getMentionGraph().edgesOutDegree(null);
			// fileParser.getMentionGraph().printEdges(null);
		} else { // validazione canale -m in _inChannel
			if (fileParser.getChannels().containsKey(_inChannel)) {
				fileParser.getMentionGraph().parseMessages(fileParser.getMessages(), fileParser.getUsers(), _inChannel);
			} else {
				System.out.println("The channel specified doesn't exist.");
			}
		}
	}

	// #38
	public void printMentionsFromUser(ZipParser fileParser, String user, String inChannel) {
		String idUser = getUserFromId(fileParser, user);
		if (inChannel.equals("")) {
			if (fileParser.getUsers().containsKey(idUser)) // l'utente esiste nel workspace
			{
				fileParser.getMentionGraph().parseMessages(fileParser.getMessages(), fileParser.getUsers(), "");
				// parse
				// dei
				// mention
				// sul
				// grafo
				// fileParser.getMentionGraph().printEdges(fileParser.getUsers().get(idUser));
			} else {
				System.out.println("The user specified doesn't exist.");
			}
		} else {
			if (fileParser.getUsers().containsKey(idUser) && fileParser.getChannels().containsKey(inChannel)) {
				fileParser.getMentionGraph().parseMessages(fileParser.getMessages(), fileParser.getUsers(), inChannel); // parse
																														// dei
																														// mention
																														// sul
																														// grafo
				// fileParser.getMentionGraph().printEdges(fileParser.getUsers().get(idUser));
			} else {
				if (!fileParser.getUsers().containsKey(idUser))
					System.out.println("The user specified doesn't exist.");
				if (!fileParser.getChannels().containsKey(inChannel))
					System.out.println("The channel specified doesn't exist.");
			}
		}
	}

	// #39
	public void printMentionsToUser(ZipParser fileParser, String user, String inChannel) {
		String idUser = getUserFromId(fileParser, user);
		if (inChannel.equals("")) {
			if (fileParser.getUsers().containsKey(idUser)) // l'utente esiste nel workspace
			{
				fileParser.getMentionGraph().parseMessages(fileParser.getMessages(), fileParser.getUsers(), "");
				// parse
				// dei
				// mention
				// sul
				// grafo
				// fileParser.getMentionGraph().printEdgesInDegree(fileParser.getUsers().get(idUser));
			} else {
				System.out.println("The user specified doesn't exist.");
			}
		} else {
			if (fileParser.getUsers().containsKey(idUser) && fileParser.getChannels().containsKey(inChannel)) {
				fileParser.getMentionGraph().parseMessages(fileParser.getMessages(), fileParser.getUsers(), inChannel);
				// parse
				// dei
				// mention
				// sul
				// grafo
				// fileParser.getMentionGraph().printEdgesInDegree(fileParser.getUsers().get(idUser));
			} else {
				if (!fileParser.getUsers().containsKey(idUser))
					System.out.println("The user specified doesn't exist.");
				if (!fileParser.getChannels().containsKey(inChannel))
					System.out.println("The channel specified doesn't exist.");
			}
		}
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
