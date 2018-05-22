package it.uniba.view;

import java.util.Collection;
import java.util.HashMap;

import it.uniba.model.Edge;
import it.uniba.workdata.User;
import it.uniba.workdata.Channel;

/**
 * This class consist of methods for the all prints needed.
 */
public class View {
	/**
	 * View's constructor.
	 */
	public View() {
	}

	// -u
	/**
	 * Prints a list of <b>users</b>.
	 * 
	 * @param users
	 *            <i>Collection</i> of users to print
	 */
	public void printMembers(final Collection<User> users) {
		for (User us : users) {
			System.out.println(us.getRealName() + "\t@" + us.getName());
		}
	}

	// -c
	/**
	 * Prints a list of <b>channels</b>.
	 * 
	 * @param channels
	 *            <i>Collection</i> of channels to print
	 */
	public void printChannels(final Collection<Channel> channels) {
		for (Channel ch : channels) {
			System.out.println(ch.getName());
		}
	}

	// -uc
	/**
	 * Prints all the <b>channels</b> and the respective <b>members</b> .
	 * 
	 * @param users
	 *            <i>HashMap</i> of users
	 * @param channels
	 *            <i>Collection</i> of channels
	 */
	public void printMembers4Channel(final HashMap<String, User> users, final Collection<Channel> channels) {
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
	/**
	 * Prints all the <b>members</b> of a specified <b>channel</b>.
	 * 
	 * @param users
	 *            <i>HashMap</i> of users
	 * @param channels
	 *            <i>HashMap</i> of channels
	 * @param nameChannel
	 *            <i>String</i> name of the channel specified
	 */
	public void printChannelMembers(final HashMap<String, User> users, final HashMap<String, Channel> channels,
			final String nameChannel) {
		if (nameChannel != null && channels.containsKey(nameChannel)) {
			System.out.print(" + " + nameChannel + "\n");
			for (String key : channels.get(nameChannel).getMemberList()) {
				User utente = users.get(key);
				System.out.print("\t -" + utente.getRealName() + " @" + utente.getName() + "\n");
			}
			System.out.println();
		} else {
			missingChannel(nameChannel);
		}
	}

	/**
	 * Prints all the mention (<i>all the 'edges'</i>).
	 * 
	 * @param edges
	 *            <i>Collection</i> of Edges (mentions)
	 * @param weight
	 *            <i>boolean</i> used to decide if print the weight of the edges.
	 */
	public void printMention(final Collection<Edge> edges, final boolean weight) {
		printEdges(edges, weight);
	}

	/**
	 * Prints a collection of edges.
	 * 
	 * @param edges
	 *            <i>Collection</i> of Edges
	 * @param weight
	 *            <i>boolean</i> used to decide if print the weight of the edges.
	 */
	private void printEdges(final Collection<Edge> edges, final boolean weight) {
		if (!edges.isEmpty()) {
			for (Edge ed : edges) {
				System.out.print("From: " + ed.getFrom().getRealName() + "\tTo: " + ed.getTo().getRealName());
				if (weight) {
					System.out.print("\t n. mention: " + (int) ed.getWeigth());
				}
				System.out.println("");
			}
		} else {
			noMention();
		}
	}

	/**
	 * Prints a message saying that there aren't mention.
	 */
	public static void noMention() {
		System.out.println("There aren't mention.");
	}

	/**
	 * Prints a message saying that the <b>user</b> specified is missing.
	 * 
	 * @param user
	 *            <i>String</i> specified user's name
	 */
	public static void missingUser(final String user) {
		System.out.println("The user '" + user + "' doesn't exist.");
	}

	/**
	 * Prints a message saying that the <b>user</b> specified is invalid.
	 * 
	 */
	public static void invalidUser() {
		System.out.println("The user specified is invalid.");
	}

	/**
	 * Prints a message that saying that the <b>user</b> specified doesn't belong to a
	 * specified <b>channel</b>.
	 * 
	 * @param user
	 *            <i>String</i> specified user's name
	 * @param channel
	 *            <i>String</i> specified channel's name
	 * 
	 */
	public static void missingUserInChannel(final String user, final String channel) {
		System.out.println("The user '" + user + "' doesn't belong to the channel '" + channel + "'.");
	}

	/**
	 * Prints a message saying that the <b>channel</b> specified is missing.
	 * 
	 * @param channel
	 *            <i>String</i> specified channel's name
	 */
	public static void missingChannel(final String channel) {
		System.out.println("The channel '" + channel + "' doesn't exist.");
	}

	/**
	 * Prints the help's message.
	 */
	public static void showHelp() {
		System.out.println("Usage:");
		System.out.println("help - Shows this help\n");
		System.out.print("-w \"path\\to\\file.zip\" ( -c | -u | ");
		System.out.print("-uc <channelFilter> | -cu | -m [from <x>] | [to <y>] [in <z>])\n");
		System.out.println("\t-w Parses a workspace.");
		System.out.println("\t-c Prints all the channels in the specified workspace.");
		System.out.println("\t-u Prints all the users in the specified workspace.");
		System.out.println("\t-uc <channelFilter> Prints all the users in the specified channel.");
		System.out.println("\t-cu Prints all the channels with their users.");
		System.out.println("\t-m Prints all the mentions in a workspace.");
		System.out.println("\t\t-m from <user> filters the mentioner.");
		System.out.println("\t\t-m to <user> filters the mentioned.");
		System.out.println("\t\t-m in <channel> filters the channel.");
		System.out.print("\t\t-m from <user> OR to <user> in <channel> ");
		System.out.print("filters the mentioner or mentioned in the channel\n");
		System.out.println("\t\t\t using -n show the n. mentions and must be placed at last");
		System.out.println("\t\t\t (example:  -m <<from/to <user>> <in <channel>> <-n>");
	}
}
