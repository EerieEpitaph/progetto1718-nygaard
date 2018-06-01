package it.uniba.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.zip.ZipException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.uniba.workdata.User;

public class EdgeTester {
	
	static Model mod = null;
	static User u_from = null;  
	static User v_to = null;
	static Edge edgeTest = null; 
	@BeforeAll
	static void Init() throws ZipException, IOException 
	{
		mod = new Model();
		mod.updateModel(".//res//ingsw.zip");
		u_from = mod.getUser("U9P18U17X");
		v_to = mod.getUser("U9NF6NSU8");
		edgeTest = new Edge(u_from,v_to,1);
	}
	
	@Test 
	void createEdgeTestNoParam() 
	{
		assertNotNull(new Edge());
	}
	
	@Test 
	void createEdgeTestWithParam() 
	{
		assertNotNull(new Edge(new Edge(u_from,v_to,1)));
	}
	
	@Test
	void failedEdgeEqualsTest()
	{
		Edge ed = new Edge();
		assertFalse(ed.equals(new Object()));
	}
	
	@Test 
	void successfulEdgeEqualsTest()
	{
		assertTrue(edgeTest.equals(new Edge(u_from,v_to,1)));
	}
	
}
