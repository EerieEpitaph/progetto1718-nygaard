package it.uniba.controller;

import it.uniba.view.View;
import it.uniba.view.WarningMessage;

import it.uniba.workdata.User;

import it.uniba.wrapping.EdgesWrapper;
import it.uniba.wrapping.UsersWrapper;
import it.uniba.wrapping.ChannelsWrapper;

import it.uniba.model.Model;

import java.io.IOException;
import java.util.Collection;
//import java.util.Map;
import java.util.zip.ZipException;

import it.uniba.model.Edge;

/**
 * This class (following the design pattern MVC (<b>MVP</b>)) is used to manage
 * the calls of the <b>View</b> and the <b>Model</b>. It's the logical part of
 * the project.
 */
public class DataController {
	/*
	 * Used for the representation of the data.
	 */
	private final Model mod;
	/*
	 * Used for the output of the data.
	 */
	private final View view;

	private ChannelsWrapper channelsWrapped;
	private UsersWrapper usersWrapped;

	/**
	 * DataController's constructor. It needs a <i>Model</i> and a <i>View</i>.
	 * 
	 * @param modIn
	 *            <i>Model</i> it contains the representation of the data
	 * @param viewIn
	 *            <i>View</i> it uses to output the data
	 */
	public DataController(final Model modIn, final View viewIn) {
		mod = modIn;
		view = viewIn;
		channelsWrapped = null;
		usersWrapped = null;
	}

	/**
	 * Updates the Model using the path of the zip file of the workspace.
	 * 
	 * @param path
	 *            <i>String</i> of the zip file
	 * @throws ZipException
	 *             if file does not exists
	 * @throws IOException
	 *             for errors at OS level
	 */
	public void updateModel(final String path) throws ZipException, IOException {
		mod.updateModel(path);
		channelsWrapped = new ChannelsWrapper(mod.getChannels());
		usersWrapped = new UsersWrapper(mod.getUsers());

	}

	/**
	 * Prints the members' list using the model for the data and the view for the
	 * output.
	 */
	public void printMembers() {
		view.printMembers(usersWrapped.values());
	}

	/**
	 * Prints the channels' list using the model for the data and the view for the
	 * output.
	 */
	public void printChannels() {
		view.printChannels(channelsWrapped.values());
	}

	/**
	 * Prints all the channels and the respective members using the model for the
	 * data and the view for the output.
	 */
	public void printMembers4Channel() {
		view.printMembers4Channel(usersWrapped, channelsWrapped);
	}

	/**
	 * Prints all the members of a specified channel using the model for the data
	 * and the view for the output.
	 * 
	 * @param nameChannel
	 *            <i>String</i> name of the channel specified
	 */
	public void printChannelMembers(final String nameChannel) {
		view.printChannelMembers(usersWrapped, channelsWrapped, nameChannel);
	}

	/**
	 * Prints all the mention of a specified channel, if <b>inChannel</b> is
	 * <b>null</b> the function prints all the mention of the workspace, with the
	 * boolean <b>weight</b> the function can show the numbers of mention.
	 * 
	 * @param inChannel
	 *            <i>String</i> name of the channel specified
	 * @param weight
	 *            <i>boolean</i> indicate if to show the weight
	 * @throws ExceptionsHandler
	 *             used to handle exceptions
	 */
	public void printAllMention(final String inChannel, final boolean weight) throws ExceptionsHandler {
		EdgesWrapper edgesW;
		if (inChannel == null || "".equals(inChannel)) {
			edgesW = new EdgesWrapper(mod.getEdgesOutDegree(null, inChannel));
			view.printMention(edgesW, weight);
		} else { // validazione canale -m in _inChannel
			if (mod.containsChannel(inChannel)) {
				edgesW = new EdgesWrapper(mod.getEdgesOutDegree(null, inChannel));
				view.printMention(edgesW, weight);
			} else {
				WarningMessage.missingChannel(inChannel);
			}
		}
	}

	/*
	 * Prints all the mention in a channel. It decides if to print the mention
	 * <i>from</i> or <i>to</i> a user (<b>from</b>) and if to print the number of
	 * mentions (<b>weight</b>).
	 *
	 * @param user <i>String</i> name of the user specified
	 * 
	 * @param inChannel <i>String</i> name of the channel specified
	 * 
	 * @param from <i>boolean</i> indicates if the mentions are <i>from</i>
	 * (<b>true</b>) o <i>to</i> (<b>false</b>) a user
	 * 
	 * @param weight <i>boolean</i> indicates if to print the number of mentions
	 * 
	 * @throws ExceptionsHandler used to handle exceptions
	 */
	private void printMentionsFromToUser(final String user, final String inChannel, final boolean from,
			final boolean weight) throws ExceptionsHandler {
		if (user != null && !("".equals(user))) {
			final String idUser = getUserFromId(user);
			if (mod.containsUser(idUser)) {
				if ((inChannel == null || inChannel.equals("")) || mod.containsChannel(inChannel)) {
					Collection<Edge> edgesneeded;
					final User userToPrint = mod.getUser(idUser);
					if (from) {
						edgesneeded = mod.getEdgesOutDegree(userToPrint, inChannel);
					} else {
						edgesneeded = mod.getEdgesInDegree(userToPrint, inChannel);
					}
					final EdgesWrapper edgesW = new EdgesWrapper(edgesneeded);
					view.printMention(edgesW, weight);
				}
			} else {
				WarningMessage.missingUser(user);
			}
			if (!(inChannel == null || ("".equals(inChannel))) && (!mod.containsChannel(inChannel))) {
				WarningMessage.missingChannel(inChannel);
			}
		} else {
			WarningMessage.invalidUser();
		}
	}

	/**
	 * Prints all mention <i>from</i> a specified <b>user</b> in a specified
	 * <b>channel</b>.
	 * 
	 * @param user
	 *            <i>String</i> name of the user specified
	 * @param inChannel
	 *            <i>String</i> name of the channel specified
	 * @throws ExceptionsHandler
	 *             used to handle exceptions
	 */
	public void printMentionsFromUser(final String user, final String inChannel) throws ExceptionsHandler {
		printMentionsFromToUser(user, inChannel, true, false);
	}

	/**
	 * Prints all mention <i>to</i> a specified <b>user</b> in a specified
	 * <b>channel</b>.
	 * 
	 * @param user
	 *            <i>String</i> name of the user specified
	 * @param inChannel
	 *            <i>String</i> name of the channel specified
	 * @throws ExceptionsHandler
	 *             used to handle exceptions
	 */
	public void printMentionsToUser(final String user, final String inChannel) throws ExceptionsHandler {
		printMentionsFromToUser(user, inChannel, false, false);
	}

	/**
	 * Prints all mention <i>from</i> a specified <b>user</b> in a specified
	 * <b>channel</b> with the number of mention (<i>weight</i>).
	 * 
	 * @param user
	 *            <i>String</i> name of the user specified
	 * @param inChannel
	 *            <i>String</i> name of the channel specified
	 * @throws ExceptionsHandler
	 *             used to handle exceptions
	 */
	public void printMentionsFromUserWeigthed(final String user, final String inChannel) throws ExceptionsHandler {
		printMentionsFromToUser(user, inChannel, true, true);
	}

	/**
	 * Prints all mention <i>to</i> a specified <b>user</b> in a specified
	 * <b>channel</b> with the number of mention(<i>weight</i>).
	 * 
	 * @param user
	 *            <i>String</i> name of the user specified
	 * @param inChannel
	 *            <i>String</i> name of the channel specified
	 * @throws ExceptionsHandler
	 *             used to handle exceptions
	 */
	public void printMentionsToUserWeigthed(final String user, final String inChannel) throws ExceptionsHandler {
		printMentionsFromToUser(user, inChannel, false, true);
	}

	/*
	 * Returns the id the user with the name specified or a empty string <i>("")</i>
	 * if it doesn't found it.
	 * 
	 * @param name <i>String</i> name of the user to search
	 * 
	 * @return <i>String</i> the id the user with the name specified
	 */
	private String getUserFromId(final String name) {
		int position = 0;
		while (position < usersWrapped.size()) {
			final String disName = usersWrapped.getDisplayNameNorm(position);
			if ((disName != null) && (name.equals(disName))) {
				return usersWrapped.getId(position);
			}
			final String realName = usersWrapped.getRealName(position);
			if ((realName != null) && (name.equals(realName))) {
				return usersWrapped.getId(position);
			}
			final String nameUser = usersWrapped.getName(position);
			if ((nameUser != null) && (name.equals(nameUser))) {
				return usersWrapped.getId(position);
			}
			position++;
		}
		return "";
	}

	/**
	 * Prints the help's message.
	 */
	public void showHelp() {
		WarningMessage.showHelp();
	}
}
