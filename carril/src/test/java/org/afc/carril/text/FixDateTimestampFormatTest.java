package org.afc.carril.text;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;

import org.afc.carril.text.FixDateTimestampFormat;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import org.afc.util.JUnitUtil;

public class FixDateTimestampFormatTest {

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
	public void testUTCDateTimestampFormatParse() {
		LocalDateTime actual = JUnitUtil.actual(new FixDateTimestampFormat().parse("20111104-14:05:11.888"));
		LocalDateTime expect = JUnitUtil.expect(LocalDateTime.parse("2011-11-04T14:05:11.888"));
		assertThat("parse", actual, is(equalTo(expect)));
	}

	@Test
	public void testUTCDateTimestampFormat() {
		String actual = JUnitUtil.actual(new FixDateTimestampFormat().format(LocalDateTime.parse("2011-11-04T14:05:11.888")));
		String expect = JUnitUtil.expect("20111104-14:05:11.888");
		assertThat("parse", actual, is(equalTo(expect)));
	}
}
