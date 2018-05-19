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
            // Gli "argomenti base" sarebbero sempre true, per com'�
            // strutturata la
            // libreria.
            // In questo if setto la loro attivit� = true solo se, usando la
            // riflessione,
            // uno dei loro field � true
            // Se pi� di un field e' true, throwo direttamente un'eccezione.
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
    public Boolean help()
    {
        if(baseArgs.getHelpStatus())
            return true;
        return false;
    }

    @Override
    public Boolean users()
    {
        if(workspace.getMembersStatus())
            return true;
        return false;
    }

    @Override
    public Boolean channels()
    {
        if(workspace.getChannelsStatus())
            return true;
        return false;
    }

    @Override
    public Boolean extendedChannels()
    {
        if(workspace.getExtChannelsStatus())
            return true;
        return false;
    }

    @Override
    public Boolean userInChannel()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getChannelFilter()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean mentions()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean from()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFromWho()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean to()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getToWho()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean in()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getInWhat()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean weighted()
    {
        // TODO Auto-generated method stub
        return null;
    }

    // ==========================================================
    // ====================INTERFACE HELPERS=====================
    // ==========================================================

    private Boolean wantsFrom(String[] mentionParams)
    {
        if ((mentionParams.length == 2) && mentionParams[0].equals("from")
                && mentionParams[1] != null)
            return true;
        else
            return false;
    }

    private Boolean wantsTo(String[] mentionParams)
    {
        if ((mentionParams.length == 2) && mentionParams[0].equals("to")
                && mentionParams[1] != null)
            return true;
        else
            return false;
    }

    private Boolean wantsIn(String[] mentionParams)
    {
        if ((mentionParams.length == 2) && mentionParams[0].equals("in")
                && mentionParams[1] != null)
            return true;
        else
            return false;
    }

    private Boolean wantsFromIn(String[] mentionParams)
    {
        if ((mentionParams.length == 4) && mentionParams[0].equals("from")
                && mentionParams[1] != null && mentionParams[2].equals("in")
                && mentionParams[1] != null)
            return true;
        else
            return false;
    }

    private Boolean wantsToIn(String[] mentionParams)
    {
        if ((mentionParams.length == 4) && mentionParams[0].equals("to")
                && mentionParams[1] != null && mentionParams[2].equals("in")
                && mentionParams[1] != null)
            return true;
        else
            return false;
    }
}
