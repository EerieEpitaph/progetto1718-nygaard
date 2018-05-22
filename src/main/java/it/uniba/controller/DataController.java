package it.uniba.controller;

import it.uniba.view.View;

import it.uniba.workdata.User;

import it.uniba.model.Model;

import java.io.IOException;
import java.util.Collection;
import java.util.zip.ZipException;

import it.uniba.model.Edge;

/**
 * This class (following the design pattern MVC (<b>MVP</b>)) is used to manage
 * the calls of the <b>View</b> and the <b>Model</b>. It's the logical part of
 * the project.
 */
public class DataController {
	/**
	 * Used for the representation of the data.
	 */
	private Model mod;
	/**
	 * Used for the output of the data.
	 */
	private View view;

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
	}

	/**
	 * Returns a boolean that indicates if the model has loaded.
	 * 
	 * @return <i>boolean</i> that indicates if the model has loaded.
	 */
	public boolean hasLoaded() {
		return mod.hasLoaded();
	}

	/**
	 * Prints the members' list using the model for the data and the view for the
	 * output.
	 */
	public void printMembers() {
		view.printMembers(mod.getUsers().values());
	}

	/**
	 * Prints the channels' list using the model for the data and the view for the
	 * output.
	 */
	public void printChannels() {
		view.printChannels(mod.getChannels().values());
	}

	/**
	 * Prints all the channels and the respective members using the model for the
	 * data and the view for the output.
	 */
	public void printMembers4Channel() {
		view.printMembers4Channel(mod.getUsers(), mod.getChannels().values());
	}

	/**
	 * Prints all the members of a specified channel using the model for the data
	 * and the view for the output.
	 * 
	 * @param nameChannel
	 *            <i>String</i> name of the channel specified
	 */
	public void printChannelMembers(final String nameChannel) {
		view.printChannelMembers(mod.getUsers(), mod.getChannels(), nameChannel);
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
	 */
	public void printAllMention(final String inChannel, final boolean weight) {
		if (inChannel == null || inChannel.equals("")) {
			mod.getMentionGraph().parseMessages(mod.getMessages(), mod.getUsers(), "");
			view.printMention(mod.getMentionGraph().edgesOutDegree(null), weight);
		} else { // validazione canale -m in _inChannel
			if (mod.getChannels().containsKey(inChannel)) {
				mod.getMentionGraph().parseMessages(mod.getMessages(), mod.getUsers(), inChannel);
				view.printMention(mod.getMentionGraph().edgesOutDegree(null), weight);
			} else {
				View.missingChannel(inChannel);
			}
		}
	}

	/**
	 * Prints all the mention in a channel. It decides if to print the
	 * mention <i>from</i> or <i>to</i> a user (<b>from</b>) and if to print the
	 * number of mentions (<b>weight</b>).
	 *
	 * @param user
	 *            <i>String</i> name of the user specified
	 * @param inChannel
	 *            <i>String</i> name of the channel specified
	 * @param from
	 *            <i>boolean</i> indicates if the mentions are <i>from</i>
	 *            (<b>true</b>) o <i>to</i> (<b>false</b>) a user
	 * @param weight
	 *            <i>boolean</i> indicates if to print the number of mentions
	 */
	private void printMentionsFromToUser(final String user, final String inChannel, final boolean from,
			final boolean weight) {
		if (user != null | user.equals("")) {
			String idUser = getUserFromId(user);
			if (mod.getUsers().containsKey(idUser)) {
				if ((inChannel == null || inChannel.equals("")) || mod.getChannels().containsKey(inChannel)) {
					mod.getMentionGraph().parseMessages(mod.getMessages(), mod.getUsers(), inChannel);
					Collection<Edge> edgesneeded;
					if (from) {
						edgesneeded = mod.getMentionGraph().edgesOutDegree(mod.getUsers().get(idUser));
					} else {
						edgesneeded = mod.getMentionGraph().edgesInDegree(mod.getUsers().get(idUser));
					}
					view.printMention(edgesneeded, weight);
				}
			} else {
				View.missingUser(user);
			}
			if (!(inChannel == null || (inChannel.equals(""))) && (!mod.getChannels().containsKey(inChannel))) {
				View.missingChannel(inChannel);
			}
		} else {
			View.invalidUser();
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
	 */
	public void printMentionsFromUser(final String user, final String inChannel) {
		printMentionsFromToUser(user, inChannel, true, false);
	}

	/**
	 * Prints all mention <i>from</i> a specified <b>user</b>.
	 * 
	 * @param user
	 *            <i>String</i> name of the user specified
	 */
	public void printMentionsFromUser(final String user) {
		printMentionsFromToUser(user, "", true, false);
	}

	/**
	 * Prints all mention <i>to</i> a specified <b>user</b> in a specified
	 * <b>channel</b>.
	 * 
	 * @param user
	 *            <i>String</i> name of the user specified
	 * @param inChannel
	 *            <i>String</i> name of the channel specified
	 */
	public void printMentionsToUser(final String user, final String inChannel) {
		printMentionsFromToUser(user, inChannel, false, false);
	}

	/**
	 * Prints all mention <i>to</i> a specified <b>user</b>.
	 * 
	 * @param user
	 *            <i>String</i> name of the user specified
	 */
	public void printMentionsToUser(final String user) {
		printMentionsFromToUser(user, "", false, false);
	}

	/**
	 * Prints all mention <i>from</i> a specified <b>user</b> in a specified
	 * <b>channel</b> with the number of mention (<i>weight</i>).
	 * 
	 * @param user
	 *            <i>String</i> name of the user specified
	 * @param inChannel
	 *            <i>String</i> name of the channel specified
	 */
	public void printMentionsFromUserWeigthed(final String user, final String inChannel) {
		printMentionsFromToUser(user, inChannel, true, true);
	}

	/**
	 * Prints all mention <i>from</i> a specified <b>user</b> with the number of
	 * mention (<i>weight</i>).
	 * 
	 * @param user
	 *            <i>String</i> name of the user specified
	 */
	public void printMentionsFromUserWeigthed(final String user) {
		printMentionsFromToUser(user, "", true, true);
	}

	/**
	 * Prints all mention <i>to</i> a specified <b>user</b> in a specified
	 * <b>channel</b> with the number of mention(<i>weight</i>).
	 * 
	 * @param user
	 *            <i>String</i> name of the user specified
	 * @param inChannel
	 *            <i>String</i> name of the channel specified
	 */
	public void printMentionsToUserWeigthed(final String user, final String inChannel) {
		printMentionsFromToUser(user, inChannel, false, true);
	}

	/**
	 * Prints the mentions <i>to</i> a specified <b>user</b> with the number of
	 * mentions(<i>weight</i>).
	 * 
	 * @param user
	 *            <i>String</i> name of the user
	 */
	public void printMentionsToUserWeigthed(final String user) {
		printMentionsFromToUser(user, "", false, true);
	}

	/**
	 * Returns the id the user with the name specified or a empty string <i>("")</i>
	 * if it doesn't found it.
	 * 
	 * @param name
	 *            <i>String</i> name of the user to search
	 * @return <i>String</i> the id the user with the name specified
	 */
	private String getUserFromId(final String name) {
		for (User x : mod.getUsers().values()) {
			String disName = x.getDisplayNameNorm();
			String rn = x.getRealName();
			String nameUser = x.getName();

			if (disName != null) {
				if (disName.equals(name)) {
					return x.getId();
				}
			}
			if (rn != null) {
				if (rn.equals(name)) {
					return x.getId();
				}
			}
			if (nameUser != null) {
				if (nameUser.equals(name)) {
					return x.getId();
				}
			}
		}
		return "";
	}

	/**
	 * Prints the help's message using the view's method.
	 */
	public void showHelp() {
		View.showHelp();
	}
}
