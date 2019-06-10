package org.afc.carril.text;


import static org.junit.Assert.*;

import java.text.ParseException;

import org.afc.carril.text.IntegerFormat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.afc.util.JUnitUtil;

public class IntFormatTest {

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
	public void testIntFormat() {
		JUnitUtil.startCurrentTest(getClass());
		try {
			Integer i = (Integer)new IntegerFormat().parseObject("99");
			assertEquals("99", new IntegerFormat().format(i));
        } catch (ParseException e) {
	        e.printStackTrace();
	        fail(e.getMessage());
        }
		JUnitUtil.endCurrentTest(getClass());
	}
}
