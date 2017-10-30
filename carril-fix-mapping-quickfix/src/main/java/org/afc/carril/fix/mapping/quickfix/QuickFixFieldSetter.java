package org.afc.carril.fix.mapping.quickfix;

import java.math.BigDecimal;
import java.util.Date;

import org.afc.carril.fix.mapping.Setter;
import org.afc.carril.fix.mapping.schema.Type;
import org.afc.carril.message.FixMessage;
import org.afc.carril.transport.TransportException;

import quickfix.FieldMap;
import quickfix.Group;
import quickfix.Message;

public class QuickFixFieldSetter implements Setter<FixMessage, FieldMap, Object> {
	
	@SuppressWarnings("unchecked")
    private TypeSetter setter;
	
	private int field;
	
	public QuickFixFieldSetter(String index, Type type) {
		field = Integer.valueOf(index);
		switch (type) {
			case INT:
				setter = new IntTypeSetter();
				break;
			case STRING:
				setter = new StringTypeSetter();
				break;
			case DECIMAL:
				setter = new DecimalTypeSetter();
				break;
			case DOUBLE:
				setter = new DoubleTypeSetter();
				break;
			case DATETIMESTAMP:
				setter = new UTCDateTimestampTypeSetter();
				break;
			case DATETIME:
				setter = new UTCDatetimeTypeSetter();
				break;
			case DATE:
				setter = new UTCDateTypeSetter();
				break;
			case TIME:
				setter = new UTCTimeTypeSetter();
				break;
			case CHAR:
				setter = new CharTypeSetter();
				break;
			case BOOLEAN:
				setter = new BoolTypeSetter();
				break;
			case GROUP:
				setter = new RepeatingGroupTypeSetter();
				break;
			default:
				throw new TransportException("Unknown data type " + type); 
		}
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public void set(FixMessage source, FieldMap target, Object value) {
		try {
			if (value == null) {
				return;
			}
			setter.set(target, value);
		} catch (Exception e) {
			throw new TransportException("Error on setting [" + field + "] = " + value, e);
		}
	}

	private interface TypeSetter<T> {

		public void set(FieldMap target, T value);

	}

	private class IntTypeSetter implements TypeSetter<Integer> {

		@Override
		public void set(FieldMap target, Integer value) {
			target.setInt(field, value);
		}
	}

	private class BoolTypeSetter implements TypeSetter<Boolean> {

		@Override
		public void set(FieldMap target, Boolean value) {
			target.setBoolean(field, value);
		}
	}

	private class CharTypeSetter implements TypeSetter<Character> {

		@Override
		public void set(FieldMap target, Character value) {
			target.setChar(field, value);
		}
	}

	private class DecimalTypeSetter implements TypeSetter<BigDecimal> {

		@Override
		public void set(FieldMap target, BigDecimal value) {
			target.setDecimal(field, value);
		}
	}

	private class DoubleTypeSetter implements TypeSetter<Double> {

		@Override
		public void set(FieldMap target, Double value) {
			target.setDouble(field, value);
		}
	}

	private class StringTypeSetter implements TypeSetter<String> {

		@Override
		public void set(FieldMap target, String value) {
			target.setString(field, value);
		}
	}

	private class UTCTimeTypeSetter implements TypeSetter<Date> {

		@Override
		public void set(FieldMap target, Date value) {
			target.setUtcTimeOnly(field, value, true);
		}
	}

	private class UTCDateTypeSetter implements TypeSetter<Date> {

		@Override
		public void set(FieldMap target, Date value) {
			target.setUtcDateOnly(field, value);
		}
	}

	private class UTCDatetimeTypeSetter implements TypeSetter<Date> {

		@Override
		public void set(FieldMap target, Date value) {
			target.setUtcTimeStamp(field, value, false);
		}
	}

	private class UTCDateTimestampTypeSetter implements TypeSetter<Date> {

		@Override
		public void set(FieldMap target, Date value) {
			target.setUtcTimeStamp(field, value, true);
		}
	}

	private class RepeatingGroupTypeSetter implements TypeSetter<Message> {

		@Override
		public void set(FieldMap target, Message value) {
			Group group = new Group(field, 0);
			group.setFields(value);
			group.setGroups(value);
			target.addGroup(group);
		}
	}
}
