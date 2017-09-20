package org.afc.ferrocarril.fix.rawfix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.afc.ferrocarril.fix.mapping.rawfix.RawFix;
import org.afc.ferrocarril.sample.FixObject;
import org.afc.ferrocarril.sample.FixObjectTestCaseFactory;
import org.afc.util.JUnit4Util;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SchemaBaseRawFixConverterFixObjectTest {

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
			SchemaBaseRawFixConverter convertor = new SchemaBaseRawFixConverter("../ferrocarril-fix-testing/src/main/resources/schema/conv-schema-sample-1.xml");
			String input = createRawFixMessageForParse(1);
			System.out.println("INPUT : " + input);
			
			FixObject expect = FixObjectTestCaseFactory.createFixObjectForParse(1);
			FixObject actual = (FixObject)convertor.parse(RawFix.forRead(input.replace('|', '\01')), FixObject.class);
			
			System.out.println("EXPECT : " + expect);
			System.out.println("ACTUAL : " + actual);
						
			assertEquals(expect, actual);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		JUnit4Util.endCurrentTest(getClass());
	}


	@Test
	public void testFormat() {
		JUnit4Util.startCurrentTest(getClass());
		try {
			SchemaBaseRawFixConverter fixConverter = new SchemaBaseRawFixConverter("../ferrocarril-fix-testing/src/main/resources/schema/conv-schema-sample-1.xml");
			
			FixObject input = FixObjectTestCaseFactory.createFixObjectForFormat(1);
			System.out.println("INPUT : " + input);
			
			String expect = createRawFixMessageForFormat(1);
			String expectString = expect.toString().replace('\01', '|');
			System.out.println("EXPECT : " + expectString);

			String actual = ((RawFix)fixConverter.format(input)).toString();
			String actualString = actual.toString().replace('\01', '|');
			System.out.println("ACTUAL : " + actualString);
			assertEquals(expectString, actualString);
		} catch (Exception e) {
	        e.printStackTrace();
			fail("failed with Exception : " + e.getMessage());
	    }
	    JUnit4Util.endCurrentTest(getClass());
	}
	


	@Test
	public void testParseNested() {
		JUnit4Util.startCurrentTest(getClass());
		try {
			SchemaBaseRawFixConverter convertor = new SchemaBaseRawFixConverter("../ferrocarril-fix-testing/src/main/resources/schema/conv-schema-sample-1.xml");
			String input = createNestedMessageForParse(1);
			
			System.out.println("INPUT : " + input);
			
			FixObject expect = FixObjectTestCaseFactory.createNestedFixObject(1);
			FixObject actual = (FixObject)convertor.parse(RawFix.forRead(input.replace('|', '\01')), FixObject.class);
			
			System.out.println("EXPECT : " + expect);
			System.out.println("ACTUAL : " + actual);
						
			assertEquals(expect, actual);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	    JUnit4Util.endCurrentTest(getClass());
	}
	
	@Test
	public void testFormatNested() {
		JUnit4Util.startCurrentTest(getClass());
		try {
			SchemaBaseRawFixConverter fixConverter = new SchemaBaseRawFixConverter("../ferrocarril-fix-testing/src/main/resources/schema/conv-schema-sample-1.xml");
			
			FixObject input = FixObjectTestCaseFactory.createNestedFixObject(1);
			System.out.println("INPUT : " + input);
			
			String expect = createNestedMessageForFormat(1);
			String expectString = expect.toString().replace('\01', '|');
			System.out.println("EXPECT : " + expectString);

			String actual = ((RawFix)fixConverter.format(input)).toString();
			String actualString = actual.toString().replace('\01', '|');
			System.out.println("ACTUAL : " + actualString);
			assertEquals(expectString, actualString);
		} catch (Exception e) {
	        e.printStackTrace();
			fail("failed with Exception : " + e.getMessage());
	    }
	    JUnit4Util.endCurrentTest(getClass());
	}
	

	private static String createRawFixMessageForFormat(int i) {
		return "8=Fix.4.2|35=j|49=INTFXSTR2|56=AFCUKFix|61=999|62=test1|63=100|64=9.9|65=20110811-19:45:12.000|66=20110811|67=19:45:12.000|68=false|37=100|1=100|2=100/200|100=Value A|20=Defined in Schema|31=100|40=111|50=999|";
	}

	private static String createNestedMessageForFormat(int i) {
		return "8=Fix.4.2|35=j|49=INTFXSTR2|56=AFCUKFix|9100=1|9101=F10|9200=2|9201=S200|9300=3|9301=T3000|9301=T8000|9301=T15000|9201=S600|9300=3|9301=T4000|9301=T10000|9301=T18000|61=999|62=Defined in Schema|2=null/null|100=Value A|20=Defined in Schema|";
	}
	
	private static String createRawFixMessageForParse(int i) {
		return "8=Fix.4.2|9=232|35=j|49=INTFXSTR2|56=AFCUKFix|34=100|52=20110809-10:56:12.345|61=999|62=test1|63=100|64=9.9|65=20110811-19:45:12.000|66=20110811|67=19:45:12.000|68=false|37=100|1=100|2=100/200|100=Value A|20=Defined in Schema|31=100|40=111|50=999|10=192|";
	}

	private static String createNestedMessageForParse(int i) {
		return "8=Fix.4.2|9=262|35=j|49=INTFXSTR2|56=AFCUKFix|34=100|52=20110809-10:56:12.345|9100=1|9101=F10|9200=2|9201=S200|9300=3|9301=T3000|9301=T8000|9301=T15000|9201=S600|9300=3|9301=T4000|9301=T10000|9301=T18000|61=999|62=Defined in Schema|2=null/null|100=Value A|20=Defined in Schema|10=232|";
	}
}
