package it.uniba.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.*;
import org.junit.Test.None;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.uniba.controller.ExceptionsHandler;
import it.uniba.model.Edge;
import it.uniba.model.Model;

import it.uniba.workdata.User;

public class MentionGraphTest {
	static ArrayList<ArrayList<Edge>> edges = new ArrayList<ArrayList<Edge>>();
	static ArrayList<Edge> ed = new ArrayList<Edge>();

	static Model mod;

	static User u;
	static User v;

	@BeforeAll
	static void Init() throws ZipException, IOException, ExceptionsHandler {
		mod = new Model();
		// se chiamo un assert qui non viene tenuta nessuna traccia nella tb dei test S
		mod.updateModel(".//res//ingsw.zip");
		mod.getMentionGraph().setModel(mod);
		mod.getMentionGraph().generate("");
		// mod.getMentionGraph().parseMessages(mod.getMessages(), mod.getUsers(), "");

		u = mod.getUsers().get("U9P18U17X"); // Manlio Amato
		v = mod.getUsers().get("U9NF6NSU8");
		ed.add(new Edge(u, v, 2));
		v = mod.getUsers().get("U9NBNJFB3");
		ed.add(new Edge(u, v, 2));
		edges.add(ed);
		// to Andrea Di Fonzo
		// ed.clear();
		ed = new ArrayList<Edge>();
		u = mod.getUsers().get("U9NCNLL83"); // Serena DeRuvo
		v = mod.getUsers().get("U9NAWRB2Q"); // Andrea DiFonzo

		ed.add(new Edge(u, v, 1));
		edges.add(ed);
	}

	@Test
	void edgesOutDegreeTest() throws ZipException, IOException {
		u = mod.getUsers().get("U9P18U17X");
		assertEquals(edges.get(0), mod.getMentionGraph().edgesOutDegree(u));
	}

	@Test
	void edgesInDegreeTest() {
		v = mod.getUsers().get("U9NAWRB2Q");
		assertEquals(edges.get(1), mod.getMentionGraph().edgesInDegree(v));
	}

	@Test
	void successfulContainsNodeTest() {
		assertTrue(mod.getMentionGraph().containsNode(u));
	}

	@Test
	void failedContainsNodeTest() {
		assertFalse(mod.getMentionGraph().containsNode(null));
	}

	@Test
	void successfullIsEmptyTest() {
		MentionGraph graphEmpty = new MentionGraph();
		assertTrue(graphEmpty.isEmpty());
	}

	@Test
	void failedfulIsEmptyTest() {
		assertFalse(mod.getMentionGraph().isEmpty());
	}

	@Test
	void successfulThrowOnGenerate() {
		// Create empty Model
		Model testEmptyModel = new Model();
		MentionGraph mgTest = new MentionGraph();
		mgTest.setModel(testEmptyModel);
		assertThrows(ExceptionsHandler.class, () -> {
			mgTest.generate("");
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
			mod.getMentionGraph().generate("");
		} catch (Exception ex) {
			fail("Eccezione catturata, sebbene non me l'aspettassi");
		}
	}

	@Test
	void successfulMentionGraph() {
		assertNotNull(new MentionGraph(mod));
	}

	@Test
	void failedMentionGraph() {
		assertThrows(ExceptionsHandler.class, () -> {
			MentionGraph gr = new MentionGraph();
			gr.generate("");
		});
	}
	
	@Test
	void successModelGetter() {
		MentionGraph mg = new MentionGraph(mod);
		assertEquals(mg.getModel(), mod);
	}
}

