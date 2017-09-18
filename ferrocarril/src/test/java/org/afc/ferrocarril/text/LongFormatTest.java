package org.afc.ferrocarril.text;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.afc.ferrocarril.text.LongFormat;
import org.afc.test.JUnit4Util;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LongFormatTest {

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
	public void testLongFormat() {
		JUnit4Util.startCurrentTest(getClass());
		try {
			Long i = (Long)new LongFormat().parseObject("99");
			assertEquals("99", new LongFormat().format(i));
        } catch (ParseException e) {
	        e.printStackTrace();
	        fail(e.getMessage());
        }
		JUnit4Util.endCurrentTest(getClass());
	}
}
