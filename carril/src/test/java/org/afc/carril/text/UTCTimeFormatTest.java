package org.afc.carril.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Date;

import org.afc.carril.text.UTCTimeFormat;
import org.afc.util.JUnit4Util;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UTCTimeFormatTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUTCTimeFormat() {
		JUnit4Util.startCurrentTest(getClass());
		try {
			Date date = new UTCTimeFormat().parse("14:05:11.888");
			assertEquals("14:05:11.888", new UTCTimeFormat().format(date));
        } catch (ParseException e) {
	        e.printStackTrace();
	        fail(e.getMessage());
        }
		JUnit4Util.endCurrentTest(getClass());
	}

}
