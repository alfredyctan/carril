package org.afc.carril.fix.quickfix;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.afc.carril.annotation.Carril.Section;
import org.afc.carril.annotation.Carril.Wire;
import org.afc.carril.converter.Converter;
import org.afc.carril.fix.tag.FixTag;
import org.afc.carril.message.FixMessage;
import org.afc.carril.text.FixDateTimestampFormat;
import org.afc.carril.text.FixTimestampFormat;
import org.afc.carril.transport.AccessorMapping;
import org.afc.carril.transport.TransportException;
import org.afc.carril.transport.util.AccessorMappingRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.afc.util.ObjectUtil;

import quickfix.BytesField;
import quickfix.FieldMap;
import quickfix.Group;
import quickfix.Message;
import quickfix.Message.Header;

public class StaticQuickFixConverter implements Converter<Message, FixMessage> {

	private static final Logger logger = LoggerFactory.getLogger(StaticQuickFixConverter.class);
	
	@Override
	public FixMessage parse(Message object, Class<? extends FixMessage> clazz) throws TransportException {
		return parseMessage(object, clazz);
	}

	public FixMessage parseMessage(Message message, Class<? extends FixMessage> clazz) throws TransportException {
        try {
	        FixMessage fixFormat = (FixMessage)clazz.newInstance();
	        FieldMap header = message.getHeader();
	        parseFieldMap(header, fixFormat, AccessorMappingRegistry.getMappings(Wire.Fix, fixFormat));

	        parseFieldMap(message, fixFormat, AccessorMappingRegistry.getMappings(Wire.Fix, fixFormat));

	        FieldMap trailer = message.getTrailer();
	        parseFieldMap(trailer, fixFormat, AccessorMappingRegistry.getMappings(Wire.Fix, fixFormat));
	        
	        fixFormat.setContext(
	        	new FixMessage.Context(
	        		header.getString(FixTag.BeginString.id()),
	        		header.getString(FixTag.SenderCompID.id()),
	        		header.getString(FixTag.TargetCompID.id()),
	        		header.getString(FixTag.MsgType.id()),
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

    public void parseFieldMap(FieldMap message, Object fixFormat, Map<String, AccessorMapping> mappings) throws TransportException {

		if (mappings == null) {
			return;
		}

		int tagID = 0;
		String name = null;
		try {
	        for (AccessorMapping mapping : mappings.values()) {
	        	try {
	        		tagID = Integer.parseInt(mapping.getFieldName());
	        	} catch (NumberFormatException e) {
	        		tagID = 0;
	        		logger.debug("{} is unparsable to fix tag", mapping.getFieldName());
	        	}
				if (tagID == 0 || !message.isSetField(tagID)) {
					continue;
				}
				Class<?> declareClass = mapping.getDeclareClass();
				//never support primitive type to prevent unassigned primitive type to publish out with 0 value;
				if (String.class.equals(declareClass)) {
					mapping.getSetMethod().invoke(fixFormat, message.getString(tagID));
				} else if (BigDecimal.class.equals(declareClass)) {
					mapping.getSetMethod().invoke(fixFormat, message.getDecimal(tagID));
				} else if (Integer.class.equals(declareClass) || int.class.equals(declareClass)) {
					mapping.getSetMethod().invoke(fixFormat, message.getInt(tagID));
				} else if (LocalDateTime.class.equals(declareClass)) {
					mapping.getSetMethod().invoke(fixFormat, message.getUtcTimeStamp(tagID));
				} else if (LocalDate.class.equals(declareClass)) {
					mapping.getSetMethod().invoke(fixFormat, message.getUtcDateOnly(tagID));
				} else if (LocalTime.class.equals(declareClass)) {
					mapping.getSetMethod().invoke(fixFormat, message.getUtcTimeOnly(tagID));
				} else if (Double.class.equals(declareClass) || double.class.equals(declareClass) ) {
					mapping.getSetMethod().invoke(fixFormat, message.getDouble(tagID));
				} else if (Boolean.class.equals(declareClass) || boolean.class.equals(declareClass)) {
					mapping.getSetMethod().invoke(fixFormat, message.getBoolean(tagID));
				} else if (Character.class.equals(declareClass) || char.class.equals(declareClass)) {
					mapping.getSetMethod().invoke(fixFormat, message.getChar(tagID));
				} else if (byte[].class.equals(declareClass)) {
					mapping.getSetMethod().invoke(fixFormat, message.getField(new BytesField(tagID)).getValue());
				} else if (List.class.equals(declareClass)) {
					List<Object> lists  = new LinkedList<Object>();
					for (Group group : message.getGroups(tagID)) {
						Object format = ObjectUtil.<Class<Object>>cast(mapping.getImplClass()).newInstance();
						parseFieldMap(group, format, AccessorMappingRegistry.getMappings(Wire.Fix, format));
						lists.add(format);
					}
					mapping.getSetMethod().invoke(fixFormat, lists);
				} else {
					throw new TransportException("Invalid data type [" + declareClass + "]. " + name + '(' + tagID + ')');
				}
	        }
        } catch (TransportException e) {
    		throw e;
        } catch (Exception e) {
       		throw new TransportException("Error on parsing message. " + name + '(' + tagID + ')', e);
        }
	}

	@Override
	public Message format(FixMessage object) throws TransportException {
		FixMessage fixFormat = (FixMessage)object;
		return formatMessage(fixFormat);
	}

	private Message formatMessage(FixMessage fixFormat) throws TransportException {
		Message message = new Message();
		
		Header header = message.getHeader();
		
		header.setString(FixTag.BeginString.id(), fixFormat.getContext().getProtocolVersion());
		header.setString(FixTag.MsgType.id(), fixFormat.getContext().getMsgType());
		header.setString(FixTag.SenderCompID.id(), fixFormat.getContext().getSenderCompID());
		header.setString(FixTag.TargetCompID.id(), fixFormat.getContext().getTargetCompID());
		
		formatFieldMap(fixFormat, message.getHeader(), AccessorMappingRegistry.getMappings(Wire.Fix, Section.Header, fixFormat));
		formatFieldMap(fixFormat, message, AccessorMappingRegistry.getMappings(Wire.Fix, Section.Body, fixFormat));
		formatFieldMap(fixFormat, message.getTrailer(), AccessorMappingRegistry.getMappings(Wire.Fix, Section.Trailer, fixFormat));
		
		return message;
	}

    private void formatFieldMap(Object fixFormat, FieldMap fieldMap, Map<String, AccessorMapping> mappings) throws TransportException {
		if (mappings == null) {
			return ;
		}
		
		int tagID = 0;
		String name = null;
		
		try {
			for (AccessorMapping mapping : mappings.values()) {
				Object value = mapping.getGetMethod().invoke(fixFormat);
	        	try {
	        		tagID = Integer.parseInt(mapping.getFieldName());
	        	} catch (NumberFormatException e) {
	        		tagID = 0;
	        		logger.debug("{} is unparsable to fix tag", mapping.getFieldName());
	        	}
				if (tagID == 0 || value == null) {
					continue;
				}
				
				Class<?> declareClass = mapping.getDeclareClass();
				if (String.class.equals(declareClass)) {
					fieldMap.setString(tagID, (String)value);
				} else if (BigDecimal.class.equals(declareClass)) {
					fieldMap.setDecimal(tagID, (BigDecimal)value);
				} else if (Integer.class.equals(declareClass) || int.class.equals(declareClass)) {
					fieldMap.setInt(tagID, (Integer)value);
				} else if (LocalDateTime.class.equals(declareClass)) {
					if (mapping.getFormat() instanceof FixDateTimestampFormat) {
						fieldMap.setUtcTimeStamp(tagID, (LocalDateTime)value, true);
					} else {
						fieldMap.setUtcTimeStamp(tagID, (LocalDateTime)value);
					}
				} else if (LocalDate.class.equals(declareClass)) {
					fieldMap.setUtcDateOnly(tagID, (LocalDate)value);
				} else if (LocalTime.class.equals(declareClass)) {
					if (mapping.getFormat() instanceof FixTimestampFormat) {
						fieldMap.setUtcTimeOnly(tagID, (LocalTime)value, true);
					} else {
						fieldMap.setUtcTimeOnly(tagID, (LocalTime)value);
					}
				} else if (Double.class.equals(declareClass) || double.class.equals(declareClass)) {
					fieldMap.setDouble(tagID, (Double)value);
				} else if (Boolean.class.equals(declareClass) || boolean.class.equals(declareClass)) {
					fieldMap.setBoolean(tagID, (Boolean)value);
				} else if (Character.class.equals(declareClass) || char.class.equals(declareClass)) {
					fieldMap.setChar(tagID, (Character)value);
				} else if (byte[].class.equals(declareClass)) {
					fieldMap.setBytes(tagID, (byte[])value);
				} else if (List.class.equals(declareClass)) {
					List<Object> lists  = ObjectUtil.<List<Object>>cast(value); 
					for (Object nestedObject : lists) {
						Group group = new Group(tagID, 0);
						formatFieldMap(nestedObject, group, AccessorMappingRegistry.getMappings(Wire.Fix, Section.Body, nestedObject));
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
