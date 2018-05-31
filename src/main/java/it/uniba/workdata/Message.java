package it.uniba.workdata;

/**
 * This class wraps GsonMessage to better present it.
 */
public class Message {

	/**
	 * Gson message to wrap.
	 */
	private final GsonMessage toWrap;

	/**
	 * This class models a message (.json files inside folders).
	 */
	public static final class GsonMessage {
		// inutilizzato
		// /**
		// * Type of message.
		// */
		// private final String type;

		/**
		 * User id that sent it.
		 */
		private final String user;

		/**
		 * Message text.
		 */
		private final String text;

		/**
		 * GsonMessage constructor.
		 * 
		 * 
		 * @param user2
		 *            user id
		 * @param text2
		 *            message text
		 */
		public GsonMessage(final String user2, final String text2) {
			// * @param type2 message type
			// (final String type2// inutilizzato
			// type = type2;
			user = user2;
			text = text2;
		}
		// inutilizzato
		// /**
		// * Type getter.
		// *
		// * @return message type as String
		// */
		// public String getType() {
		// return type;
		// }

		/**
		 * User getter.
		 * 
		 * @return user id as String
		 */
		public String getUser() {
			return user;
		}

		/**
		 * Text getter.
		 * 
		 * @return message text as String
		 */
		public String getText() {
			return text;
		}

		/**
		 * Return a boolean true if the text contains the textContained else false
		 * 
		 * @param textContained
		 *            <i>String</i> text to check
		 * @return <i>boolean</i> true if the text contains the textContained else false
		 */
		public boolean contains(final String textContained) {
			return text.contains(textContained);
		}

		/**
		 * Wrapper.
		 * 
		 * @return this as Message
		 */
		public Message toMessage() {
			return new Message(this);
		}
	}

	/**
	 * Message constructor.
	 * 
	 * @param message2
	 *            message to wrap
	 */
	public Message(final GsonMessage message2) {
		// channel = _channel;
		toWrap = message2;
	}
	// inutilizzato
	// /**
	// * Wrapper type returner.
	// *
	// * @return GsonMessage type
	// */
	// public String getType() {
	// return toWrap.getType();
	// }

	/**
	 * Wrapper user returner.
	 * 
	 * @return GsonMessage user
	 */
	public String getUser() {
		return toWrap.getUser();
	}

	/**
	 * Wrapper text returner.
	 * 
	 * @return GsonMessage text
	 */
	public String getText() {
		return toWrap.getText();
	}

	/**
	 * Return a boolean true if the text contains the textContained else false
	 * 
	 * @param textContained
	 *            <i>String</i> text to check
	 * @return <i>boolean</i> true if the text contains the textContained else false
	 */
	public boolean contains(final String textContained) {
		return toWrap.contains(textContained);
	}
}
