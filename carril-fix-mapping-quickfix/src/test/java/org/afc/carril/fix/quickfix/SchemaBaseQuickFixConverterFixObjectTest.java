package org.afc.carril.fix.quickfix;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.afc.carril.fix.quickfix.SchemaBaseQuickFixConverter;
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

public class SchemaBaseQuickFixConverterFixObjectTest {

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
	public void testParse() {
		try {
			SchemaBaseQuickFixConverter fixConverter = new SchemaBaseQuickFixConverter("../carril-fix-mapping/src/test/resources/schema/conv-schema-sample.xml");
			
			Sample actual = JUnitUtil.actual((Sample)fixConverter.parse(QuickFixMock.messages().get(1), Sample.class));
			Sample expect = JUnitUtil.expect(SampleMock.samples().get(1));
			
			assertThat("The pojo", actual, is(equalTo(expect)));
			
	    } catch (Exception e) {
	        e.printStackTrace();
	        fail(e.getMessage());
	    }
	}

	@Test
	public void testParseDispatch() {
		try {
			SchemaBaseQuickFixConverter fixConverter = new SchemaBaseQuickFixConverter("../carril-fix-mapping/src/test/resources/schema/conv-schema-sample.xml");
			
			Sample actualMsg = JUnitUtil.actual((Sample)fixConverter.parse(QuickFixMock.messages().get(2), Sample.class));
			Sample expectMsg = JUnitUtil.expect(SampleMock.samples().get(2));
			
			assertThat("The pojo", actualMsg, is(equalTo(expectMsg)));
	    } catch (Exception e) {
	        e.printStackTrace();
	        fail(e.getMessage());
	    }
	}
	

	@Test
	public void testFormat() {
		try {
			SchemaBaseQuickFixConverter fixConverter = new SchemaBaseQuickFixConverter("../carril-fix-mapping/src/test/resources/schema/conv-schema-sample.xml");
			
			Message actualMsg = JUnitUtil.actual((Message)fixConverter.format(SampleMock.samples().get(1)));
			Message expectMsg = JUnitUtil.expect(m -> {
				//remove field managed by FIX Engine
				m.getHeader().removeField(FixTag.BeginString.id());
				m.getHeader().removeField(FixTag.MsgSeqNum.id());
				m.getHeader().removeField(FixTag.SendingTime.id());
			}, QuickFixMock.messages().get(1));
			
			QuickFixMock.assertMessage(actualMsg, expectMsg);
	    } catch (Exception e) {
	        e.printStackTrace();
	        fail(e.getMessage());
	    }
	}

	@Test
	public void testFormatDispatch() {
		try {
			SchemaBaseQuickFixConverter fixConverter = new SchemaBaseQuickFixConverter("../carril-fix-mapping/src/test/resources/schema/conv-schema-sample.xml");
			
			Message actualMsg = JUnitUtil.actual((Message)fixConverter.format(SampleMock.samples().get(2)));
			Message expectMsg = JUnitUtil.expect(m -> {
				//remove field managed by FIX Engine
				m.getHeader().removeField(FixTag.BeginString.id());
				m.getHeader().removeField(FixTag.MsgSeqNum.id());
				m.getHeader().removeField(FixTag.SendingTime.id());
			}, QuickFixMock.messages().get(2));
			
			QuickFixMock.assertMessage(actualMsg, expectMsg);
	    } catch (Exception e) {
	        e.printStackTrace();
	        fail(e.getMessage());
	    }
	}
	
}
