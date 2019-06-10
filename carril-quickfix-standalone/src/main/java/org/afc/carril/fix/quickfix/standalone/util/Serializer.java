package org.afc.carril.fix.quickfix.standalone.util;

import org.afc.AFCException;
import org.afc.carril.fix.tag.FixTag;

import quickfix.DataDictionary;
import quickfix.InvalidMessage;
import quickfix.Message;
import quickfix.MessageUtils;

public class Serializer {

	private static final char SOH = '\01';
	
	private static final char CD = '|';

	private static final String S1 = "\01";
	
	private static final String S2 = "\02";

	private static final String D = "|";
	
	private static final String DD = "||";

	public static String serialize(Message message) {
		return message.toString().replace(D, DD).replace(SOH, CD);
	}

	public static Message deserialize(String str, DataDictionary dd) {
		try {
			StringBuilder builder = new StringBuilder(str.replace(DD, S2).replace(CD, SOH).replace(S2, D));
			int bodyLengthStart = builder.substring(0, 30).indexOf(SOH + FixTag.BodyLength.prefix());
			
			if (bodyLengthStart > -1) {
				bodyLengthStart++;
				builder.delete(bodyLengthStart, builder.indexOf(S1, bodyLengthStart) + 1);
			}
			
			int checkSumStart = builder.lastIndexOf(S1 + FixTag.CheckSum.prefix());
			if (checkSumStart > -1) {
				checkSumStart++;
				builder.delete(checkSumStart, builder.indexOf(S1, checkSumStart) + 1);
			}
			
			bodyLengthStart = builder.indexOf(S1) + 1;
			int bodyLength = builder.length() - bodyLengthStart;
			builder.insert(bodyLengthStart, FixTag.BodyLength.prefix() + bodyLength + SOH);
			
			builder.append(FixTag.CheckSum.prefix() + MessageUtils.checksum(builder.toString()) + SOH);
			return new Message(builder.toString(), dd);
		} catch (InvalidMessage e) {
			throw new AFCException(e);
		}
	}

	public static String escape(Message message) {
		return escape(serialize(message));
	}

	public static String escape(String message) {
		return message.replace("\r", "\\r").replace("\n", "\\n");
	}
	
	public static String unescape(String message) {
		return message.replace("\\r", "\r").replace("\\n", "\n");
	}
}
