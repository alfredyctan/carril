package org.afc.carril.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.afc.carril.message.GenericMessage;
import org.afc.carril.transport.Transport.State;
import org.afc.carril.transport.mock.MockMessage;
import org.afc.carril.transport.mock.MockPublisher;
import org.afc.carril.transport.mock.MockSubscriber;
import org.afc.carril.transport.mock.MockTransport;
import org.afc.util.JUnit4Util;
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
		MockTransport mockTransport = new MockTransport();
		Transport transport = mockTransport;
		transport.init();
		transport.start();
		assertEquals("state", State.START, transport.getState());

		transport.stop();
		transport.dispose();
		assertTrue("init", mockTransport.isInit());
		assertTrue("start", mockTransport.isStart());
		assertTrue("stop", mockTransport.isStop());
		assertTrue("dispose", mockTransport.isDispose());
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testWrongCycle() {
		JUnit4Util.startCurrentTest(getClass());
		MockTransport mockTransport = new MockTransport();
		Transport transport = mockTransport;
		transport.start();
		assertFalse("start", mockTransport.isStart());
		transport.stop();
		assertFalse("stop", mockTransport.isStop());
		transport.dispose();
		assertFalse("dispose", mockTransport.isDispose());
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testPublish() {
		JUnit4Util.startCurrentTest(getClass());
		MockTransport mockTransport = new MockTransport();
		Transport transport = mockTransport;
		MockPublisher publisher = (MockPublisher)transport.registerPublisher("TEST");
		transport.publish("TEST", new MockMessage());
		assertTrue(publisher.getPublishCount() == 1);
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testPublishRequest() {
		JUnit4Util.startCurrentTest(getClass());
		MockTransport mockTransport = new MockTransport();
		Transport transport = mockTransport;
		MockMessage reply = transport.publishRequest("TEST", new MockMessage(), MockMessage.class);
		assertTrue(reply instanceof MockMessage);
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testSubscribeBeforeStart() {
		JUnit4Util.startCurrentTest(getClass());
		MockTransport mockTransport = new MockTransport();
		Transport transport = mockTransport;
		final TransportListener transportListener = context.mock(TransportListener.class);
		MockSubscriber s1 = (MockSubscriber) transport.subscribe("TEST1", transportListener, MockMessage.class);
		MockSubscriber s2 = (MockSubscriber) transport.subscribe("TEST2", transportListener, MockMessage.class);
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
		MockTransport mockTransport = new MockTransport();
		Transport transport = mockTransport;
		final TransportListener transportListener = context.mock(TransportListener.class);

		transport.init();
		transport.start();
		MockSubscriber s1 = (MockSubscriber) transport.subscribe("TEST1", transportListener, MockMessage.class);
		MockSubscriber s2 = (MockSubscriber) transport.subscribe("TEST2", transportListener, MockMessage.class);

		assertEquals("sub 1", 1, s1.getSubscribeCount());
		assertEquals("sub 2", 1, s2.getSubscribeCount());

		assertEquals("unsub 1", 0, s1.getUnsubscribeCount());
		assertEquals("unSub 2", 0, s2.getUnsubscribeCount());
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testUnsubscribe() {
		JUnit4Util.startCurrentTest(getClass());
		MockTransport mockTransport = new MockTransport();
		Transport transport = mockTransport;
		final TransportListener transportListener = context.mock(TransportListener.class);

		transport.init();
		transport.start();
		MockSubscriber s1 = (MockSubscriber) transport.subscribe("TEST1", transportListener, MockMessage.class);
		MockSubscriber s2 = (MockSubscriber) transport.subscribe("TEST2", transportListener, MockMessage.class);

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
		MockTransport mockTransport = new MockTransport();
		Transport transport = mockTransport;
		final ExceptionListener listener = context.mock(ExceptionListener.class);

		context.checking(new Expectations() {
			{
				exactly(2).of(listener).onException(with(any(TransportException.class)));
			}
		});

		transport.addExceptionListener(listener);
		transport.addExceptionListener(listener);
		transport.init();
		transport.start();
		transport.stop();
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testInitStartSubscribeStopDispose() {
		JUnit4Util.startCurrentTest(getClass());
		MockTransport mockTransport = new MockTransport();
		Transport transport = mockTransport;
		final TransportListener transportListener = context.mock(TransportListener.class);

		transport.init();
		assertTrue("init", mockTransport.isInit());
		transport.start();
		assertTrue("start", mockTransport.isStart());
		MockSubscriber s1 = (MockSubscriber) transport.subscribe("TEST1", transportListener, MockMessage.class);
		MockSubscriber s2 = (MockSubscriber) transport.subscribe("TEST2", transportListener, MockMessage.class);

		assertEquals("sub 1", 1, s1.getSubscribeCount());
		assertEquals("sub 2", 1, s2.getSubscribeCount());

		assertEquals("unsub 1", 0, s1.getUnsubscribeCount());
		assertEquals("unSub 2", 0, s2.getUnsubscribeCount());

		transport.stop();
		assertTrue("stop", mockTransport.isStop());
		assertEquals("sub 1", 1, s1.getSubscribeCount());

		assertEquals("sub 1", 1, s1.getSubscribeCount());
		assertEquals("sub 2", 1, s2.getSubscribeCount());

		assertEquals("unsub 1", 1, s1.getUnsubscribeCount());
		assertEquals("unSub 2", 1, s2.getUnsubscribeCount());
		transport.dispose();
		assertTrue("dispose", mockTransport.isDispose());

		JUnit4Util.endCurrentTest(getClass());
	}
}
