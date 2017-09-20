package org.afc.ferrocarril.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.afc.ferrocarril.subscriber.DefaultSubscriberRegistry;
import org.afc.ferrocarril.transport.impl.DefaultSubjectContext;
import org.afc.util.JUnit4Util;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DefaultSubscriberRegistryTest {

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
	public void testRegisterSubjectContexts() {
		JUnit4Util.startCurrentTest(getClass());
		DefaultSubscriberRegistry registry = new DefaultSubscriberRegistry();
		assertEquals(0, registry.allSubjectContexts().size());

		registry.register("TEST", new DefaultSubjectContext("TEST"));
		assertEquals(1, registry.allSubjectContexts().size());

		registry.register("TEST", new DefaultSubjectContext("TEST"));
		assertEquals(1, registry.allSubjectContexts().size());

		registry.register("TEST2", new DefaultSubjectContext("TEST2"));
		assertEquals(2, registry.allSubjectContexts().size());
		JUnit4Util.endCurrentTest(getClass());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
	public void testUnregisterAll() {
		JUnit4Util.startCurrentTest(getClass());
		DefaultSubscriberRegistry registry = new DefaultSubscriberRegistry();
		assertEquals(0, registry.allSubjectContexts().size());

		registry.register("TEST", new DefaultSubjectContext("TEST"));
		registry.register("TEST", new DefaultSubjectContext("TEST"));
		registry.register("TEST2", new DefaultSubjectContext("TEST2"));
		
		registry.unregisterAll();
		assertEquals(0, registry.allSubjectContexts().size());
		JUnit4Util.endCurrentTest(getClass());
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testUnregister() {
		JUnit4Util.startCurrentTest(getClass());
		DefaultSubscriberRegistry registry = new DefaultSubscriberRegistry();

		final SubjectContextFactory<SubjectContext> factory = context.mock(SubjectContextFactory.class);
		context.checking(new Expectations() {
			{
				oneOf(factory).createSubjectContext("TEST");
				will(returnValue(new DefaultSubjectContext("TEST")));
			}
		});		
		registry.setSubjectContextFactory(factory);

		assertEquals(0, registry.allSubjectContexts().size());

		SubjectContext removed = registry.unregister("TEST");

		assertEquals(0, removed.getSubscribers().size());
		assertEquals(0, registry.allSubjectContexts().size());

		DefaultSubjectContext subjectContext = new DefaultSubjectContext("TEST");
		registry.register("TEST", subjectContext);
		registry.register("TEST3", new DefaultSubjectContext("TEST3"));
		registry.register("TEST2", new DefaultSubjectContext("TEST2"));
		
		registry.unregister("TEST3");
		assertSame(subjectContext, registry.getSubjectContext("TEST"));
		assertEquals(2, registry.allSubjectContexts().size());
		JUnit4Util.endCurrentTest(getClass());
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testRegisterSubscriber() {
		JUnit4Util.startCurrentTest(getClass());
		DefaultSubscriberRegistry registry = new DefaultSubscriberRegistry();
		
		final SubjectContextFactory<SubjectContext> factory = context.mock(SubjectContextFactory.class);

		
		context.checking(new Expectations() {
			{
				oneOf(factory).createSubjectContext("TEST");
				will(returnValue(new DefaultSubjectContext("TEST")));

				oneOf(factory).createSubjectContext("TEST1");
				will(returnValue(new DefaultSubjectContext("TEST1")));

				oneOf(factory).createSubjectContext("TEST2");
				will(returnValue(new DefaultSubjectContext("TEST2")));

				oneOf(factory).createSubjectContext("TEST3");
				will(returnValue(new DefaultSubjectContext("TEST3")));

				oneOf(factory).createSubjectContext("TEST4");
				will(returnValue(new DefaultSubjectContext("TEST4")));
			}
		});		
		
		registry.setSubjectContextFactory(factory);
		registry.register("TEST", new MockSubscriber());
		registry.register("TEST", new MockSubscriber());
		registry.register("TEST", new MockSubscriber());
		registry.register("TEST1", new MockSubscriber());
		registry.register("TEST2", new MockSubscriber());
		registry.register("TEST3", new MockSubscriber());
		
		assertEquals("TEST", 3, registry.getSubjectContext("TEST").getSubscribers().size());
		assertEquals("TEST1", 1, registry.getSubjectContext("TEST1").getSubscribers().size());
		assertEquals("TEST2", 1, registry.getSubjectContext("TEST2").getSubscribers().size());
		assertEquals("TEST3", 1, registry.getSubjectContext("TEST3").getSubscribers().size());
		assertEquals("TEST4", 4, registry.allSubjectContexts().size());
		assertEquals("TEST4", 0, registry.getSubjectContext("TEST4").getSubscribers().size());
		JUnit4Util.endCurrentTest(getClass());
	}
}
