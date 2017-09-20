package org.afc.ferrocarril.fix.mapping.impl;

import static org.junit.Assert.fail;

import org.afc.ferrocarril.FixBaseMessage;
import org.afc.ferrocarril.fix.tag.FixTag;
import org.afc.ferrocarril.message.FixMessage;
import org.afc.util.JUnit4Util;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FixMessageSetterTest {

	Mockery context;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		context = new JUnit4Mockery();
	}

	@After
	public void tearDown() throws Exception {
		context.assertIsSatisfied();
	}

	@Test
	public void testSet() {
		JUnit4Util.startCurrentTest(getClass());
		final Object source = new Object();
		final FixMessage target = new FixBaseMessage();
		final String value = "test";

		FixMessageSetter<Object, FixMessage> setter = new FixMessageSetter<Object, FixMessage>(FixTag.Product.name());

		setter.set(source, target, value);

		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testSetNull() {
		JUnit4Util.startCurrentTest(getClass());
		final Object source = new Object();
		final FixMessage target = new FixBaseMessage();

		FixMessageSetter<Object, FixMessage> setter = new FixMessageSetter<Object, FixMessage>(FixTag.Product.name());
		try {
			setter.set(source, target, null);
			fail("Should throw IllegalArgumentException");
		} catch (IllegalArgumentException iae) {

		} catch (Exception e) {
			e.printStackTrace();
			fail("Should throw IllegalArgumentException " + e.getMessage());
		}

		JUnit4Util.endCurrentTest(getClass());
	}
}
