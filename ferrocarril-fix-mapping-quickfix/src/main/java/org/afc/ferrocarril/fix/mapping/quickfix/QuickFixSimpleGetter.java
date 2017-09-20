package org.afc.ferrocarril.fix.mapping.quickfix;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.afc.ferrocarril.fix.mapping.Getter;
import org.afc.ferrocarril.fix.mapping.schema.Type;
import org.afc.ferrocarril.transport.TransportException;

import quickfix.FieldMap;
import quickfix.FieldNotFound;
import quickfix.Group;

public class QuickFixSimpleGetter implements Getter<FieldMap> {
	
	private TypeGetter<?> getter;
	
	private int field;
	
	public QuickFixSimpleGetter(String index, Type type) {
		field = Integer.valueOf(index);
		switch (type) {
			case INT:
				getter = new IntTypeGetter();
				break;
			case STRING:
				getter = new StringTypeGetter();
				break;
			case DECIMAL:
				getter = new DecimalTypeGetter();
				break;
			case DOUBLE:
				getter = new DoubleTypeGetter();
				break;
			case DATETIMESTAMP:
				getter = new UTCDateTimestampTypeGetter();
				break;
			case DATETIME:
				getter = new UTCDatetimeTypeGetter();
				break;
			case DATE:
				getter = new UTCDateTypeGetter();
				break;
			case TIME:
				getter = new UTCTimeTypeGetter();
				break;
			case CHAR:
				getter = new CharTypeGetter();
				break;
			case BOOLEAN:
				getter = new BoolTypeGetter();
				break;
			case GROUP:
				getter = new RepeatingGroupTypeGetter();
				break;
			default:
				throw new TransportException("Unknown data type " + type); 
		}
    }
	
	@Override
	public Object get(FieldMap source) {
		try {
			return getter.get(source);
		} catch (FieldNotFound e) {
			System.err.println("ATYC : " + source);
			throw new TransportException(e.getMessage());
		} catch (Exception e) {
			throw new TransportException(e);
		}
	}

	private interface TypeGetter<T> {

		public T get(FieldMap source) throws FieldNotFound;

	}

	private class IntTypeGetter implements TypeGetter<Integer> {

		@Override
		public Integer get(FieldMap source) throws FieldNotFound {
			return source.getInt(field);
		}
	}

	private class BoolTypeGetter implements TypeGetter<Boolean> {

		@Override
		public Boolean get(FieldMap source) throws FieldNotFound {
			return source.getBoolean(field);
		}
	}

	private class CharTypeGetter implements TypeGetter<Character> {

		@Override
		public Character get(FieldMap source) throws FieldNotFound {
			return source.getChar(field);
		}
	}

	private class DecimalTypeGetter implements TypeGetter<BigDecimal> {

		@Override
		public BigDecimal get(FieldMap source) throws FieldNotFound {
			return source.getDecimal(field);
		}
	}

	private class DoubleTypeGetter implements TypeGetter<Double> {

		@Override
		public Double get(FieldMap source) throws FieldNotFound {
			return source.getDouble(field);
		}
	}

	private class StringTypeGetter implements TypeGetter<String> {

		@Override
		public String get(FieldMap source) throws FieldNotFound {
			return source.getString(field);
		}
	}

	private class UTCTimeTypeGetter implements TypeGetter<Date> {

		@Override
		public Date get(FieldMap source) throws FieldNotFound {
			return source.getUtcTimeOnly(field);
		}
	}

	private class UTCDateTypeGetter implements TypeGetter<Date> {

		@Override
		public Date get(FieldMap source) throws FieldNotFound {
			return source.getUtcDateOnly(field);
		}
	}

	private class UTCDatetimeTypeGetter implements TypeGetter<Date> {

		@Override
		public Date get(FieldMap source) throws FieldNotFound {
			return source.getUtcTimeStamp(field);
		}
	}

	private class UTCDateTimestampTypeGetter implements TypeGetter<Date> {

		@Override
		public Date get(FieldMap source) throws FieldNotFound {
			return source.getUtcTimeStamp(field);
		}
	}

	private class RepeatingGroupTypeGetter implements TypeGetter<List<Group>> {

		@Override
		public List<Group> get(FieldMap source) throws FieldNotFound {
			return source.getGroups(field);
		}
	}
}
