package org.afc.carril.fix.quickfix.standalone.filter;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.afc.carril.fix.quickfix.standalone.filter.QuickFixAttributeAccessor;
import org.afc.carril.fix.quickfix.standalone.serialize.MessageMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import org.afc.util.JUnitUtil;

public class QuickFixAttributeAccessorTest {

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
	public void testTag() {
		QuickFixAttributeAccessor accessor = new QuickFixAttributeAccessor();
		String actual = JUnitUtil.actual(accessor.getString(MessageMock.message3(), "%{ #35 }"));
		String expect = JUnitUtil.expect("R");
		assertThat(actual, is(equalTo(expect)));
	}
	
	@Test
	public void testGroup() {
		QuickFixAttributeAccessor accessor = new QuickFixAttributeAccessor();
		String actual = JUnitUtil.actual(accessor.getString(MessageMock.message3(), "%{ #146.1.107 }"));
		String expect = JUnitUtil.expect("FCN");
		assertThat(actual, is(equalTo(expect)));
	}
	
	@Test
	public void testString() {
		QuickFixAttributeAccessor accessor = new QuickFixAttributeAccessor();
		String actual = JUnitUtil.actual(accessor.getString(MessageMock.message3(), "%{$xpath(#146.1.351:/FCN/Underlyings/Underlying[1]/ID)}"));
		String expect = JUnitUtil.expect("0001.HK");
		assertThat(actual, is(equalTo(expect)));
	}

	@Test
	public void testDate() {
		QuickFixAttributeAccessor accessor = new QuickFixAttributeAccessor();
		String actual = JUnitUtil.actual(accessor.getString(MessageMock.message3(), "%{$xpath(#146.1.351:/FCN/DeliveryDate)}"));
		String expect = JUnitUtil.expect("2016-02-29");
		assertThat(actual, is(equalTo(expect)));
	}
}
