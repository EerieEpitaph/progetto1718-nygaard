package it.uniba.controller.input;

import java.io.IOException;
import java.util.zip.ZipException;

import it.uniba.controller.DataController;
import it.uniba.controller.ExceptionsHandler;

/**
 * This class interprets commands based on current interface rules.
 */
public class CommandInterpreter {
	/**
	 * This method, given a CommandParser and a DataController object, interprets
	 * CLI arguments and gives them a meaning.
	 * 
	 * @param parser
	 *            CommandParser object that validated our commands; has quite a few
	 *            useful methods
	 * @param dataCtr
	 *            DataController object that takes care of printing and managing
	 *            data structures
	 * @throws ZipException
	 *             on workspace loading error
	 * @throws IOException
	 *             on OS level errors with files
	 * @throws ExceptionsHandler
	 *             used to handle exceptions
	 */
	public void executeCommands(final CommandParser parser, final DataController dataCtr)
			throws ZipException, IOException, ExceptionsHandler {
		final CommandParserInterface bridge = parser;

		if (bridge.help()) {
			dataCtr.showHelp();
		} else if (bridge.validWorkspace()) {
			dataCtr.updateModel(bridge.getWorkspace());

			if (bridge.users()) {
				dataCtr.printMembers();
			} else if (bridge.channels()) {
				dataCtr.printChannels();
			} else if (bridge.extendedChannels()) {
				dataCtr.printMembers4Channel();
			} else if (bridge.usersInChannel()) {
				dataCtr.printChannelMembers(bridge.getChannelFilter());
			} else if (bridge.mentions()) {

				String inChannel = "";
				if (bridge.in()) {
					inChannel = bridge.getInWhat();
				}

				if (bridge.from() && bridge.to()) {
					throw new IllegalStateException();
				}

				final Boolean weight = bridge.weighted();
				String user = "";
				if (bridge.from()) {
					user = bridge.getFromWho();
					if (weight) {
						dataCtr.printMentionsFromUserWeigthed(user, inChannel);
					} else {
						dataCtr.printMentionsFromUser(user, inChannel);
					}
				} else {
					if (bridge.to()) {
						user = bridge.getToWho();
						if (weight) {
							dataCtr.printMentionsToUserWeigthed(user, inChannel);
						} else {
							dataCtr.printMentionsToUser(user, inChannel);
						}
					} else {
						dataCtr.printAllMention(inChannel, weight);
					}
				}

			} else {
				throw new IllegalStateException();
			}
		} else {
			throw new IllegalStateException();
		}
	}
}
