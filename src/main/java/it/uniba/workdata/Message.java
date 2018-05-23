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

		/**
		 * Type of message.
		 */
		private final String type;

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
		 * @param type2 message type
		 * @param user2 user id
		 * @param text2 message text
		 */
		public GsonMessage(final String type2, final String user2, final String text2) {
			type = type2;
			user = user2;
			text = text2;
		}

		/**
		 * Type getter.
		 * @return message type as String
		 */
		public String getType() {
			return type;
		}

		/**
		 * User getter.
		 * @return user id as String
		 */
		public String getUser() {
			return user;
		}

		/**
		 * Text getter.
		 * @return message text as String
		 */
		public String getText() {
			return text;
		}

		/**
		 * Wrapper.
		 * @return this as Message
		 */
		public Message toMessage() {
			return new Message(this);
		}
	}

	/**
	 * Message constructor.
	 * @param message2 message to wrap
	 */
	public Message(final GsonMessage message2) {
		// channel = _channel;
		toWrap = message2;
	}

	/**
	 * Wrapper type returner.
	 * @return GsonMessage type
	 */
	public String getType() {
		return toWrap.getType();
	}

	/**
	 * Wrapper user returner.
	 * @return GsonMessage user
	 */
	public String getUser() {
		return toWrap.getUser();
	}

	/**
	 * Wrapper text returner.
	 * @return GsonMessage text
	 */
	public String getText() {
		return toWrap.getText();
	}
}
