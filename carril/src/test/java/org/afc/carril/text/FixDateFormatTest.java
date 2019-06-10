package org.afc.carril.text;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;

import org.afc.carril.text.FixDateFormat;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import org.afc.util.JUnitUtil;

public class FixDateFormatTest {

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
	public void testUTCDateFormatParse() {
		LocalDate actual = JUnitUtil.actual(new FixDateFormat().parse("20111104"));
		LocalDate expect = JUnitUtil.expect(LocalDate.parse("2011-11-04"));
		assertThat("parse", actual, is(equalTo(expect)));
	}

	@Test
	public void testUTCDateFormat() {
		String actual = JUnitUtil.actual(new FixDateFormat().format(LocalDate.parse("2011-11-04")));
		String expect = JUnitUtil.expect("20111104");
		assertThat("parse", actual, is(equalTo(expect)));
	}
}
