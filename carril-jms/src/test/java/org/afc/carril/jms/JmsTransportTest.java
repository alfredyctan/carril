package org.afc.carril.jms;

import org.afc.carril.transport.TransportListener;
import org.afc.util.JUnit4Util;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JmsTransportTest {

	private JUnit4Mockery mockery;
	
	private JmsTransport send1;
	
	private JmsTransport send2;

	private JmsTransport recv1;
	
	private JmsTransport recv2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		send1 = createJmsTransport();
		send2 = createJmsTransport();
		recv1 = createJmsTransport();
		recv2 = createJmsTransport();
		mockery = new JUnit4Mockery();
	}

	@After
	public void tearDown() throws Exception {
		mockery.assertIsSatisfied();
	}

	@Test
	public void testPubSub1() {
		JUnit4Util.startCurrentTest(getClass());
		final MockMessage message = new MockMessage(); 
		
		TransportListener listener1 = mockery.mock(TransportListener.class, "listener1"); 
		TransportListener listener2 = mockery.mock(TransportListener.class, "listener2"); 
				
		mockery.checking(new Expectations() {{
			allowing(listener1).getExecutorService();
			will(returnValue(null));
			exactly(2).of(listener1).onMessage(message);
			
			allowing(listener2).getExecutorService();
			will(returnValue(null));
			exactly(2).of(listener2).onMessage(message);
		}});
		
		recv1.subscribe("TEST", listener1, MockMessage.class);
		recv2.subscribe("TEST", listener2, MockMessage.class);

		send1.publish("TEST", message);
		send2.publish("TEST", message);
		
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testPubSub2() {
	}

	private static JmsTransport createJmsTransport() {
		JmsTransport transport = new JmsTransport();
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost:61636?broker.persistent=false");
//		transport.setConnectionFactory(connectionFactory);
		
		return transport;
	}
}
