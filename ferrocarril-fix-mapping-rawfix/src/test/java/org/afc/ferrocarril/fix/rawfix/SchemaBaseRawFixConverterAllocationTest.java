package org.afc.ferrocarril.fix.rawfix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.afc.ferrocarril.alloc.Allocation;
import org.afc.ferrocarril.alloc.AllocationTestCaseFactory;
import org.afc.ferrocarril.fix.mapping.rawfix.RawFix;
import org.afc.util.JUnit4Util;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SchemaBaseRawFixConverterAllocationTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testParse() {
		JUnit4Util.startCurrentTest(getClass());
		try {
			SchemaBaseRawFixConverter fixConverter = new SchemaBaseRawFixConverter("../ferrocarril-fix-testing/src/main/resources/schema/conv-schema-allocation.xml");
			
			String input = AllocationTestCaseFactory.createAllocationMessageForRawFix(1);
			System.out.println("INPUT : " + input);
			
			Allocation expect = AllocationTestCaseFactory.createAllocationObjectAsExpected(1);
			System.out.println("EXPECT : " + expect);

			Allocation actual = (Allocation)fixConverter.parse(RawFix.forRead(input.replace('|', '\01')), Allocation.class);
			System.out.println("ACTUAL : " + actual);

//			long start = System.currentTimeMillis();
//			for (int i = 0; i < 100000; i++) {
//				fixConverter.parse(input, Allocation.class);
//			}
//			long end = System.currentTimeMillis();
//			System.out.println("ACTUAL : " + (end - start));
			

			assertEquals(expect, actual);
			
			
	    } catch (Exception e) {
	        e.printStackTrace();
			fail("failed with Exception : " + e.getMessage());
	    }
	    JUnit4Util.endCurrentTest(getClass());

	}

	@Test
	public void testFormat() {
		JUnit4Util.startCurrentTest(getClass());
		try {
			SchemaBaseRawFixConverter fixConverter = new SchemaBaseRawFixConverter("../ferrocarril-fix-testing/src/main/resources/schema/conv-schema-allocation.xml");
			
			Allocation input = AllocationTestCaseFactory.createAllocationObjectAsInput(1);
			System.out.println("INPUT : " + input);
			
			String expect = AllocationTestCaseFactory.createAllocationMessageForRawFixFormat(1);
			System.out.println("EXPECT : " + expect);

			String actual = ((RawFix)fixConverter.format(input)).toString();
			String actualString = actual.toString().replace('\01', '|');
			System.out.println("ACTUAL : " + actualString);
			assertEquals(expect, actualString);


//			long start = System.currentTimeMillis();
//			String actual2 = null;
//			for (int i = 0; i < 100000; i++) {
//				actual2 = ((RawFix)fixConverter.format(input)).toString();
//			}
//			long end = System.currentTimeMillis();
//			System.out.println("ACTUAL : " + (end - start));
//			System.out.println(actual2.replace('\01', '|'));

		
		} catch (Exception e) {
	        e.printStackTrace();
			fail("failed with Exception : " + e.getMessage());
	    }
	    JUnit4Util.endCurrentTest(getClass());
	}


}
