package org.afc.carril.sample;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.afc.carril.message.FixMessage.Context;
import org.afc.util.DateUtil;

public class SampleMock {
	
	public static Map<Integer, Sample> samples() {
		Map<Integer, Sample> samples = new HashMap<Integer, Sample>();
		samples.put(1,
			with(new Sample()
		    	.setMsgID("MsgA")
		    	.setFromSchema("Value from schema")
		    	.setInteger(999)
		    	.setString("test")
		    	.setDecimal(new BigDecimal(100))
		    	.setFloating(9.9)
		    	.setDatetime(DateUtil.localDateTime("2011-08-11T19:45:12"))
		    	.setDatetimestamp(DateUtil.localDateTime("2011-08-11T19:45:12.345"))
		    	.setDate(DateUtil.localDate("2011-08-11"))
		    	.setTime(DateUtil.localTime("19:45:12"))
		    	.setTimestamp(DateUtil.localTime("19:45:12.345"))
		    	.setBool(true)
		    	.setConstant("Value A")
		    	.setBytes(String.valueOf(9876).getBytes())
		    	.setField1(new BigDecimal(10))
		    	.setField2(new BigDecimal(20))
		    	.setOptional(new BigDecimal(111))
		    	.setProhibit(new BigDecimal(999))
		    	.setOnes(Arrays.asList(
		    		new LevelOne().setName("one.1").setValue(100).setTwos(Arrays.asList(
		    			new LevelTwo().setName("one.1.two.1").setValue(110).setThrees(Arrays.asList(
		    				new LevelThree().setName("one.1.two.1.three.1").setValue(111),
		    				new LevelThree().setName("one.1.two.1.three.2").setValue(112)
		    			)),
		    			new LevelTwo().setName("one.1.two.2").setValue(120).setThrees(Arrays.asList(
		    				new LevelThree().setName("one.1.two.2.three.1").setValue(121),
		    				new LevelThree().setName("one.1.two.2.three.2").setValue(122)
		    			))
		    		)),
		    		new LevelOne().setName("one.2").setValue(200).setTwos(Arrays.asList(
		    			new LevelTwo().setName("one.2.two.1").setValue(210).setThrees(Arrays.asList(
		    				new LevelThree().setName("one.2.two.1.three.1").setValue(211),
		    				new LevelThree().setName("one.2.two.1.three.2").setValue(212)
		    			)),
		    			new LevelTwo().setName("one.2.two.2").setValue(220).setThrees(Arrays.asList(
		    				new LevelThree().setName("one.2.two.2.three.1").setValue(221),
		    				new LevelThree().setName("one.2.two.2.three.2").setValue(222)
		    			))
		    		))
		    	)), 
		    	new Context("FIX.4.4", "SENDFIX01", "RECVFIX01", "R")
		    )
		);

		samples.put(2,
			with(new Sample()
		    	.setMsgID("MsgA")
		    	.setInteger(100)
		    	.setString("simple"),
		    	new Context("FIX.4.4", "SENDFIX02", "RECVFIX02", "R")
		    )
		);
		return samples;
    }

	private static Sample with(Sample sample, Context context) {
		sample.setContext(context);
		return sample;
	}
}
