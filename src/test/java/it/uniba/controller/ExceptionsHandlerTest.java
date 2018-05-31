package it.uniba.controller;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class ExceptionsHandlerTest {
	@Test
	void successfulExHandler()
	{
		assertNotNull(new ExceptionsHandler());
	}
}
