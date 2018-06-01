package it.uniba.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.zip.ZipException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.uniba.controller.ExceptionsHandler;
import it.uniba.model.Edge;
import it.uniba.model.Model;
import it.uniba.workdata.Message;
import it.uniba.workdata.User;

public class MentionGraphTest {
	static ArrayList<ArrayList<Edge>> edges = new ArrayList<ArrayList<Edge>>();
	static ArrayList<Edge> ed = new ArrayList<Edge>();

	static Model mod = null;

	static User u_from = null;
	static User v_to = null;
	final static MentionGraph testerGraph = new MentionGraph();

	@BeforeAll
	static void Init() throws ZipException, IOException, ExceptionsHandler {
		mod = new Model();
		// se chiamo un assert qui non viene tenuta nessuna traccia nella tb dei test S
		mod.updateModel(".//res//ingsw.zip");

		testerGraph.generate("", mod.getMessages(), mod.getUsers());

		u_from = mod.getUser("U9P18U17X"); // Manlio Amato
		v_to = mod.getUser("U9NF6NSU8");
		ed.add(new Edge(u_from, v_to, 2));
		v_to = mod.getUser("U9NBNJFB3");
		ed.add(new Edge(u_from, v_to, 2));
		edges.add(ed);
		// to Andrea Di Fonzo
		// ed.clear();
		ed = new ArrayList<Edge>();
		u_from = mod.getUser("U9NCNLL83"); // Serena DeRuvo
		v_to = mod.getUser("U9NAWRB2Q"); // Andrea DiFonzo

		ed.add(new Edge(u_from, v_to, 1));
		edges.add(ed);
	}

	@Test
	void edgesOutDegreeTest() throws ZipException, IOException {
		u_from = mod.getUser("U9P18U17X");
		assertEquals(edges.get(0), testerGraph.edgesOutDegree(u_from));
	}

	@Test
	void edgesInDegreeTest() throws ExceptionsHandler {
		v_to = mod.getUser("U9NAWRB2Q");
		assertEquals(edges.get(1), testerGraph.edgesInDegree(v_to));
	}

	@Test
	void successfulContainsNodeTest() throws ExceptionsHandler {
		assertTrue(testerGraph.containsNode(u_from));
	}

	@Test
	void failedContainsNodeTest() {
		assertThrows(ExceptionsHandler.class, () -> {
			testerGraph.containsNode(null);
		});
	}

	@Test
	void successfullIsEmptyTest() {
		final MentionGraph graphEmpty = new MentionGraph();
		assertTrue(graphEmpty.isEmpty());
	}

	@Test
	void failedIsEmptyTest() {
		assertFalse(testerGraph.isEmpty());
	}

	@Test
	void successfulThrowOnGenerate() {
		final MentionGraph graphTest = new MentionGraph();
		// graphTest.setModel(testEmptyModel);
		assertThrows(ExceptionsHandler.class, () -> {
			graphTest.generate(null, null, null);
		});
	}

	@Test
	void failedThrowGenerateOnFilledModel() {
		/*
		 * Since every exception derives from Exception, we use a catch block to catch
		 * any of them that gets thrown If an exception gets thrown somehow (it should
		 * not with a filled Model), the test fails
		 */
		try {
			final MentionGraph graphsucc = new MentionGraph();
			graphsucc.generate("", mod.getMessages(), mod.getUsers());
		} catch (Exception ex) {
			fail("Exception catched.");
		}
	}

	@Test
	void successfulMentionGraph() {
		assertNotNull(new MentionGraph());
	}

	@Test
	void successfulThrowOnEmptyGraph() {
		assertThrows(ExceptionsHandler.class, () -> {
			final MentionGraph graph = new MentionGraph();
			final HashMap<String, ArrayList<Message>> messEmpty = new HashMap<String, ArrayList<Message>>();
			final HashMap<String, User> usersEmpty = new HashMap<String, User>();
			graph.generate(null, messEmpty, usersEmpty);
		});
	}

}
