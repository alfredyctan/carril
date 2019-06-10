package org.afc.carril.fix.quickfix;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.afc.carril.fix.quickfix.QuickFixMock;
import org.afc.carril.fix.quickfix.StaticQuickFixConverter;
import org.afc.carril.fix.tag.FixTag;
import org.afc.carril.sample.Sample;
import org.afc.carril.sample.SampleMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import org.afc.util.JUnitUtil;

import quickfix.Message;


public class StaticFixConverterTest {

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
	public void testFormat() {
		StaticQuickFixConverter fixConverter = new StaticQuickFixConverter();
		try {
			Message actual = JUnitUtil.actual((Message) fixConverter.format(SampleMock.samples().get(1)));
			Message expect = JUnitUtil.expect(m -> {
				// remove field managed by FIX Engine
				m.getHeader().removeField(FixTag.MsgSeqNum.id());
				m.getHeader().removeField(FixTag.SendingTime.id());
				m.removeField(9009);
				m.removeField(9010);
				m.removeField(9020);
				m.removeField(9030);
				m.removeField(9040);
				m.removeField(19001);
			}, QuickFixMock.messages().get(1));

			QuickFixMock.assertMessage(actual, expect);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testParse() {
		StaticQuickFixConverter fixConverter = new StaticQuickFixConverter();
		try {
			Sample actual = JUnitUtil.actual((Sample) fixConverter.parse(QuickFixMock.messages().get(1), Sample.class));
			Sample expect = JUnitUtil.expect(s -> {
				s.setMsgID(null);
				s.setFromSchema(null);
				s.setField1(null);
				s.setField2(null);
				s.setConstant(null);
			}, SampleMock.samples().get(1));

			assertThat("The pojo", actual, is(equalTo(expect)));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
