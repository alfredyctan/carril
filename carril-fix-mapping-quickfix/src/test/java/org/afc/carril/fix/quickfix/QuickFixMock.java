package org.afc.carril.fix.quickfix;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.afc.carril.fix.tag.FixTag;

import org.afc.util.DateUtil;

import quickfix.Group;
import quickfix.Message;
import quickfix.UtcTimestampPrecision;

public class QuickFixMock {

	public static Map<Integer, Message> messages() {
		Map<Integer, Message> samples = new HashMap<Integer, Message>();
		
		samples.put(1, message1());
		samples.put(2, message2());
		
		return samples;
    }

	private static Message message1() {
		Message message = new Message(); 
    	message.getHeader().setString(FixTag.BeginString.id(), "FIX.4.4");
		message.getHeader().setString(FixTag.SenderCompID.id(), "SENDFIX01");
		message.getHeader().setString(FixTag.TargetCompID.id(), "RECVFIX01");
		message.getHeader().setString(FixTag.MsgType.id(), "R");
		message.getHeader().setInt(FixTag.MsgSeqNum.id(), 1);
		message.getHeader().setUtcTimeStamp(FixTag.SendingTime.id(), DateUtil.localDateTime("2011-08-11T19:45:12.345"), UtcTimestampPrecision.MILLIS);

		message.setString(9001, "test");
		message.setDecimal(9002, new BigDecimal("100"));
		message.setDouble(9003, 9.9d);
		message.setUtcTimeStamp(9004, DateUtil.localDateTime("2011-08-11T19:45:12"), false);
		message.getHeader().setUtcTimeStamp(9052, DateUtil.localDateTime("2011-08-11T19:45:12.345"), UtcTimestampPrecision.MILLIS);
		message.setUtcDateOnly(9005, DateUtil.localDate("2011-08-11"));
		message.setUtcTimeOnly(9006, DateUtil.localTime("19:45:12"), false);
		message.setUtcTimeOnly(9007, DateUtil.localTime("19:45:12.345"), UtcTimestampPrecision.MILLIS);
		message.setDecimal(9009, new BigDecimal("10"));
		message.setString(9010, "10/20");
		message.setBytes(9011, String.valueOf(9876).getBytes());
		message.setDecimal(9020, new BigDecimal("10"));
		message.setString(9030, "Value A");
		message.setString(9040, "Value in Schema");
		message.setDecimal(9050, new BigDecimal("111"));
		message.setDecimal(9060, new BigDecimal("999"));

		message.setDecimal(19001, new BigDecimal("10"));
		
		
		Group group1 = group(9100, 
			9101, "one.1",
			9102, 100,
			group(9200, 
				9201, "one.1.two.1",
				9202, 110,
				group(9300, 9301, "one.1.two.1.three.1", 9302, 111), 
				group(9300, 9301, "one.1.two.1.three.2", 9302, 112)
			), group(9200, 
				9201, "one.1.two.2", 
				9202, 120,
				group(9300, 9301, "one.1.two.2.three.1", 9302, 121), 
				group(9300, 9301, "one.1.two.2.three.2", 9302, 122)
			)
		);
		message.addGroup(group1);

		Group group2 = group(9100, 
			9101, "one.2", 
			9102, 200,
			group(9200, 
				9201, "one.2.two.1", 
				9202, 210,
				group(9300, 9301, "one.2.two.1.three.1", 9302, 211), 
				group(9300, 9301, "one.2.two.1.three.2", 9302, 212)
			), group(9200, 
				9201, "one.2.two.2", 
				9202, 220,
				group(9300, 9301, "one.2.two.2.three.1", 9302, 221), 
				group(9300, 9301, "one.2.two.2.three.2", 9302, 222)
			)
		);
		message.addGroup(group2);

		message.getTrailer().setBoolean(9008, true);
		message.getTrailer().setInt(9501, 999);
		return message;
	}

	private static Message message2() {
		Message message = new Message();
    	message.getHeader().setString(FixTag.BeginString.id(), "FIX.4.4");
		message.getHeader().setString(FixTag.SenderCompID.id(), "SENDFIX02");
		message.getHeader().setString(FixTag.TargetCompID.id(), "RECVFIX02");
		message.getHeader().setString(FixTag.MsgType.id(), "R");
		message.getHeader().setInt(FixTag.MsgSeqNum.id(), 2);
		message.getHeader().setUtcTimeStamp(FixTag.SendingTime.id(), DateUtil.localDateTime("2011-08-11T19:45:12.345"), UtcTimestampPrecision.MILLIS);
		message.setString(9001, "simple");
		message.getTrailer().setInt(9501, 100);
		return message;
	}

	private static Group group(int countTag, int nameTag, String name, int valueTag, int value, Group... groups) {
		Group group = new Group(countTag, 0);
		group.setString(nameTag, name);
		group.setInt(valueTag, value);
		for (Group g: groups) {
			group.addGroup(g);
		}
		return group;
	}
	
	public static void assertMessage(Message actual, Message expect) {
		assertThat("FIX string", actual.toString(), is(equalTo(expect.toString())));
	}
}
