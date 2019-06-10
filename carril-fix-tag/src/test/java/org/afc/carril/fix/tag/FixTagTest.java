package org.afc.carril.fix.tag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.afc.carril.fix.tag.FixTag;
import org.afc.util.JUnit4Util;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FixTagTest {

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
		FixTag tag = FixTag.fromID(8);
		
		switch (tag) {
			case BeginString:
				break;
			default:
				fail("cannot convert enum");
		}
		JUnit4Util.endCurrentTest(getClass());
	}


	@Test
	public void testValueOf() {
		JUnit4Util.startCurrentTest(getClass());
		
		FixTag tag = FixTag.valueOf("BeginString");
		
		switch (tag) {
			case BeginString:
				break;
			default:
				fail("cannot convert enum");
		}
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testValueOfAndThenValue() {
		JUnit4Util.startCurrentTest(getClass());
		
		FixTag tag = FixTag.valueOf("BeginString");
		assertEquals(8, tag.id());

		JUnit4Util.endCurrentTest(getClass());
	}
	
	@Test
	public void testValueNotExist() {
		JUnit4Util.startCurrentTest(getClass());
		
		FixTag tag = FixTag.fromID(9992);
		assertNull(tag);

		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testToString() {
		JUnit4Util.startCurrentTest(getClass());
		
		assertEquals("BeginString(8)", FixTag.BeginString.toString());

		JUnit4Util.endCurrentTest(getClass());
	}

}
