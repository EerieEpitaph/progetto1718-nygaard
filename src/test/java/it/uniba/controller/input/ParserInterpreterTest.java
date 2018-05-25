package it.uniba.controller.input;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.ZipException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniba.controller.DataController;
import it.uniba.model.Model;
import it.uniba.view.View;
import picocli.CommandLine.UnmatchedArgumentException;

public class ParserInterpreterTest
{
    CommandParser parser;
    DataController dataCtr;
    CommandInterpreter interpreter;
    
    PrintStream originalOut = System.out;
    ByteArrayOutputStream newConsole1;
    ByteArrayOutputStream newConsole2;
    PrintStream newOut1;
    PrintStream newOut2;
    
    @BeforeEach
    void initDataCtr()
    {
        //Inizializzo il dataController
        Model model = new Model();
        View view = new View();
        dataCtr = new DataController(model, view);
        
        //Inizializzo i buffer di stampa per controllare
        //Eguaglianze tra print()
        newConsole1 = new ByteArrayOutputStream();
        newConsole2 = new ByteArrayOutputStream();
        newOut1 = new PrintStream(newConsole1);
        newOut2 = new PrintStream(newConsole2);
    }
    
    @AfterEach
    void returnControlToSystemOut()
    {
        //Reimposto il System.out
        System.setOut(originalOut);
    }
    
    //Triggero un workspace inesistente o invalid
    @Test 
    void InvalidWorkspace() throws ZipException, IOException
    {
        String[] args = {"-w", ""};
        parser = new CommandParser(args);
        interpreter = new CommandInterpreter();
        assertThrows(IllegalStateException.class, () -> {interpreter.executeCommands(parser, dataCtr);});
    }
    
    //Testo la chiamata all'help
    @Test
    void HelpCalled() throws ZipException, IOException
    {
        String[] args = {"help"};
        parser = new CommandParser(args);
        interpreter = new CommandInterpreter();
        
        System.setOut(newOut1);
        interpreter.executeCommands(parser, dataCtr);
        
        System.setOut(newOut2);
        dataCtr.showHelp();
        
        String out1 = new String(newConsole1.toByteArray(), StandardCharsets.UTF_8);
        String out2 = new String(newConsole2.toByteArray(), StandardCharsets.UTF_8);
        assertEquals(out1, out2);
    }
    
    //Testo la chiamata ai membri
    @Test
    void MembersCalled() throws ZipException, IOException
    {
        String[] args = {"-w", ".//res//ingsw.zip", "-u"};
        parser = new CommandParser(args);
        interpreter = new CommandInterpreter();
        
        System.setOut(newOut1);
        interpreter.executeCommands(parser, dataCtr);
        
        System.setOut(newOut2);
        dataCtr.printMembers();
        
        String out1 = new String(newConsole1.toByteArray(), StandardCharsets.UTF_8);
        String out2 = new String(newConsole2.toByteArray(), StandardCharsets.UTF_8);
        assertEquals(out1, out2);
    }
    
    //Testo la chiamata ai channels
    @Test
    void ChannelsCalled() throws ZipException, IOException
    {
        String[] args = {"-w", ".//res//ingsw.zip", "-c"};
        parser = new CommandParser(args);
        interpreter = new CommandInterpreter();
        
        System.setOut(newOut1);
        interpreter.executeCommands(parser, dataCtr);
        
        System.setOut(newOut2);
        dataCtr.printChannels();
        
        String out1 = new String(newConsole1.toByteArray(), StandardCharsets.UTF_8);
        String out2 = new String(newConsole2.toByteArray(), StandardCharsets.UTF_8);
        assertEquals(out1, out2);
    }
    
    //Testo la chiamata ai channels estesi
    @Test
    void ExtendedChannelCalled() throws ZipException, IOException
    {
        String[] args = {"-w", ".//res//ingsw.zip", "-cu"};
        parser = new CommandParser(args);
        interpreter = new CommandInterpreter();
        
        System.setOut(newOut1);
        interpreter.executeCommands(parser, dataCtr);
        
        System.setOut(newOut2);
        dataCtr.printMembers4Channel();;
        
        String out1 = new String(newConsole1.toByteArray(), StandardCharsets.UTF_8);
        String out2 = new String(newConsole2.toByteArray(), StandardCharsets.UTF_8);
        assertEquals(out1, out2);
    }
    
    //Testo la chiamata ad utenti in un channel
    @Test
    void UsersInChannelCalled() throws ZipException, IOException
    {
        String[] args = {"-w", ".//res//ingsw.zip", "-uc", "micali"};
        parser = new CommandParser(args);
        interpreter = new CommandInterpreter();
        
        System.setOut(newOut1);
        interpreter.executeCommands(parser, dataCtr);
        
        System.setOut(newOut2);
        dataCtr.printChannelMembers("micali");
        
        String out1 = new String(newConsole1.toByteArray(), StandardCharsets.UTF_8);
        String out2 = new String(newConsole2.toByteArray(), StandardCharsets.UTF_8);
        assertEquals(out1, out2);
    }
    
    //Testo mention from Lanubile in General
    @Test 
    void FromInMentionCalled() throws ZipException, IOException
    {
        String[] args = {"-w", ".//res//ingsw.zip", "-m", "from", "Lanubile", "in", "general", "-n"};
        parser = new CommandParser(args);
        interpreter = new CommandInterpreter();
        
        System.setOut(newOut1);
        interpreter.executeCommands(parser, dataCtr);
        
        System.setOut(newOut2);
        dataCtr.printMentionsFromUserWeigthed("Lanubile", "general");
        
        byte[] temp1 = newConsole1.toByteArray();
        byte[] temp2 = newConsole1.toByteArray();
        Arrays.sort(temp1);
        Arrays.sort(temp2);
        
        String out1 = new String(temp1, StandardCharsets.UTF_8);
        String out2 = new String(temp2, StandardCharsets.UTF_8);
        assertEquals(out1.hashCode(), out2.hashCode());
    }
    
    //Testo mention to Lanubile in General
    @Test 
    void ToInMentionCalled() throws ZipException, IOException
    {
        String[] args = {"-w", ".//res//ingsw.zip", "-m", "to", "Lanubile", "in", "general", "-n"};
        parser = new CommandParser(args);
        interpreter = new CommandInterpreter();
        
        System.setOut(newOut1);
        interpreter.executeCommands(parser, dataCtr);
        
        System.setOut(newOut2);
        dataCtr.printMentionsFromUserWeigthed("Lanubile", "general");
        
        byte[] temp1 = newConsole1.toByteArray();
        byte[] temp2 = newConsole1.toByteArray();
        Arrays.sort(temp1);
        Arrays.sort(temp2);
        
        String out1 = new String(temp1, StandardCharsets.UTF_8);
        String out2 = new String(temp2, StandardCharsets.UTF_8);
        assertEquals(out1.hashCode(), out2.hashCode());
    }
    
    //Testo la mention base con -n
    @Test
    void BaseMentionWeightCalled() throws ZipException, IOException
    {
        String[] args = {"-w", ".//res//ingsw.zip", "-m", "-n"};
        parser = new CommandParser(args);
        interpreter = new CommandInterpreter();
        
        System.setOut(newOut1);
        interpreter.executeCommands(parser, dataCtr);
        
        System.setOut(newOut2);
        dataCtr.printAllMention("", true);
        
        byte[] temp1 = newConsole1.toByteArray();
        byte[] temp2 = newConsole1.toByteArray();
        Arrays.sort(temp1);
        Arrays.sort(temp2);
        
        String out1 = new String(temp1, StandardCharsets.UTF_8);
        String out2 = new String(temp2, StandardCharsets.UTF_8);
        assertEquals(out1.hashCode(), out2.hashCode());
    }
    
    //Testo la mention base con in e -n
    @Test
    void BaseInMentionWeightCalled() throws ZipException, IOException
    {
        String[] args = {"-w", ".//res//ingsw.zip", "-m", "in", "micali", "-n"};
        parser = new CommandParser(args);
        interpreter = new CommandInterpreter();
        
        System.setOut(newOut1);
        interpreter.executeCommands(parser, dataCtr);
        
        System.setOut(newOut2);
        dataCtr.printAllMention("micali", true);
        
        byte[] temp1 = newConsole1.toByteArray();
        byte[] temp2 = newConsole1.toByteArray();
        Arrays.sort(temp1);
        Arrays.sort(temp2);
        
        String out1 = new String(temp1, StandardCharsets.UTF_8);
        String out2 = new String(temp2, StandardCharsets.UTF_8);
        assertEquals(out1.hashCode(), out2.hashCode());
    }
    
    //Testo mention verso qualcuno
    @Test
    void MentionToCalled() throws ZipException, IOException
    {
        String[] args = {"-w", ".//res//ingsw.zip", "-m", "to", "Lanubile"};
        parser = new CommandParser(args);
        interpreter = new CommandInterpreter();
        
        System.setOut(newOut1);
        interpreter.executeCommands(parser, dataCtr);
        
        System.setOut(newOut2);
        dataCtr.printMentionsToUser("Lanubile", "");
        
        byte[] temp1 = newConsole1.toByteArray();
        byte[] temp2 = newConsole1.toByteArray();
        Arrays.sort(temp1);
        Arrays.sort(temp2);
        
        String out1 = new String(temp1, StandardCharsets.UTF_8);
        String out2 = new String(temp2, StandardCharsets.UTF_8);
        assertEquals(out1.hashCode(), out2.hashCode());
    }
    
    //Testo un parametro inesistente
    @Test
    void BadParameters()
    {
        String[] args = {"-w", ".//res//ingsw.zip", "-testFallisci!"};
        assertThrows(UnmatchedArgumentException.class, () -> {parser = new CommandParser(args);});
    }
}
