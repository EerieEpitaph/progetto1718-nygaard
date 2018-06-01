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
import it.uniba.parsing.ZipParser;
import it.uniba.view.View;
import it.uniba.controller.ExceptionsHandler;
import it.uniba.main.AppMain;
import picocli.CommandLine.UnmatchedArgumentException;

//La maggior parte di questi test controllanto che l'output 
//dell'interprete sia uguale all'output della funzione che ci aspettiamo.
//Verrï¿½ testato il parsing e l'interpretazione degli argomenti,
//cercando di eliminare problemi in comune.

public class ParserInterpreterTest {
	CommandParser parser;
	DataController dataCtr;
	CommandInterpreter interpreter;

	final PrintStream originalOut = System.out;
	ByteArrayOutputStream newConsole1;
	ByteArrayOutputStream newConsole2;
	PrintStream newOut1;
	PrintStream newOut2;

	@BeforeEach
	void setUp() {
		// Inizializzo il dataController
		final Model model = new Model();
		final View view = new View();
		dataCtr = new DataController(model, view);
		interpreter = new CommandInterpreter();

		// Inizializzo i buffer di stampa per controllare
		// Eguaglianze tra print()
		newConsole1 = new ByteArrayOutputStream();
		newConsole2 = new ByteArrayOutputStream();
		newOut1 = new PrintStream(newConsole1);
		newOut2 = new PrintStream(newConsole2);
	}

	@AfterEach
	void tearDown() {
		// Reimposto il System.out
		System.setOut(originalOut);
	}

	// Triggero un workspace inesistente o invalid
	@Test
	void invalidWorkspace() throws ZipException, IOException {
		final String[] args = { "-w", "" };
		parser = new CommandParser(args);
		interpreter = new CommandInterpreter();
		assertThrows(IllegalStateException.class, () -> {
			interpreter.executeCommands(parser, dataCtr);
		});
	}

	// Testo la chiamata all'help
	@Test
	void helpCalled() throws ZipException, IOException, ExceptionsHandler {
		// Costruiamo l'array di argomenti da testare
		final String[] args = { "help" };
		// E li diamo in pasto al parser
		parser = new CommandParser(args);
		interpreter = new CommandInterpreter();

		// Cambiamo l'out di sistema per farlo finire in un nostro stream
		System.setOut(newOut1);
		// Eseguendo l'interprete, la stampa a video verrï¿½ rediretta
		// nello stream sopracitato, potendola salvare in una variabile
		interpreter.executeCommands(parser, dataCtr);

		// Cambio nuovamente l'out e lo faccio finire in un altro stream
		System.setOut(newOut2);
		// Qui chiamo la funzione che mi aspetto venga eseguita con quei parametri.
		// In questo caso l'help "stamperï¿½" nel secondo nostro stream
		dataCtr.showHelp();

		// Possiamo finalmente convertire entrambi gli stream in una stringa.
		// Questo ci permette di controllare la loro uguaglianza
		final String out1 = new String(newConsole1.toByteArray(), StandardCharsets.UTF_8);
		final String out2 = new String(newConsole2.toByteArray(), StandardCharsets.UTF_8);
		// Se la stampa con argomenti "help" ï¿½ infatti la funzione dataCtr.help(),
		// il test viene superato.
		assertEquals(out1, out2);
	}

	// Testo la chiamata ai membri
	@Test
	void membersCalled() throws ZipException, IOException, ExceptionsHandler {
		final String[] args = { "-w", ".//res//ingsw.zip", "-u" };
		parser = new CommandParser(args);
		interpreter = new CommandInterpreter();

		System.setOut(newOut1);
		interpreter.executeCommands(parser, dataCtr);

		System.setOut(newOut2);
		dataCtr.printMembers();

		final String out1 = new String(newConsole1.toByteArray(), StandardCharsets.UTF_8);
		final String out2 = new String(newConsole2.toByteArray(), StandardCharsets.UTF_8);
		assertEquals(out1, out2);
	}

	// Testo la chiamata ai channels
	@Test
	void channelsCalled() throws ZipException, IOException, ExceptionsHandler {
		final String[] args = { "-w", ".//res//ingsw.zip", "-c" };
		parser = new CommandParser(args);
		interpreter = new CommandInterpreter();

		System.setOut(newOut1);
		interpreter.executeCommands(parser, dataCtr);

		System.setOut(newOut2);
		dataCtr.printChannels();

		final String out1 = new String(newConsole1.toByteArray(), StandardCharsets.UTF_8);
		final String out2 = new String(newConsole2.toByteArray(), StandardCharsets.UTF_8);
		assertEquals(out1, out2);
	}

	// Testo la chiamata ai channels estesi
	@Test
	void extendedChannelCalled() throws ZipException, IOException, ExceptionsHandler {
		final String[] args = { "-w", ".//res//ingsw.zip", "-cu" };
		parser = new CommandParser(args);
		interpreter = new CommandInterpreter();

		System.setOut(newOut1);
		interpreter.executeCommands(parser, dataCtr);

		System.setOut(newOut2);
		dataCtr.printMembers4Channel();

		final String out1 = new String(newConsole1.toByteArray(), StandardCharsets.UTF_8);
		final String out2 = new String(newConsole2.toByteArray(), StandardCharsets.UTF_8);
		assertEquals(out1, out2);
	}

	// Testo la chiamata ad utenti in un channel
	@Test
	void usersInChannelCalled() throws ZipException, IOException, ExceptionsHandler {
		final String[] args = { "-w", ".//res//ingsw.zip", "-uc", "micali" };
		parser = new CommandParser(args);
		interpreter = new CommandInterpreter();

		System.setOut(newOut1);
		interpreter.executeCommands(parser, dataCtr);

		System.setOut(newOut2);
		dataCtr.printChannelMembers("micali");

		final String out1 = new String(newConsole1.toByteArray(), StandardCharsets.UTF_8);
		final String out2 = new String(newConsole2.toByteArray(), StandardCharsets.UTF_8);
		assertEquals(out1, out2);
	}

	// Testo mention from Lanubile in General
	@Test
	void fromInMentionCalled() throws ZipException, IOException, ExceptionsHandler {
		final String[] args = { "-w", ".//res//ingsw.zip", "-m", "from", "Lanubile", "in", "general", "-n" };
		parser = new CommandParser(args);
		interpreter = new CommandInterpreter();

		System.setOut(newOut1);
		interpreter.executeCommands(parser, dataCtr);

		System.setOut(newOut2);
		dataCtr.printMentionsFromUserWeigthed("Lanubile", "general");

		final byte[] temp1 = newConsole1.toByteArray();
		final byte[] temp2 = newConsole1.toByteArray();
		Arrays.sort(temp1);
		Arrays.sort(temp2);

		final String out1 = new String(temp1, StandardCharsets.UTF_8);
		final String out2 = new String(temp2, StandardCharsets.UTF_8);
		assertEquals(out1.hashCode(), out2.hashCode());
	}

	// Testo mention to Lanubile in General
	@Test
	void toInMentionCalled() throws ZipException, IOException, ExceptionsHandler {
		final String[] args = { "-w", ".//res//ingsw.zip", "-m", "to", "Lanubile", "in", "general", "-n" };
		parser = new CommandParser(args);
		interpreter = new CommandInterpreter();

		System.setOut(newOut1);
		interpreter.executeCommands(parser, dataCtr);

		System.setOut(newOut2);
		dataCtr.printMentionsFromUserWeigthed("Lanubile", "general");

		final byte[] temp1 = newConsole1.toByteArray();
		final byte[] temp2 = newConsole1.toByteArray();
		Arrays.sort(temp1);
		Arrays.sort(temp2);

		final String out1 = new String(temp1, StandardCharsets.UTF_8);
		final String out2 = new String(temp2, StandardCharsets.UTF_8);
		assertEquals(out1.hashCode(), out2.hashCode());
	}

	// Testo la mention base con -n
	@Test
	void baseMentionWeightCalled() throws ZipException, IOException, ExceptionsHandler {
		final String[] args = { "-w", ".//res//ingsw.zip", "-m", "-n" };
		parser = new CommandParser(args);
		interpreter = new CommandInterpreter();

		System.setOut(newOut1);
		interpreter.executeCommands(parser, dataCtr);

		System.setOut(newOut2);
		dataCtr.printAllMention("", true);

		final byte[] temp1 = newConsole1.toByteArray();
		final byte[] temp2 = newConsole1.toByteArray();
		Arrays.sort(temp1);
		Arrays.sort(temp2);

		final String out1 = new String(temp1, StandardCharsets.UTF_8);
		final String out2 = new String(temp2, StandardCharsets.UTF_8);
		assertEquals(out1.hashCode(), out2.hashCode());
	}

	// Testo la mention base con in e -n
	@Test
	void baseInMentionWeightCalled() throws ZipException, IOException, ExceptionsHandler {
		final String[] args = { "-w", ".//res//ingsw.zip", "-m", "in", "micali", "-n" };
		parser = new CommandParser(args);
		interpreter = new CommandInterpreter();

		System.setOut(newOut1);
		interpreter.executeCommands(parser, dataCtr);

		System.setOut(newOut2);
		dataCtr.printAllMention("micali", true);

		final byte[] temp1 = newConsole1.toByteArray();
		final byte[] temp2 = newConsole1.toByteArray();
		Arrays.sort(temp1);
		Arrays.sort(temp2);

		final String out1 = new String(temp1, StandardCharsets.UTF_8);
		final String out2 = new String(temp2, StandardCharsets.UTF_8);
		assertEquals(out1.hashCode(), out2.hashCode());
	}

	// Testo mention verso qualcuno
	@Test
	void mentionToCalled() throws ZipException, IOException, ExceptionsHandler {
		final String[] args = { "-w", ".//res//ingsw.zip", "-m", "to", "Lanubile" };
		parser = new CommandParser(args);
		interpreter = new CommandInterpreter();

		System.setOut(newOut1);
		interpreter.executeCommands(parser, dataCtr);

		System.setOut(newOut2);
		dataCtr.printMentionsToUser("Lanubile", "");

		final byte[] temp1 = newConsole1.toByteArray();
		final byte[] temp2 = newConsole1.toByteArray();
		Arrays.sort(temp1);
		Arrays.sort(temp2);

		final String out1 = new String(temp1, StandardCharsets.UTF_8);
		final String out2 = new String(temp2, StandardCharsets.UTF_8);
		assertEquals(out1.hashCode(), out2.hashCode());
	}

	// Testo from senza weight
	@Test
	void fromNoWeight() throws ZipException, IOException, ExceptionsHandler {
		final String[] args = { "-w", ".//res//ingsw.zip", "-m", "from", "Lanubile" };
		parser = new CommandParser(args);
		interpreter = new CommandInterpreter();

		System.setOut(newOut1);
		interpreter.executeCommands(parser, dataCtr);

		System.setOut(newOut2);
		dataCtr.printMentionsFromUser("Lanubile", "");

		final byte[] temp1 = newConsole1.toByteArray();
		final byte[] temp2 = newConsole1.toByteArray();
		Arrays.sort(temp1);
		Arrays.sort(temp2);

		final String out1 = new String(temp1, StandardCharsets.UTF_8);
		final String out2 = new String(temp2, StandardCharsets.UTF_8);
		assertEquals(out1.hashCode(), out2.hashCode());
	}

	// Testo un parametro inesistente
	@Test
	void badParameters() {
		final String[] args = { "-w", ".//res//ingsw.zip", "-testFallisci!" };
		assertThrows(UnmatchedArgumentException.class, () -> {
			parser = new CommandParser(args);
		});
	}

	// Testo fromWho in casi eccezionali
	@Test
	void badFromWho() {
		final String[] args = { "-w", ".//res//ingsw.zip", "-m" };
		parser = new CommandParser(args);
		assertEquals("", parser.getFromWho());
	}

	// Testo fromWho in caso outOfBound
	@Test
	void fromWhoOutOfBound() {
		final String[] args = { "-w", ".//res//ingsw.zip", "-m", "from" };
		parser = new CommandParser(args);
		assertThrows(IllegalStateException.class, () -> {
			parser.getFromWho();
		});
	}

	// Testo toWho in casi eccezionali
	@Test
	void badToWho() {
		final String[] args = { "-w", ".//res//ingsw.zip", "-m" };
		parser = new CommandParser(args);
		assertEquals("", parser.getToWho());
	}

	// Testo toWho in caso outOfBound
	@Test
	void toWhoOutOfBound() {
		final String[] args = { "-w", ".//res//ingsw.zip", "-m", "to" };
		parser = new CommandParser(args);
		assertThrows(IllegalStateException.class, () -> {
			parser.getToWho();
		});
	}

	// Testo "in what" in casi eccezionali
	@Test
	void badInWhat() {
		final String[] args = { "-w", ".//res//ingsw.zip", "-m" };
		parser = new CommandParser(args);
		assertEquals("", parser.getInWhat());
	}

	// Testo "in what" in caso outOfBound
	@Test
	void inWhatOutOfBound() {
		final String[] args = { "-w", ".//res//ingsw.zip", "-m", "in" };
		parser = new CommandParser(args);
		assertThrows(IllegalStateException.class, () -> {
			parser.getInWhat();
		});
	}

	// Testo from e to in contemporanea
	@Test
	void fromToTogether() {
		final String[] args = { "-w", ".//res//ingsw.zip", "-m", "from", "Lanubile", "to", "Lanubile" };
		parser = new CommandParser(args);
		assertThrows(IllegalStateException.class, () -> {
			interpreter.executeCommands(parser, dataCtr);
		});
	}

	// Testo l'illegalState se non riconosco cosa interpretare
	@Test
	void IllegalStateAfterMentions() {
		final String[] args = { "-w", ".//res//ingsw.zip" };
		parser = new CommandParser(args);
		assertThrows(IllegalStateException.class, () -> {
			interpreter.executeCommands(parser, dataCtr);
		});
	}

	// Testo attività del baseArgs
	@Test
	void baseArgsActive() {
		final String[] args = { "help" };
		parser = new CommandParser(args);
		assertEquals(true, parser.getBaseArgs().isActive());
	}

	// Testo validità del CommWorkspace
	@Test
	void commWorkspaceValid() {
		final String[] args = { "-w", "", "-m" };
		parser = new CommandParser(args);
		assertEquals(false, parser.getCommWorkspace().isValidWorkspace());
	}

	// Testo validità del channelFilter
	@Test
	void commWorkspaceFilterValid() {
		final String[] args = { "-w", "", "-m" };
		parser = new CommandParser(args);
		assertEquals(false, parser.getCommWorkspace().isValidFilter());
	}

	// Testo validità del channelFilter
	@Test
	void commWorkspaceFilterValid2() {
		final String[] args = { "-w", ".//res//ingsw.zip", "-uc", "" };
		parser = new CommandParser(args);
		assertEquals(false, parser.getCommWorkspace().isValidFilter());
	}

	// Testo validità del channelFilter
	@Test
	void commWorkspaceFilterValid3() {
		final String[] args = { "-w", ".//res//ingsw.zip", "-uc", "general" };
		parser = new CommandParser(args);
		assertEquals(true, parser.getCommWorkspace().isValidFilter());
	}

	// Testo validità del mentionparams
	@Test
	void commWorkspaceMentionParamsValid() {
		final String[] args = { "-w", ".//res//ingsw.zip", "-m", "from", "Lanubile", "in", "general" };
		final String[] mentionParams = { "from", "Lanubile", "in", "general" };
		parser = new CommandParser(args);

		assertArrayEquals(mentionParams, parser.getCommWorkspace().getMentionParams());
	}

	// Testo nullità del mentionparams
	@Test
	void commWorkspaceNull() {
		final String[] args = { "-w", ".//res//ingsw.zip" };
		parser = new CommandParser(args);

		assertEquals(false, parser.mentions());
	}

	// Testo errori nel from() interfaccia
	@Test
	void fromInterfaceTest() {
		final String[] args = { "-w", ".//res//ingsw.zip" };
		parser = new CommandParser(args);

		assertEquals(false, parser.mentions());
	}

	// Testo errori nel fromWho() interfaccia
	@Test
	void fromWhoInterfaceTest() {
		final String[] args = { "-w", ".//res//ingsw.zip", "-m", "from", "Lanubile" };
		final String[] badArgs = { "" };
		parser = new CommandParser(args);
		parser.getCommWorkspace().setMentionParams(badArgs);
		
		assertEquals("", parser.getFromWho());
	}
	
	// Testo errori nel fromWho() interfaccia
	@Test
	void fromWhoInterfaceTest2() {
		final String[] args = { "-w", ".//res//ingsw.zip", "-m", "from", "Lanubile" };
		final String[] badArgs = { "", "pippo" };
		parser = new CommandParser(args);
		parser.getCommWorkspace().setMentionParams(badArgs);
		
		assertEquals("", parser.getFromWho());
	}
	
	// Testo errori nel fromWho() interfaccia
	@Test
	void fromWhoInterfaceTest3() {
		final String[] args = { "-w", ".//res//ingsw.zip", "-m", "from", "Lanubile" };
		final String[] badArgs = { "from" };
		parser = new CommandParser(args);
		parser.getCommWorkspace().setMentionParams(badArgs);
		
		assertThrows(IllegalStateException.class, () -> {parser.getFromWho();});
	}
	
	// Testo errori nel fromWho() interfaccia
	@Test
	void fromWhoInterfaceTest4() {
		final String[] args = { "-w", ".//res//ingsw.zip", "-m", "from", "Lanubile" };
		final String[] badArgs = { "from", "pippo" };
		parser = new CommandParser(args);
		parser.getCommWorkspace().setMentionParams(badArgs);
		
		assertEquals("pippo", parser.getFromWho());
	}
	
	// Testo errori nel toWho() interfaccia
	@Test
	void toWhoInterfaceTest() {
		final String[] args = { "-w", ".//res//ingsw.zip", "-m", "to", "Lanubile" };
		final String[] badArgs = { "", "pippo" };
		parser = new CommandParser(args);
		parser.getCommWorkspace().setMentionParams(badArgs);
		
		assertEquals("", parser.getToWho());
	}
	
	// Testo help da main
	@Test
	void mainHelpTest() {
		final String[] args = {};

		System.setOut(newOut1);
		AppMain.main(args);

		System.setOut(newOut2);
		dataCtr.showHelp();

		final byte[] temp1 = newConsole1.toByteArray();
		final byte[] temp2 = newConsole1.toByteArray();
		Arrays.sort(temp1);
		Arrays.sort(temp2);

		final String out1 = new String(temp1, StandardCharsets.UTF_8);
		final String out2 = new String(temp2, StandardCharsets.UTF_8);
		assertEquals(out1.hashCode(), out2.hashCode());
	}

	// Testo "getWorkspaceName" di zipParser
	@Test
	void getWorkspaceNameTest() throws ZipException, IOException {
		final String path = ".//res//ingsw.zip";
		ZipParser zipper = new ZipParser();
		zipper.load(path);
		assertEquals(zipper.getWorkspaceName(), "ingsw");
	}

	// Testo "setWorkspaceName" di zipParser
	@Test
	void setWorkspaceNameTest() throws ZipException, IOException {
		final String path = ".//res//ingsw.zip";
		ZipParser zipper = new ZipParser();
		zipper.load(path);
		zipper.setWorkspaceName("pippo");
		assertEquals(zipper.getWorkspaceName(), "pippo");
	}

	// Testo "hasLoaded" di zipParser
	@Test
	void hasLoadedTest() throws ZipException, IOException {
		final String path = ".//res//ingsw.zip";
		ZipParser zipper = new ZipParser();
		zipper.load(path);
		assertEquals(zipper.hasLoaded(), true);
	}

	// Testo "hasLoaded" di zipParser -false
	@Test
	void hasLoadedFalseTest() throws ZipException, IOException {
		final String path = ".//res//ingsw.zip";
		ZipParser zipper = new ZipParser();
		zipper.load(path);
		zipper.setWorkspaceName("");
		assertEquals(zipper.hasLoaded(), false);
	}

	// Testo se zipParser carica qualcosa anche con zip fasulli
	@Test
	void fakeZipperTest() throws ZipException, IOException {
		final String path = ".//res//ingsw_solo_logs.zip";
		ZipParser zipper = new ZipParser();
		assertThrows(ZipException.class, () -> {
			zipper.load(path);
		});
	}
}
