package org.afc.carril.transport;

import java.lang.reflect.Method;
import java.text.Format;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
public class AccessorMapping {
	
	/* Field name in the external wire format name */
	private String fieldName;

	/* Declared class in the object, normally will be same to implClass except List/Map...etc member */
	private Class<?> declareClass;

	/* Actual implementation class of the member */
	private Class<?> implClass;

	/* Getter accessor Method object for reflection */
	private Method getMethod;

	/* Setter accessor Method object for reflection */
	private Method setMethod;

	/* Format for String type data conversion */
	private Format format;

	public AccessorMapping(String fieldName, Method getMethod, Method setMethod, Class<?> declareClass) {
		this(fieldName, getMethod, setMethod, declareClass, declareClass, null);
	}

	public AccessorMapping(String fieldName, Method getMethod, Method setMethod, Class<?> declareClass, Format format) {
		this(fieldName, getMethod, setMethod, declareClass, declareClass, format);
	}
	
	public AccessorMapping(String fieldName, Method getMethod, Method setMethod, Class<?> declareClass, Class<?> implClass) {
		this(fieldName, getMethod, setMethod, declareClass, implClass, null);
	}

	public AccessorMapping(String fieldName, Method getMethod, Method setMethod, Class<?> declareClass, Class<?> implClass, Format format) {
		this.fieldName = fieldName;
		this.getMethod = getMethod;
		this.setMethod = setMethod;
		this.declareClass = declareClass;
		this.implClass = implClass;
		this.format = format;
	}	

	public String getFieldName() {
		return fieldName;
	}

	public Class<?> getDeclareClass() {
		return declareClass;
	}

	public Class<?> getImplClass() {
		return implClass;
	}

	public Method getGetMethod() {
		return getMethod;
	}

	public Method getSetMethod() {
		return setMethod;
	}

	public Format getFormat() {
    	return format;
    }
}
