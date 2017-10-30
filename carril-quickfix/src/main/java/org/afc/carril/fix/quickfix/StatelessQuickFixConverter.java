package org.afc.carril.fix.quickfix;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.afc.carril.converter.Converter;
import org.afc.carril.fix.tag.FixTag;
import org.afc.carril.message.FixMessage;
import org.afc.carril.message.QuickFixMessage;
import org.afc.carril.text.UTCDateFormat;
import org.afc.carril.text.UTCTimeFormat;
import org.afc.carril.transport.AccessorMapping;
import org.afc.carril.transport.TransportException;
import org.afc.util.ObjectUtil;

import quickfix.FieldMap;
import quickfix.Group;
import quickfix.Message;
import quickfix.Message.Header;

public class StatelessQuickFixConverter implements Converter<Message, QuickFixMessage> {

	@Override
	public QuickFixMessage parse(Message object, Class<? extends QuickFixMessage> clazz) throws TransportException {
		return parseMessage(object, clazz);
	}

	public QuickFixMessage parseMessage(Message message, Class<? extends QuickFixMessage> clazz) throws TransportException {
        try {
	        QuickFixMessage fixFormat = (QuickFixMessage)clazz.newInstance();
	        FieldMap header = message.getHeader();
	        parseFieldMap(header, fixFormat, fixFormat.getFixHeaderMap());

	        parseFieldMap(message, fixFormat, fixFormat.getFixMessageMap());

	        FieldMap trailer = message.getTrailer();
	        parseFieldMap(trailer, fixFormat, fixFormat.getFixTrailerMap());

	        fixFormat.setContext(
	        	new FixMessage.Context(
	        		header.getString(FixTag.BeginString.id()),
	        		header.getString(FixTag.MsgType.id()),
	        		header.getString(FixTag.SenderCompID.id()),
	        		header.getString(FixTag.TargetCompID.id()),
	        		header.getInt(FixTag.MsgSeqNum.id()),
	        		header.getUtcTimeStamp(FixTag.SendingTime.id())
	        	)
	        );
	        return fixFormat;
        } catch (Exception e) {
        	if (e instanceof TransportException) {
        		throw (TransportException)e;
        	} else {
        		throw new TransportException("Error on parsing message. ", e);
        	}
        }
	}

    public void parseFieldMap(FieldMap message, QuickFixMessage fixFormat, Map<String, AccessorMapping> mappings) throws TransportException {

		if (mappings == null) {
			return;
		}

		int tagID = 0;
		String name = null;
		try {
	        for (AccessorMapping mapping : mappings.values()) {
	        	name = mapping.getFieldName();
				tagID = FixTag.valueOf(name).id();
				if (!message.isSetField(tagID)) {
					continue;
				}
				Class<?> declareClass = mapping.getDeclareClass();
				//never support primitive type to prevent unassigned primitive type to publish out with 0 value;
				if (String.class.equals(declareClass)) {
					mapping.getSetMethod().invoke(fixFormat, message.getString(tagID));
				} else if (BigDecimal.class.equals(declareClass)) {
					mapping.getSetMethod().invoke(fixFormat, message.getDecimal(tagID));
				} else if (Integer.class.equals(declareClass)) {
					mapping.getSetMethod().invoke(fixFormat, message.getInt(tagID));
				} else if (Double.class.equals(declareClass)) {
					mapping.getSetMethod().invoke(fixFormat, message.getDouble(tagID));
				} else if (Boolean.class.equals(declareClass)) {
					mapping.getSetMethod().invoke(fixFormat, message.getBoolean(tagID));
				} else if (Character.class.equals(declareClass)) {
					mapping.getSetMethod().invoke(fixFormat, message.getChar(tagID));
				} else if (Date.class.equals(declareClass)) {
					if (mapping.getFormat() instanceof UTCDateFormat) {
						mapping.getSetMethod().invoke(fixFormat, message.getUtcDateOnly(tagID));
					} else if (mapping.getFormat() instanceof UTCTimeFormat) {
						mapping.getSetMethod().invoke(fixFormat, message.getUtcTimeOnly(tagID));
					} else {
						mapping.getSetMethod().invoke(fixFormat, message.getUtcTimeStamp(tagID));
					}
				} else if (List.class.equals(declareClass)) {
					List<QuickFixMessage> lists  = new LinkedList<QuickFixMessage>();
					for (Group group : message.getGroups(tagID)) {
						QuickFixMessage format = ObjectUtil.<Class<QuickFixMessage>>cast(mapping.getImplClass()).newInstance();
						parseFieldMap(group, format, format.getFixMessageMap());
						lists.add(format);
					}
					mapping.getSetMethod().invoke(fixFormat, lists);
				} else {
					throw new TransportException("Invalid data type [" + declareClass + "]. " + name + '(' + tagID + ')');
				}
	        }
        } catch (Exception e) {
        	if (e instanceof TransportException) {
        		throw (TransportException)e;
        	} else {
        		throw new TransportException("Error on parsing message. " + name + '(' + tagID + ')', e);
        	}
        }
	}

	@Override
	public Message format(QuickFixMessage object) throws TransportException {
		QuickFixMessage fixFormat = (QuickFixMessage)object;
		return formatMessage(fixFormat);
	}

	private Message formatMessage(QuickFixMessage fixFormat) throws TransportException {
		Message message = new Message();
		
		Header header = message.getHeader();
		
		header.setString(FixTag.BeginString.id(), fixFormat.getContext().getProtocolVersion());
		header.setString(FixTag.MsgType.id(), fixFormat.getContext().getMsgType());
		header.setString(FixTag.SenderCompID.id(), fixFormat.getContext().getSenderCompID());
		header.setString(FixTag.TargetCompID.id(), fixFormat.getContext().getTargetCompID());
		header.setInt(FixTag.MsgSeqNum.id(), fixFormat.getContext().getMsgSeqNum());
		header.setUtcTimeStamp(FixTag.SendingTime.id(), fixFormat.getContext().getSendingTime(), true);
		
		formatFieldMap(fixFormat, message.getHeader(), fixFormat.getFixHeaderMap());
		formatFieldMap(fixFormat, message, fixFormat.getFixMessageMap());
		formatFieldMap(fixFormat, message.getTrailer(), fixFormat.getFixTrailerMap());
		
		return message;
	}

    private void formatFieldMap(QuickFixMessage fixFormat, FieldMap fieldMap, Map<String, AccessorMapping> mappings) throws TransportException {
		if (mappings == null) {
			return ;
		}
		
		int tagID = 0;
		String name = null;
		
		try {
			for (AccessorMapping mapping : mappings.values()) {
				Object value = mapping.getGetMethod().invoke(fixFormat);
				if (value == null) {
					continue;
				}
				
				name = mapping.getFieldName();
				tagID = FixTag.valueOf(name).id();
				Class<?> declareClass = mapping.getDeclareClass();

				if (String.class.equals(declareClass)) {
					fieldMap.setString(tagID, (String)value);
				} else if (BigDecimal.class.equals(declareClass)) {
					fieldMap.setDecimal(tagID, (BigDecimal)value);
				} else if (Integer.class.equals(declareClass)) {
					fieldMap.setInt(tagID, (Integer)value);
				} else if (Double.class.equals(declareClass)) {
					fieldMap.setDouble(tagID, (Double)value);
				} else if (Boolean.class.equals(declareClass)) {
					fieldMap.setBoolean(tagID, (Boolean)value);
				} else if (Character.class.equals(declareClass)) {
					fieldMap.setChar(tagID, (Character)value);
				} else if (Date.class.equals(declareClass)) {
					if (mapping.getFormat() instanceof UTCDateFormat) {
						fieldMap.setUtcDateOnly(tagID, (Date)value);
					} else if (mapping.getFormat() instanceof UTCTimeFormat) {
						fieldMap.setUtcTimeOnly(tagID, (Date)value, true);
					} else {
						fieldMap.setUtcTimeStamp(tagID, (Date)value, true);
					}
				} else if (List.class.equals(declareClass)) {
					List<QuickFixMessage> lists  = ObjectUtil.<List<QuickFixMessage>>cast(value); 
					for (QuickFixMessage subQuickFixFormat: lists) {
						Group group = new Group(tagID, 0);
						formatFieldMap(subQuickFixFormat, group, subQuickFixFormat.getFixMessageMap());
						fieldMap.addGroup(group);
					}
				} else {
					throw new TransportException("Invalid data type [" + declareClass + "]. " + name + '(' + tagID + ')');
				}
			}
			return;
        } catch (Exception e) {
        	if (e instanceof TransportException) {
        		throw (TransportException)e;
        	} else {
        		throw new TransportException("Error on formatting message.", e);
        	}
        }
	}
	
	
	
}
