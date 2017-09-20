package org.afc.ferrocarril.fix.mapping.rawfix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.ParseException;

import org.afc.ferrocarril.fix.tag.FixTag;
import org.afc.util.JUnit4Util;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RawFixTest {

	private static final char SOH = '\01';

//	0         1         2         3         4         5         6         7         8         9         
//	0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789
//	8=Fix.4.2|9=202|61=999|35=D|34=291|49=INTFXSTR2|52=20111124-00:04:51.716|56=AFCUKFix|1=Alior Bank S
//	0         1         2         3         4         5         6         7         8         9         
//	0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789
//	A|110=FXI154886615|15=USD|21=2|38=10000|40=D|44=77.24|54=1|55=USD/JPY|60=20111124-00:04:51.716|117=2
//	0         1         2         3  
//	012345678901234567890123456789012
//	523232|167=FOR|7533=INTS2|10=066|	

	private String rawFixMsg = ("8=Fix.4.2|9=202|61=999|35=D|34=291|49=INTFXSTR2|52=20111124-00:04:51.716|" + 
			"56=AFCUKFix|1=Alior Bank SA|110=FXI154886615|15=USD|21=2|38=10000|40=D|44=77.24|54=1|55=USD/JPY|" + 
			"60=20111124-00:04:51.716|117=2523232|167=FOR|7533=INTS2|10=066|").replace('|', SOH);	

	
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
	public void testFindString() {
		JUnit4Util.startCurrentTest(getClass());
		RawFix pilot = RawFix.forRead(rawFixMsg);
		
		assertEquals("Fix.4.2",               pilot.findValue("8"));
		assertEquals(10,               pilot.getIndex());
		assertEquals("20111124-00:04:51.716", pilot.findValue("52"));
		assertEquals(73,               pilot.getIndex());
		assertEquals("AFCUKFix",             pilot.findValue("56"));
		assertEquals(86,               pilot.getIndex());
		assertEquals(null,                     pilot.findValue("35"));
		assertEquals(86,               pilot.getIndex());
		assertEquals("066",                   pilot.findValue("10"));
		assertEquals(233,               pilot.getIndex());
		assertNull(pilot.findValue(FixTag.AllocID.idAsString()));
		assertEquals(233,               pilot.getIndex());

		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testFindStringNotFound() {
		JUnit4Util.startCurrentTest(getClass());
		RawFix pilot = RawFix.forRead(rawFixMsg);
		
		assertNull(pilot.findValue("5"));
		assertEquals(0,               pilot.getIndex());
		JUnit4Util.endCurrentTest(getClass());
	}
//	@Test
//	public void testIndexOfStringInt() {
//		RawFix rawFix = new RawFix(rawFixMsg);
//		assertEquals(0, rawFix.fi("8=", 0));
//		assertEquals(-1, rawFix.indexOf(SOH + "8=", 0));
//		assertEquals(-1, rawFix.indexOf(SOH + "8=", 10));
//		assertEquals(27, rawFix.indexOf(SOH + "34=", 0));
//		assertEquals(27, rawFix.indexOf(SOH + "34=", 10));
//		assertEquals(-1, rawFix.indexOf(SOH + "34=", 29));
//		
//	}
//
//	@Test
//	public void testIndexOfIntInt() {
//		RawFix rawFix = new RawFix(rawFixMsg);
//		assertEquals(0, rawFix.indexOf('8', 0));
//		assertEquals(9, rawFix.indexOf(SOH, 0));
//		assertEquals(-1, rawFix.indexOf('x', 0));
//		assertEquals(135, rawFix.indexOf('0', 130));
//		assertEquals(35, rawFix.indexOf('4', 35));
//		assertEquals(-1, rawFix.indexOf('9', 214));
//	}
//
//	@Test
//	public void testSubstring() {
//		RawFix rawFix = new RawFix(rawFixMsg);
//		assertEquals("8=Fix.4.2", rawFix.substring(0, 9));
//		assertEquals("10=066\01", rawFix.substring(226, 233));
//		assertEquals("8", rawFix.substring(0, 1));
//		assertEquals("", rawFix.substring(140, 140));
//		assertEquals("4", rawFix.substring(140, 141));
//	}
//
//	@Test
//	public void testCharAt() {
//		RawFix rawFix = new RawFix(rawFixMsg);
//		assertEquals('8', rawFix.charAt(0));
//		assertEquals('=', rawFix.charAt(1));
//		assertEquals(SOH, rawFix.charAt(232));
//	}
//
	@Test
	public void testAppend() throws ParseException {
		RawFix rawFix = RawFix.forWrite("Fix.4.2", "D", "INTFXSTR2", "AFCUKFix");

		rawFix.append("110", "FXI154886615");
		assertEquals("35=D|49=INTFXSTR2|56=AFCUKFix|110=FXI154886615|", rawFix.toString().replace(SOH, '|'));

		rawFix.append("54", "1");
		assertEquals("35=D|49=INTFXSTR2|56=AFCUKFix|110=FXI154886615|54=1|", rawFix.toString().replace(SOH, '|'));

		rawFix.append("55", "USD/JPY");
		assertEquals("35=D|49=INTFXSTR2|56=AFCUKFix|110=FXI154886615|54=1|55=USD/JPY|", rawFix.toString().replace(SOH, '|'));
	}
	
	@Test
	public void testSeal() throws ParseException {
		RawFix rawFix = RawFix.forWrite("Fix.4.2", "D", "INTFXSTR2", "AFCUKFix");

		rawFix.append("110", "FXI154886615");
		rawFix.append("54", "1");
		rawFix.append("55", "USD/JPY");
		rawFix.seal();
		assertEquals("8=Fix.4.2|35=D|49=INTFXSTR2|56=AFCUKFix|110=FXI154886615|54=1|55=USD/JPY|", rawFix.toString().replace(SOH, '|'));
		
//		long start = System.currentTimeMillis();
//		for (int i = 0; i < 10000000; i++) {
//			RawFix rawFix2 = new RawFix("Fix.4.2", "D", "INTFXSTR2", "AFCUKFix");
//			rawFix2.append("110", "FXI154886615");
//			rawFix2.append("54", "1");
//			rawFix2.append("55", "USD/JPY");
//			rawFix2.seal();
//			String s = rawFix2.toString();
//		}
//		long end = System.currentTimeMillis();
//		System.out.println(end - start);
		
	}
}
