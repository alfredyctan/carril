package org.afc.carril.transport;

import static org.junit.Assert.*;

import org.afc.carril.subscriber.Subscriber;
import org.afc.carril.transport.impl.DefaultSubjectContext;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DefaultSubjectContextTest {

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
	public void testGetSubject() {
		DefaultSubjectContext subjectContext = new DefaultSubjectContext("TEST");
		assertEquals("TEST", subjectContext.getSubject());
	}

	@Test
	public void testAndGetSubscribers() {
		DefaultSubjectContext subjectContext = new DefaultSubjectContext("TEST");

		Subscriber subscription1 = context.mock(Subscriber.class, "1");
		Subscriber subscription2 = context.mock(Subscriber.class, "2");
		Subscriber subscription3 = context.mock(Subscriber.class, "3");
		
		
		subjectContext.addSubscriber(subscription1);
		subjectContext.addSubscriber(subscription2);
		subjectContext.addSubscriber(subscription3);
		assertEquals("size", 3, subjectContext.getSubscribers().size());
	}

	@Test
	public void testRemoveSubscriber() {
		DefaultSubjectContext subjectContext = new DefaultSubjectContext("TEST");

		Subscriber subscription1 = context.mock(Subscriber.class, "1");
		Subscriber subscription2 = context.mock(Subscriber.class, "2");
		Subscriber subscription3 = context.mock(Subscriber.class, "3");
		
		
		subjectContext.addSubscriber(subscription1);
		subjectContext.addSubscriber(subscription2);
		subjectContext.addSubscriber(subscription3);
		assertEquals("size", 3, subjectContext.getSubscribers().size());
		
		subjectContext.removeSubscriber(subscription1);
		assertEquals("size", 2, subjectContext.getSubscribers().size());
		
		subjectContext.removeSubscriber(subscription2);
		assertEquals("size", 1, subjectContext.getSubscribers().size());

		subjectContext.removeSubscriber(subscription3);
		assertEquals("size", 0, subjectContext.getSubscribers().size());
}

}
