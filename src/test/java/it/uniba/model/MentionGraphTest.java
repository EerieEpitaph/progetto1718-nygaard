package it.uniba.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.uniba.model.Edge;
import it.uniba.model.Model;

import it.uniba.workdata.User;

public class MentionGraphTest {
	static ArrayList<ArrayList<Edge>> edges = new ArrayList<ArrayList<Edge>>();
	static ArrayList<Edge> ed = new ArrayList<Edge>();

	static Model mod = new Model();

	static User u;
	static User v;

	@BeforeAll
	static void Init() throws ZipException, IOException {
		// se chiamo un assert qui non viene tenuta nessuna traccia nella tb dei test S
		mod.updateModel(".//res//ingsw.zip");
		mod.getMentionGraph().parseMessages(mod.getMessages(), mod.getUsers(), "");

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

	// @Test
	// void successfullIsEmptyTest() {
	// // perhè il grafo è stato correttamente caricato quindi è false perche non è
	// vuoto
	// assertTrue(mod.getMentionGraph().isEmpty());
	// }

	@Test
	void failedfulIsEmptyTest() {
		assertFalse(mod.getMentionGraph().isEmpty());
	}

}
