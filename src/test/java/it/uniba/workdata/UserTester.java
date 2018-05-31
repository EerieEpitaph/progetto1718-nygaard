package it.uniba.workdata;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserTester {

	static User user1, user2, user3;

	@BeforeAll

	static void setUpAl()

	{

		final User.Profile profile1 = new User.Profile("phDisplay");

		user1 = new User("#IdPhonix", "Nygaard", "joe", "Giovanni", profile1);

		user2 = user1;

		user3 = new User("#IdSigsky", "Nygaard", "joe", "Giovanni", profile1);

	}

	@Test

	void successfulEquals()

	{

		assertTrue(user1.equals(user2));

	}

	@Test

	void failedEquals()

	{
		final Object test = new Object();
		assertFalse(user1.equals(test));

	}

}