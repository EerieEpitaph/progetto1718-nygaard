package it.uniba.workdata;

public class Message
{
    private String type;
    private String user;
    private String text;
    
    public Message(String _type, String _user, String _text)
    {
        type = _type;
        user = _user;
        text = _text;
    }
    
    public String getType()
    {
        return type;
    }
    
    public String getUser()
    {
        return user;
    }
    
    public String getText()
    {
        return text;
    }
}
