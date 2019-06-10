package org.afc.carril.fix.quickfix.standalone.serialize;

import quickfix.Group;
import quickfix.Message;

public class MessageMock {

	public static Message message() {
		Message message = new Message();
		
		message.getHeader().setString(8, "FIX.4.4");
		message.getHeader().setString(9, "125");
		message.getHeader().setString(35, "R");
		message.getHeader().setString(34, "5");
		message.getHeader().setString(49, "BUY");
		message.getHeader().setString(52, "20190212-08:29:05.170");
		message.getHeader().setString(56, "SELL1");
		
		message.setString(131, "QRID-1");
		Group group = new Group(146, 55, new int[] { 55, 48, 22, 107, 350, 351, 54 });
		group.setString(55, "N/A");
		group.setString(48, "N/A");
		group.setString(22, "4");
		group.setString(107, "ELN");
		group.setString(350, "7");
		group.setString(351, "<desc>|</desc>");
		group.setString(54, "1");
		message.addGroup(group);
		
		message.getTrailer().setString(10, "217");
		return message;
	}

	public static String fixString() {
		return "8=FIX.4.4|9=125|35=R|34=5|49=BUY|52=20190212-08:29:05.170|56=SELL1|" +
				"131=QRID-1|146=1|55=N/A|48=N/A|22=4|107=ELN|350=7|351=<desc>||</desc>|54=1|" + 
				"10=217|";
	}

	public static Message message2() {
		Message message = new Message();
		
		message.getHeader().setString(8, "FIX.4.4");
		message.getHeader().setString(9, "125");
		message.getHeader().setString(35, "R");
		message.getHeader().setString(34, "5");
		message.getHeader().setString(49, "BUY");
		message.getHeader().setString(52, "20190212-08:29:05.170");
		message.getHeader().setString(56, "SELL1");
		
		message.setString(131, "QRID-1");
		Group group = new Group(146, 55, new int[] { 55, 48, 22, 107, 350, 351, 54 });
		group.setString(55, "N/A");
		group.setString(48, "N/A");
		group.setString(22, "4");
		group.setString(107, "ELN");
		group.setString(350, "7");
		group.setString(351, "<desc>|</desc>");
		group.setString(54, "1");
		message.addGroup(group);

		group.setString(55, "N/A");
		group.setString(48, "N/A");
		group.setString(22, "4");
		group.setString(107, "OPT");
		group.setString(350, "7");
		group.setString(351, "<desc2>|</desc2>");
		group.setString(54, "1");
		message.addGroup(group);

		
		message.getTrailer().setString(10, "217");
		return message;
	}
	
	public static String fixString2() {
		return "8=FIX.4.4|9=184|35=R|34=5|49=BUY|52=20190212-08:29:05.170|56=SELL1|" + 
				"131=QRID-1|146=2|" +
				"55=N/A|48=N/A|22=4|107=ELN|350=7|351=<desc>|</desc>|54=1|" + 
				"55=N/A|48=N/A|22=4|107=ELN|350=7|351=<desc2>|</desc2>|54=1|" +
				"10=053|";
	}

	public static Message message3() {
		Message message = new Message();
		
		message.getHeader().setString(8, "FIX.4.4");
		message.getHeader().setString(9, "125");
		message.getHeader().setString(35, "R");
		message.getHeader().setString(34, "5");
		message.getHeader().setString(49, "BUY");
		message.getHeader().setString(52, "20190212-08:29:05.170");
		message.getHeader().setString(56, "SELL1");
		
		message.setString(131, "QRID-1");
		Group group = new Group(146, 55, new int[] { 55, 48, 22, 107, 350, 351, 54 });
		group.setString(55, "N/A");
		group.setString(48, "N/A");
		group.setString(22, "4");
		group.setString(107, "FCN");
		group.setString(350, "2276");
		group.setString(351, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><FCN>"
				+ "<Underlyings><Underlying><ID>0001.HK</ID></Underlying>"
				+ "<Underlying><ID>0005.HK</ID></Underlying></Underlyings>"
				+ "<DeliveryDate>2016-02-29</DeliveryDate></FCN>");
		group.setString(54, "1");
		message.addGroup(group);

		group.setString(55, "N/A");
		group.setString(48, "N/A");
		group.setString(22, "4");
		group.setString(107, "ELN");
		group.setString(350, "733");
		group.setString(351, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ELN>"
				+ "<Underlyings><Underlying><ID>AAPL.OQ</ID></Underlying></Underlyings>"
				+ "<DeliveryDate>2016-12-19</DeliveryDate>"
				+ "</ELN>");
		group.setString(54, "1");
		message.addGroup(group);

		
		message.getTrailer().setString(9001, "217");
		message.getTrailer().setString(9000, "146.1.55");
		message.getTrailer().setString(10, "063");
		return message;
	}
}
