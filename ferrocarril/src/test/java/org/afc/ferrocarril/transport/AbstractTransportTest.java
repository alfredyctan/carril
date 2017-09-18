package org.afc.ferrocarril.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.afc.ferrocarril.transport.ExceptionListener;
import org.afc.ferrocarril.transport.TransportException;
import org.afc.ferrocarril.transport.TransportListener;
import org.afc.ferrocarril.transport.Transport.State;
import org.afc.test.JUnit4Util;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AbstractTransportTest {

	Mockery context;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		context = new JUnit4Mockery();
	}

	@After
	public void tearDown() throws Exception {
		context.assertIsSatisfied();
	}

	
	@Test
	public void testInitStartStopDispose() {
		JUnit4Util.startCurrentTest(getClass());
		MockTransport transport = new MockTransport();
		transport.init();
		transport.start();
		assertEquals("state", State.START, transport.getState());

		transport.stop();
		transport.dispose();
		assertTrue("init", transport.isInit());
		assertTrue("start", transport.isStart());
		assertTrue("stop", transport.isStop());
		assertTrue("dispose", transport.isDispose());
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testWrongCycle() {
		JUnit4Util.startCurrentTest(getClass());
		MockTransport transport = new MockTransport();
		transport.start();
		assertFalse("start", transport.isStart());
		transport.stop();
		assertFalse("stop", transport.isStop());
		transport.dispose();
		assertFalse("dispose", transport.isDispose());
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testPublish() {
		JUnit4Util.startCurrentTest(getClass());
		MockTransport transport = new MockTransport();
		transport.publish("TEST", new MockMessage());
		assertTrue(transport.isPublish());
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testPublishRequest() {
		JUnit4Util.startCurrentTest(getClass());
		MockTransport transport = new MockTransport();
		MockMessage reply = (MockMessage) transport.publishRequest("TEST", new MockMessage(), MockMessage.class);
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testSubscribeBeforeStart() {
		JUnit4Util.startCurrentTest(getClass());
		MockTransport transport = new MockTransport();
		final TransportListener transportListener = context.mock(TransportListener.class);
		MockSubscriber s1 = (MockSubscriber)transport.subscribe("TEST1", transportListener, MockMessage.class);
		MockSubscriber s2 = (MockSubscriber)transport.subscribe("TEST2", transportListener, MockMessage.class);
		transport.init();
		transport.start();
		transport.start();
		
		assertEquals("sub 1", 1, s1.getSubscribeCount());
		assertEquals("sub 2", 1, s2.getSubscribeCount());
		
		assertEquals("unsub 1", 0, s1.getUnsubscribeCount());
		assertEquals("unSub 2", 0, s2.getUnsubscribeCount());
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testSubscribeAfterStart() {
		JUnit4Util.startCurrentTest(getClass());
		MockTransport transport = new MockTransport();
		final TransportListener transportListener = context.mock(TransportListener.class);

		transport.init();
		transport.start();
		MockSubscriber s1 = (MockSubscriber)transport.subscribe("TEST1", transportListener, MockMessage.class);
		MockSubscriber s2 = (MockSubscriber)transport.subscribe("TEST2", transportListener, MockMessage.class);
		
		assertEquals("sub 1", 1, s1.getSubscribeCount());
		assertEquals("sub 2", 1, s2.getSubscribeCount());
		
		assertEquals("unsub 1", 0, s1.getUnsubscribeCount());
		assertEquals("unSub 2", 0, s2.getUnsubscribeCount());
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testUnsubscribe() {
		JUnit4Util.startCurrentTest(getClass());
		MockTransport transport = new MockTransport();
		final TransportListener transportListener = context.mock(TransportListener.class);

		transport.init();
		transport.start();
		MockSubscriber s1 = (MockSubscriber)transport.subscribe("TEST1", transportListener, MockMessage.class);
		MockSubscriber s2 = (MockSubscriber)transport.subscribe("TEST2", transportListener, MockMessage.class);
		
		assertEquals("sub 1", 1, s1.getSubscribeCount());
		assertEquals("sub 2", 1, s2.getSubscribeCount());
		
		transport.unsubscribe("TEST1");
		assertEquals("unsub 1", 1, s1.getUnsubscribeCount());
		assertEquals("unSub 2", 0, s2.getUnsubscribeCount());
		transport.unsubscribe("TEST2");
		assertEquals("unsub 1", 1, s1.getUnsubscribeCount());
		assertEquals("unSub 2", 1, s2.getUnsubscribeCount());
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testAddAndFireTransportExceptionListener() {
		JUnit4Util.startCurrentTest(getClass());
		MockTransport transport = new MockTransport();
		final ExceptionListener<TransportException> listener = context.mock(ExceptionListener.class);
		
		context.checking(new Expectations() {
			{
				exactly(2).of(listener).onException(with(any(TransportException.class)));
			}
		});
		
		transport.addExceptionListener(listener);
		transport.addExceptionListener(listener);
		transport.init();
		transport.start();
		transport.publish(null, null);
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testInitStartSubscribeStopDispose() {
		JUnit4Util.startCurrentTest(getClass());
		MockTransport transport = new MockTransport();
		final TransportListener transportListener = context.mock(TransportListener.class);

		transport.init();
		assertTrue("init", transport.isInit());
		transport.start();
		assertTrue("start", transport.isStart());
		MockSubscriber s1 = (MockSubscriber)transport.subscribe("TEST1", transportListener, MockMessage.class);
		MockSubscriber s2 = (MockSubscriber)transport.subscribe("TEST2", transportListener, MockMessage.class);
		
		assertEquals("sub 1", 1, s1.getSubscribeCount());
		assertEquals("sub 2", 1, s2.getSubscribeCount());
		
		assertEquals("unsub 1", 0, s1.getUnsubscribeCount());
		assertEquals("unSub 2", 0, s2.getUnsubscribeCount());

		transport.stop();
		assertTrue("stop", transport.isStop());
		assertEquals("sub 1", 1, s1.getSubscribeCount());

		assertEquals("sub 1", 1, s1.getSubscribeCount());
		assertEquals("sub 2", 1, s2.getSubscribeCount());
		
		assertEquals("unsub 1", 1, s1.getUnsubscribeCount());
		assertEquals("unSub 2", 1, s2.getUnsubscribeCount());
		transport.dispose();
		assertTrue("dispose", transport.isDispose());

		JUnit4Util.endCurrentTest(getClass());
	}
}
