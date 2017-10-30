package org.afc.ferrocarril.fix.mapping.impl;

import static org.junit.Assert.assertEquals;

import org.afc.carril.fix.mapping.impl.SchemaGetter;
import org.afc.util.JUnit4Util;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SchemaGetterTest {

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
	public void testGet() {
		JUnit4Util.startCurrentTest(getClass());
		SchemaGetter getter = new SchemaGetter("TEST");
		assertEquals("TEST", getter.get(new Object()));
		JUnit4Util.endCurrentTest(getClass());
	}

}
