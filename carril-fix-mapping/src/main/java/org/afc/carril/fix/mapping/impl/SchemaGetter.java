package org.afc.carril.fix.mapping.impl;

import java.math.BigDecimal;

import org.afc.carril.fix.mapping.Getter;
import org.afc.carril.fix.mapping.schema.Type;

import org.afc.carril.transport.TransportException;
import org.afc.util.DateUtil;


public class SchemaGetter<S> implements Getter<S> {

	private Object value;
	
	public SchemaGetter(String index, Type type) {
		switch (type) {
			case INT:
				value = Integer.valueOf(index);
				break;
			case STRING:
				value = index;
				break;
			case DECIMAL:
				value = new BigDecimal(index);
				break;
			case DOUBLE:
				value = Double.valueOf(index);
				break;
			case DATETIMESTAMP:
				value = DateUtil.localDateTime(index);
				break;
			case DATETIME:
				value = DateUtil.localDateTime(index);
				break;
			case DATE:
				value = DateUtil.localDate(index);
				break;
			case TIME:
				value = DateUtil.localTime(index);
				break;
			case TIMESTAMP:
				value = DateUtil.localTime(index);
				break;
			case CHAR:
				value = index.charAt(0);
				break;
			case BOOLEAN:
				value = Boolean.valueOf(index);
				break;
			case BYTES:
				value = index.getBytes();
				break;
			case GROUP:
				throw new TransportException("cannot define repeating group value from schema " + type); 
			default:
				throw new TransportException("Unknown data type " + type); 
		}
    }
		
	@Override
	public Object get(S source) {
		return value;
	}
}
