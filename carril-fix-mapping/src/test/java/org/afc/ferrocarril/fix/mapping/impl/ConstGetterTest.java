package org.afc.ferrocarril.fix.mapping.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.afc.carril.SomeConstant;
import org.afc.carril.SomeEnum;
import org.afc.carril.fix.mapping.impl.ConstGetter;
import org.afc.carril.transport.TransportException;
import org.afc.util.JUnit4Util;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConstGetterTest {

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
	public void testGetStaticFinalString() {
		JUnit4Util.startCurrentTest(getClass());

		ConstGetter<?> getter = new ConstGetter<Void>("org.afc.ferrocarril.SomeConstant.CONST_A",
				null);
		String expect = SomeConstant.CONST_A;
		String actual = (String) getter.get(null);
		System.out.println("actual:");
		System.out.println(actual);

		assertEquals(expect, actual);
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testGetStaticFinalInt() {
		JUnit4Util.startCurrentTest(getClass());

		ConstGetter<?> getter = new ConstGetter<Void>("org.afc.ferrocarril.SomeConstant.INT_A",
				null);
		int expect = SomeConstant.INT_A;
		int actual = (Integer) getter.get(null);
		System.out.println("actual:");
		System.out.println(actual);

		assertEquals(expect, actual);
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testGetStaticFinalStringFail() {
		JUnit4Util.startCurrentTest(getClass());

		try {
			ConstGetter<?> getter = new ConstGetter<Void>(
					"org.afc.ferrocarril.SomeConstant.NOT_EXIST", null);
			String actual = (String) getter.get(null);
			System.out.println("actual:");
			System.out.println(actual);

		} catch (TransportException te) {
			System.out.println("expected:" + te.getMessage());
		}
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testGetEnum() {
		JUnit4Util.startCurrentTest(getClass());

		ConstGetter<?> getter = new ConstGetter<Void>("org.afc.ferrocarril.SomeEnum.ENUM_A",
				null);
		SomeEnum expect = SomeEnum.ENUM_A;
		Object actual = getter.get(null);
		System.out.println("actual:");
		System.out.println(actual);

		assertEquals(expect, actual);
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testGetEnumFail() {
		JUnit4Util.startCurrentTest(getClass());

		try {
			ConstGetter<?> getter = new ConstGetter<Void>(
					"org.afc.ferrocarril.SomeEnum.ENUM_NOT_EXIST", null);
			Object actual = getter.get(null);
			System.out.println("actual:");
			System.out.println(actual);
			fail("expected to throw TransportException");
		} catch (TransportException te) {
			System.out.println("expected:" + te.getMessage());
		}
		JUnit4Util.endCurrentTest(getClass());
	}

}
