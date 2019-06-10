package org.afc.carril.fix.quickfix.standalone.fixman;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.concurrent.Executors;

import org.afc.carril.fix.quickfix.standalone.context.EngineProperties;
import org.afc.carril.fix.quickfix.standalone.context.EngineProperties.Fixman;
import org.afc.carril.fix.quickfix.standalone.fixman.RepositoryFixman;
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

public class RepositoryFixmanTest {

	private JUnit4Mockery context;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		JUnitUtil.deleteFile("target/fixman");
		new File("target/fixman/draft").mkdirs();
		context =  new JUnit4Mockery() {{
		    setThreadingPolicy(new Synchroniser());
		}};
	}

	@After
	public void tearDown() throws Exception {
		context.assertIsSatisfied();
	}

	@Test
	public void testSent() throws ConfigError, SessionNotFound, FieldNotFound {
		JUnitUtil.copyFile("src/test/resources/repository/A/A.dat", "target/fixman/draft/A.dat");
		SessionSender sender = context.mock(SessionSender.class);
		context.checking(new Expectations() {{
			exactly(2).of(sender).send(with(any(Message.class)));
		}});

		AutoRepositorySender repositorySender = new AutoRepositorySender(Executors.newSingleThreadExecutor())
			.setDictionary(new DataDictionary("FIX44.xml"))
			.setSender(sender);
		
		RepositoryFixman fixman = new RepositoryFixman(new EngineProperties().setFixman(
			new Fixman()
			.setDraft("target/fixman/draft/A.dat")
			.setError("target/fixman/error")
			.setSent("target/fixman/sent")
			.setBreakPattern("wait=")
			.setBreakInclude(true)
		))
		.setDictionary(new DataDictionary("FIX44.xml"))
		.setRepositorySender(repositorySender);
		
		fixman.collect();
		
		JUnitUtil.sleep(100);
		assertThat("found error file", new File("target/fixman/sent/A.dat").exists(), is(true));
	}

	@Test
	public void testError() throws ConfigError, SessionNotFound, FieldNotFound {
		JUnitUtil.copyFile("src/test/resources/repository/E/E.dat", "target/fixman/draft/E.dat");
		SessionSender sender = context.mock(SessionSender.class);
		context.checking(new Expectations() {{
			oneOf(sender).send(with(any(Message.class)));
			will(throwException(new FieldNotFound("?")));
		}});
		AutoRepositorySender repositorySender = new AutoRepositorySender(Executors.newSingleThreadExecutor())
			.setDictionary(new DataDictionary("FIX44.xml"))
			.setSender(sender);
		RepositoryFixman fixman = new RepositoryFixman(new EngineProperties().setFixman(
			new Fixman()
			.setDraft("target/fixman/draft/E.dat")
			.setError("target/fixman/error")
			.setSent("target/fixman/sent")
			.setBreakPattern("wait=")
			.setBreakInclude(true)
		))
		.setDictionary(new DataDictionary("FIX44.xml"))
		.setRepositorySender(repositorySender);
		
		fixman.collect();
	
		JUnitUtil.sleep(100);
		assertThat("found error file", new File("target/fixman/error/E.dat").exists(), is(true));
	}
}
