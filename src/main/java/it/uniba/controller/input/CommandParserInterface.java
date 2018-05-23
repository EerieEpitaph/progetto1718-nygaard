package it.uniba.controller.input;

/**
 * This interface provides general methods to parse a command.
 */
public interface CommandParserInterface {
	/**
	 * Provides info on workspace validity.
	 * 
	 * @return true if a valid workspace has been loaded, false every other time
	 */
	Boolean validWorkspace();

	/**
	 * Provides name of current workspace.
	 * 
	 * @return name of current loaded workspace
	 */
	String getWorkspace();

	/**
	 * Provides info on called help command.
	 * 
	 * @return true if help called, false every other time
	 */
	Boolean help();

	/**
	 * Provides info on user command.
	 * 
	 * @return true if user called, false every other time
	 */
	Boolean users();

	/**
	 * Provides info on channels command.
	 * 
	 * @return true if channels called, false every other time
	 */
	Boolean channels();

	/**
	 * Provides info on extended channels command.
	 * 
	 * @return true if extended channel called, false every other time
	 */
	Boolean extendedChannels();

	/**
	 * Provides info on users in channel command.
	 * 
	 * @return true if users in channel called
	 */
	Boolean usersInChannel();

	/**
	 * Provides filtered channel.
	 * 
	 * @return name of asked filter
	 */
	String getChannelFilter();

	/**
	 * Provides info on mention command.
	 * 
	 * @return true if mention called, false every other time
	 */
	Boolean mentions();

	/**
	 * Provides info on from command.
	 * 
	 * @return true if from called, false every other time
	 */
	Boolean from();

	/**
	 * Provides name of user from which mentions are to be parsed.
	 * 
	 * @return name of asked user
	 */
	String getFromWho();

	/**
	 * Provides info on to command.
	 * 
	 * @return true if to called, false every other time
	 */
	Boolean to();

	/**
	 * Provides name of user to which mentions are to be parsed.
	 * 
	 * @return name of asked user
	 */
	String getToWho();

	/**
	 * Provides info on in command.
	 * 
	 * @return true if in called, false every other time
	 */
	Boolean in();

	/**
	 * Provides name of channel in which mentions are to be parsed.
	 * 
	 * @return name of asked channel
	 */
	String getInWhat();

	/**
	 * Provides info on weighted command.
	 * 
	 * @return true if weighted called, false every other time
	 */
	Boolean weighted();
}
