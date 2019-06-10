package org.afc.carril.fix.quickfix.standalone.util;

import org.afc.carril.fix.quickfix.standalone.util.Serializer;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import org.afc.util.JUnitUtil;

import quickfix.ConfigError;
import quickfix.DataDictionary;

public class SerializerTest {

	@Rule
	public TestName name = new TestName();

	@Before
	public void setUp() throws Exception {
		JUnitUtil.startCurrentTest(getClass(), name);
	}
	
	@After
	public void tearDown() throws Exception {
		JUnitUtil.endCurrentTest(getClass(), name);
	}

	@Test
	public void testWith9With10() throws ConfigError {
		String fix = "8=FIX.4.4|9=49|35=0|34=3|49=BUY|52=20190219-01:56:45.629|56=SELL1|10=050|";
		Serializer.deserialize(fix, new DataDictionary("FIX44.xml"));
	}

	@Test
	public void testWith9Without10() throws ConfigError {
		String fix = "8=FIX.4.4|9=50|35=0|34=3|49=BUY|52=20190219-01:56:45.629|56=SELL1|";
		Serializer.deserialize(fix, new DataDictionary("FIX44.xml"));
	}

	@Test
	public void testWithout9With10() throws ConfigError {
		String fix = "8=FIX.4.4|35=0|34=3|49=BUY|52=20190219-01:56:45.629|56=SELL1|10=050|";
		Serializer.deserialize(fix, new DataDictionary("FIX44.xml"));
	}

	@Test
	public void testWithout9Without10() throws ConfigError {
		String fix = "8=FIX.4.4|35=0|34=3|49=BUY|52=20190219-01:56:45.629|56=SELL1|";
		Serializer.deserialize(fix, new DataDictionary("FIX44.xml"));
	}
}
