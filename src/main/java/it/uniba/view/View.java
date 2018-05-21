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

	// -m to x Tutte le mention in cui � menzionato User x
	// -m to x in y Tutte le mention in cui � menzionato User x in Channel y

	// -m to x Tutte le mention in cui � menzionato User x
	// -m to x in y Tutte le mention in cui � menzionato User x in Channel y

	public void printEdges(Collection<Edge> edges, final boolean _weigth) {
		if (!edges.isEmpty()) {
			for (Edge ed : edges) {
				System.out.print("From: " + ed.getFrom().getRealName() + "\tTo: " + ed.getTo().getRealName());
				if (_weigth)
					System.out.print("\t n. mention: " + (int) ed.getWeigth());
				System.out.println("");
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

	public static void showHelp() {
		System.out.println("Usage:");
		System.out.println("help - Shows this help\n");
		System.out.println(
				"-w \"path\\to\\file.zip\" ( -c | -u | -uc <channelFilter> | -cu | -m [from <x>] | [to <y>] [in <z>])");
		System.out.println("\t-w Parses a workspace.");
		System.out.println("\t-c Prints all the channels in the specified workspace.");
		System.out.println("\t-u Prints all the users in the specified workspace.");
		System.out.println("\t-uc <channelFilter> Prints all the users in the specified channel.");
		System.out.println("\t-cu Prints all the channels with their users.");
		System.out.println("\t-m Prints all the mentions in a workspace.");
		System.out.println("\t\t-m from <user> filters the mentioner.");
		System.out.println("\t\t-m to <user> filters the mentioned.");
		System.out.println("\t\t-m in <channel> filters the channel.");
		System.out.println(
				"\t\t-m from <user> OR to <user> in <channel> filters the mentioner or mentioned in the channel");
		System.out.println("\t\t\t using -n show the n. mentions and must be placed at last");
		System.out.println("\t\t\t (example:  -m <<from/to <user>> <in <channel>> <-n>");
	}

}
