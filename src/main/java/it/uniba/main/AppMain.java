package it.uniba.main;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.zip.ZipException;

import com.google.gson.JsonParseException;

import it.uniba.controller.Controller;
import picocli.CommandLine.UnmatchedArgumentException;
import picocli.CommandLine.MissingParameterException;

/**
 * The AppMain of the project SNA4Slack implements a SNA (Social Network
 * Analysis) based on the results (message, mention, etc) of the platform Slack.
 * 
 * @author Curci Nicola
 * @author Dimeo Giovanni
 * @author Lagatta Valentina
 * @author Lischio Ottavio
 */
public final class AppMain {

	private AppMain() {
	}

	/**
	 * Main of the SNA4Slack. The behavior of the program is based on the
	 * <b>args</b> in input.
	 * 
	 * 
	 * @param args
	 *            <i>String[]</i> list of arguments
	 */
	public static void main(final String[] args) {
		Controller control = new Controller();

		try {
			if (args.length == 0) {
				control.showHelp();
			} else {
				control.controlExecuteCLI(args);
			}
		} catch (NullPointerException e) {
			System.out.println("NullPointer encountered");
		} catch (ZipException e) {
			System.out.println("Unable to analyze. Damaged or wrong file");
		} catch (InvalidPathException e) {
			System.out.println("Illegal char used in path");
		} catch (IOException e) {
			System.out.println("Invalid file. Usage: -w \"path\\to\\file.zip\"");
		} catch (JsonParseException e) {
			System.out.println(e.toString());
		} catch (UnmatchedArgumentException e) {
			System.out.println("Invalid syntax. Refer to 'help' command");
		} catch (MissingParameterException e) {
			System.out.println("Invalid syntax: there are missing parameters. Refer to 'help' command");
		} catch (IllegalStateException e) {
			System.out.println("Invalid syntax. Refer to 'help' command");
		} catch (Exception e) {
			System.out.println("Unexpected exception encountered");
		}
	}
}
