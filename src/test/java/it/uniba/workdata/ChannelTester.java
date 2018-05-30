package it.uniba.workdata;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ChannelTester {
	static Channel chan;
	static List<String> members;

	@BeforeAll
	static void setUpAll()
	{
		members = new ArrayList<String>();
		members.add("Ottavio");
		members.add("Giovanni");
		members.add("Nicola");
		members.add("Valentina");
		// per il test l'id è una stringa personalizzata
	    	chan = new Channel("#idNygaard",2018L,"phonixCreatore",members,"Giovanni");
	}

	@Test
	void channelGetterTest() {
			assertAll("Check Channel data", () -> {
			assertEquals("#idNygaard",chan.getId());
			assertEquals(new Long(2018L),chan.getDateCreation());
			assertEquals("phonixCreatore", chan.getCreator());
			assertEquals("Giovanni", chan.getName());
		});
	}
}
