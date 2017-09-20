package org.afc.ferrocarril.fix.mapping.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.afc.util.JUnit4Util;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SimpleSessionStateTest {

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
	public void testGetPut() {
		JUnit4Util.startCurrentTest(getClass());
		SimpleSessionState state = new SimpleSessionState();
		assertNull(state.get("KEY1"));

		state.put("KEY1", "V1");
		state.put("KEY2", "V2");
		assertEquals("V1", state.get("KEY1"));
		assertEquals("V2", state.get("KEY2"));
		JUnit4Util.endCurrentTest(getClass());
	}
}