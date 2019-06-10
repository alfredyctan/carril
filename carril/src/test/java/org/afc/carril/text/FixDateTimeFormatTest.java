package org.afc.carril.text;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;

import org.afc.carril.text.FixDateTimeFormat;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import org.afc.util.JUnitUtil;

public class FixDateTimeFormatTest {

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
	public void testUTCDateTimeFormatParse() {
		LocalDateTime actual = JUnitUtil.actual(new FixDateTimeFormat().parse("20111104-14:05:11"));
		LocalDateTime expect = JUnitUtil.expect(LocalDateTime.parse("2011-11-04T14:05:11"));
		assertThat("parse", actual, is(equalTo(expect)));
	}

	@Test
	public void testUTCDateTimeFormat() {
		String actual = JUnitUtil.actual(new FixDateTimeFormat().format(LocalDateTime.parse("2011-11-04T14:05:11")));
		String expect = JUnitUtil.expect("20111104-14:05:11");
		assertThat("parse", actual, is(equalTo(expect)));
	}
}
