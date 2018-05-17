package it.uniba.controller;

import it.uniba.model.Model;
import it.uniba.view.View;

import java.io.IOException;
import java.util.zip.ZipException;

import it.uniba.controller.input.*;


public class Controller {
	Model mod = new Model();
	View view = new View();
	
	
	// mi collego con la parte che manipola i dati
	DataController dataCtr = new DataController(mod, view);

	// CLI
	CommandParser commandParser;
	CommandInterpreter interpreter;

	public Controller() { 
		commandParser = null;
	}

	public void controlExecuteCLI(String[] args) throws ZipException, IOException {
		// fileParser deve essere preso dal Model / f
		commandParser = new CommandParser(args);
		interpreter = new CommandInterpreter();
		interpreter.executeCommands(commandParser, dataCtr);
	}
	
	
	
	
	public void showHelp() {
		interpreter.showHelp();
	}

}
