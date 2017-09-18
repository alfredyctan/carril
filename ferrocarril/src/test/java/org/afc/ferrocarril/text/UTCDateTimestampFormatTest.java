package org.afc.ferrocarril.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Date;

import org.afc.ferrocarril.text.UTCDateTimestampFormat;
import org.afc.test.JUnit4Util;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UTCDateTimestampFormatTest {

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
	public void testUTCDateTimestampFormat() {
		JUnit4Util.startCurrentTest(getClass());
		try {
			Date date = new UTCDateTimestampFormat().parse("20111104-14:05:11.888");
			assertEquals("20111104-14:05:11.888", new UTCDateTimestampFormat().format(date));
        } catch (ParseException e) {
	        e.printStackTrace();
	        fail(e.getMessage());
        }
		JUnit4Util.endCurrentTest(getClass());
	}

}
