package org.afc.ferrocarril.fix.mapping.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

import org.afc.ferrocarril.fix.mapping.Getter;
import org.afc.ferrocarril.message.FixMessage;
import org.afc.ferrocarril.sample.FixObject;
import org.afc.util.DateUtil;
import org.afc.util.JUnit4Util;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FixMessageGetterTest {

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
	public void testParse1() {
		JUnit4Util.startCurrentTest(getClass());

		String index = "key/${member1}/${value2}";
		System.out.println(index);

		String[] expectedNames = { "member1", "value2" };
		String[] expectedTokens = { "key/", "/" };

		FixMessageGetter<FixMessage> getter = new FixMessageGetter<FixMessage>(index);
		Getter<?>[] getters = (Getter[]) JUnit4Util.getPrivateMember(getter, "getters");
		String[] tokens = (String[]) JUnit4Util.getPrivateMember(getter, "tokens");
		for (int i = 0; i < getters.length; i++) {
			System.out.print(getters[i] + " ");
		}
		System.out.println();

		for (int i = 0; i < tokens.length; i++) {
			System.out.print(tokens[i] + " ");
		}

		assertEquals(expectedNames[0], JUnit4Util.getPrivateMember(getters[0], "index"));
		assertEquals(expectedNames[1], JUnit4Util.getPrivateMember(getters[1], "index"));

		assertEquals(expectedTokens[0], tokens[0]);
		assertEquals(expectedTokens[1], tokens[1]);

		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testParse2() {
		JUnit4Util.startCurrentTest(getClass());

		String index = "${member1}/key/${member1}.../${value2}";
		System.out.println(index);

		String[] expectedNames = { "member1", "member1", "value2" };
		String[] expectedTokens = { "", "/key/", ".../" };

		FixMessageGetter<FixMessage> getter = new FixMessageGetter<FixMessage>(index);
		Getter<?>[] getters = (Getter[]) JUnit4Util.getPrivateMember(getter, "getters");
		String[] tokens = (String[]) JUnit4Util.getPrivateMember(getter, "tokens");
		for (int i = 0; i < getters.length; i++) {
			System.out.print(getters[i] + " ");
		}
		System.out.println();

		for (int i = 0; i < tokens.length; i++) {
			System.out.print(tokens[i] + " ");
		}

		assertEquals(expectedNames[0], JUnit4Util.getPrivateMember(getters[0], "index"));
		assertEquals(expectedNames[1], JUnit4Util.getPrivateMember(getters[1], "index"));
		assertEquals(expectedNames[2], JUnit4Util.getPrivateMember(getters[2], "index"));
		assertArrayEquals(expectedTokens, tokens);

		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testParse3() {
		JUnit4Util.startCurrentTest(getClass());

		String index = "/${member1}/key/${member1}.../${value2}";
		System.out.println(index);

		String[] expectedNames = { "member1", "member1", "value2" };
		String[] expectedTokens = { "/", "/key/", ".../" };

		FixMessageGetter<FixMessage> getter = new FixMessageGetter<FixMessage>(index);
		Getter<?>[] getters = (Getter[]) JUnit4Util.getPrivateMember(getter, "getters");
		String[] tokens = (String[]) JUnit4Util.getPrivateMember(getter, "tokens");
		for (int i = 0; i < getters.length; i++) {
			System.out.print(getters[i] + " ");
		}
		System.out.println();

		for (int i = 0; i < tokens.length; i++) {
			System.out.print(tokens[i] + " ");
		}

		assertEquals(expectedNames[0], JUnit4Util.getPrivateMember(getters[0], "index"));
		assertEquals(expectedNames[1], JUnit4Util.getPrivateMember(getters[1], "index"));
		assertEquals(expectedNames[2], JUnit4Util.getPrivateMember(getters[2], "index"));
		assertArrayEquals(expectedTokens, tokens);

		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testParse4() {
		JUnit4Util.startCurrentTest(getClass());

		String index = "/${member1}/key/${member1}.../${value2}?";
		System.out.println(index);

		String[] expectedNames = { "member1", "member1", "value2" };
		String[] expectedTokens = { "/", "/key/", ".../", "?" };

		FixMessageGetter<FixMessage> getter = new FixMessageGetter<FixMessage>(index);
		Getter<?>[] getters = (Getter[]) JUnit4Util.getPrivateMember(getter, "getters");
		String[] tokens = (String[]) JUnit4Util.getPrivateMember(getter, "tokens");
		for (int i = 0; i < getters.length; i++) {
			System.out.print(getters[i] + " ");
		}
		System.out.println();

		for (int i = 0; i < tokens.length; i++) {
			System.out.print(tokens[i] + " ");
		}
		System.out.println();

		assertEquals(expectedNames[0], JUnit4Util.getPrivateMember(getters[0], "index"));
		assertEquals(expectedNames[1], JUnit4Util.getPrivateMember(getters[1], "index"));
		assertEquals(expectedNames[2], JUnit4Util.getPrivateMember(getters[2], "index"));
		assertArrayEquals(expectedTokens, tokens);

		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testParse5() {
		JUnit4Util.startCurrentTest(getClass());

		String index = "${member1}";
		System.out.println(index);

		String[] expectedNames = { "member1" };
		String[] expectedTokens = { "" };

		FixMessageGetter<FixMessage> getter = new FixMessageGetter<FixMessage>(index);
		Getter<?>[] getters = (Getter[]) JUnit4Util.getPrivateMember(getter, "getters");
		String[] tokens = (String[]) JUnit4Util.getPrivateMember(getter, "tokens");
		for (int i = 0; i < getters.length; i++) {
			System.out.print(getters[i] + " ");
		}
		System.out.println();

		for (int i = 0; i < tokens.length; i++) {
			System.out.print(tokens[i] + " ");
		}

		assertEquals(expectedNames[0], JUnit4Util.getPrivateMember(getters[0], "index"));
		assertArrayEquals(expectedTokens, tokens);

		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testParseString1() {
		JUnit4Util.startCurrentTest(getClass());

		String index = "key/${decimal}/${string}";
		System.out.println(index);

		String expect = "key/1234567/Dor Lo";

		FixMessageGetter<FixMessage> getter = new FixMessageGetter<FixMessage>(index);
		String actual = getter.get(createFixObject(1)).toString();
		System.out.println(actual);

		assertEquals(expect, actual);

		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testParseString2() {
		JUnit4Util.startCurrentTest(getClass());

		String index = "${decimal}/key/${string}.../${MsgID}";
		System.out.println(index);

		String expect = "1234567/key/Dor Lo.../Dor Duck";

		FixMessageGetter<FixMessage> getter = new FixMessageGetter<FixMessage>(index);
		String actual = getter.get(createFixObject(1)).toString();
		System.out.println(actual);

		assertEquals(expect, actual);
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testParseString3() {
		JUnit4Util.startCurrentTest(getClass());

		String index = "/${decimal}/key/${string}.../${MsgID}";
		System.out.println(index);

		String expect = "/1234567/key/Dor Lo.../Dor Duck";

		FixMessageGetter<FixMessage> getter = new FixMessageGetter<FixMessage>(index);
		String actual = getter.get(createFixObject(1)).toString();
		System.out.println(actual);

		assertEquals(expect, actual);

		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testParseString4() {
		JUnit4Util.startCurrentTest(getClass());

		String index = "/${decimal}/key/${string}.../${MsgID}?";
		System.out.println(index);

		String expect = "/1234567/key/Dor Lo.../Dor Duck?";

		FixMessageGetter<FixMessage> getter = new FixMessageGetter<FixMessage>(index);
		String actual = getter.get(createFixObject(1)).toString();
		System.out.println(actual);

		assertEquals(expect, actual);
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testParseString5() {
		JUnit4Util.startCurrentTest(getClass());
		String index = "${decimal}";
		System.out.println(index);

		String expect = "1234567";

		FixMessageGetter<FixMessage> getter = new FixMessageGetter<FixMessage>(index);
		String actual = getter.get(createFixObject(1)).toString();
		System.out.println(actual);

		assertEquals(expect, actual);
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testParseStringDatetime() {
		JUnit4Util.startCurrentTest(getClass());
		String index = "${datetime}";
		System.out.println(index);

		Date expect = null;
		try {
			expect = DateUtil.parseTimestamp("2011-08-11 19:45:12.345", TimeZone.getTimeZone("UTC"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		FixMessageGetter<FixMessage> getter = new FixMessageGetter<FixMessage>(index);
		Date actual = (Date) getter.get(createFixObject(1));
		System.out.println(actual);

		assertEquals(expect, actual);
		JUnit4Util.endCurrentTest(getClass());
	}

	private static FixObject createFixObject(int seed) {
		FixObject fixObject = new FixObject();
		fixObject.setDecimal(new BigDecimal("1234567"));
		fixObject.setString("Dor Lo");
		fixObject.setMsgID("Dor Duck");
		try {
			fixObject.setDatetime(DateUtil.parseTimestamp("2011-08-11 19:45:12.345", TimeZone.getTimeZone("UTC")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fixObject;
	}

}
