package it.uniba.controller.input;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 *This class manages the parsing of command line arguments.
 */
public final class CommandParser implements CommandParserInterface {
	/**
	 *This class manages no-parameter commands.
	 */
	public final class CommBaseArgs {
		/*
		 *Activity notifier.
		 */
		private Boolean active = false;

		/*
		 *Help found.
		 */
		@Option(names = "help")
		private boolean helpStatus;

		/**
		 *Gives activity status.
		 *@return active status
		 */
		public Boolean isActive() {
			return active;
		}

		/**
		 *Gives help status.
		 *@return helpstatus
		 */
		public Boolean getHelpStatus() {
			return helpStatus;
		}
	}

	/**
	 *This class manages workspace-related commands.
	 */
	public final class CommWorkspace {
		/*
		 *Activity notifier.
		 */
		private Boolean active = false;

		/*
		 *Name of the current workspace.
		 */
		@Parameters(index = "0", arity = "1")
		private String workspaceName;

		/*
		 *Status of member command.
		 */
		@Option(names = "-u", arity = "0..1")
		private boolean membersStatus;

		/*
		 *Status of channels command.
		 */
		@Option(names = "-c", arity = "0..1")
		private boolean channelsStatus;

		/*
		 *Status of extended channels command.
		 */
		@Option(names = "-cu", arity = "0..1")
		private boolean extChannelsStatus;

		/*
		 *Name of channel filter.
		 */
		@Option(names = "-uc", arity = "1")
		private String channelFilter;

		/*
		 *Parameters passed at mention command.
		 */
		@Option(names = "-m", arity = "0..5")
		private String[] mentionParams;

		/**
		 *Activity getter.
		 *@return active status
		 */
		public Boolean isActive() {
			return active;
		}

		/**
		 *Worskpace getter.
		 *@return name of workspace
		 */
		public String getWorkspaceName() {
			return workspaceName;
		}

		/**
		 *Workspace validity getter.
		 *@return workspace validity
		 */
		public Boolean isValidWorkspace() {
			return (workspaceName != null && !"".equals(workspaceName));
		}

		/**
		 *Member status getter.
		 *@return memberStatus value
		 */
		public Boolean getMembersStatus() {
			return membersStatus;
		}

		/**
		 *Channel status getter.
		 *@return channelStatus value
		 */
		public Boolean getChannelsStatus() {
			return channelsStatus;
		}

		/**
		 *Extended channel status getter.
		 *@return extChannelStatus value
		 */
		public Boolean getExtChannelsStatus() {
			return extChannelsStatus;
		}

		/**
		 *Channel filter getter.
		 *@return channelFilter value
		 */
		public String getChannelFilter() {
			return channelFilter;
		}

		/**
		 *Channel filter validity getter.
		 *@return channelFilter validity
		 */
		public Boolean isValidFilter() {
			return (channelFilter != null && !"".equals(channelFilter));
		}

		/**
		 *Mention params getter.
		 *@return array of parameters passed after mention command
		 */
		public String[] getMentionParams() {
			return Arrays.copyOf(mentionParams, mentionParams.length);
		}
	}

	/*
	 *Base commands representer.
	 */
	private CommBaseArgs baseArgs;
	/*
	 *Workspace command representer.
	 */
	private CommWorkspace workspace;

	/**
	 *CommandParser constructor.
	 *@param args array of command line arguments
	 *@throws IllegalStateException if conflicting commands parsed
	 */
	public CommandParser(final String[] args) throws IllegalStateException {
		baseArgs = new CommBaseArgs();
		workspace = new CommWorkspace();

		CommandLine commandLine = new CommandLine(baseArgs).addSubcommand("-w", workspace);

		List<CommandLine> result = commandLine.parse(args);

		for (CommandLine x : result) {
			// Gli "argomenti base" sarebbero sempre true, per com'e'
			// strutturata la
			// libreria.
			// In questo if setto la loro attivita' = true solo se, usando la
			// riflessione,
			// uno dei loro field e' true
			// Se piu' di un field e' true, throwo direttamente un'eccezione.
			if (x.getCommand().getClass() == CommBaseArgs.class) {
				baseArgs = (CommBaseArgs) x.getCommand();

				ArrayList<Field> baseFields = new ArrayList<Field>(
						Arrays.asList(CommBaseArgs.class.getDeclaredFields()));
				baseFields.remove(0);
				baseFields.remove(baseFields.size() - 1);

				for (Field y : baseFields) {
					// System.out.println(y.getName());
					y.setAccessible(true);
					try {
						if (y.get(baseArgs).toString().equals("true")) {
							if (baseArgs.active) {
								throw new IllegalStateException(); // CATCHED
							} else {
								baseArgs.active = true;
							}
						}

						y.setAccessible(false);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			} else if (x.getCommand().getClass() == CommWorkspace.class) {
				workspace = (CommWorkspace) x.getCommand();
				workspace.active = true;
			}
		}
	}

	// ==========================================================
	// ================INTERFACE IMPLEMENTATION==================
	// ==========================================================

	/**
	 *Interface overriding.
	 */
	@Override
	public Boolean validWorkspace() {
		if (workspace.isActive() && workspace.isValidWorkspace()) {
			return true;
		}
		return false;
	}

	/**
	 *Interface overriding.
	 */
	@Override
	public String getWorkspace() {
		return workspace.getWorkspaceName();
	}

	/**
	 *Interface overriding.
	 */
	@Override
	public Boolean help() {
		if (baseArgs.getHelpStatus()) {
			return true;
		}
		return false;
	}

	/**
	 *Interface overriding.
	 */
	@Override
	public Boolean users() {
		if (workspace.getMembersStatus()) {
			return true;
		}
		return false;
	}

	/**
	 *Interface overriding.
	 */
	@Override
	public Boolean channels() {
		if (workspace.getChannelsStatus()) {
			return true;
		}
		return false;
	}

	/**
	 *Interface overriding.
	 */
	@Override
	public Boolean extendedChannels() {
		if (workspace.getExtChannelsStatus()) {
			return true;
		}
		return false;
	}

	/**
	 *Interface overriding.
	 */
	@Override
	public Boolean usersInChannel() {
		if (workspace.isValidFilter()) {
			return true;
		}
		return false;
	}

	/**
	 *Interface overriding.
	 */
	@Override
	public String getChannelFilter() {
		return workspace.getChannelFilter();
	}

	/**
	 *Interface overriding.
	 */
	@Override
	public Boolean mentions() {
		if (workspace.mentionParams != null) {
			return true;
		}
		return false;
	}

	/**
	 *Interface overriding.
	 */
	@Override
	public Boolean from() {
		String[] params = workspace.mentionParams;
		for (int i = 0; i < params.length; i++) {
			try {
				if (params[i].equals("from")) {
					if (params[i + 1] != null) {
						return true;
					}
				}
			} catch (Exception e) {
				throw new IllegalStateException();
			}
		}
		return false;
	}

	/**
	 *Interface overriding.
	 */
	@Override
	public String getFromWho() {
		String[] params = workspace.mentionParams;
		for (int i = 0; i < params.length; i++) {
			try {
				if (params[i].equals("from")) {
					if (params[i + 1] != null) {
						return params[i + 1];
					}
				}
			} catch (Exception e) {
				throw new IllegalStateException();
			}
		}
		return "";
	}

	/**
	 *Interface overriding.
	 */
	@Override
	public Boolean to() {
		String[] params = workspace.mentionParams;
		for (int i = 0; i < params.length; i++) {
			try {
				if (params[i].equals("to")) {
					if (params[i + 1] != null) {
						return true;
					}
				}
			} catch (Exception e) {
				throw new IllegalStateException();
			}
		}
		return false;
	}

	/**
	 *Interface overriding.
	 */
	@Override
	public String getToWho() {
		String[] params = workspace.mentionParams;
		for (int i = 0; i < params.length; i++) {
			try {
				if (params[i].equals("to")) {
					if (params[i + 1] != null) {
						return params[i + 1];
					}
				}
			} catch (Exception e) {
				throw new IllegalStateException();
			}
		}
		return "";
	}

	/**
	 *Interface overriding.
	 */
	@Override
	public Boolean in() {
		String[] params = workspace.mentionParams;
		for (int i = 0; i < params.length; i++) {
			try {
				if (params[i].equals("in")) {
					if (params[i + 1] != null) {
						return true;
					}
				}
			} catch (Exception e) {
				throw new IllegalStateException();
			}
		}
		return false;
	}

	/**
	 *Interface overriding.
	 */
	@Override
	public String getInWhat() {
		String[] params = workspace.mentionParams;
		for (int i = 0; i < params.length; i++) {
			try {
				if (params[i].equals("in")) {
					if (params[i + 1] != null) {
						return params[i + 1];
					}
				}
			} catch (Exception e) {
				throw new IllegalStateException();
			}
		}
		return "";
	}

	/**
	 *Interface overriding.
	 */
	@Override
	public Boolean weighted() {
		if (workspace.mentionParams != null && workspace.mentionParams.length != 0
				&& workspace.mentionParams[workspace.mentionParams.length - 1].equals("-n")) {
			return true;
		}
		return false;
	}
}
