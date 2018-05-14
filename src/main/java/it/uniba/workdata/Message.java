package it.uniba.workdata;

public class Message {
	public class GsonMessage {
		private String type;
		private String user;
		private String text;

		public GsonMessage(final String _type, final String _user, final String _text) {
			type = _type;
			user = _user;
			text = _text;
		}

		public String getType() {
			return type;
		}

		public String getUser() {
			return user;
		}

		public String getText() {
			return text;
		}
	}

	// private String channel;
	private GsonMessage message;

	public Message(GsonMessage _message) {
		// channel = _channel;
		message = _message;
	}

	// public String getChannel()
	// {
	// return channel;
	// }

	public String getType() {
		return message.getType();
	}

	public String getUser() {
		return message.getUser();
	}

	public String getText() {
		return message.getText();
	}
}
