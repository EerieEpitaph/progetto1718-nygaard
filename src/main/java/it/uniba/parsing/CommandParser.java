package it.uniba.parsing;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class CommandParser
{
    public class Args 
    {
        @Parameter
        private List<String> parameters = new ArrayList<String>();
        
        Map<String, Boolean> evaluatedParams = new HashMap<String, Boolean>();

        @Parameter(names = { "load", "-l" }, description = "Path of the zip file to load") 
        private String zipFile;
        
        @Parameter(names = { "quit", "-q" }, description = "Quits") 
        private boolean sigKill = false;
        
        @Parameter(names = { "drop", "-d" }, description = "Drops current workspace") 
        private boolean toDrop = false;
        
        @Parameter(names = { "channels", "-c" }, description = "Prints workspace's channels") 
        private boolean printChannels = false;
        
        @Parameter(names = { "members", "-m" }, description = "Prints workspace's members")
        private boolean printMembers; //TEMP


        /*
        @Parameter(names = { "workspace", "-w" }, description = "Prints the name of the workspace")
        private boolean debug = false; //TEMP
        */
        
        public Map<String, Boolean> getEvals()
        {
            return evaluatedParams;
        }
        
        public String getZipFile()
        {
            return zipFile;
        }
        public boolean getSigKill()
        {
            return sigKill;
        }
        public boolean getDrop()
        {
            return toDrop;
        }
        public boolean getChannelize()
        {
            return printChannels;
        }
        public boolean getMembers()
        {
            return printMembers;
        }
    }
    
    private Args arguments;
    private ArrayList<Field> fields;
    
    public CommandParser(String[] parameters)
    {
        arguments = new Args();
 
        //Passando un vettore di parametri, il JCommander prova ad interfacciarli con i membri privati della classe "Args" sopra elencati
        //Se un parametro è stato definito, il corrispettivo membro privato viene settato col giusto valore
        JCommander.newBuilder().addObject(arguments).build().parse(parameters);        
        
        //Popolo la lista di booleani con i comandi parsati con successo
        Field[] tempFields = Args.class.getDeclaredFields();
        fields = new ArrayList<Field>(Arrays.asList(tempFields));
        
        //Remove necessari per togliere i meta-membri di classe (this, riferimenti alla sovraclasse)
        fields.remove(0);
        fields.remove(0);
        fields.remove(fields.size()-1);    
    }
    
    public Args getParsedArgs()
    {
        return arguments;
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
                        tempEval = (x.get(arguments) != null);
                    //Altrimenti castiamo a booleano
                    else 
                        tempEval = (boolean) x.get(arguments);
                    
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
                        tempEval = (x.get(arguments) != null);
                    //Altrimenti castiamo a booleano
                    else 
                        tempEval = (boolean) x.get(arguments);
                    
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
        
        if(isTrue("toDrop"))
            assert(isUnique("toDrop"));
    }
}
