package org.afc.carril.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.afc.carril.transport.SubjectContext;
import org.afc.carril.transport.SubjectContextFactory;
import org.afc.carril.transport.impl.DefaultSubjectContext;
import org.afc.carril.transport.impl.DefaultSubjectRegistry;
import org.afc.util.JUnit4Util;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DefaultSubjectRegistryTest {

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
		DefaultSubjectRegistry registry = new DefaultSubjectRegistry();
		assertEquals(0, registry.getSubjectContexts().size());

		registry.register("TEST", new DefaultSubjectContext("TEST"));
		assertEquals(1, registry.getSubjectContexts().size());

		registry.register("TEST", new DefaultSubjectContext("TEST"));
		assertEquals(1, registry.getSubjectContexts().size());

		registry.register("TEST2", new DefaultSubjectContext("TEST2"));
		assertEquals(2, registry.getSubjectContexts().size());
		JUnit4Util.endCurrentTest(getClass());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
	public void testUnregisterAll() {
		JUnit4Util.startCurrentTest(getClass());
		DefaultSubjectRegistry registry = new DefaultSubjectRegistry();
		assertEquals(0, registry.getSubjectContexts().size());

		registry.register("TEST", new DefaultSubjectContext("TEST"));
		registry.register("TEST", new DefaultSubjectContext("TEST"));
		registry.register("TEST2", new DefaultSubjectContext("TEST2"));
		
		registry.unregisterAll();
		assertEquals(0, registry.getSubjectContexts().size());
		JUnit4Util.endCurrentTest(getClass());
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testUnregister() {
		JUnit4Util.startCurrentTest(getClass());
		
		DefaultSubjectRegistry registry = new DefaultSubjectRegistry();

		SubjectContext subjectContext1 = context.mock(SubjectContext.class, "TEST1");
		SubjectContext subjectContext2 = context.mock(SubjectContext.class, "TEST2");
		SubjectContext subjectContext3 = context.mock(SubjectContext.class, "TEST3");
		
		registry.register("TEST1", subjectContext1);
		registry.register("TEST2", subjectContext2);
		registry.register("TEST3", subjectContext3);

		registry.unregister("TEST2");
		assertEquals("TEST2", 2, registry.getSubjectContexts().size());

		JUnit4Util.endCurrentTest(getClass());
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testRegisterSubscriber() {
		JUnit4Util.startCurrentTest(getClass());
		
		DefaultSubjectRegistry registry = new DefaultSubjectRegistry();
		
		SubjectContext subjectContext1 = context.mock(SubjectContext.class, "TEST1");
		SubjectContext subjectContext2 = context.mock(SubjectContext.class, "TEST2");
		SubjectContext subjectContext3 = context.mock(SubjectContext.class, "TEST3");
		
		registry.register("TEST1", subjectContext1);
		assertEquals("TEST1", 1, registry.getSubjectContexts().size());
		
		registry.register("TEST2", subjectContext2);
		assertEquals("TEST2", 2, registry.getSubjectContexts().size());

		registry.register("TEST2", subjectContext3);
		assertEquals("TEST2", 2, registry.getSubjectContexts().size());
		
		registry.register("TEST3", subjectContext3);
		assertEquals("TEST3", 3, registry.getSubjectContexts().size());

		JUnit4Util.endCurrentTest(getClass());
	}
}
