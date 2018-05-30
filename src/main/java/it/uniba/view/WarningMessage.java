package it.uniba.view;

public final class WarningMessage {
	private WarningMessage() {
		// nulla di importante
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
	 * Prints a message that saying that the <b>user</b> specified doesn't belong to
	 * a specified <b>channel</b>.
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