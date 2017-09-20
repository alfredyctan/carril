package org.afc.ferrocarril.fix.quickfix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import org.afc.ferrocarril.FixBaseMessage;
import org.afc.ferrocarril.fix.quickfix.text.QuickFixMsgStringWrapper;
import org.afc.ferrocarril.fix.tag.FixTag;
import org.afc.ferrocarril.message.FixMessage;
import org.afc.util.JUnit4Util;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import quickfix.Group;
import quickfix.Message;

public class StatelessFixConverterTest {

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
	public void testFormat() {
		JUnit4Util.startCurrentTest(getClass());
		StatelessQuickFixConverter fixConverter = new StatelessQuickFixConverter();
		
		FixBaseMessage fixMessage = createTestFixMessage1();

		Message expectMessage = createTestQuickFixMessage1();
		
		
		try {
			Message actualMessage = (Message) fixConverter.format(fixMessage);
			System.out.println("actual:");
			System.out.println(actualMessage);
			System.out.println("expect:");
			System.out.println(expectMessage);

			assertEquals(expectMessage.getString(FixTag.Product.id()),           actualMessage.getString(FixTag.Product.id()));
			assertEquals(expectMessage.getTrailer().getInt(FixTag.CIOrdID.id()),              actualMessage.getTrailer().getInt(FixTag.CIOrdID.id()));
			assertEquals(expectMessage.getDecimal(FixTag.OrderQty.id()),         actualMessage.getDecimal(FixTag.OrderQty.id()));
			assertTrue(expectMessage.getDouble(FixTag.Price.id())==             actualMessage.getDouble(FixTag.Price.id()));
			assertEquals(expectMessage.getHeader().getChar(FixTag.MsgType.id()),             actualMessage.getHeader().getChar(FixTag.MsgType.id()));
			assertEquals(expectMessage.getUtcDateOnly(FixTag.FutSettDate.id()),  actualMessage.getUtcDateOnly(FixTag.FutSettDate.id()));
			assertEquals(expectMessage.getUtcTimeStamp(FixTag.TradeDate.id()).getTime(),   actualMessage.getUtcTimeStamp(FixTag.TradeDate.id()).getTime());
			assertEquals(expectMessage.getHeader().getUtcTimeOnly(FixTag.TransactTime.id()), actualMessage.getHeader().getUtcTimeOnly(FixTag.TransactTime.id())); 
        } catch (Exception e) {
	        e.printStackTrace();
	        fail(e.getMessage());
        }

		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testFormatRepeatGroup() {
		JUnit4Util.startCurrentTest(getClass());
		StatelessQuickFixConverter fixConverter = new StatelessQuickFixConverter();
		
		FixBaseMessage input = createTestFixMessageRepeatingGroup();

		Message expect = createTestQuickFixMessageRepeatingGroup();
		
		try {
			Message actual = (Message) fixConverter.format(input);

			System.out.println("actual:");
			System.out.println(actual.toString().replace('\01', '|'));
			System.out.println("expect:");
			System.out.println(expect.toString().replace('\01', '|'));
			
			System.out.println("actual:");
			System.out.println(new QuickFixMsgStringWrapper(actual));
			System.out.println("expect:");
			System.out.println(new QuickFixMsgStringWrapper(expect));

	        assertEquals(expect.getString(FixTag.Product.id()),           actual.getString(FixTag.Product.id()));
			assertEquals(expect.getTrailer().getInt(FixTag.CIOrdID.id()),              actual.getTrailer().getInt(FixTag.CIOrdID.id()));
			assertEquals(expect.getDecimal(FixTag.OrderQty.id()),         actual.getDecimal(FixTag.OrderQty.id()));
			assertTrue(expect.getDouble(FixTag.Price.id())==             actual.getDouble(FixTag.Price.id()));
			assertEquals(expect.getHeader().getChar(FixTag.MsgType.id()),             actual.getHeader().getChar(FixTag.MsgType.id()));
			assertEquals(expect.getUtcDateOnly(FixTag.FutSettDate.id()),  actual.getUtcDateOnly(FixTag.FutSettDate.id()));
			assertEquals(expect.getUtcTimeStamp(FixTag.TradeDate.id()).getTime(),   actual.getUtcTimeStamp(FixTag.TradeDate.id()).getTime());
			assertEquals(expect.getHeader().getUtcTimeOnly(FixTag.TransactTime.id()), actual.getHeader().getUtcTimeOnly(FixTag.TransactTime.id()));

			assertEquals(
				expect.toString(), 
				actual.toString()
			);

        } catch (Exception e) {
	        e.printStackTrace();
	        fail(e.getMessage());
        }
		JUnit4Util.endCurrentTest(getClass());
	}
	@Test
	public void testParse() {
		JUnit4Util.startCurrentTest(getClass());
		StatelessQuickFixConverter fixConverter = new StatelessQuickFixConverter();
		
		Message input = createTestQuickFixMessage1();
		FixBaseMessage expectFixMessage = createTestFixMessage1();
		
		FixBaseMessage actualFixMessage = (FixBaseMessage) fixConverter.parse(input, FixBaseMessage.class);
		
		assertEquals(expectFixMessage.getProduct(), actualFixMessage.getProduct());
		assertEquals(expectFixMessage.getReferenceID(), actualFixMessage.getReferenceID());
		assertEquals(expectFixMessage.getAmount(), actualFixMessage.getAmount());
		assertEquals(expectFixMessage.getRate(), actualFixMessage.getRate());
		assertEquals(expectFixMessage.getContext().getMsgType(), actualFixMessage.getContext().getMsgType());
		assertEquals(expectFixMessage.getValueDate(), actualFixMessage.getValueDate());
		assertEquals(expectFixMessage.getTradeDate(), actualFixMessage.getTradeDate());
		assertEquals(expectFixMessage.getTradeTime(), actualFixMessage.getTradeTime());

		System.out.println("actual:");
		System.out.println(new XStream().toXML(actualFixMessage));
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testParseRepeatingGroup() {
		JUnit4Util.startCurrentTest(getClass());
		
		System.out.println(createTestQuickFixMessageRepeatingGroup().toString().replace('\u0001', ','));
		System.out.println();
		System.out.println();
		System.out.println(new QuickFixMsgStringWrapper(createTestQuickFixMessageRepeatingGroup()));

		StatelessQuickFixConverter fixConverter = new StatelessQuickFixConverter();
		Message input = createTestQuickFixMessageRepeatingGroup();
		input.getHeader().setString(8, "Fix.4.2");
		input.getHeader().setString(35, "R");
		input.getHeader().setString(49, "AFCAPFix");
		input.getHeader().setString(56, "AFC2DEV1");
		
		
		FixBaseMessage expectFixMessage = createTestFixMessageRepeatingGroup();
		FixBaseMessage actualFixMessage = null;
		try {
			actualFixMessage = (FixBaseMessage) fixConverter.parse(input, FixBaseMessage.class);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		System.out.println("actual:");
		System.out.println(new XStream().toXML(actualFixMessage));

		System.out.println("expected:");
		System.out.println(new XStream().toXML(expectFixMessage));
		
		assertEquals(expectFixMessage.getProduct(), actualFixMessage.getProduct());
		assertEquals(expectFixMessage.getReferenceID(), actualFixMessage.getReferenceID());
		assertEquals(expectFixMessage.getAmount(), actualFixMessage.getAmount());
		assertEquals(expectFixMessage.getRate(), actualFixMessage.getRate());
		assertEquals(expectFixMessage.getContext().getMsgType(), actualFixMessage.getContext().getMsgType());
		assertEquals(expectFixMessage.getValueDate(), actualFixMessage.getValueDate());
		assertEquals(expectFixMessage.getTradeDate(), actualFixMessage.getTradeDate());
		assertEquals(expectFixMessage.getTradeTime(), actualFixMessage.getTradeTime());

		assertEquals(
				new XStream().toXML(expectFixMessage), 
				new XStream().toXML(actualFixMessage)
		);
		JUnit4Util.endCurrentTest(getClass());
	}
	
	private FixBaseMessage createTestFixMessage1() {
		FixBaseMessage fixMessage = new FixBaseMessage();
		fixMessage.setContext(
			new FixMessage.Context(
				"Fix.4.2", "R", "AFCAPFix", "AFC2DEV1", 100, createDatetime() 
			)
		);
		fixMessage.setProduct("SPT");
		fixMessage.setReferenceID(1234567);
		fixMessage.setAmount(new BigDecimal(100000.0));
		fixMessage.setRate(0.789356);
		fixMessage.setMsgType("R");
		fixMessage.setValueDate(createDate());
		fixMessage.setTradeDate(createDatetime());
		fixMessage.setTradeTime(createTime());

		return fixMessage;
	}

	private FixBaseMessage createTestFixMessageRepeatingGroup() {
		FixBaseMessage fixMessage = new FixBaseMessage();
		fixMessage.setContext(new FixMessage.Context(
			"Fix.4.2", "R", "AFCAPFix", "AFC2DEV1" , 100, createDatetime()
		));
		fixMessage.setProduct("SPT");
		fixMessage.setReferenceID(1234567);
		fixMessage.setAmount(new BigDecimal(100000.0));
		fixMessage.setRate(0.789356);
		fixMessage.setMsgType("R");
		fixMessage.setValueDate(createDate());
		fixMessage.setTradeDate(createDatetime());
		fixMessage.setTradeTime(createTime());

		FixBaseMessage fixMessage1 = new FixBaseMessage();
		fixMessage1.setProduct("SPT");
//		fixMessage1.setReferenceID(11111);
		fixMessage1.setAmount(new BigDecimal(100000.0));
		fixMessage1.setRate(0.789356);
//		fixMessage1.setMsgType("R");
		
		FixBaseMessage fixMessage2 = new FixBaseMessage();
		fixMessage2.setProduct("SPT");
//		fixMessage2.setReferenceID(22222);
		fixMessage2.setAmount(new BigDecimal(100000.0));

		FixBaseMessage fixMessage3 = new FixBaseMessage();
		fixMessage3.setProduct("SPT");
//		fixMessage3.setReferenceID(33333);
		
		List<FixBaseMessage> messages = new LinkedList<FixBaseMessage>();
		messages.add(fixMessage1);
		messages.add(fixMessage2);
		messages.add(fixMessage3);
		
		
		FixBaseMessage line1 = new FixBaseMessage();
		line1.setProduct("SPT");
		FixBaseMessage line2 = new FixBaseMessage();
		line2.setProduct("SWP");
		FixBaseMessage line3 = new FixBaseMessage();
		line3.setProduct("FWD");
		FixBaseMessage line4 = new FixBaseMessage();
		line4.setProduct("TMO");
		FixBaseMessage line5 = new FixBaseMessage();
		line5.setProduct("OTK");

		List<FixBaseMessage> lists = new LinkedList<FixBaseMessage>();
		lists.add(line1);
		lists.add(line2);
		lists.add(line3);
		lists.add(line4);
		lists.add(line5);
		
		
		fixMessage.setMessages(messages);
		fixMessage.setLists(lists);
		
		return fixMessage;
	}
	
	
	private Message createTestQuickFixMessage1() {
		Message message = new Message();
		message.getHeader().setString(FixTag.BeginString.id(), "Fix.4.2");
		message.getHeader().setString(FixTag.MsgType.id(), "R");
		message.getHeader().setString(FixTag.SenderCompID.id(), "AFCAPFix");
		message.getHeader().setString(FixTag.TargetCompID.id(), "AFC2DEV1");
		message.getHeader().setInt(FixTag.MsgSeqNum.id(), 100);
		message.getHeader().setUtcTimeStamp(FixTag.SendingTime.id(), createDatetime(), true);

		message.setString(FixTag.Product.id(), "SPT");
		message.getTrailer().setInt(FixTag.CIOrdID.id(), 1234567);
		message.setDecimal(FixTag.OrderQty.id(), new BigDecimal(100000.0));
		message.setDouble(FixTag.Price.id(), 0.789356);
		message.setUtcDateOnly(FixTag.FutSettDate.id(), createDate());
		message.setUtcTimeStamp(FixTag.TradeDate.id(), createDatetime(), true);
		message.getHeader().setUtcTimeOnly(FixTag.TransactTime.id(), createTime(), true);

		return message;
	}

	private Message createTestQuickFixMessageRepeatingGroup() {
		Message message = createTestQuickFixMessage1();
		
		Message message1 = createTestQuickFixMessage1();
		message1.getTrailer().setInt(FixTag.CIOrdID.id(), 11111);
		message1.removeField(FixTag.FutSettDate.id());
		message1.removeField(FixTag.TradeDate.id());
		message1.getHeader().removeField(FixTag.TransactTime.id());
		message1.getHeader().removeField(FixTag.MsgType.id());
		
		Message message2 = createTestQuickFixMessage1();
		message2.getTrailer().setInt(FixTag.CIOrdID.id(), 22222);
		message2.removeField(FixTag.Price.id());
		message2.getHeader().removeField(FixTag.MsgType.id());
		message2.removeField(FixTag.FutSettDate.id());
		message2.removeField(FixTag.TradeDate.id());
		message2.getHeader().removeField(FixTag.TransactTime.id());
		message2.getHeader().removeField(FixTag.MsgType.id());

		Message message3 = createTestQuickFixMessage1();
		message3.getTrailer().setInt(FixTag.CIOrdID.id(), 33333);
		message3.removeField(FixTag.OrderQty.id());
		message3.removeField(FixTag.Price.id());
		message3.getHeader().removeField(FixTag.MsgType.id());
		message3.removeField(FixTag.FutSettDate.id());
		message3.removeField(FixTag.TradeDate.id());
		message3.getHeader().removeField(FixTag.TransactTime.id());
		message3.getHeader().removeField(FixTag.MsgType.id());

		Group group1 = new Group(FixTag.NoOrder.id(), 0);
		group1.setFields(message1);
		message.addGroup(group1);

		Group group2 = new Group(FixTag.NoOrder.id(), 0);
		group2.setFields(message2);
		message.addGroup(group2);

		Group group3 = new Group(FixTag.NoOrder.id(), 0);
		group3.setFields(message3);
		message.addGroup(group3);

		Group line1 = new Group(FixTag.LinesOfText.id(), 0);
		line1.setString(FixTag.Product.id(), "SPT");
		message.addGroup(line1);
		
		Group line2 = new Group(FixTag.LinesOfText.id(), 0);
		line2.setString(FixTag.Product.id(), "SWP");
		message.addGroup(line2);

		Group line3 = new Group(FixTag.LinesOfText.id(), 0);
		line3.setString(FixTag.Product.id(), "FWD");
		message.addGroup(line3);

		Group line4 = new Group(FixTag.LinesOfText.id(), 0);
		line4.setString(FixTag.Product.id(), "TMO");
		message.addGroup(line4);

		Group line5 = new Group(FixTag.LinesOfText.id(), 0);
		line5.setString(FixTag.Product.id(), "OTK");
		message.addGroup(line5);

		return message;
	}

	private Date createDate() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			format.setTimeZone(TimeZone.getTimeZone("UTC"));
	        return format.parse("20111104");
        } catch (ParseException e) {
	        e.printStackTrace();
        }
        return null;
	}

	private Date createDatetime() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HH:mm:ss.SSS");
			format.setTimeZone(TimeZone.getTimeZone("UTC"));
	        return format.parse("20111104-13:43:32.678");
        } catch (ParseException e) {
	        e.printStackTrace();
        }
        return null;
		
	}

	private Date createTime() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");
			format.setTimeZone(TimeZone.getTimeZone("UTC"));
	        return format.parse("13:43:32.678");
        } catch (ParseException e) {
	        e.printStackTrace();
        }
        return null;
	}
}
