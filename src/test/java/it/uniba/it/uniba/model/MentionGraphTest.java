package it.uniba.it.uniba.model;

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
	static ArrayList<Edge> edges = new ArrayList<Edge>();
	static Model mod = new Model();

	static User u;
	static User v;

	@BeforeAll
	static void Init() throws ZipException, IOException {

		mod.updateModel("//home//phinkie//Downloads//ingsw.zip");
		mod.getMentionGraph().parseMessages(mod.getMessages(), mod.getUsers(), "");
		u = mod.getUsers().get("U9P18U17X");
		v = mod.getUsers().get("U9NF6NSU8");
		edges.add(new Edge(u, v, 2));
		v = mod.getUsers().get("U9NBNJFB3");
		edges.add(new Edge(u, v, 2));
	}

	@Test
	void edgesOutDegreeTest() throws ZipException, IOException {
		assertEquals(edges, mod.getMentionGraph().edgesOutDegree(u));
	}

}
