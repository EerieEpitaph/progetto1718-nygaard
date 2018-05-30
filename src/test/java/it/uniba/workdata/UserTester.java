package it.uniba.workdata;

import static org.junit.jupiter.api.Assertions.*;
//import it.uniba.workdata;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserTester {

	static User u1, u2, u3;

	@BeforeAll

	static void setUpAl()

	{

		User.Profile p1 = new User.Profile("phDisplay");

		u1 = new User("#IdPhonix", "Nygaard", "joe", "Giovanni", p1);

		u2 = u1;

		u3 = new User("#IdSigsky", "Nygaard", "joe", "Giovanni", p1);

	}

	@Test

	void successfulEquals()

	{

		assertTrue(u1.equals(u2));

	}

	@Test

	void failedEquals()

	{
		Object test = new Object();
		assertFalse(u1.equals(test));

	}

}