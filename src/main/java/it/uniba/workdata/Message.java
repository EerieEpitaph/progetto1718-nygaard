package it.uniba.workdata;

public class Message
{    
    public class gsonMessage
    {
        private String type;
        private String user;
        private String text;
        
        public gsonMessage(String _type, String _user, String _text)
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
    
//    private String channel;
    private gsonMessage message;
    
    public Message(gsonMessage _message)
    {
        //channel = _channel;
        message = _message;
    }
    
//    public String getChannel()
//    {
//        return channel;
//    }

    public String getType()
    {
        return message.getType();
    }
    
    public String getUser()
    {
        return message.getUser();
    }
    
    public String getText()
    {
        return message.getText();
    }
}
