package it.uniba.controller;

import it.uniba.model.Model;
import it.uniba.view.View;

import java.io.IOException;
import java.util.zip.ZipException;

import it.uniba.controller.input.*;
import it.uniba.parsing.ZipParser;

public class Controller {
	Model mod = new Model();
	View view = new View();
	// mi collego con la parte che manipola i dati 
	DataController dataCtr = new DataController(mod,view); 
	
	
	// CLI 
	CommandParser commandParser;
	CommandInterpreter interpreter;
	
	public Controller() {
		commandParser = null;
		interpreter = new CommandInterpreter();
	}
	
	public void controlExecuteCLI(ZipParser fileParser) throws ZipException, IOException
	{
		// fileParser deve essere preso dal Model / f
		interpreter.executeCommands(commandParser,dataCtr, fileParser); 
	}
	
	public void showHelp()
	{
		interpreter.showHelp();
	}
	
	
}
