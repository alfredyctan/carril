package org.afc.carril.fix.quickfix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.afc.carril.alloc.Allocation;
import org.afc.carril.alloc.AllocationTestCaseFactory;
import org.afc.carril.fix.quickfix.SchemaBaseQuickFixConverter;
import org.afc.carril.text.UTCDateFormat;
import org.afc.carril.text.UTCDateTimestampFormat;
import org.afc.util.DateUtil;
import org.afc.util.JUnit4Util;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import quickfix.Group;
import quickfix.Message;

public class SchemaBaseQuickFixConverterAllocationTest {

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
			SchemaBaseQuickFixConverter fixConverter = new SchemaBaseQuickFixConverter("../carril-fix-testing/src/main/resources/schema/conv-schema-allocation.xml");
			
			Message input = createAllocationMessage(1);
			input.getHeader().setString(8, "Fix.4.2");
			input.getHeader().setString(49, "AFCAPFix");
			input.getHeader().setString(56, "AFCDEV1");
			System.out.println("INPUT : " + input);
			
			Allocation expect = AllocationTestCaseFactory.createAllocationObjectAsExpected(1);
			System.out.println("EXPECT : " + expect);

			Allocation actual = (Allocation)fixConverter.parse(input, Allocation.class);
			System.out.println("ACTUAL : " + actual);

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
			SchemaBaseQuickFixConverter fixConverter = new SchemaBaseQuickFixConverter("../carril-fix-testing/src/main/resources/schema/conv-schema-allocation.xml");
			
			Allocation input = AllocationTestCaseFactory.createAllocationObjectAsInput(1);
			System.out.println("INPUT : " + input);
			
			Message expect = createAllocationMessage(1);
			System.out.println("EXPECT : " + expect.toString().replace('\01', '|'));
			
			String expectString = createAllocationString(1);
			System.out.println("EXPECT String: " + expectString);
			
			Message actual = (Message)fixConverter.format(input);
			String actualString = actual.toString().replace('\01', '|');
			System.out.println("ACTUAL : " + actualString);

			assertEquals(expectString, actualString);
			
			
	    } catch (Exception e) {
	        e.printStackTrace();
			fail("failed with Exception : " + e.getMessage());
	    }
	    JUnit4Util.endCurrentTest(getClass());
	}

	private static Message createAllocationMessage(int i) {
		Message message = new Message();
		try {
			message.getHeader().setString(35, "J");
	    	message.getHeader().setString(49, "INTFXSTR2");
	    	message.getHeader().setString(56, "AFCDUKFIX");
	    	message.getHeader().setInt(34, 100);
	    	message.getHeader().setUtcTimeStamp(52, DateUtil.parseUTCTimestamp("2011-08-09 10:56:12.345"), true);
			message.setDecimal(6, BigDecimal.valueOf(1.2246));
			message.setString(15, "USD");
			message.setString(22, "6");
			message.setString(48, "USD/JPY");
			message.setDecimal(53, BigDecimal.valueOf(1000.0));
			message.setChar(54, '1');
			message.setString(55, "USD/JPY");
//			message.setString(57, "UUID:1234567");
			message.setString(58, "Hello world!");
	        message.setUtcTimeStamp(60, new UTCDateTimestampFormat().parse("20110809-10:56:12.345"), true);
			message.setChar(63, '0');
			message.setUtcDateOnly(64, new UTCDateFormat().parse("20110811"));
			message.setString(70, "AllocID:000999");
			message.setChar(71, '0');
			message.setUtcDateOnly(75, new UTCDateFormat().parse("20110809"));
			message.setDecimal(118, BigDecimal.valueOf(1224600.21));
			message.setString(196, "AllocLinkID:000001");
			message.setInt(197, 1);
			message.setInt(460, 4);
			message.setString(461, "MRCXXX");
			Group alloc1 = new Group(78, 79);
			alloc1.setString(79, "ACC0001");
			alloc1.setDecimal(80, BigDecimal.valueOf(100000.12));
			message.addGroup(alloc1);

			Group alloc2 = new Group(78, 79);
			alloc2.setString(79, "ACC0002");
			alloc2.setDecimal(80, BigDecimal.valueOf(200000.12));
			message.addGroup(alloc2);

			Group alloc3 = new Group(78, 79);
			alloc3.setString(79, "ACC0003");
			alloc3.setDecimal(80, BigDecimal.valueOf(300000.12));
			message.addGroup(alloc3);
			
			Group order1 = new Group(73, 11);
			order1.setString(11,"CI:1234");
			order1.setString(198,"SCI:1234");
			message.addGroup(order1);

			Group order2 = new Group(73, 11);
			order2.setString(11,"CI:2234");
			order2.setString(198,"SCI:2234");
			message.addGroup(order2);
			
			Group order3 = new Group(73, 11);
			order3.setString(11,"CI:3234");
			order3.setString(198,"SCI:3234");
			message.addGroup(order3);

			Group order4 = new Group(73, 11);
			order4.setString(11,"CI:4234");
			order4.setString(198,"SCI:4234");
			message.addGroup(order4);

			Group order5 = new Group(73, 11);
			order5.setString(11,"CI:5234");
			order5.setString(198,"SCI:5234");
			message.addGroup(order5);

			Group exec1 = new Group(124, 17);
			exec1.setString(17,"EX:123");
			exec1.setString(31,"1.2124");
			exec1.setString(32,"40000.44");
			message.addGroup(exec1);

			Group exec2 = new Group(124, 17);
			exec2.setString(17,"EX:456");
			exec2.setString(31,"1.2125");
			exec2.setString(32,"50000.55");
			message.addGroup(exec2);
			
			Group exec3 = new Group(124, 17);
			exec3.setString(17,"EX:789");
			exec3.setString(31,"1.2126");
			exec3.setString(32,"60000.66");
			message.addGroup(exec3);

			Group exec4 = new Group(124, 17);
			exec4.setString(17,"EX:ABC");
			exec4.setString(31,"1.2127");
			exec4.setString(32,"70000.77");
			message.addGroup(exec4);

		} catch (Exception e) {
	        e.printStackTrace();
        }
		return message;
	}

	
	
	private static String createAllocationString(int i) {
		return "9=562|35=J|49=INTFXSTR2|56=AFCDUKFIX|6=1.2246|15=USD|22=6|48=USD/JPY|53=1000.0|54=1|55=USD/JPY|58=Hello world!|60=20110809-10:56:12.345|63=0|64=20110811|70=AllocID:000999|71=0|75=20110809|118=1224600.21|196=AllocLinkID:000001|197=1|73=5|11=CI:1234|198=SCI:1234|11=CI:2234|198=SCI:2234|11=CI:3234|198=SCI:3234|11=CI:4234|198=SCI:4234|11=CI:5234|198=SCI:5234|78=3|79=ACC0001|80=100000.12|79=ACC0002|80=200000.12|79=ACC0003|80=300000.12|124=4|17=EX:123|31=1.2124|32=40000.44|17=EX:456|31=1.2125|32=50000.55|17=EX:789|31=1.2126|32=60000.66|17=EX:ABC|31=1.2127|32=70000.77|10=047|";
	}
}
