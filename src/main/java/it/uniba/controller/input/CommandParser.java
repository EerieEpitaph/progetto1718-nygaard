package it.uniba.controller.input;

import java.util.Arrays;
import java.util.List;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 * This class manages the parsing of command line arguments.
 */
public final class CommandParser implements CommandParserInterface {
	/*
	 * Base commands representer.
	 */
	private CommBaseArgs baseArgs;
	/*
	 * Workspace command representer.
	 */
	private CommWorkspace workspace;

	/**
	 * This class manages no-parameter commands.
	 */
	public final class CommBaseArgs {
		/*
		 * Activity notifier.
		 */
		private Boolean active = false;

		/*
		 * Help found.
		 */
		@Option(names = "help")
		private boolean helpStatus;

		/**
		 * Gives activity status.
		 * 
		 * @return active status
		 */
		public Boolean isActive() {
			return active;
		}

		/**
		 * Gives help status.
		 * 
		 * @return helpstatus
		 */
		public Boolean getHelpStatus() {
			return helpStatus;
		}
	}

	/**
	 * This class manages workspace-related commands.
	 */
	public static final class CommWorkspace {

		/*
		 * Name of the current workspace.
		 */
		@Parameters(index = "0", arity = "1")
		private String workspaceName;

		/*
		 * Status of member command.
		 */
		@Option(names = "-u", arity = "0..1")
		private boolean membersStatus;

		/*
		 * Status of channels command.
		 */
		@Option(names = "-c", arity = "0..1")
		private boolean channelsStatus;

		/*
		 * Status of extended channels command.
		 */
		@Option(names = "-cu", arity = "0..1")
		private boolean extChannelsStatus;

		/*
		 * Name of channel filter.
		 */
		@Option(names = "-uc", arity = "1")
		private String channelFilter;

		/*
		 * Parameters passed at mention command.
		 */
		@Option(names = "-m", arity = "0..5")
		private String[] mentionParams;

		/**
		 * Worskpace getter.
		 * 
		 * @return name of workspace
		 */
		public String getWorkspaceName() {
			return workspaceName;
		}

		/**
		 * Workspace validity getter.
		 * 
		 * @return workspace validity
		 */
		public Boolean isValidWorkspace() {
			return !"".equals(workspaceName);
		}

		/**
		 * Member status getter.
		 * 
		 * @return memberStatus value
		 */
		public Boolean getMembersStatus() {
			return membersStatus;
		}

		/**
		 * Channel status getter.
		 * 
		 * @return channelStatus value
		 */
		public Boolean getChannelsStatus() {
			return channelsStatus;
		}

		/**
		 * Extended channel status getter.
		 * 
		 * @return extChannelStatus value
		 */
		public Boolean getExtChannelsStatus() {
			return extChannelsStatus;
		}

		/**
		 * Channel filter getter.
		 * 
		 * @return channelFilter value
		 */
		public String getChannelFilter() {
			return channelFilter;
		}

		/**
		 * Channel filter validity getter.
		 * 
		 * @return channelFilter validity
		 */
		public Boolean isValidFilter() {
			return (channelFilter != null && !"".equals(channelFilter));
		}

		/**
		 * Mention params getter.
		 * 
		 * @return array of parameters passed after mention command
		 */
		public String[] getMentionParams() {
			return Arrays.copyOf(mentionParams, mentionParams.length);
		}

		/**
		 * Mention params setter.
		 * 
		 * @param newParams new parameter mentions
		 */
		public void setMentionParams(final String[] newParams) {
			mentionParams = Arrays.copyOf(newParams, newParams.length);
		}
	}

	/**
	 * CommandParser constructor.
	 * 
	 * @param args
	 *            array of command line arguments
	 * @throws IllegalStateException
	 *             if conflicting commands parsed
	 */
	public CommandParser(final String[] args) {
		baseArgs = new CommBaseArgs();
		workspace = new CommWorkspace();

		final CommandLine commandLine = new CommandLine(baseArgs).addSubcommand("-w", workspace);

		final List<CommandLine> result = commandLine.parse(args);

		for (final CommandLine single : result) {
			if (single.getCommand().getClass() == CommBaseArgs.class) {
				baseArgs = (CommBaseArgs) single.getCommand();
				baseArgs.active = true;

			} else {
				workspace = (CommWorkspace) single.getCommand();
			}
		}
	}

	/**
	 * No-param args getter.
	 * 
	 * @return baseArguments
	 */
	public CommBaseArgs getBaseArgs() {
		return baseArgs;
	}

	/**
	 * Workspace commadn getter.
	 * 
	 * @return workspaceCommand
	 */
	public CommWorkspace getCommWorkspace() {
		return workspace;
	}

	// ==========================================================
	// ================INTERFACE IMPLEMENTATION==================
	// ==========================================================

	/**
	 * Interface overriding.
	 */
	@Override
	public Boolean validWorkspace() {
		if (workspace.isValidWorkspace()) {
			return true;
		}
		return false;
	}

	/**
	 * Interface overriding.
	 */
	@Override
	public String getWorkspace() {
		return workspace.getWorkspaceName();
	}

	/**
	 * Interface overriding.
	 */
	@Override
	public Boolean help() {
		if (baseArgs.getHelpStatus()) {
			return true;
		}
		return false;
	}

	/**
	 * Interface overriding.
	 */
	@Override
	public Boolean users() {
		if (workspace.getMembersStatus()) {
			return true;
		}
		return false;
	}

	/**
	 * Interface overriding.
	 */
	@Override
	public Boolean channels() {
		if (workspace.getChannelsStatus()) {
			return true;
		}
		return false;
	}

	/**
	 * Interface overriding.
	 */
	@Override
	public Boolean extendedChannels() {
		if (workspace.getExtChannelsStatus()) {
			return true;
		}
		return false;
	}

	/**
	 * Interface overriding.
	 */
	@Override
	public Boolean usersInChannel() {
		if (workspace.isValidFilter()) {
			return true;
		}
		return false;
	}

	/**
	 * Interface overriding.
	 */
	@Override
	public String getChannelFilter() {
		return workspace.getChannelFilter();
	}

	/**
	 * Interface overriding.
	 */
	@Override
	public Boolean mentions() {
		if (workspace.mentionParams != null) {
			return true;
		}
		return false;
	}

	/**
	 * Interface overriding.
	 */
	@Override
	public Boolean from() {
		final String[] params = workspace.mentionParams;
		final int length = params.length;
		for (int i = 0; i < length; i++) {
			if ("from".equals(params[i]) && (params[i + 1] != null)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Interface overriding.
	 */
	@Override
	public String getFromWho() {
		final String[] params = workspace.mentionParams;
		final int length = params.length;
		for (int i = 0; i < length; i++) {
			try {
				if (("from".equals(params[i])) && (params[i + 1] != null)) {
					return params[i + 1];
				}
			} catch (Exception e) {
				throw new IllegalStateException();
			}
		}
		return "";
	}

	/**
	 * Interface overriding.
	 */
	@Override
	public Boolean to() {
		final String[] params = workspace.mentionParams;
		final int length = params.length;
		for (int i = 0; i < length; i++) {
			if (("to".equals(params[i])) && (params[i + 1] != null)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Interface overriding.
	 */
	@Override
	public String getToWho() {
		final String[] params = workspace.mentionParams;
		final int length = params.length;
		for (int i = 0; i < length; i++) {
			try {
				if (("to".equals(params[i])) && (params[i + 1] != null)) {
					return params[i + 1];
				}
			} catch (Exception e) {
				throw new IllegalStateException();
			}
		}
		return "";
	}

	/**
	 * Interface overriding.
	 */
	@Override
	public Boolean in() {
		final String[] params = workspace.mentionParams;
		final int length = params.length;
		for (int i = 0; i < length; i++) {
			if (("in".equals(params[i])) && (params[i + 1] != null)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Interface overriding.
	 */
	@Override
	public String getInWhat() {
		final String[] params = workspace.mentionParams;
		final int length = params.length;
		for (int i = 0; i < length; i++) {
			try {
				if (("in".equals(params[i])) && (params[i + 1] != null)) {
					return params[i + 1];
				}
			} catch (Exception e) {
				throw new IllegalStateException();
			}
		}
		return "";
	}

	/**
	 * Interface overriding.
	 */
	@Override
	public Boolean weighted() {

		if (workspace.mentionParams != null && workspace.mentionParams.length != 0
				&& "-n".equals(workspace.mentionParams[workspace.mentionParams.length - 1])) {
			return true;
		}
		return false;
	}
}
