package org.afc.carril.fix.quickfix.standalone.listener;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.concurrent.Executors;

import org.afc.carril.fix.quickfix.standalone.context.EngineProperties;
import org.afc.carril.fix.quickfix.standalone.context.EngineProperties.AutoResponse;
import org.afc.carril.fix.quickfix.standalone.filter.QuickFixAttributeAccessor;
import org.afc.carril.fix.quickfix.standalone.listener.AutoResponseListener;
import org.afc.carril.fix.quickfix.standalone.send.AutoRepositorySender;
import org.afc.carril.fix.quickfix.standalone.send.SessionSender;
import org.afc.carril.fix.quickfix.standalone.serialize.MessageMock;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.afc.carril.fix.quickfix.message.Crude;
import org.afc.filter.ExpressionAttributeFilterFactory;
import org.afc.filter.QueryAttributeFilterFactory;
import org.afc.util.JUnitUtil;

import quickfix.ConfigError;
import quickfix.DataDictionary;
import quickfix.FieldNotFound;
import quickfix.Message;
import quickfix.SessionNotFound;

public class AutoResponseListenerTest {

	private JUnit4Mockery context;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		context =  new JUnit4Mockery() {{
		    setThreadingPolicy(new Synchroniser());
		}};
	}

	@After
	public void tearDown() throws Exception {
		context.assertIsSatisfied();
	}

	@Test
	public void test() throws ConfigError, SessionNotFound, FieldNotFound {
		SessionSender sender = context.mock(SessionSender.class);
		context.checking(new Expectations() {{
			exactly(2).of(sender).send(with(any(Message.class)));
		}});

		AutoRepositorySender repositorySender = new AutoRepositorySender(Executors.newSingleThreadExecutor())
			.setDictionary(new DataDictionary("FIX44.xml"))
			.setSender(sender);
		AutoResponseListener listener = new AutoResponseListener(
			new EngineProperties().setAutoResponses(Arrays.asList(
				new AutoResponse()
				.setFilter("[%{#35}=R] && [%{#49}=BUY]")
				.setRepository("src/test/resources/repository/A/A.dat")
				.setBreakPattern("wait=")
			)),
			new QueryAttributeFilterFactory<>(new ExpressionAttributeFilterFactory<>(new QuickFixAttributeAccessor()), '[', ']') 
		);
		listener.setRepositorySender(repositorySender);
		
		Crude actual = JUnitUtil.actual(listener.onMessage(new Crude(MessageMock.message())));
		Crude expect = JUnitUtil.expect(null);
		JUnitUtil.sleep(100);
		assertThat("no return value", actual, is(equalTo(expect)));
	}
}
