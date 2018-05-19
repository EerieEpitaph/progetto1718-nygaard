package it.uniba.controller.input;

import java.io.IOException;
import java.util.zip.ZipException;

import it.uniba.controller.DataController;

import it.uniba.controller.input.CommandParser;
import it.uniba.controller.input.CommandParser.*;

// da prendere datacontroller 
public class CommandInterpreter
{

    public void executeCommands(CommandParser parser, DataController dataCtr)
            throws ZipException, IOException
    {
        CommandParserInterface bridge = parser;

        if (parser.help())
            showHelp();

        else if (parser.users())
            dataCtr.printMembers();

        else if (parser.channels())
            dataCtr.printChannels();

        else if (parser.extendedChannels())
            dataCtr.printMembers4Channel();

        else if (parser.userInChannel())
            dataCtr.printChannelMembers(parser.getChannelFilter());

        else if (parser.mentions())
        {
            Boolean weight = false;
            String in = "";
            String name = "";

            if (parser.weighted())
                weight = true;
            if (parser.in())
                in = parser.getInWhat();

            if (parser.from())
            {
                name = parser.getFromWho();
                // Qualcosa tipo printMentionsFrom(name, in, weight); TODO
            } else if (parser.to())
            {
                name = parser.getToWho();
                // Qualcosa tipo printMentionsTo(name, in, weight); TODO
            } else
            {
                // Qualcosa tipo printMentions(name, in, weight); TODO
            }
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
