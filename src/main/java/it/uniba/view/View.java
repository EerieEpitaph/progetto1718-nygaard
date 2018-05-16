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
	public void printMention(Collection<Edge> edges) {
		printEdges(edges);
	}

	// -m from x Tutte le mention dall'User x
	// -m from x in y Tutte le mention dall'User x in Channel y

	// -m to x Tutte le mention in cui � menzionato User x
	// -m to x in y Tutte le mention in cui � menzionato User x in Channel y

	// -m to x Tutte le mention in cui � menzionato User x
	// -m to x in y Tutte le mention in cui � menzionato User x in Channel y

	void printEdges(Collection<Edge> edges) {
		if (!edges.isEmpty()) {
			for (Edge ed : edges) {
				System.out.println("From: " + ed.getFrom().getRealName() + "\tTo: " + ed.getTo().getRealName()
						+ "\t n. mention: " + (int) ed.getWeigth());
			}
		} else {
			noMention();
		}
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
