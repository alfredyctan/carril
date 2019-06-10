package org.afc.carril.fix.quickfix.standalone.listener;

import static org.afc.carril.fix.quickfix.standalone.serialize.MessageMock.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.Arrays;

import org.afc.carril.fix.quickfix.standalone.context.EngineProperties;
import org.afc.carril.fix.quickfix.standalone.context.EngineProperties.Capture;
import org.afc.carril.fix.quickfix.standalone.filter.QuickFixAttributeAccessor;
import org.afc.carril.fix.quickfix.standalone.listener.CaptureListener;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.afc.carril.fix.quickfix.message.Crude;
import org.afc.filter.ExpressionAttributeFilterFactory;
import org.afc.filter.QueryAttributeFilterFactory;
import org.afc.util.JUnitUtil;

public class CaptureListenerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() {
		JUnitUtil.deleteFile("target/captured");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		CaptureListener listener = new CaptureListener(
			new EngineProperties().setCaptures(Arrays.asList(
				new Capture().setFilter("%{#35}=R").setFile("target/captured/%{#49}-%{#56}/%{#8}-%{#35}.log")
			)),
			new QueryAttributeFilterFactory<>(new ExpressionAttributeFilterFactory<>(new QuickFixAttributeAccessor()), '[', ']')
		);
		listener.onMessage(new Crude(message()));

		assertThat("captured", new File("target/captured/BUY-SELL1/FIX.4.4-R.log").length(), is(equalTo(149L + System.lineSeparator().length())));
	}

	@Test
	public void testDefault() {
		CaptureListener listener = new CaptureListener(
			new EngineProperties().setCaptures(Arrays.asList(
				new Capture().setFilter(null).setFile("target/captured/default.log")
			)),
			new QueryAttributeFilterFactory<>(new ExpressionAttributeFilterFactory<>(new QuickFixAttributeAccessor()), '[', ']')
		);
		listener.onMessage(new Crude(message()));

		assertThat("captured", new File("target/captured/default.log").length(), is(equalTo(149L + System.lineSeparator().length())));
	}
}
