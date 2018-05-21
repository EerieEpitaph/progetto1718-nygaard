package it.uniba.controller;

import it.uniba.view.View;

import it.uniba.workdata.User;

import it.uniba.model.Model;

import java.io.IOException;
import java.util.Collection;
import java.util.zip.ZipException;

import it.uniba.model.Edge;

/**
*
*/
public class DataController {
	/**
	*
	*/
	private Model mod;
	/**
	 *
	 */
	private View view;

	/**
	 *
	 */
	public DataController(final Model modIn, final View viewIn) {
		mod = modIn;
		view = viewIn;
	}

	/**
	 *
	 * @param path
	 *            a collection of arguments
	 * @throws ZipException
	 *             if file does not exists
	 * @throws IOException
	 *             for errors at OS level
	 */
	public void updateModel(String path) throws ZipException, IOException {
		mod.updateModel(path);
	}

	/**
	 * Return a boolean that indicates if the model has loaded.
	 * 
	 * @return boolean that indicates if the model has loaded.
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
	 */
	public void printChannelMembers(final String nameChannel) {
		view.printChannelMembers(mod.getUsers(), mod.getChannels(), nameChannel);
	}

	/**
	 *
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
	 *
	 * @param user
	 * @param inChannel
	 * @param from
	 * @param weight
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
		}
	}

	/**
	 *
	 */
	public void printMentionsFromUser(final String user, final String inChannel) {
		printMentionsFromToUser(user, inChannel, true, false);
	}

	/**
	 *
	 */
	public void printMentionsFromUser(final String user) {
		printMentionsFromToUser(user, "", true, false);
	}

	/**
	 *
	 */
	public void printMentionsToUser(final String user, final String inChannel) {
		printMentionsFromToUser(user, inChannel, false, false);
	}

	/**
	 *
	 */
	public void printMentionsToUser(final String user) {
		printMentionsFromToUser(user, "", false, false);
	}

	/**
	 *
	 */
	public void printMentionsFromUserWeigthed(final String user, final String inChannel) {
		printMentionsFromToUser(user, inChannel, true, true);
	}

	/**
	 *
	 */
	public void printMentionsFromUserWeigthed(final String user) {
		printMentionsFromToUser(user, "", true, true);
	}

	/**
	 *
	 */
	public void printMentionsToUserWeigthed(final String user, final String inChannel) {
		printMentionsFromToUser(user, inChannel, false, true);
	}

	/**
	 *
	 */
	public void printMentionsToUserWeigthed(final String user) {
		printMentionsFromToUser(user, "", false, true);
	}

	/**
	 * Returns the id the user with the name specified of a string empty ("") if it
	 * doesn't found it.
	 * 
	 * @param name
	 *            name of the user to search
	 * @return the id the user with the name specified
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
