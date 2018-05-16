package it.uniba.view;

import java.util.Collection;
import java.util.HashMap;

import it.uniba.model.Edge;
import it.uniba.workdata.User;
import it.uniba.workdata.Channel;

public class View {

	// private HashMap<String, User> users = new HashMap<String, User>();
	// private HashMap<String, Channel> channels = new HashMap<String, Channel>();
	// private HashMap<String, ArrayList<Message>> messages = new HashMap<String,
	// ArrayList<Message>>();
	//
	// MentionGraph snagraph = new MentionGraph();

	public View() {
	}

	// -u
	public void printMembers(Collection<User> users) {
		for (User us : users) {
			System.out.println(us.getRealName() + "\t@" + us.getName());
		}
	}

	// -c
	public void printChannels(Collection<Channel> channels) {
		for (Channel ch : channels) {
			System.out.println(ch.getName());
		}
	}

	// -uc
	public void printMembers4Channel(HashMap<String, User> users, Collection<Channel> channels) {
		for (Channel canale : channels) {
			System.out.print(" + " + canale.getName() + "\n\t");
			for (String membro : canale.getMemberList()) {
				User user = users.get(membro);
				System.out.print(" - " + user.getRealName() + "\t@ " + user.getName() + "\n\t");
			}
			System.out.println();
		}
	}
	// -cu <channel>

	public void printChannelMembers(HashMap<String, User> users, HashMap<String, Channel> channels,
			final String _nameChannel) {
		if (channels.containsKey(_nameChannel)) {
			System.out.print(" + " + _nameChannel + "\n");
			for (String key : channels.get(_nameChannel).getMemberList()) {
				User utente = users.get(key);
				System.out.print("\t -" + utente.getRealName() + " @" + utente.getName() + "\n");
			}
			System.out.println();
		} else
			System.out.println("There is no channel \"" + _nameChannel + "\"");
	}

	// -m || -m in <channel>
	// -m in x Tutte le mention in Channel x
	public void printMention(Collection<Edge> edges, final boolean _weigth) {
		printEdges(edges, _weigth);
	}

	// -m from x Tutte le mention dall'User x
	// -m from x in y Tutte le mention dall'User x in Channel y
//	public void printMentionFromUser() {
//	}

	// -m to x Tutte le mention in cui � menzionato User x
	// -m to x in y Tutte le mention in cui � menzionato User x in Channel y
//	public void printMentionsToUser() {
//	}

	void printEdges(Collection<Edge> edges, final boolean _weigth) {
		if (!edges.isEmpty()) {
			for (Edge ed : edges) {
				System.out.print("From: " + ed.getFrom().getRealName() + "\tTo: " + ed.getTo().getRealName());
				if (_weigth) {
					System.out.print("\t n. mention: " + (int) ed.getWeigth());
				}
				System.out.println(".");
			}
		} else {
			noMention();
		}

		/*
		 * int numNodesPrinted = 0; if (user == null) { for (User x : snagraph.nodes())
		 * for (User adiacenti : snagraph.adjacentNodes(x)) if
		 * (snagraph.hasEdgeConnecting(x, adiacenti)) { numNodesPrinted++; } if
		 * (numNodesPrinted == 0) // eccezione: non ci sono mention nel workspace
		 * System.out.println("There aren't mention."); } else { if
		 * (snagraph.nodes().contains(user)) { for (User adiacenti :
		 * snagraph.adjacentNodes(user)) if (snagraph.hasEdgeConnecting(user,
		 * adiacenti)) { System.out.println("From: " + user.getRealName() + "\tTo: " +
		 * adiacenti.getRealName() + "\t n. mention: " + snagraph.edgeValue(user,
		 * adiacenti).get()); numNodesPrinted++; } if (numNodesPrinted == 0) //
		 * eccezione: non ci sono mention nel channel specificato
		 * System.out.println("There aren't mention in the channel specified."); } else
		 * { // eccezione : user non presente nel canale specificato
		 * System.out.println("The user specified doesn't belong to this channel."); } }
		 */
	}

	public static void noMention() {
		System.out.println("There aren't mention.");
	}

	public static void missingUser(final String _user) {
		System.out.println("The user '" + _user + "' doesn't exist.");
	}

	public static void missingUserInChannel(final String _user, final String _channel) {
		System.out.println("The user '" + _user + "' doesn't belong to the channel '" + _channel + "'.");
	}

	public static void missingChannel(final String _channel) {
		System.out.println("The channel '" + _channel + "' doesn't exist.");
	}
	// public static void altro() {}

}
