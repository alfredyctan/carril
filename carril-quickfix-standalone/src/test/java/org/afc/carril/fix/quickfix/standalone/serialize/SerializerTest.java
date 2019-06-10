package org.afc.carril.fix.quickfix.standalone.serialize;

import static org.afc.carril.fix.quickfix.standalone.serialize.MessageMock.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.afc.carril.fix.quickfix.standalone.util.Serializer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.afc.util.JUnitUtil;

import quickfix.ConfigError;
import quickfix.DataDictionary;

public class SerializerTest {


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
	public void testSerialize() throws ConfigError {
		String actual = JUnitUtil.actual(Serializer.serialize(message()));
		String expect = JUnitUtil.expect(fixString());
		assertThat("serialize", actual, is(equalTo(expect)));
	}

	@Test
	public void testDeserialize() throws ConfigError {
		DataDictionary dd = new DataDictionary("FIX44.xml");
		
		String actual = JUnitUtil.actual(Serializer.deserialize(fixString(), dd).toString());
		String expect = JUnitUtil.expect(message().toString());

		assertThat("deserialize", actual, is(equalTo(expect)));
	}
}
