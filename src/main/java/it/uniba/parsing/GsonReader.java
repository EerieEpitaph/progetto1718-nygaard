package it.uniba.parsing;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.uniba.workdata.Channel;
import it.uniba.workdata.Message;
import it.uniba.workdata.User;
import it.uniba.workdata.Message.GsonMessage;

/**
 * This class implement JsonParserInterface.
 */
public class GsonReader implements JsonParserInterface {

	/**
	 * New object responsible for gson parsing created.
	 */
	private final Gson gson = new GsonBuilder().create();

	/**
	 * Interface overriding.
	 * @param reader same as overrided.
	 */
	@Override
	public Map<String, User> populateUsers(final Reader reader) {
		final HashMap<String, User> tempMap = new HashMap<String, User>();
		final User[] tempUser = gson.fromJson(reader, User[].class);

		for (final User x : tempUser) {
			// System.out.println(x.getId() + " " +
			// x.getDisplayNameNorm());
			tempMap.put(x.getId(), x);
		}
		return tempMap;
	}

	/**
	 * Interface overriding.
	 * @param reader same as overrided.
	 */
	@Override
	public Map<String, Channel> populateChannels(final Reader reader) {
		final HashMap<String, Channel> tempMap = new HashMap<String, Channel>();
		final Channel[] tempChannel = gson.fromJson(reader, Channel[].class);
		for (final Channel x : tempChannel) {
			// System.out.println(x.getId());
			tempMap.put(x.getName(), x);
		}
		return tempMap;
	}

	/**
	 * Interface overriding.
	 * @param reader same as overrided.
	 */
	@Override
	public Map<String, ArrayList<Message>> populateMessages(final Map<String, ArrayList<Message>> tempMap,
			final String currChannel, final Reader reader) {
		final GsonMessage[] tempMessage = gson.fromJson(reader, GsonMessage[].class);
		for (final GsonMessage x : tempMessage) {
			final Message tempMes = x.toMessage();
			if (tempMap.containsKey(currChannel)) {
				final ArrayList<Message> tempMessList = new ArrayList<>(tempMap.get(currChannel));
				tempMessList.add(tempMes);
				tempMap.replace(currChannel, tempMessList);
			} else {
				final ArrayList<Message> msg = new ArrayList<Message>();
				msg.add(tempMes);
				tempMap.put(currChannel, msg);
			}
		}
		return tempMap;
	}

}
