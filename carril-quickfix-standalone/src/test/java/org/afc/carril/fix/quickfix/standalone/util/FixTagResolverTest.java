package org.afc.carril.fix.quickfix.standalone.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

import org.afc.carril.fix.quickfix.standalone.serialize.MessageMock;
import org.afc.carril.fix.quickfix.standalone.util.FixTagResolver;
import org.afc.carril.fix.quickfix.standalone.util.modifier.ModifierContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import org.afc.AFCException;
import org.afc.util.JUnitUtil;


public class FixTagResolverTest {

	@Rule
	public TestName name = new TestName();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		JUnitUtil.startCurrentTest(getClass(), name);
	}

	@After
	public void tearDown() throws Exception {
		JUnitUtil.endCurrentTest(getClass(), name);
	}

	@Test
	public void testSimple() {
		String in = "8=FIX.4.4|49=%{#49}|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|49=BUY|");
		assertThat("simple", actual, is(equalTo(expect)));
	}

	@Test
	public void testSimple2() {
		String in = "8=FIX.4.4|49=%{#49}|131=%{#131}|146=2|10=%{#9001}|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|49=BUY|131=QRID-1|146=2|10=217|");
		assertThat("simple 2", actual, is(equalTo(expect)));
	}

	@Test
	public void testSimpleUnresolved() {
		String in = "8=FIX.4.4|49=%{#49}|131=%{1301}|146=2|10=%{#9001}|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|49=BUY|131=1301|146=2|10=217|");
		assertThat("simple 2", actual, is(equalTo(expect)));
	}

	@Test
	public void testSimpleWrongSyntax() {
		try {
			String in = "8=FIX.4.4|49=%{49|131=%{1301}|146=2|10=%{10}|";
			JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
			fail("should has exception");
		} catch (AFCException e) {
			System.out.println("expected to caught exception");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testNested() {
		String in = "8=FIX.4.4|49=%{%{%{%{#49}%{#131}}}%{%{%{#49}%{#146.1.107}}}}|131=%{#131}|146=2|10=%{#9001}|";
		String actual = FixTagResolver.resolve(in, MessageMock.message3());
		String expect = JUnitUtil.expect("8=FIX.4.4|49=BUYQRID-1BUYFCN|131=QRID-1|146=2|10=217|");
		assertThat("Nested", actual, is(equalTo(expect)));
		JUnitUtil.actual(actual);
	}

	@Test
	public void testRepeatingGroup() {
		String in = "8=FIX.4.4|131=%{#131}|146=2|107=%{#146.1.107}|350=7|107=%{#146.2.107}|350=7|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|131=QRID-1|146=2|107=FCN|350=7|107=ELN|350=7|10=053|");
		assertThat("repeating group", actual, is(equalTo(expect)));
	}
	
	@Test
	public void testScale() {
		String in = "8=FIX.4.4|9=184|35=R|131=%{$scale(11.54, 5)}|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|131=11.54000|146=2|10=053|");
		assertThat("scale", actual, is(equalTo(expect)));
	}

	@Test
	public void testScaleAuto() {
		String in = "8=FIX.4.4|9=184|35=R|131=%{$scale(11.54000, STZ)}|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|131=11.54|146=2|10=053|");
		assertThat("scale", actual, is(equalTo(expect)));
	}

	@Test
	public void testScaleAutoWithWhiteSpace() {
		String in = "8=FIX.4.4|9=184|35=R|131=%{ $scale ( 11.54000 , STZ ) }|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|131=11.54|146=2|10=053|");
		assertThat("scale", actual, is(equalTo(expect)));
	}
	
	@Test
	public void testRandom100() {
		String in = "8=FIX.4.4|9=184|35=R|131=%{$rand(100)}|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4\\|9=184\\|35=R\\|131=\\d*?\\.\\d\\d\\d\\d\\|146=2\\|10=053\\|");
		assertThat("random number", Pattern.compile(expect).matcher(actual).matches(), is(true));
	}

	@Test
	public void testRandom1To100() {
		String in = "8=FIX.4.4|9=184|35=R|131=%{$rand(1~100)}|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4\\|9=184\\|35=R\\|131=\\d*?\\.\\d\\d\\d\\d\\|146=2\\|10=053\\|");
		assertThat("random number", Pattern.compile(expect).matcher(actual).matches(), is(true));
	}
	
	@Test
	public void testRandom1To100With4DecimalPlace() {
		String in = "8=FIX.4.4|9=184|35=R|131=%{$rand(1~100:4)}|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4\\|9=184\\|35=R\\|131=\\d*?\\.\\d\\d\\d\\d\\|146=2\\|10=053\\|");
		assertThat("random number", Pattern.compile(expect).matcher(actual).matches(), is(true));
	}

	@Test
	public void testRandom1To100With4DecimalPlaceWithWhiteSpace() {
		String in = "8=FIX.4.4|9=184|35=R|131=%{ $rand ( 1~100:4 ) }|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4\\|9=184\\|35=R\\|131=\\d*?\\.\\d\\d\\d\\d\\|146=2\\|10=053\\|");
		assertThat("random number", Pattern.compile(expect).matcher(actual).matches(), is(true));
	}
	
	@Test
	public void testAddConstant() {
		String in = "8=FIX.4.4|9=184|35=R|131=%{%{#9001} + 1}|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|131=218|146=2|10=053|");
		assertThat("add", actual, is(equalTo(expect)));
	}

	@Test
	public void testArthTag() {
		String in = "8=FIX.4.4|9=184|35=R|131=%{%{#9001} + %{#9001}}|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|131=434|146=2|10=053|");
		assertThat("add", actual, is(equalTo(expect)));
	}


	@Test
	public void testArthRand() {
		String in = "8=FIX.4.4|9=184|35=R|131=%{%{#9001} + %{$scale(%{$rand(10)}, 4)}}|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4\\|9=184\\|35=R\\|131=\\d*?\\.\\d*?\\|146=2\\|10=053\\|");
		assertThat("add random number", Pattern.compile(expect).matcher(actual).matches(), is(true));
	}

	@Test
	public void testRepeatArth() {
		String in = "8=FIX.4.4|9=184|35=R|131=%{%{#9001} + %{#9001} + 1}|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|131=435|146=2|10=053|");
		assertThat("repeated arth", actual, is(equalTo(expect)));
	}
	
	@Test
	public void testConcat() {
		String in = "8=FIX.4.4|9=184|35=R|131=%{#9001} %{#9001}|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|131=217 217|146=2|10=053|");
		assertThat("concat", actual, is(equalTo(expect)));
	}

	@Test
	public void testNestedConcat() {
		String in = "8=FIX.4.4|9=184|35=R|131=%{%{#%{#9000}} %{#9001}}|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|131=N/A 217|146=2|10=053|");
		assertThat("nested concat", actual, is(equalTo(expect)));
	}

	@Test
	public void testLength() {
		String in = "8=FIX.4.4|9=184|35=R|131=%{$len(%{#146.1.351})}|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|131=197|146=2|10=053|");
		assertThat("xpath text default", actual, is(equalTo(expect)));
	}

	@Test
	public void testLengthWithWhiteSpace() {
		String in = "8=FIX.4.4|9=184|35=R|131=%{ $len (%{#146.1.351}) }|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|131=197|146=2|10=053|");
		assertThat("xpath text default", actual, is(equalTo(expect)));
	}
	
	@Test
	public void testXPathTextDefault() {
		String in = "8=FIX.4.4|9=184|35=R|131=%{$xpath(#146.1.351:/FCN/Underlyings/Underlying[1]/ID)}|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|131=0001.HK|146=2|10=053|");
		assertThat("xpath text default", actual, is(equalTo(expect)));
	}

	@Test
	public void testXPathNodeDefault() {
		String in = "8=FIX.4.4|9=184|35=R|131=%{$xpath(#146.1.351:/FCN/Underlyings/Underlying[1])}|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|131=<ID>0001.HK</ID>|146=2|10=053|");
		assertThat("xpath node default", actual, is(equalTo(expect)));
	}
	
	@Test
	public void testXPathTextValue() {
		String in = "8=FIX.4.4|9=184|35=R|131=%{$xpath(#146.1.351:v:/FCN/Underlyings/Underlying[1]/ID)}|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|131=0001.HK|146=2|10=053|");
		assertThat("xpath text value", actual, is(equalTo(expect)));
	}

	@Test
	public void testXPathNodeChild() {
		String in = "8=FIX.4.4|9=184|35=R|131=%{$xpath(#146.1.351:v:/FCN/Underlyings/Underlying[1])}|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|131=<ID>0001.HK</ID>|146=2|10=053|");
		assertThat("xpath node child", actual, is(equalTo(expect)));
	}

	@Test
	public void testXPathTextNode() {
		String in = "8=FIX.4.4|9=184|35=R|131=%{$xpath(#146.1.351:f:/FCN/Underlyings/Underlying[1]/ID)}|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|131=<ID>0001.HK</ID>|146=2|10=053|");
		assertThat("xpath text node", actual, is(equalTo(expect)));
	}

	@Test
	public void testXPathNodeFull() {
		String in = "8=FIX.4.4|9=184|35=R|131=%{$xpath(#146.1.351:f:/FCN/Underlyings/Underlying[1])}|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|131=<Underlying><ID>0001.HK</ID></Underlying>|146=2|10=053|");
		assertThat("xpath node full", actual, is(equalTo(expect)));
	}
	@Test
	public void testXPathNodeFullWithWhiteSpace() {
		String in = "8=FIX.4.4|9=184|35=R|131=%{ $xpath ( #146.1.351:f:/FCN/Underlyings/Underlying[1] ) }|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|131=<Underlying><ID>0001.HK</ID></Underlying>|146=2|10=053|");
		assertThat("xpath node full", actual, is(equalTo(expect)));
	}

	
	@Test
	public void testPluginModifier() throws InterruptedException, ExecutionException, TimeoutException {
		FixTagResolver.addPluginModifier(PluginTagModifier.class);
		String in = "8=FIX.4.4|49=%{$double(%{#49})}|";

		// use another thread to force create new delegated modifier in thread local after plugin added 
		FutureTask<String> task = new FutureTask<>(() -> FixTagResolver.resolve(in, MessageMock.message3()));
		new Thread(task).start();
		String actual = JUnitUtil.actual(task.get(1000, TimeUnit.SECONDS));
		String expect = JUnitUtil.expect("8=FIX.4.4|49=BUYBUY|");
		assertThat("simple", actual, is(equalTo(expect)));
	}

	@Test
	public void testDateAddLocalDate() {
		String in = "8=FIX.4.4|9=184|52=%{$date(2019-10-10,-3D)}|35=R|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|52=2019-10-07|35=R|146=2|10=053|");
		assertThat("date add local date", actual, is(equalTo(expect)));
	}

	@Test
	public void testDateAddXMLDateTime() {
		String in = "8=FIX.4.4|9=184|52=%{$date(2019-10-10T20:00:00,-3D)}|35=R|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|52=2019-10-07T20:00:00|35=R|146=2|10=053|");
		assertThat("date add local date", actual, is(equalTo(expect)));
	}

	@Test
	public void testDateAddFixDate() {
		String in = "8=FIX.4.4|9=184|52=%{$date(20191010,-3D)}|35=R|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|52=20191007|35=R|146=2|10=053|");
		assertThat("date add local date", actual, is(equalTo(expect)));
	}

	@Test
	public void testDateAddFixDateTime() {
		String in = "8=FIX.4.4|9=184|52=%{$date(20191010-20:00:00,-3D)}|35=R|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|52=20191007-20:00:00|35=R|146=2|10=053|");
		assertThat("date add local date", actual, is(equalTo(expect)));
	}

	@Test
	public void testDateAddFixDateTimeYear() {
		String in = "8=FIX.4.4|9=184|52=%{$date(20191010-20:00:00,3Y)}|35=R|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|52=20221010-20:00:00|35=R|146=2|10=053|");
		assertThat("date add local date", actual, is(equalTo(expect)));
	}
	
	@Test
	public void testDateAddFixDateTimestamp() {
		String in = "8=FIX.4.4|9=184|52=%{$date(20191010-20:00:00.001,-3D)}|35=R|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|52=20191007-20:00:00.001|35=R|146=2|10=053|");
		assertThat("date add local date", actual, is(equalTo(expect)));
	}

	@Test
	public void testDateAddFixDateTimestampMonth() {
		String in = "8=FIX.4.4|9=184|52=%{$date(20191010-20:00:00.001,3M)}|35=R|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|52=20200110-20:00:00.001|35=R|146=2|10=053|");
		assertThat("date add local date", actual, is(equalTo(expect)));
	}

	@Test
	public void testDateAddFixDateTimestampMonthWithWhiteSpace() {
		String in = "8=FIX.4.4|9=184|52=%{ $date ( 20191010-20:00:00.001 , 3M ) }|35=R|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|52=20200110-20:00:00.001|35=R|146=2|10=053|");
		assertThat("date add local date", actual, is(equalTo(expect)));
	}

	@Test
	public void testDateAddXMLDateTimeHour() {
		String in = "8=FIX.4.4|9=184|52=%{$date(2019-10-10T20:00:00,-3h)}|35=R|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|52=2019-10-10T17:00:00|35=R|146=2|10=053|");
		assertThat("date add local date", actual, is(equalTo(expect)));
	}

	@Test
	public void testDateAddXMLDateTimeMilli() {
		String in = "8=FIX.4.4|9=184|52=%{$date(2019-10-10T20:00:00,-3S)}|35=R|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|52=2019-10-10T19:59:59|35=R|146=2|10=053|");
		assertThat("date add local date", actual, is(equalTo(expect)));
	}
	
	@Test
	public void testDateAddFixDateTimestampHour() {
		String in = "8=FIX.4.4|9=184|52=%{$date(20191010-20:00:00.001,3h)}|35=R|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|52=20191010-23:00:00.001|35=R|146=2|10=053|");
		assertThat("date add local date", actual, is(equalTo(expect)));
	}

	@Test
	public void testDateAddFixDateTimestampMinute() {
		String in = "8=FIX.4.4|9=184|52=%{$date(20191010-20:00:00.001,3m)}|35=R|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|52=20191010-20:03:00.001|35=R|146=2|10=053|");
		assertThat("date add local date", actual, is(equalTo(expect)));
	}

	@Test
	public void testDateAddFixDateTimestampSecond() {
		String in = "8=FIX.4.4|9=184|52=%{$date(20191010-20:00:00.001,3s)}|35=R|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|52=20191010-20:00:03.001|35=R|146=2|10=053|");
		assertThat("date add local date", actual, is(equalTo(expect)));
	}

	@Test
	public void testDateAddFixDateTimestampMilli() {
		String in = "8=FIX.4.4|9=184|52=%{$date(20191010-20:00:00.001,3S)}|35=R|146=2|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|52=20191010-20:00:00.004|35=R|146=2|10=053|");
		assertThat("date add local date", actual, is(equalTo(expect)));
	}
	
	@Test
	public void testLink() {
		String in = "8=FIX.4.4|9=184|35=R|146=2|131=%{$link(classpath://repository/tmpl/desc.tmpl)}|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|146=2|131=<desc><fcn>R</fcn></desc>|10=053|");
		assertThat("file ", actual, is(equalTo(expect)));
	}

	@Test
	public void testLinkWithWhiteSpace() {
		String in = "8=FIX.4.4|9=184|35=R|146=2|131=%{ $link ( classpath://repository/tmpl/desc.tmpl ) }|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|146=2|131=<desc><fcn>R</fcn></desc>|10=053|");
		assertThat("file ", actual, is(equalTo(expect)));
	}

	@Test
	public void testPutGet() {
		String in = "8=FIX.4.4|9=184|35=R|146=2|131=%{$get(KEY)}|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|146=2|131=|10=053|");
		assertThat("file ", actual, is(equalTo(expect)));

		String in1 = "8=FIX.4.4|9=184|35=R|146=2|131=%{$put(KEY,VALUE)}|10=053|";
		String actual1 = JUnitUtil.actual(FixTagResolver.resolve(in1, MessageMock.message3()));
		String expect1 = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|146=2|131=VALUE|10=053|");
		assertThat("file ", actual1, is(equalTo(expect1)));

		String in2 = "8=FIX.4.4|9=184|35=R|146=2|131=%{$get(KEY)}|10=053|";
		String actual2 = JUnitUtil.actual(FixTagResolver.resolve(in2, MessageMock.message3()));
		String expect2 = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|146=2|131=VALUE|10=053|");
		assertThat("file ", actual2, is(equalTo(expect2)));
		ModifierContext.clear();
	}

	@Test
	public void testPutGetWithWhiteSpace() {
		String in = "8=FIX.4.4|9=184|35=R|146=2|131=%{ $get ( KEY ) }|10=053|";
		String actual = JUnitUtil.actual(FixTagResolver.resolve(in, MessageMock.message3()));
		String expect = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|146=2|131=|10=053|");
		assertThat("file ", actual, is(equalTo(expect)));

		String in1 = "8=FIX.4.4|9=184|35=R|146=2|131=%{ $put ( KEY , VALUE ) }|10=053|";
		String actual1 = JUnitUtil.actual(FixTagResolver.resolve(in1, MessageMock.message3()));
		String expect1 = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|146=2|131=VALUE|10=053|");
		assertThat("file ", actual1, is(equalTo(expect1)));

		String in2 = "8=FIX.4.4|9=184|35=R|146=2|131=%{ $get ( KEY ) }|10=053|";
		String actual2 = JUnitUtil.actual(FixTagResolver.resolve(in2, MessageMock.message3()));
		String expect2 = JUnitUtil.expect("8=FIX.4.4|9=184|35=R|146=2|131=VALUE|10=053|");
		assertThat("file ", actual2, is(equalTo(expect2)));
		ModifierContext.clear();
	}
}
