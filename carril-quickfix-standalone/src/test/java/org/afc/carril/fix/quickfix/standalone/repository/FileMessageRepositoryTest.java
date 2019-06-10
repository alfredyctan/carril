package org.afc.carril.fix.quickfix.standalone.repository;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.afc.carril.fix.quickfix.standalone.repository.FileMessageRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import org.afc.util.JUnitUtil;

public class FileMessageRepositoryTest {


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
	public void testReturnLineFeed() {
		FileMessageRepository repository = new FileMessageRepository("src/test/resources/repository/N/N.dat");

		for (int j = 0; j < msgRN().length; j++) {
			String actual = JUnitUtil.actual(repository.next());
			String expect = JUnitUtil.expect(msgRN()[j]);
			assertThat("1 loop of msg " + j, actual, is(equalTo(expect)));
		}
		assertThat("no more msg", JUnitUtil.actual(repository.next()), is(nullValue()));
	}

	@Test
	public void testBreak() {
		FileMessageRepository repository = new FileMessageRepository("src/test/resources/repository/A", "wait=", true);

		for (int j = 0; j < msg8().length; j++) {
			String actual = JUnitUtil.actual(repository.next());
			String expect = JUnitUtil.expect(msg8()[j]);
			assertThat("1 loop of msg " + j, actual, is(equalTo(expect)));
		}
		assertThat("no more msg", JUnitUtil.actual(repository.next()), is(nullValue()));
	}

	@Test
	public void testComment() {
		FileMessageRepository repository = new FileMessageRepository("src/test/resources/repository/C", "wait=", true);

		for (int j = 0; j < msgRN().length; j++) {
			String actual = JUnitUtil.actual(repository.next());
			String expect = JUnitUtil.expect(msgRN()[j]);
			assertThat("1 loop of msg " + j, actual, is(equalTo(expect)));
		}
		assertThat("no more msg", JUnitUtil.actual(repository.next()), is(nullValue()));
	}
	
	private static String[] msgRN() {
		return new String[]{
			"wait=0,next=Y|8=FIX.4.4|9=125|35=R|34=5|49=BUY|52=20190212-08:29:05.170|56=SELL1|131=%{131}|146=1|55=N/A|48=N/A|22=4|107=ELN|350=7|351=<desc>||</desc>|54=1|10=217|",
			"wait=0,next=N|8=FIX.4.4|9=125|35=R|34=6|49=BUY|52=20190212-08:29:05.170|56=SELL1|131=QRID-2|146=1|55=N/A|48=N/A|22=4|107=ELN|350=7|351=<desc>||</desc>|54=1|10=217|"
		};
	}

	private static String[] msg8() {
		return new String[]{
			"wait=0,next=Y|8=FIX.4.4|9=125|35=A|34=7|49=BUY|52=20190212-08:29:05.170|56=SELL1|131=QRID-1|146=1|55=N/A|48=N/A|22=4|107=ELN|350=7|351=<desc>" + System.lineSeparator() + "</desc>|54=1|10=217|",
			"wait=0,next=N|8=FIX.4.4|9=125|35=A|34=8|49=BUY|52=20190212-08:29:05.170|56=SELL1|131=QRID-1|146=1|55=N/A|48=N/A|22=4|107=ELN|350=7|351=<desc>" + System.lineSeparator() + "</desc>|54=1|10=217|"
		};
	}
}
