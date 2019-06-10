package org.afc.carril.fix.quickfix.standalone.filter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.function.BiFunction;

import org.afc.carril.fix.quickfix.standalone.util.FixTagResolver;
import org.afc.carril.fix.quickfix.standalone.util.modifier.TagModifier;
import org.afc.carril.fix.quickfix.standalone.util.modifier.XPathModifier;

import org.afc.carril.fix.quickfix.text.MessageUtil;
import org.afc.filter.AttributeAccessor;

import quickfix.FieldMap;
import quickfix.Message;

public class QuickFixAttributeAccessor implements AttributeAccessor<Message>{

	private static final int TEMP_TAG = 9999;

	private static TagModifier xpathModifier = new XPathModifier();
	
	@Override
	public String getString(Message attributes, String name) {
		return get(attributes, name, MessageUtil::getString);
	}

	@Override
	public BigDecimal getNumeric(Message attributes, String name) {
		return get(attributes, name, MessageUtil::getDecimal);
	}

	@Override
	public Date getDate(Message attributes, String name) {
		return null;
	}

	@Override
	public LocalDate getLocalDate(Message attributes, String name) {
		return get(attributes, name, MessageUtil::getUtcDateOnly);
	}

	@Override
	public LocalTime getLocalTime(Message attributes, String name) {
		return get(attributes, name, MessageUtil::getUtcTimeOnly);
	}

	@Override
	public LocalDateTime getLocalDateTime(Message attributes, String name) {
		return get(attributes, name, MessageUtil::getUtcTimeStamp);
	}

	@Override
	public OffsetDateTime getOffsetDateTime(Message attributes, String name) {
		return null;
	}

	@Override
	public ZonedDateTime getZonedDateTime(Message attributes, String name) {
		return null;
	}

	private static <R> R get(Message fieldMap, String name, BiFunction<FieldMap, Integer, R> accessor) {
		Message temp = new Message();
		temp.setString(TEMP_TAG, FixTagResolver.resolve(name, fieldMap));
		return accessor.apply(temp, TEMP_TAG);
	}
}
