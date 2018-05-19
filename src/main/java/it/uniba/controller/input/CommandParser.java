package it.uniba.controller.input;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

public class CommandParser implements CommandParserInterface
{
    // Comandi booleani senza parametri
    public class CommBaseArgs
    {
        private Boolean active = false;

        @Option(names = "help")
        private boolean helpStatus;

        public Boolean isActive()
        {
            return active;
        }

        public Boolean getHelpStatus()
        {
            return helpStatus;
        }
    }

    public class CommWorkspace
    {
        private Boolean active = false;

        @Parameters(index = "0", arity = "1")
        String workspaceName;

        @Option(names = "-u", arity = "0..1")
        private boolean membersStatus;

        @Option(names = "-c", arity = "0..1")
        private boolean channelsStatus;

        @Option(names = "-cu", arity = "0..1")
        private boolean extChannelsStatus;

        @Option(names = "-uc", arity = "1")
        private String channelFilter;

        @Option(names = "-m", arity = "0..4")
        private String[] mentionParams;

        public Boolean isActive()
        {
            return active;
        }

        public String getWorkspaceName()
        {
            return workspaceName;
        }

        public Boolean isValidWorkspace()
        {
            return (workspaceName != null && workspaceName != "");
        }

        public Boolean getMembersStatus()
        {
            return membersStatus;
        }

        public Boolean getChannelsStatus()
        {
            return channelsStatus;
        }

        public Boolean getExtChannelsStatus()
        {
            return extChannelsStatus;
        }

        public String getChannelFilter()
        {
            return channelFilter;
        }

        public Boolean isValidFilter()
        {
            return (channelFilter != null && channelFilter != "");
        }

        public String[] getMentionParams()
        {
            return mentionParams;
        }
    }

    private CommBaseArgs baseArgs;
    private CommWorkspace workspace;

    public CommandParser(String[] args) throws IllegalStateException
    {
        baseArgs = new CommBaseArgs();
        workspace = new CommWorkspace();

        CommandLine commandLine = new CommandLine(baseArgs).addSubcommand("-w",
                workspace);

        List<CommandLine> result = commandLine.parse(args);

        for (CommandLine x : result)
        {
            // Gli "argomenti base" sarebbero sempre true, per com'e'
            // strutturata la
            // libreria.
            // In questo if setto la loro attivita' = true solo se, usando la
            // riflessione,
            // uno dei loro field e' true
            // Se piu' di un field e' true, throwo direttamente un'eccezione.
            if (x.getCommand().getClass() == CommBaseArgs.class)
            {
                baseArgs = (CommBaseArgs) x.getCommand();

                ArrayList<Field> baseFields = new ArrayList<Field>(
                        Arrays.asList(CommBaseArgs.class.getDeclaredFields()));
                baseFields.remove(0);
                baseFields.remove(baseFields.size() - 1);

                for (Field y : baseFields)
                {
                    // System.out.println(y.getName());
                    y.setAccessible(true);
                    try
                    {
                        if (y.get(baseArgs).toString().equals("true"))
                        {
                            if (baseArgs.active)
                                throw new IllegalStateException(); // CATCHED
                            else
                                baseArgs.active = true;
                        }

                        y.setAccessible(false);
                    } catch (IllegalArgumentException e)
                    {
                        e.printStackTrace();
                    } catch (IllegalAccessException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            // Il comando parsato � "-w"
            else if (x.getCommand().getClass() == CommWorkspace.class)
            {
                workspace = (CommWorkspace) x.getCommand();
                workspace.active = true;
            }
        }
    }

    public CommBaseArgs getBaseArgs()
    {
        return baseArgs;
    }

    public CommWorkspace getCommWorkspace()
    {
        return workspace;
    }

    // ==========================================================
    // ================INTERFACE IMPLEMENTATION==================
    // ==========================================================

    @Override
    public Boolean validWorkspace()
    {
        if (workspace.isActive() && workspace.isValidWorkspace())
            return true;
        return false;
    }
    
    @Override
    public String getWorkspace()
    {
        return workspace.getWorkspaceName();
    }
    
    @Override
    public Boolean help()
    {
        if (baseArgs.getHelpStatus())
            return true;
        return false;
    }

    @Override
    public Boolean users()
    {
        if (workspace.getMembersStatus())
            return true;
        return false;
    }

    @Override
    public Boolean channels()
    {
        if (workspace.getChannelsStatus())
            return true;
        return false;
    }

    @Override
    public Boolean extendedChannels()
    {
        if (workspace.getExtChannelsStatus())
            return true;
        return false;
    }

    @Override
    public Boolean userInChannel()
    {
        if (workspace.isValidFilter())
            return true;
        return false;
    }

    @Override
    public String getChannelFilter()
    {
        return workspace.getChannelFilter();
    }

    @Override
    public Boolean mentions()
    {
        if (workspace.mentionParams != null)
            return true;
        return false;
    }

    @Override
    public Boolean from()
    {
        String[] params = workspace.mentionParams;
        if (params.length >= 2)
        {
            if (params[0].equals("from"))
            {
                try
                {
                    if (params[1] != null)
                        return true;
                } catch (Exception e)
                {
                    throw new IllegalStateException();
                }
            }
        }
        return false;
    }

    @Override
    public String getFromWho()
    {
        try
        {
            return workspace.mentionParams[1];
        } catch (Exception e)
        {
            throw new IllegalStateException();
        }
    }

    @Override
    public Boolean to()
    {
        String[] params = workspace.mentionParams;
        if (params.length >= 2)
        {
            if (params[0].equals("to"))
            {
                try
                {
                    if (params[1] != null)
                        return true;
                } catch (Exception e)
                {
                    throw new IllegalStateException();
                }
            }
        }
        return false;
    }

    @Override
    public String getToWho()
    {
        try
        {
            return workspace.mentionParams[1];
        } catch (Exception e)
        {
            throw new IllegalStateException();
        }
    }

    @Override
    public Boolean in()
    {
        String[] params = workspace.mentionParams;
        for (int i = 0; i < params.length; i++)
        {
            try
            {
                if (params[i].equals("in"))
                    if (params[i + 1] != null)
                        return true;
            } catch (Exception e)
            {
                throw new IllegalStateException();
            }
        }
        return false;
    }

    @Override
    public String getInWhat()
    {
        String[] params = workspace.mentionParams;
        for (int i = 0; i < params.length; i++)
        {
            try
            {
                if (params[i].equals("in"))
                    if (params[i + 1] != null)
                        return params[i + 1];
            } catch (Exception e)
            {
                throw new IllegalStateException();
            }
        }
        return "";
    }

    @Override
    public Boolean weighted()
    {
        if (workspace.mentionParams != null
                && workspace.mentionParams.length != 0
                && workspace.mentionParams[workspace.mentionParams.length - 1]
                        .equals("-n"))
            return true;
        return false;
    }
}
