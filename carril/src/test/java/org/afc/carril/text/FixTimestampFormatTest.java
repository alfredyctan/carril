package org.afc.carril.text;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalTime;

import org.afc.carril.text.FixTimestampFormat;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import org.afc.util.JUnitUtil;

public class FixTimestampFormatTest {

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
	public void testUTCTimeFormatParse() {
		LocalTime actual = JUnitUtil.actual(new FixTimestampFormat().parse("14:05:11.888"));
		LocalTime expect = JUnitUtil.expect(LocalTime.parse("14:05:11.888"));
		assertThat("parse", actual, is(equalTo(expect)));
	}

	@Test
	public void testUTCTimeFormat() {
		String actual = JUnitUtil.actual(new FixTimestampFormat().format(LocalTime.parse("14:05:11.888")));
		String expect = JUnitUtil.expect("14:05:11.888");
		assertThat("parse", actual, is(equalTo(expect)));
	}
}
