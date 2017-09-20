package org.afc.ferrocarril.fix.tag;

import static org.junit.Assert.assertEquals;

import org.afc.util.JUnit4Util;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FixMsgTypeTest {

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
	public void testValue() {
		JUnit4Util.startCurrentTest(getClass());
		assertEquals("h", FixMsgType.TYPE_h.id());
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testFromValue() {
		JUnit4Util.startCurrentTest(getClass());
		assertEquals(FixMsgType.TYPE_h, FixMsgType.fromID("h"));
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testToString() {
		JUnit4Util.startCurrentTest(getClass());
		assertEquals("h(Trading Session Status)", FixMsgType.TYPE_h.toString());
		JUnit4Util.endCurrentTest(getClass());
	}

}
