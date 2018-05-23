package it.uniba.parsing;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;

import it.uniba.workdata.User;
import it.uniba.workdata.Channel;
import it.uniba.workdata.Message;

/**
 * This interface provides general map-filling functions for our zipParser.
 */
public interface JsonParserInterface {

	/**
	 * Populates an User HashMap based on a IO reader.
	 * @param reader standard IO reader
	 * @return filled hashmap
	 */
	Map<String, User> populateUsers(Reader reader);

	/**
	 * Populates a Channel HashMap based on a IO reader.
	 * @param reader standard IO reader
	 * @return filled hashmap
	 */
	Map<String, Channel> populateChannels(Reader reader);

	/**
	 * Populates a List of messages hashmap.
	 * @param messages previous loaded messages, if any
	 * @param currChannel channel under parsing
	 * @param reader standard IO reader
	 * @return partially filled hashmap with currChannel's data
	 */
	Map<String, ArrayList<Message>> populateMessages(Map<String, ArrayList<Message>> messages,
			String currChannel, Reader reader);
}
