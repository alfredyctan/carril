package org.afc.ferrocarril.text;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.text.ParseException;

import org.afc.ferrocarril.text.BigDecFormat;
import org.afc.test.JUnit4Util;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BigDecFormatTest {

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
	public void testBigDecFormat() {
		JUnit4Util.startCurrentTest(getClass());
		try {
			BigDecimal bigDecimal = (BigDecimal)new BigDecFormat().parseObject("1.22222");
			assertEquals("1.22222", new BigDecFormat().format(bigDecimal));
        } catch (ParseException e) {
	        e.printStackTrace();
	        fail(e.getMessage());
        }
		JUnit4Util.endCurrentTest(getClass());
	}

}
