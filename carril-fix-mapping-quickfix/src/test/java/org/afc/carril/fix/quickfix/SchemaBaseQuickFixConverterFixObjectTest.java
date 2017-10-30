package org.afc.carril.fix.quickfix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.text.ParseException;

import org.afc.carril.SomeConstant;
import org.afc.carril.fix.quickfix.SchemaBaseQuickFixConverter;
import org.afc.carril.sample.FixObject;
import org.afc.carril.sample.FixObjectTestCaseFactory;
import org.afc.util.DateUtil;
import org.afc.util.JUnit4Util;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import quickfix.Group;
import quickfix.Message;

public class SchemaBaseQuickFixConverterFixObjectTest {

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
			SchemaBaseQuickFixConverter fixConverter = new SchemaBaseQuickFixConverter("../ferrocarril-fix-testing/src/main/resources/schema/conv-schema-sample-1.xml");
	
			Message input = createMessage(1);
			input.getHeader().setString(8, "Fix.4.2");
			input.getHeader().setString(49, "AFCAPFix");
			input.getHeader().setString(56, "AFC2DEV1");
			FixObject expect = FixObjectTestCaseFactory.createFixObjectForParse(1);
			FixObject actual = (FixObject)fixConverter.parse(input, FixObject.class);
	
			System.out.println("expectFixFormat");
			System.out.println(new XStream().toXML(expect));
			System.out.println();
			System.out.println();
			System.out.println("actual");
			System.out.println(new XStream().toXML(actual));
			
			assertEquals(expect.getInteger(), actual.getInteger());
			
			assertEquals(expect.getInteger(), actual.getInteger());
			assertEquals(expect.getString(), actual.getString());
			assertEquals(expect.getDecimal(), actual.getDecimal());
			assertEquals(expect.getFloating(), actual.getFloating(), 0);
			assertEquals(expect.getDatetime(), actual.getDatetime());
			assertEquals(expect.getDate(), actual.getDate());
			assertEquals(expect.getTime(), actual.getTime());
			assertEquals(expect.getBool(), actual.getBool());

			assertEquals(expect.getField1(), actual.getField1());
			assertEquals(SomeConstant.CONST_A, actual.getConstant());
			
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
			SchemaBaseQuickFixConverter fixConverter = new SchemaBaseQuickFixConverter("../ferrocarril-fix-testing/src/main/resources/schema/conv-schema-sample-1.xml");
			
			FixObject input = FixObjectTestCaseFactory.createFixObjectForFormat(1);
			Message expect = createMessage(1);
			System.out.println("expect : " + expect.toString().replace('\01', '|'));
			String expectString = createRawFixString(1);
			System.out.println("expect String : " + expectString);
			
			Message actual = (Message)fixConverter.format(input);
			String actualString = actual.toString().replace('\01', '|');
			System.out.println();
			System.out.println("actual : " + actualString);
			
			assertEquals(expectString, actualString);

			assertEquals(expect.getHeader().getInt(61), actual.getHeader().getInt(61));
			assertEquals(expect.getString(62), actual.getString(62));
			assertEquals(expect.getDecimal(63), actual.getDecimal(63));
			assertEquals(expect.getDouble(64), actual.getDouble(64), 0);
			expect.getUtcTimeStamp(65);
			actual.getUtcTimeStamp(65);
			assertEquals(expect.getUtcTimeStamp(65), actual.getUtcTimeStamp(65));
			assertEquals(expect.getUtcDateOnly(66), actual.getUtcDateOnly(66));
			assertEquals(expect.getUtcTimeOnly(67), actual.getUtcTimeOnly(67));
			assertEquals(expect.getTrailer().getBoolean(68), actual.getTrailer().getBoolean(68));
			assertEquals(expect.getString(2), actual.getString(2));
			assertEquals(expect.getString(100), actual.getString(100));
			assertEquals(expect.getString(20), actual.getString(20));
			assertEquals(expect.getString(31), actual.getString(31));
			assertEquals(expect.getString(40), actual.getString(40));
			assertEquals(expect.getString(50), actual.getString(50));

			
			
			
	    } catch (Exception e) {
	        e.printStackTrace();
	        fail(e.getMessage());
	    }
	    JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testParseNested() {
		JUnit4Util.startCurrentTest(getClass());
		try {
			SchemaBaseQuickFixConverter fixConverter = new SchemaBaseQuickFixConverter("../ferrocarril-fix-testing/src/main/resources/schema/conv-schema-sample-1.xml");
			
			Message input = createNestedMessage(1);
			input.getHeader().setString(8, "Fix.4.2");
			input.getHeader().setString(49, "AFCAPFix");
			input.getHeader().setString(56, "AFC2DEV1");
			
			FixObject expect = FixObjectTestCaseFactory.createNestedFixObject(1);
			FixObject actual = (FixObject)fixConverter.parse(input, FixObject.class);
	
			System.out.println("INPUT : " + input.toString().replace('\01', '|'));

			System.out.println("EXPECT : " + expect);
			System.out.println("ACTUAL : " + actual);

			assertEquals(expect.getFirsts().get(0).getFirstField(), actual.getFirsts().get(0).getFirstField());
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
			SchemaBaseQuickFixConverter fixConverter = new SchemaBaseQuickFixConverter("../ferrocarril-fix-testing/src/main/resources/schema/conv-schema-sample-1.xml");
			
			FixObject input = FixObjectTestCaseFactory.createNestedFixObject(1);
			
			Message expect = createNestedMessage(1);
			System.out.println("EXPECT : " + expect.toString().replace('\01', '|'));

			String expectString = createNestedRawFixString(1);
			System.out.println("EXPECT String : " + expectString);
			
			Message actual = (Message)fixConverter.format(input);
			String actualString = actual.toString().replace('\01', '|');
			System.out.println("ACTUAL : " + actualString);
			
			assertEquals(expectString, actualString);
			
	    } catch (Exception e) {
	        e.printStackTrace();
	        fail(e.getMessage());
	    }
	    JUnit4Util.endCurrentTest(getClass());
	}

	private Message createMessage(int seed) throws ParseException {
    	Message message = new Message();
    	message.getHeader().setString(35, "R");
    	message.getHeader().setInt(61, seed * 999);
    	message.getHeader().setInt(34, 100);
    	message.getHeader().setUtcTimeStamp(52, DateUtil.parseUTCDatetime("2011-08-11 19:45:12.345"), true);
    	message.setString(62, "test" + seed);
    	message.setDecimal(63, new BigDecimal(seed * 100));
    	message.setDouble(64, 9.9 * seed );
    	message.setUtcTimeStamp(65, DateUtil.parseUTCDatetime("2011-08-11 19:45:12.345"), true);
        message.setUtcDateOnly(66, DateUtil.parseUTCDatetime("2011-08-11 19:45:12.345"));
        message.setUtcTimeOnly(67, DateUtil.parseUTCDatetime("2011-08-11 19:45:12.345"), true);
        message.getTrailer().setBoolean(68, (seed%2==0));
//        message.setString(1 , "100");
        message.setString(2 , "100/200");
        message.setString(100, SomeConstant.CONST_A);
        message.setString(20, "Defined in Schema");
        message.setString(31, "100");
        message.setDecimal(37, new BigDecimal(seed * 200));
        message.setDecimal(40, new BigDecimal(seed * 111));
        message.setDecimal(50, new BigDecimal(seed * 999));
    	return message;
    }


	private Message createNestedMessage(int seed) throws ParseException {
    	Message message = new Message();
    	message.getHeader().setString(35, "j");
    	message.getHeader().setString(49, "INTFXSTR2");
    	message.getHeader().setString(56, "AFCDUKFIX");
    	message.getHeader().setInt(34, 100);
    	message.getHeader().setUtcTimeStamp(52, DateUtil.parseUTCTimestamp("2011-08-09 10:56:12.345"), true);
    	message.getHeader().setInt(61, seed * 999);
    	message.setString(2, "null/null");
    	message.setString(100, "Value A");
    	message.setString(20, "Defined in Schema");
    	message.setString(62, "Defined in Schema");
    	message.addGroup(createFirstGroupSingle(1, 10));
    	return message;
    }

	private static Group createFirstGroupSingle(int seed, int m) {
    	Group group = new Group(9100, 0);
    	Message message = new Message();
    	message.setString(9101, "F" + seed * m);
    	group.setFields(message);
    	group.addGroup(createSecondGroupSingle(seed + 1, 100));
    	group.addGroup(createSecondGroupSingle(seed + 2, 200));
    	return group;
    }

	private static Group createSecondGroupSingle(int seed, int m) {
    	Group group = new Group(9200, 0);
    	Message message = new Message();
    	message.setString(9201, "S" + seed * m);
    	group.setFields(message);
    	group.addGroup(createThirdGroupSingle(seed + 1, 1000));
    	group.addGroup(createThirdGroupSingle(seed + 2, 2000));
    	group.addGroup(createThirdGroupSingle(seed + 3, 3000));
    	return group;
    }

	private static Group createThirdGroupSingle(int seed, int m) {
		Group group = new Group(9300, 0);
		Message message = new Message();
		message.setString(9301, "T" + seed * m);
		group.setFields(message);
		return group;
	}

	private static String createRawFixString(int seed) {
		return "9=196|35=j|49=INTFXSTR2|56=AFCDUKFIX|61=999|1=100|2=100/200|20=Defined in Schema|31=100|37=100|40=111|50=999|62=test1|63=100|64=9.9|65=20110811-19:45:12.000|66=20110811|67=19:45:12.000|100=Value A|68=N|10=209|";
	}

	private static String createNestedRawFixString(int seed) {
		return "9=230|35=j|49=INTFXSTR2|56=AFCDUKFIX|61=999|2=null/null|20=Defined in Schema|62=Defined in Schema|100=Value A|9100=1|9101=F10|9200=2|9201=S200|9300=3|9301=T3000|9301=T8000|9301=T15000|9201=S600|9300=3|9301=T4000|9301=T10000|9301=T18000|10=168|";
	}
}
