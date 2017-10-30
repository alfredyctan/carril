package org.afc.ferrocarril.fix.mapping.impl;

import static org.junit.Assert.assertEquals;

import org.afc.carril.fix.mapping.Getter;
import org.afc.carril.fix.mapping.SessionState;
import org.afc.carril.fix.mapping.impl.StateGetter;
import org.afc.util.JUnit4Util;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StateGetterTest {

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
	public void testGet() {
		JUnit4Util.startCurrentTest(getClass());
		final SessionState state = context.mock(SessionState.class);
		final Getter getter = context.mock(Getter.class);
		final Object input = new Object();

		context.checking(new Expectations() {
			{
				oneOf(getter).get(input);
				will(returnValue("KEY"));

				oneOf(state).get("KEY");
				will(returnValue("V1"));

			}
		});

		StateGetter stateGetter = new StateGetter(state, getter);

		assertEquals("V1", stateGetter.get(input));
		JUnit4Util.endCurrentTest(getClass());
	}
}
