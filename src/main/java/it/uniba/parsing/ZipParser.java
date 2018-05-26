package it.uniba.parsing;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import it.uniba.workdata.Message;
import it.uniba.workdata.Channel;
import it.uniba.workdata.User;

/**
 * This class parses a zipped workspace and populated our datastructures.
 */
public class ZipParser {

	/*
	 * Current workspace.
	 */
	private String workspaceLoaded = "";

	// I tre dizionari users, channels e i messaggi
	/*
	 * Map of users.
	 */
	private Map<String, User> users = new HashMap<String, User>();

	/*
	 * Map of channels.
	 */
	private Map<String, Channel> channels = new HashMap<String, Channel>();

	/*
	 * Map of lists of messages.
	 */
	private Map<String, ArrayList<Message>> messages = new HashMap<String, ArrayList<Message>>();

	/**
	 * Sets a new current workspace.
	 * @param value workspace name
	 */
	public void setWorkspaceName(final String value) {
		workspaceLoaded = value;
	}

	/**
	 * Return current workspace name.
	 * @return current workspace name
	 */
	public String getWorkspaceName() {
		return workspaceLoaded;
	}

	/**
	 * Loader monitor.
	 * @return true if a workspace was loaded, false if not.
	 */
	public Boolean hasLoaded() {
		return !"".equals(workspaceLoaded);
	}

	/**
	 * User getter.
	 * @return map of users
	 */
	public Map<String, User> getUsers() {
		return users;
	}

	/**
	 * Channel getter.
	 * @return map of channels
	 */
	public Map<String, Channel> getChannels() {
		return channels;
	}

	/**
	 * Messages getter.
	 * @return map of lists of messages
	 */
	public Map<String, ArrayList<Message>> getMessages() {
		return messages;
	}

	/**
	 * This function loads a zipped file and inflates it in central memory.
	 * After inflating, it parses every useful json and populates our structures.
	 * @param path the path of the .zipped file
	 * @throws ZipException if file does not exists
	 * @throws IOException for errors at OS level
	 */
	public void load(final String path) throws ZipException, IOException {
		Boolean loadedSomething = false;

		String currChannel = "";
		final ZipFile zip = new ZipFile(path);
		final Enumeration<? extends ZipEntry> entries = zip.entries();

		while (entries.hasMoreElements()) {
			final ZipEntry entry = entries.nextElement();
			// System.out.println(entry.getName() + " ");

			if (!entry.getName().equals("integration_logs.json")) {
				if (entry.isDirectory()) {
					currChannel = entry.getName().substring(0, entry.getName().length() - 1);
				} else {
					loadedSomething = true;
					final JsonParserInterface jsonBridge = new GsonReader();
					final Reader lecturer = new InputStreamReader(zip.getInputStream(entry),
							StandardCharsets.ISO_8859_1);

					if (entry.getName().equals("users.json")) {
						users = (HashMap<String, User>) jsonBridge.populateUsers(lecturer);
					} else if (entry.getName()
							.equals("channels.json")) {
						channels = (HashMap<String, Channel>)
								jsonBridge.populateChannels(lecturer);
					} else {
						messages = (HashMap<String, ArrayList<Message>>) jsonBridge
								.populateMessages(messages, currChannel, lecturer);
					}

					lecturer.close();

					// Non ho trovato i file che ci servono
					if (!loadedSomething) {
						throw new ZipException();
					}
				}
			}
		}
		final File tempFile = new File(zip.getName());
		workspaceLoaded = tempFile.getName().replaceFirst("[.][^.]+$", "");
		// System.out.println(workspaceLoaded);
		zip.close();
	}
}
