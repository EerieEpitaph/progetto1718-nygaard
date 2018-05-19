package it.uniba.controller.input;

public interface CommandParserInterface
{
    public Boolean validWorkspace();

    public String getWorkspace();

    public Boolean help();

    public Boolean users();

    public Boolean channels();

    public Boolean extendedChannels();

    public Boolean userInChannel();

    public String getChannelFilter();

    public Boolean mentions();

    public Boolean from();

    public String getFromWho();

    public Boolean to();

    public String getToWho();

    public Boolean in();

    public String getInWhat();

    public Boolean weighted();
}
