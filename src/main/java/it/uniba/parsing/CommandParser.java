package it.uniba.parsing;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

public class CommandParser
{
    public class Args 
    {
        @Parameter
        private List<String> parameters = new ArrayList<String>();

        @Parameter(names = "load", description = "Path of the zip file to load") 
        private String zipFile;
        
        @Parameter(names = "quit", description = "Quits") 
        private boolean sigKill = false;
        
        @Parameter(names = "drop", description = "Drops current workspace") 
        private boolean toDrop = false;
        
        @Parameter(names = "channels", description = "Lists all channels") 
        private boolean channels = false;
        
        @Parameter(names = "members", description = "Lists all users") 
        private boolean members = false;
        
        public String getZipFile()
        {
            return zipFile;
        }
        public Boolean getSigKill()
        {
            return sigKill;
        }
        public Boolean getDrop()
        {
            return toDrop;
        }
        public Boolean getNakedChannels()
        {
            return channels;
        }
        public Boolean getNakedUsers()
        {
            return members;
        }
    }
    
    @Parameters(commandNames = "channels", separators = " ", commandDescription = "Prints channels")
    public class CommandChannels
    {
        @Parameter(names = "-m" , description = "List of channels with their users")
        private boolean fullChannels;
        
        public Boolean getFullChannels()
        {
            return fullChannels;
        }
    }
    
    @Parameters(commandNames = "members" , separators = " ", commandDescription = "Prints users")
    public class CommandUsers
    {
        @Parameter(names = "-c" , description = "List of users with their channels")
        private String filterChannel;
        
        public String getFilterChannel()
        {
            return filterChannel;
        }
    }
    
    private JCommander commander;
    private Args nakedArgs;
    private CommandChannels cc;
    private CommandUsers cu;
    private ArrayList<Field> fields;
    
    public CommandParser(String[] parameters)
    {
        nakedArgs = new Args();
        cc = new CommandChannels();
        cu = new CommandUsers();
        commander = JCommander.newBuilder()
            .addObject(nakedArgs)
            .addCommand(cc)
            .addCommand(cu)
            .build();
        
        commander.parse(parameters);
        
        fields = new ArrayList(Arrays.asList(Args.class.getDeclaredFields()));
        fields.remove(0);
        fields.remove(fields.size()-1); 
    }
    
    public JCommander getCommander()
    {
        return commander;
    }
    public Args getSingleArgs()
    {
        return nakedArgs;
    }
    public CommandChannels getCommandChannels()
    {
        return cc;
    }
    public CommandUsers getCommandUsers()
    {
        return cu;
    }
    public ArrayList<Field> getArgsFields()
    {
        return fields;
    }
    
    //Controlla se la stringa "param" è definita tra i parametri associati
    private boolean isTrue(String param)
    {
        for(Field x : fields)
        {
            try
            {
                x.setAccessible(true);
                boolean tempEval = false;
                
                //Se il field a cui stiamo facendo accesso è quello del parametro
                if(x.getName().equals(param))
                {
                    //Se x non è un booleano vediamo se almeno non è nullo
                    if(!x.getType().equals(boolean.class))
                        tempEval = (x.get(nakedArgs) != null);
                    //Altrimenti castiamo a booleano
                    else 
                        tempEval = (boolean) x.get(nakedArgs);
                    
                    x.setAccessible(false);
                    if(tempEval)
                        return true;
                }
            } 
            catch (IllegalArgumentException e)
            {e.printStackTrace();} 
            catch (IllegalAccessException e)
            {e.printStackTrace();}  
        }
        return false;
    }
    
    //Controlla se la stringa "param", che ha il nome di un field della classe Args, è l'unico ad esse true oppure no
    private boolean isUnique(String param)
    {
        for(Field x : fields)
        {
            try
            {
                x.setAccessible(true);
                boolean tempEval = false;
                
                //Se il field a cui stiamo facendo accesso NON è quello del parametro
                if(!x.getName().equals(param))
                {
                    //Se x non è un booleano vediamo se almeno non è nullo
                    if(!x.getType().equals(boolean.class))
                        tempEval = (x.get(nakedArgs) != null);
                    //Altrimenti castiamo a booleano
                    else 
                        tempEval = (boolean) x.get(nakedArgs);
                    
                    x.setAccessible(false);
                    if(tempEval)
                        return false;
                }
            } 
            catch (IllegalArgumentException e)
            {e.printStackTrace();} 
            catch (IllegalAccessException e)
            {e.printStackTrace();}  
        }
        return true;
    }
    
    public void validateArguments()
    {
        if(isTrue("zipFile"))
            assert(isUnique("zipFile"));
        if(isTrue("sigKill"))
            assert(isUnique("sigKill"));
        if(isTrue("toDrop"))
            assert(isUnique("toDrop"));
        if(isTrue("channels"))
            assert(isUnique("channels"));
        if(isTrue("members"))
            assert(isUnique("members"));
    }
}
