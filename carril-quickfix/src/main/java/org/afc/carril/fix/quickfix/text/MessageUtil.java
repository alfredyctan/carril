package org.afc.carril.fix.quickfix.text;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.BiFunction;

import quickfix.FieldMap;
import quickfix.FieldNotFound;
import quickfix.Group;
import quickfix.Message;

public class MessageUtil {

	public static String getString(Message message, int tag) {
		return get(message, tag, MessageUtil::getString);
	}

	public static BigDecimal getNumeric(Message message, int tag) {
		return get(message, tag, MessageUtil::getDecimal);
	}

	public static LocalDate getLocalDate(Message message, int tag) {
		return get(message, tag, MessageUtil::getUtcDateOnly);
	}

	public static LocalTime getLocalTime(Message message, int tag) {
		return get(message, tag, MessageUtil::getUtcTimeOnly);
	}

	public static LocalDateTime getLocalDateTime(Message message, int tag) {
		return get(message, tag, MessageUtil::getUtcTimeStamp);
	}

	public static Group getGroup(Message message, int num, int groupTag) {
		Group value = getGroupValue(message, num, groupTag);
		if (value != null) {
			return value;
		}
		value = getGroupValue(message.getHeader(), num, groupTag);
		if (value != null) {
			return value;
		}
		return getGroupValue(message.getTrailer(), num, groupTag);
	}

	private static Group getGroupValue(FieldMap fieldMap, int num, int groupTag) {
		if (fieldMap == null) {
			return null;
		}
		try {
			return fieldMap.getGroup(num, groupTag);
		} catch (FieldNotFound e) {
			return null;
		}
	}
	
	public static <R> R get(Message fieldMap, int tag, BiFunction<FieldMap, Integer, R> accessor) {
		R value = getField(fieldMap, tag, accessor);
		if (value != null) {
			return value;
		}
		value = getField(fieldMap.getHeader(), tag, accessor);
		if (value != null) {
			return value;
		}
		value = getField(fieldMap.getTrailer(), tag, accessor);
		if (value != null) {
			return value;
		}
		return null;
	}	

	public static <R> R getField(FieldMap fieldMap, int tag, BiFunction<FieldMap, Integer, R> accessor) {
		if (fieldMap == null) {
			return null;
		}
		return accessor.apply(fieldMap, tag);
	}	

	public static String getString(FieldMap fieldMap, int tag) {
		try {
			return fieldMap.getString(tag);
		} catch (FieldNotFound e) {
			return null;
		}
	}

	public static BigDecimal getDecimal(FieldMap fieldMap, int tag) {
		try {
			return fieldMap.getDecimal(tag);
		} catch (FieldNotFound e) {
			return null;
		}
	}

	public static LocalDate getUtcDateOnly(FieldMap fieldMap, int tag) {
		try {
			return fieldMap.getUtcDateOnly(tag);
		} catch (FieldNotFound e) {
			return null;
		}
	}

	public static LocalTime getUtcTimeOnly(FieldMap fieldMap, int tag) {
		try {
			return fieldMap.getUtcTimeOnly(tag);
		} catch (FieldNotFound e) {
			return null;
		}
	}

	public static LocalDateTime getUtcTimeStamp(FieldMap fieldMap, int tag) {
		try {
			return fieldMap.getUtcTimeStamp(tag);
		} catch (FieldNotFound e) {
			return null;
		}
	}
	
}
