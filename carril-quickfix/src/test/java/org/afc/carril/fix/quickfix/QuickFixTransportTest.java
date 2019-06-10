package org.afc.carril.fix.quickfix;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

import org.afc.carril.fix.quickfix.QuickFixSettings;
import org.afc.carril.fix.quickfix.QuickFixTransport;
import org.afc.carril.fix.quickfix.SchemaBaseQuickFixConverter;
import org.afc.carril.fix.quickfix.embedded.EmbeddedQuickFixEngine;
import org.afc.carril.fix.quickfix.listener.TestListener;
import org.afc.carril.fix.quickfix.model.Logon;
import org.afc.carril.fix.quickfix.model.Quote;
import org.afc.carril.fix.quickfix.model.QuoteRequest;
import org.afc.carril.fix.quickfix.model.Symbol;
import org.afc.carril.message.FixMessage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import org.afc.logging.SDC;
import org.afc.util.JUnitUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ActiveProfiles({ "test", "sg", "default", "sg1", "debug" })
public class QuickFixTransportTest {

	private static final int ACCEPTOR = 0;
	
	private static final int INITIATOR = 1;
	
	@Rule
	public TestName name = new TestName();

	private EmbeddedQuickFixEngine acceptor;

	private EmbeddedQuickFixEngine initiator;
	
	@Autowired
	@Qualifier("acceptorTransport")
	private QuickFixTransport acceptorTransport; 
	
	@Autowired
	@Qualifier("initiatorTransport")
	private QuickFixTransport initiatorTransport;
	
	private TestListener[][] listeners = new TestListener[2][2];
	
	@BeforeClass
	public static void setUpBeforeClass() {
	}

	@AfterClass
	public static void tearDownAfterClass() {
	}

	@Before
	public void setUp() throws Exception {
		JUnitUtil.startCurrentTest(getClass(), name);
		for (int i = 0; i < listeners.length; i++) {
			for (int j = 0; j < listeners[i].length; j++) {
				listeners[i][j] = new TestListener();
			}
		}

	}
	
	@After
	public void tearDown() throws Exception {
		if (initiator != null) {
			initiator.stop();
		}
		if (acceptor != null) {
			acceptor.stop();
		}
		JUnitUtil.endCurrentTest(getClass(), name);
	}

	@Test
	public void testInitiator() throws Exception {
		try {
			SDC.set("ACCEP");
			acceptor = new EmbeddedQuickFixEngine(
				"acceptor.yml", 
				"carril.quickfix.acceptor", 
				new SchemaBaseQuickFixConverter("src/test/resources/schema/issuer.xml")
			);
			acceptor.add("FIX.4.4:SELL0-BUY", listeners[ACCEPTOR][0], FixMessage.class);
			acceptor.add("FIX.4.4:SELL1-BUY", listeners[ACCEPTOR][1], FixMessage.class);
			acceptor.start();
			acceptorTransport = acceptor.getTransport();
			
			SDC.set("INITI");
			initiatorTransport.init();
			initiatorTransport.subscribe("FIX.4.4:BUY-SELL0", listeners[INITIATOR][0], FixMessage.class);
			initiatorTransport.subscribe("FIX.4.4:BUY-SELL1", listeners[INITIATOR][1], FixMessage.class);
			initiatorTransport.start();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		JUnitUtil.sleep(2000);
		
		SDC.set("QRID1");
		initiatorTransport.publish("FIX.4.4:BUY-SELL1", new QuoteRequest()
			.setQuoteReqID("QRID-1")
			.setSymbols(Arrays.asList(
				new Symbol().setSymbol("N/A")
				.setSecurityID("N/A")
				.setSecurityIDSource("4")
				.setSecurityDesc("ELN")
				.setEncodedSecurityDescLen(7)
				.setEncodedSecurityDesc("<desc/>")
				.setSide('1')
			))
		);
		
		JUnitUtil.sleep(100);

		List<FixMessage> actual00 = JUnitUtil.actual(listeners[ACCEPTOR][0].getMessages());
		List<FixMessage> expect00 = JUnitUtil.expect(Arrays.asList(
			new Logon().setEncryptMethod(0).setHeartBtInt(30).setResetSeqNumFlag(true)
		)); 
		
		
		List<FixMessage> actual01 = JUnitUtil.actual(listeners[ACCEPTOR][1].getMessages());
		List<FixMessage> expect01 = JUnitUtil.expect(Arrays.asList(
			new Logon().setEncryptMethod(0).setHeartBtInt(30).setResetSeqNumFlag(true),
			new QuoteRequest()
			.setQuoteReqID("QRID-1")
			.setSymbols(Arrays.asList(
				new Symbol().setSymbol("N/A")
				.setSecurityID("N/A")
				.setSecurityIDSource("4")
				.setSecurityDesc("ELN")
				.setEncodedSecurityDescLen(7)
				.setEncodedSecurityDesc("<desc/>")
				.setSide('1')
			))
		)); 

		List<FixMessage> actual10 = JUnitUtil.actual(listeners[INITIATOR][0].getMessages());
		List<FixMessage> expect10 = JUnitUtil.expect(Arrays.asList());
		
		List<FixMessage> actual11 = JUnitUtil.actual(listeners[INITIATOR][1].getMessages());
		List<FixMessage> expect11 = JUnitUtil.expect(Arrays.asList(
			new Quote()
			.setQuoteReqID("QRID-1")
			.setQuoteID("100")
			.setSymbol("N/A")
			.setSecurityID("N/A")
			.setSecurityIDSource("4")
			.setEncodedSecurityDescLen(7)
			.setEncodedSecurityDesc("<desc/>")
			.setSide('1')
		)); 
		
		assertThat("acceptor 0 received", actual00, containsInAnyOrder(expect00.toArray()));
		assertThat("acceptor 1 received", actual01, containsInAnyOrder(expect01.toArray()));
		
		assertThat("initiator 0 received", actual10, containsInAnyOrder(expect10.toArray()));
		assertThat("initiator 1 received", actual11, containsInAnyOrder(expect11.toArray()));
		
		acceptorTransport.stop();
		acceptorTransport.dispose();
		initiatorTransport.stop();
		initiatorTransport.dispose();

		
		JUnitUtil.sleep(1000);
	}

	@Test
	public void testAcceptor() throws Exception {
		try {
			SDC.set("ACCEP");
			acceptorTransport.init();
			acceptorTransport.subscribe("FIX.4.4:SELL0-BUY", listeners[ACCEPTOR][0], FixMessage.class);
			acceptorTransport.subscribe("FIX.4.4:SELL1-BUY", listeners[ACCEPTOR][1], FixMessage.class);
			acceptorTransport.start();
			
			SDC.set("INITI");
			initiator = new EmbeddedQuickFixEngine(
				"initiator.yml", 
				"carril.quickfix.initiator", 
				new SchemaBaseQuickFixConverter("src/test/resources/schema/issuer.xml")
			);
			initiator.add("FIX.4.4:BUY-SELL0", listeners[INITIATOR][0], FixMessage.class);
			initiator.add("FIX.4.4:BUY-SELL1", listeners[INITIATOR][1], FixMessage.class);
			initiator.start();
			initiatorTransport = initiator.getTransport();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		JUnitUtil.sleep(2000);
		
		SDC.set("QRID1");
		acceptorTransport.publish("FIX.4.4:SELL1-BUY", new QuoteRequest()
			.setQuoteReqID("QRID-1")
			.setSymbols(Arrays.asList(
				new Symbol().setSymbol("N/A")
				.setSecurityID("N/A")
				.setSecurityIDSource("4")
				.setSecurityDesc("ELN")
				.setEncodedSecurityDescLen(7)
				.setEncodedSecurityDesc("<desc/>")
				.setSide('1')
			))
		);
		
		JUnitUtil.sleep(100);

		List<FixMessage> actual00 = JUnitUtil.actual(listeners[ACCEPTOR][0].getMessages());
		List<FixMessage> expect00 = JUnitUtil.expect(Arrays.asList());
		
		
		List<FixMessage> actual01 = JUnitUtil.actual(listeners[ACCEPTOR][1].getMessages());
		List<FixMessage> expect01 = JUnitUtil.expect(Arrays.asList(
				new Quote()
				.setQuoteReqID("QRID-1")
				.setQuoteID("100")
				.setSymbol("N/A")
				.setSecurityID("N/A")
				.setSecurityIDSource("4")
				.setEncodedSecurityDescLen(7)
				.setEncodedSecurityDesc("<desc/>")
				.setSide('1')
			)); 

		List<FixMessage> actual10 = JUnitUtil.actual(listeners[INITIATOR][0].getMessages());
		List<FixMessage> expect10 = JUnitUtil.expect(Arrays.asList(
				new Logon().setEncryptMethod(0).setHeartBtInt(120).setResetSeqNumFlag(true)
			)); 
		
		List<FixMessage> actual11 = JUnitUtil.actual(listeners[INITIATOR][1].getMessages());
		List<FixMessage> expect11 = JUnitUtil.expect(Arrays.asList(
				new Logon().setEncryptMethod(0).setHeartBtInt(30).setResetSeqNumFlag(true),
				new QuoteRequest()
				.setQuoteReqID("QRID-1")
				.setSymbols(Arrays.asList(
					new Symbol().setSymbol("N/A")
					.setSecurityID("N/A")
					.setSecurityIDSource("4")
					.setSecurityDesc("ELN")
					.setEncodedSecurityDescLen(7)
					.setEncodedSecurityDesc("<desc/>")
					.setSide('1')
				))
			)); 
		
		assertThat("acceptor 0 received", actual00, containsInAnyOrder(expect00.toArray()));
		assertThat("acceptor 1 received", actual01, containsInAnyOrder(expect01.toArray()));
		
		assertThat("initiator 0 received", actual10, containsInAnyOrder(expect10.toArray()));
		assertThat("initiator 1 received", actual11, containsInAnyOrder(expect11.toArray()));
		
		acceptorTransport.stop();
		acceptorTransport.dispose();
		initiatorTransport.stop();
		initiatorTransport.dispose();
		
		JUnitUtil.sleep(1000);
	}
	
	@Configuration
	@EnableAutoConfiguration
	static class BeanConfiguration {

		@Bean
		@ConfigurationProperties("carril.quickfix.initiator")
		public QuickFixSettings initiatorQuickFixSettings() {
			return new QuickFixSettings();
		}
		
		@Bean
		public QuickFixTransport initiatorTransport() {
			QuickFixTransport transport = new QuickFixTransport();
			transport.setName("initiator");
			transport.setConverter(new SchemaBaseQuickFixConverter("src/test/resources/schema/distributor.xml"));
			transport.setSettings(initiatorQuickFixSettings());
			return transport;
		}

		@Bean
		@ConfigurationProperties("carril.quickfix.acceptor")
		public QuickFixSettings acceptorQuickFixSettings() {
			return new QuickFixSettings();
		}
		
		@Bean
		public QuickFixTransport acceptorTransport() {
			QuickFixTransport transport = new QuickFixTransport();
			transport.setName("acceptor");
			transport.setConverter(new SchemaBaseQuickFixConverter("src/test/resources/schema/distributor.xml"));
			transport.setSettings(acceptorQuickFixSettings());
			return transport;
		}
	}
}
