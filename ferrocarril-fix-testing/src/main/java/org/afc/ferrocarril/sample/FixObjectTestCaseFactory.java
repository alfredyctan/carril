package org.afc.ferrocarril.sample;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import org.afc.ferrocarril.FixMessage;
import org.afc.ferrocarril.SomeConstant;
import org.afc.util.DateUtil;

public class FixObjectTestCaseFactory {

	public static FixObject createFixObjectForParse(int seed) throws ParseException {
    	FixObject fixObject = new FixObject();
    	fixObject.setInteger(seed * 999);
    	fixObject.setMsgID("Msg" + seed + "A");
    	fixObject.setString("test" + seed);
    	fixObject.setDecimal(new BigDecimal(seed * 100));
    	fixObject.setFloating(9.9 * seed);
    	fixObject.setDatetime(DateUtil.parseUTCDatetime("2011-08-11 19:45:12.345"));
    	fixObject.setDate(DateUtil.parseUTCDatetime("2011-08-11 00:00:00.00"));
    	fixObject.setTime(DateUtil.parseUTCDatetime("1970-01-01 19:45:12.345"));
    	fixObject.setBool((seed%2==0));
    	fixObject.setConstant("Value A");
    	fixObject.setContext(new FixMessage.Context("FIX.4.2", "R", "INTFXSTR2", "HSBCUKFIX", 100, DateUtil.parseUTCTimestamp("2011-08-09 10:56:12.345")));
    	return fixObject; 
    }
	
	public static FixObject createFixObjectForFormat(int seed) throws ParseException {
    	FixObject fixObject = new FixObject();
    	fixObject.setInteger(seed * 999);
    	fixObject.setMsgID("Msg" + seed + "A");
    	fixObject.setString("test" + seed);
    	fixObject.setDecimal(new BigDecimal(seed * 100));
    	fixObject.setFloating(9.9 * seed);
    	fixObject.setDatetime(DateUtil.parseUTCDatetime("2011-08-11 19:45:12.345"));
    	fixObject.setDate(DateUtil.parseUTCDatetime("2011-08-11 00:00:00.00"));
    	fixObject.setTime(DateUtil.parseUTCDatetime("1970-01-01 19:45:12.345"));
    	fixObject.setBool((seed%2==0));
    	fixObject.setConstant("Value A");
    	fixObject.setBytes(String.valueOf(seed * 9876).getBytes());
    	fixObject.setField1(new BigDecimal(seed * 100));
    	fixObject.setField2(new BigDecimal(seed * 200));
    	fixObject.setOptional(new BigDecimal(seed * 111));
    	fixObject.setProhibit(new BigDecimal(seed * 999));
    	fixObject.setContext(new FixMessage.Context("FIX.4.2", "R", "INTFXSTR2", "HSBCUKFIX", 100, DateUtil.parseUTCTimestamp("2011-08-09 10:56:12.345")));
    	return fixObject; 
    }

//	public static String createRawFixString(int seed) {
//		return "9=228|35=j|34=100|49=INTFXSTR2|52=20110809-10:56:12.345|56=HSBCUKFIX|61=999|1=100|2=100/200|20=Defined in Schema|31=100|37=100|40=111|50=999|62=test1|63=100|64=9.9|65=20110811-19:45:12.000|66=20110811|67=19:45:12.000|100=Value A|68=N|10=233|";
//	}
//	
//	public static String createNestedRawFixString(int seed) {
//		return "9=262|35=j|34=100|49=INTFXSTR2|52=20110809-10:56:12.345|56=HSBCUKFIX|61=999|2=null/null|20=Defined in Schema|62=Defined in Schema|100=Value A|9100=1|9101=F10|9200=2|9201=S200|9300=3|9301=T3000|9301=T8000|9301=T15000|9201=S600|9300=3|9301=T4000|9301=T10000|9301=T18000|10=201|";
//	}
	
	public static FixObject createNestedFixObject(int seed) throws ParseException {
    	FixObject fixObject = new FixObject();
    	fixObject.setMsgID("Msg" + seed + "A");
    	fixObject.setInteger(seed * 999);
    	fixObject.setConstant(SomeConstant.CONST_A);
    	fixObject.setString("Defined in Schema");
    	List<First> firsts = new LinkedList<First>();
		firsts.add(createFirstObject(1, 10));
    	fixObject.setFirsts(firsts);
    	fixObject.setContext(new FixMessage.Context("FIX.4.2", "R", "INTFXSTR2", "HSBCUKFIX", 100, DateUtil.parseUTCTimestamp("2011-08-09 10:56:12.345")));
    	return fixObject;
    }

	private static First createFirstObject(int seed, int m) {
		First first = new First();
		first.setFirstField("F" + seed * m);
		List<Second> seconds = new LinkedList<Second>();
		seconds.add(createSecondObject(seed + 1, 100));
		seconds.add(createSecondObject(seed + 2, 200));
		first.setSeconds(seconds);
		return first;
	}	

	private static Second createSecondObject(int seed, int m) {
		Second second = new Second();
		second.setSecondField("S" + seed * m);
		List<Third> thirds= new LinkedList<Third>();
		thirds.add(createThirdObject(seed + 1, 1000));
		thirds.add(createThirdObject(seed + 2, 2000));
		thirds.add(createThirdObject(seed + 3, 3000));
		second.setThirds(thirds);
		return second; 
	}

	private static Third createThirdObject(int seed, int m) {
		Third third = new Third();
		third.setThirdField("T" + seed * m);
		return third; 
	}
	

}
