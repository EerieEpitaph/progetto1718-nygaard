package it.uniba.controller.input;

import java.io.IOException;
import java.util.zip.ZipException;

import it.uniba.controller.DataController;

import it.uniba.controller.input.CommandParser;

// da prendere datacontroller 
public class CommandInterpreter {

	public void executeCommands(CommandParser parser, DataController dataCtr) throws ZipException, IOException {
		CommandParserInterface bridge = parser;

		if (bridge.validWorkspace()) {
			dataCtr.updateModel(bridge.getWorkspace());

			if (bridge.help())
				dataCtr.showHelp();

			else if (bridge.users())
				dataCtr.printMembers();

			else if (bridge.channels())
				dataCtr.printChannels();

			else if (bridge.extendedChannels())
				dataCtr.printMembers4Channel();

			else if (bridge.userInChannel())
				dataCtr.printChannelMembers(bridge.getChannelFilter());

			else if (bridge.mentions()) {
				Boolean weight = bridge.weighted();
				String inChannel = "";
				String user = "";

				if (bridge.in())
					inChannel = bridge.getInWhat();

				if (bridge.from() && bridge.to())
					throw new IllegalStateException();

				if (bridge.from()) {
					user = bridge.getFromWho();
					if (weight)
						dataCtr.printMentionsFromUserWeigthed(user, inChannel);

					else
						dataCtr.printMentionsFromUser(user, inChannel);
				}

				else {
					if (bridge.to()) {
						user = bridge.getToWho();
						if (weight)
							dataCtr.printMentionsToUserWeigthed(user, inChannel);
						else
							dataCtr.printMentionsToUser(user, inChannel);
					}

					else
						dataCtr.printAllMention(inChannel, weight);
				}

			} else
				throw new IllegalStateException();
		} else
			throw new IllegalStateException();
	}
}
