package org.afc.carril.fix.quickfix.text;

import java.util.Iterator;

import org.afc.carril.fix.tag.FixMsgType;
import org.afc.carril.fix.tag.FixTag;

import quickfix.Field;
import quickfix.FieldMap;
import quickfix.FieldNotFound;
import quickfix.Group;
import quickfix.Message;

public class QuickFixMsgStringWrapper {

	private Message message;
	
	public QuickFixMsgStringWrapper(Message message) {
	    this.message = message;
    }
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		buildMsgString(builder, message.getHeader());
		buildMsgString(builder, message);
		buildMsgString(builder, message.getTrailer());
		builder.setCharAt(builder.length() - 1, ']');
		return builder.toString();
	}

	private static void buildMsgString(StringBuilder builder, FieldMap fieldMap) {
		for (Iterator<Field<?>> itr = fieldMap.iterator(); itr.hasNext();) {
			Field<?> field = itr.next();
			int tag = field.getTag();
			FixTag fixTag = FixTag.fromID(tag);
		
			if (fieldMap.hasGroup(tag)) {
				continue;
			}


			if (fixTag == FixTag.MsgType) {
				String type = field.getObject().toString();
				buildFieldString(builder, fieldMap, tag, fixTag, FixMsgType.fromID(type).toString()) ;
			} else {
				buildFieldString(builder, fieldMap, tag, fixTag, field.getObject().toString());
			}
		}

		for (Iterator<Integer> key = fieldMap.groupKeyIterator(); key.hasNext();) {
			int tag = key.next();
			FixTag fixTag = FixTag.fromID(tag);
			try {
				buildFieldString(builder, fieldMap, tag, fixTag, String.valueOf(fieldMap.getInt(tag)));
            } catch (FieldNotFound e) {
				buildFieldString(builder, fieldMap, tag, fixTag, "?");
            }
			for (Group group : fieldMap.getGroups(tag)) {
				buildMsgString(builder, group);
			}
		}
		
	}	

	private static void buildFieldString(StringBuilder builder, FieldMap fieldMap, int tag, FixTag fixTag, String value) {
		if (fixTag == null) {
			builder.append(tag);
		} else {
			builder.append(fixTag);
		}
		builder.append('=').append(value);
		builder.append('|');
	}

}
