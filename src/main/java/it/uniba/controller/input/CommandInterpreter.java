package it.uniba.controller.input;

import java.io.IOException;
import java.util.zip.ZipException;

import it.uniba.controller.DataController;

import it.uniba.controller.input.CommandParser;

// da prendere datacontroller 
public class CommandInterpreter
{

    public void executeCommands(CommandParser parser, DataController dataCtr)
            throws ZipException, IOException
    {
        CommandParserInterface bridge = parser;

        if (bridge.validWorkspace())
        {
            dataCtr.updateModel(bridge.getWorkspace());

            if (bridge.help())
                showHelp();

            else if (bridge.users())
                dataCtr.printMembers();

            else if (bridge.channels())
                dataCtr.printChannels();

            else if (bridge.extendedChannels())
                dataCtr.printMembers4Channel();

            else if (bridge.userInChannel())
                dataCtr.printChannelMembers(bridge.getChannelFilter());

            else if (bridge.mentions())
            {
                Boolean from = false;
                Boolean weight = false;
                String in = "";
                String name = "";

                if (bridge.weighted())
                    weight = true;
                if (bridge.in())
                    in = bridge.getInWhat();
                if (bridge.from())
                {
                    name = bridge.getFromWho();
                    from = true;
                } else if (bridge.to())
                {
                    name = bridge.getToWho();
                    from = false;
                }

                dataCtr.printMentions(in, name, from, weight);

            } else
                throw new IllegalStateException();
        } else
            throw new IllegalStateException();
    }

    public void showHelp()
    {
        System.out.println("Usage:");
        System.out.println("help - shows this help\n");
        System.out.println(
                "-w \"path\\to\\file.zip\" ( -c | -u | -uc <channelFilter> | -cu | -m [from <x>] | [to <y>] [in <z>])");
        System.out.println("\t-w parses a workspace");
        System.out.println(
                "\t-c prints all the channels in the specified workspace");
        System.out.println(
                "\t-u prints all the users in the specified workspace");
        System.out.println(
                "\t-uc <channelFilter> prints all the users in the specified channel ");
        System.out.println("\t-cu prints all the channels with their users\n");
        System.out.println("\t-m prints all the mentions in a workspace");
        System.out.println("\t\t-m from <user> filters the mentioner");
        System.out.println("\t\t-m to <user> filters the mentioned");
        System.out.println("\t\t-m in filters the channel");
        System.out.println(
                "\t\t-m from <user> OR to <user> in <channel> filters the mentioner or mentioned in the channel");
    }
}
