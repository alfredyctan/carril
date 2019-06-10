package org.afc.carril.fix.quickfix.standalone.send;

import java.util.concurrent.Executors;

import org.afc.carril.fix.quickfix.standalone.repository.CyclicMessageRepository;
import org.afc.carril.fix.quickfix.standalone.send.AutoRepositorySender;
import org.afc.carril.fix.quickfix.standalone.send.SessionSender;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.afc.util.JUnitUtil;

import quickfix.ConfigError;
import quickfix.DataDictionary;
import quickfix.FieldNotFound;
import quickfix.Message;
import quickfix.SessionNotFound;


public class AutoRepositorySenderTest {

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
			exactly(3).of(sender).send(with(any(Message.class)));
		}});

		CyclicMessageRepository repository = new CyclicMessageRepository("src/test/resources/repository/AT", "wait=", true);
		AutoRepositorySender autoRepositorySender = new AutoRepositorySender(Executors.newSingleThreadExecutor());
		autoRepositorySender.setDictionary(new DataDictionary("FIX44.xml"));
		autoRepositorySender.setSender(sender);

		autoRepositorySender.send(repository, new Message(), () -> {}, e -> {});
		JUnitUtil.sleep(500);
	}
}
