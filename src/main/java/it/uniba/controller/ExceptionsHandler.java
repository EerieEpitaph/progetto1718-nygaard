package it.uniba.controller;

@SuppressWarnings("serial") // Da applicare il pattern visto a lezione
public class ExceptionsHandler extends Exception {
	public ExceptionsHandler() {
		// This constructor is intentionally empty. Nothing special is needed here.
	}

	public ExceptionsHandler(String message) {
		super(message);
	}
}
