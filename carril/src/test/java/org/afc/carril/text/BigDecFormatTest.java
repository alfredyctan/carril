package org.afc.carril.text;


import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.ParseException;

import org.afc.carril.text.BigDecimalFormat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.afc.util.JUnitUtil;

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
		JUnitUtil.startCurrentTest(getClass());
		try {
			BigDecimal bigDecimal = (BigDecimal)new BigDecimalFormat().parseObject("1.22222");
			assertEquals("1.22222", new BigDecimalFormat().format(bigDecimal));
        } catch (ParseException e) {
	        e.printStackTrace();
	        fail(e.getMessage());
        }
		JUnitUtil.endCurrentTest(getClass());
	}

}
