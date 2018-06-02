package it.uniba.controller;

/**
 * Non c'e' bisogno del serial!
 */
@SuppressWarnings("serial") // Da applicare il pattern visto a lezione
/**
 * 
 * Custom Exceptions for sna4Slack
 *
 */
public class ExceptionsHandler extends Exception {
	/**
	 * Constructor of ExceptionsHandler
	 */
	public ExceptionsHandler() {
		// This constructor is intentionally empty. Nothing special is needed here.
	}

	/**
	 * 
	 * @param message
	 *            <i>String</i> Exception's message
	 */
	public ExceptionsHandler(final String message) {
		super(message);
	}
}
