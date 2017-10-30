package org.afc.ferrocarril.fix.mapping.impl;

import org.afc.carril.fix.mapping.Getter;
import org.afc.carril.fix.mapping.SessionState;
import org.afc.carril.fix.mapping.impl.StateSetter;
import org.afc.util.JUnit4Util;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StateSetterTest {

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
		final SessionState state = context.mock(SessionState.class);
		final Getter getter = context.mock(Getter.class);
		final Object source = new Object();
		final Object target = new Object();

		context.checking(new Expectations() {
			{
				oneOf(getter).get(source);
				will(returnValue("KEY"));

				oneOf(state).put("KEY", "V1");

			}
		});

		StateSetter stateSetter = new StateSetter(state, getter);
		stateSetter.set(source, target, "V1");

		JUnit4Util.endCurrentTest(getClass());
	}

}
