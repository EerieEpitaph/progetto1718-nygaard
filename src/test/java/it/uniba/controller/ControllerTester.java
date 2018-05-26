package it.uniba.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.zip.ZipException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniba.controller.Controller;
import it.uniba.model.Model;
import it.uniba.parsing.ZipParser;
import it.uniba.view.View;
import it.uniba.workdata.Channel;
import it.uniba.workdata.User;
import picocli.CommandLine.MissingParameterException;

//In questo file di test verifichiamo che il controller, 
//che si occupa di stampare i dati raccolti, riceva ogni volta dati
//integri dallo zipParser 

public class ControllerTester {
	Controller control;
	static DataController dataCtr = new DataController(new Model(), new View());
	static ZipParser zipper;

	PrintStream originalOut = System.out;
	ByteArrayOutputStream newConsole;
	PrintStream newOut;

	@BeforeAll
	static void Init() throws ZipException, IOException {
		String zipPath = ".//res//ingsw.zip";
		zipper = new ZipParser();
		zipper.load(zipPath);
		dataCtr.updateModel(zipPath);
	}

	@BeforeEach
	void setUp() {
		// Inizializzo i buffer di stampa per controllare
		// Eguaglianze tra print()
		newConsole = new ByteArrayOutputStream();
		newOut = new PrintStream(newConsole);
	}

	@AfterEach
	void tearDown() {
		// Reimposto il System.out
		System.setOut(originalOut);
	}

	// Provo ad immettere un path Illegale
	@Test
	void invalidPathTester() throws ZipException, IOException {
		String[] args = { "-w", "§no.zip" };
		control = new Controller();
		assertThrows(IOException.class, () -> {
			control.controlExecuteCLI(args);
		});
	}

	// Provo ad immettere un path mezzo fatto
	@Test
	void goodPathTester() throws ZipException, IOException {
		String[] args = { "-w" };
		control = new Controller();
		assertThrows(MissingParameterException.class, () -> {
			control.controlExecuteCLI(args);
		});
	}

	// Controlliamo se la stampa utenti coincide
	@Test
	void parsedUsersMatchPrintedUsers() throws ZipException, IOException {
		// Mi faccio dare gli utenti trovati dallo zipParser
		Collection<User> users = zipper.getUsers().values();

		// Cambio l'out di sistema e raccolgo la stampa di tutti i membri
		System.setOut(newOut);
		dataCtr.printMembers();

		// Da qui fino al for trasformo l'out di sistema in un vettore di stringhe,
		// dove ogni stringa e' un'intera linea di output su console (nome del membro +
		// username)
		// I replaceAll sono per eliminare i tab e i carriage return
		byte[] temp = newConsole.toByteArray();
		String out = new String(temp, StandardCharsets.ISO_8859_1);
		out = out.replaceAll("\t", " ");
		out = out.replaceAll("\r", "");
		String[] singleUsers = out.split("\n");

		// Il for conferma che ogni utente trovato dallo zipParser sia stato infatti
		// stampato da dataController. Se questo non e' avvenuto, l'assertEquals
		// fallira'
		int i = 0;
		for (User user : users) {
			//Converto le due stringhe in UTF-8
			String expectedUser = user.getRealName() + " @" + user.getName();
			expectedUser = new String(expectedUser.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
			String actualUser = new String(singleUsers[i].getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
			
			assertEquals(expectedUser, actualUser);
			i++;
		}
	}

	// Controlliamo se la stampa canali coincide
	@Test
	void parsedChannelsMatchPrintedChannels() throws ZipException, IOException {
		Collection<Channel> channels = zipper.getChannels().values();

		System.setOut(newOut);
		dataCtr.printChannels();

		byte[] temp = newConsole.toByteArray();
		String out = new String(temp, StandardCharsets.ISO_8859_1);
		out = out.replaceAll("\r", "");
		String[] singleChannels = out.split("\n");

		int i = 0;
		for (Channel channel : channels) {
			String expectedChannel = new String(channel.getName().getBytes(StandardCharsets.US_ASCII), StandardCharsets.ISO_8859_1);
			String actualChannel = new String(singleChannels[i].getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
			assertEquals(expectedChannel, actualChannel);
			i++;
		}
	}
}
