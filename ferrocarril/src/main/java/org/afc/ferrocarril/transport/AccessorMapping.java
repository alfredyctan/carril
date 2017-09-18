package org.afc.ferrocarril.transport;

import java.lang.reflect.Method;
import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	public static AccessorMapping createAccessorMapping(Class<?> clazz, String fieldName, String getter, String setter, Class<?> declareClass) {
		return createAccessorMapping(clazz, fieldName, getter, setter, declareClass, declareClass, null);
	}

	public static AccessorMapping createAccessorMapping(Class<?> clazz, String fieldName, String getter, String setter, Class<?> declareClass, Format format) {
		return createAccessorMapping(clazz, fieldName, getter, setter, declareClass, declareClass, format);
	}

	public static AccessorMapping createAccessorMapping(Class<?> clazz, String fieldName, String getter, String setter, Class<?> declareClass, Class<?> implClass) {
		return createAccessorMapping(clazz, fieldName, getter, setter, declareClass, implClass, null);
	}
	
	public static AccessorMapping createAccessorMapping(Class<?> clazz, String fieldName, String getter, String setter, Class<?> declareClass, Class<?> implClass, Format format) {
		try {
			return new AccessorMapping(fieldName, clazz.getMethod(getter), clazz.getMethod(setter, declareClass), declareClass, implClass, format);
		} catch (NoSuchMethodException nsme) {
			throw new TransportException(nsme);
		}
	}
	
	public static List<AccessorMapping> createAccessorMappingList(AccessorMapping... mappings) {
		List<AccessorMapping> list = new ArrayList<AccessorMapping>(); 
		for (AccessorMapping m : mappings){
			list.add(m);
		}
		return list;
	}	

	public static Map<String, AccessorMapping> createAccessorMappingMap(AccessorMapping... mappings) {
		Map<String, AccessorMapping> map = new HashMap<String, AccessorMapping>(); 
		for (AccessorMapping m : mappings){
			map.put(m.getFieldName(), m);
		}
		return map;
	}
	
	@Override
	public boolean equals(Object obj) {
		AccessorMapping compare = (AccessorMapping) obj;

		if (!isEqual(fieldName, compare.fieldName)) {
			return false;
		}
		if (!isEqual(declareClass, compare.declareClass)) {
			return false;
		}
		if (!isEqual(implClass, compare.implClass)) {
			return false;
		}
		if (!isEqual(getMethod, compare.getMethod)) {
			return false;
		}
		if (!isEqual(setMethod, compare.setMethod)) {
			return false;
		}
		if (!isEqual(format, compare.format)) {
			return false;
		}

		return true;
	}
	
	private static boolean isEqual(Object o1, Object o2) {
		return (o1 != null) ? o1.equals(o2) : (o2 == null);
	}
}
