package it.uniba.controller;

@SuppressWarnings("serial") // Da applicare il pattern visto a lezione
public class ExceptionsHandler extends Exception {
	public ExceptionsHandler() {
	}

	public ExceptionsHandler(String message) {
		super(message);
	}
}
