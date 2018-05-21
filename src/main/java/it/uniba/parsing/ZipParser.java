package it.uniba.parsing;

//import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import it.uniba.workdata.Message;
//import it.uniba.model.MentionGraph;
import it.uniba.workdata.Channel;
import it.uniba.workdata.User;

public class ZipParser {
	private String workspaceLoaded = "";
	// I tre dizionari users, channels e i messaggi
	private HashMap<String, User> users = new HashMap<String, User>();
	private HashMap<String, Channel> channels = new HashMap<String, Channel>();
	private HashMap<String, ArrayList<Message>> messages = new HashMap<String, ArrayList<Message>>();

	public void setWorkspaceName(String _value) {
		workspaceLoaded = _value;
	}

	public String getWorkspaceName() {
		return workspaceLoaded;
	}

	public Boolean hasLoaded() {
		return (workspaceLoaded != "");
	}

	public HashMap<String, User> getUsers() {
		return users;
	}

	public HashMap<String, Channel> getChannels() {
		return channels;
	}

	public HashMap<String, ArrayList<Message>> getMessages() {
		return messages;
	}

	public void load(String path) throws ZipException, IOException {
		Boolean loadedSomething = false;

		String currChannel = "";
		ZipFile zip = new ZipFile(path);
		Enumeration<? extends ZipEntry> entries = zip.entries();

		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			// System.out.println(entry.getName() + " ");

			if (!entry.getName().equals("integration_logs.json")) {
				if (entry.isDirectory())
					currChannel = entry.getName().substring(0, entry.getName().length() - 1);
				else {
					loadedSomething = true;
					JsonParserInterface jsonBridge = new GsonReader();
					Reader lecturer = new InputStreamReader(zip.getInputStream(entry));

					if (entry.getName().equals("users.json"))
						users = (HashMap<String, User>) jsonBridge.populateUsers(lecturer);

					else if (entry.getName().equals("channels.json"))
						channels = (HashMap<String, Channel>) jsonBridge.populateChannels(lecturer);

					else
						messages = (HashMap<String, ArrayList<Message>>) jsonBridge.populateMessages(messages,
								currChannel, lecturer);

					lecturer.close();

					// Non ho trovato i file che ci servono
					if (!loadedSomething)
						throw new ZipException();
				}
			}
		}
		File tempFile = new File(zip.getName());
		workspaceLoaded = tempFile.getName().replaceFirst("[.][^.]+$", "");
		// System.out.println(workspaceLoaded);
		zip.close();
	}
}