package it.uniba.controller;

import it.uniba.model.Model;
import it.uniba.view.View;
import it.uniba.view.WarningMessage;

import java.io.IOException;
import java.util.zip.ZipException;

import it.uniba.controller.input.CommandParser;
import it.uniba.controller.input.CommandInterpreter;
//import it.uniba.controller.input.CommandParserInterface;

/**
 * This class (following the design pattern MVC (MVP)) is used to manage the
 * command's parsing's side, the model and the view.
 */
public class Controller {
	/*
	 * Model used for the representation of the data.
	 */
	private final Model mod = new Model();
	/*
	 * View used for the output of the data.
	 */
	private final View view = new View();
	/*
	 * DataController used for manage the logic's side of the MVC(MVP).
	 */
	private final DataController dataCtr = new DataController(mod, view);

	// CLI
	/*
	 * Used for the commands' parsing.
	 */
	private CommandParser commandParser;
	/*
	 * Used for the commands' interpreting and executing.
	 */
	private CommandInterpreter interpreter;

	/**
	 * Controller's constructor.
	 */
	public Controller() {
		commandParser = null;
		interpreter = null;
	}

	/**
	 * This function manages the calls of the CommandParser CommandInterpreter for
	 * parsing a zip file, and executing the commands specified.
	 * 
	 * @param args
	 *            <i>String[]</i> a collection of arguments
	 * @throws ZipException
	 *             if file does not exists
	 * @throws IOException
	 *             for errors at OS level
	 */
	public void controlExecuteCLI(final String[] args) throws ZipException, IOException {
		commandParser = new CommandParser(args);
		interpreter = new CommandInterpreter();
		interpreter.executeCommands(commandParser, dataCtr);
	}

	/**
	 * Prints the help's message, calling the view's method.
	 */
	public void showHelp() {
		WarningMessage.showHelp();
	}

}
