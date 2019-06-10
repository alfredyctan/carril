package org.afc.carril.transport.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.Format;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.afc.carril.annotation.Carril;
import org.afc.carril.annotation.Carril.Section;
import org.afc.carril.annotation.Carril.Wire;
import org.afc.carril.transport.AccessorMapping;
import org.afc.carril.transport.TransportException;

import org.afc.util.StringUtil;

public class AccessorMappingRegistry {

	private static final String SET = "set";

	private static final String GET = "get";
	
	private static final String IS = "is";

	@SuppressWarnings("unchecked")
	private final static ThreadLocal<Map<Class<?>, Map<String, AccessorMapping>>>[] mappings = new ThreadLocal[Wire.values().length];
	static {
		for (int i = 0; i < mappings.length; i++) {
			mappings[i] = new ThreadLocal<Map<Class<?>, Map<String, AccessorMapping>>>() {
				@Override
				protected Map<Class<?>, Map<String, AccessorMapping>> initialValue() {
					return new ConcurrentHashMap<>();
				}
			};
		}
	}

	@SuppressWarnings("unchecked")
	private final static ThreadLocal<Map<Class<?>, Map<String, AccessorMapping>>>[][] sectionMappings = new ThreadLocal[Wire.values().length][Section.values().length];
	static {
		for (int i = 0; i < sectionMappings.length; i++) {
			for (int j = 0; j < sectionMappings[i].length; j++) {
				sectionMappings[i][j] = new ThreadLocal<Map<Class<?>, Map<String, AccessorMapping>>>() {
					@Override
					protected Map<Class<?>, Map<String, AccessorMapping>> initialValue() {
						return new ConcurrentHashMap<>();
					}
				};
			}
		}
	}
	
	public static <T> AccessorMapping getMapping(Wire wire, T message, String tag) {
		return getMappings(mappings[wire.index()].get(), wire, null, message).get(tag);
	}

	public static <T> Map<String, AccessorMapping> getMappings(Wire wire, T message) {
		return getMappings(mappings[wire.index()].get(), wire, null, message);
	}

	public static <T> AccessorMapping getMapping(Wire wire, Section section, T message, String tag) {
		return getMappings(sectionMappings[wire.index()][section.index()].get(), wire, section, message).get(tag);
	}

	public static <T> Map<String, AccessorMapping> getMappings(Wire wire, Section section, T message) {
		return getMappings(sectionMappings[wire.index()][section.index()].get(), wire, section, message);
	}
	
	private static <T> Map<String, AccessorMapping> getMappings(Map<Class<?>, Map<String, AccessorMapping>> classMappings, Wire wire, Section section, T message) {
		Map<String, AccessorMapping> tagMappings = classMappings.get(message.getClass());
		if (tagMappings == null) {
			tagMappings = createMappings(wire, section, message.getClass(), new HashMap<>());
			classMappings.put(message.getClass(), tagMappings);
		}
		return tagMappings;
	}
	
	private static Map<String, AccessorMapping> createMappings(Wire wire, Section section, Class<?> clazz, Map<String, AccessorMapping> tagMappings) {
		if (clazz.getSuperclass() != null) {
			createMappings(wire, section, clazz.getSuperclass(), tagMappings);
		}
		for (Field field : clazz.getDeclaredFields()) {
			Carril ann = field.getAnnotation(Carril.class);
			if (ann == null || (section != null && ann.section() != section)) {
				continue;
			}
			if (ann.wire() == Wire.Generic || ann.wire() == wire) {
				String name = StringUtil.hasValue(ann.name()) ? ann.name() : field.getName();
				tagMappings.put(
					name, 
					createAccessorMapping(
						clazz, 
						name, 
						ann.section(),
						StringUtil.hasValue(ann.getter()) ? ann.getter() : deriveGetter(field), 
						StringUtil.hasValue(ann.setter()) ? ann.setter() : deriveSetter(field), 
						ann.declare() != Void.class ? ann.declare() : field.getType(),
						ann.implement(), 
						createFormat(ann)
					)
				);
			}
		}
		return tagMappings;
	}
	
	private static String deriveGetter(Field field) {
//		String prefix = (field.getType() == Boolean.class || field.getType() == boolean.class) ? IS : GET;
		String prefix = (field.getType() == boolean.class) ? IS : GET; // for lombok generated Boolean getter 
		return prefix + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
	}

	private static String deriveSetter(Field field) {
		return SET + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
	}

	private static Format createFormat(Carril ann) {
		try {
			if (Void.class.equals(ann.format())) {
				return null;
			}
			
			if (!"".equals(ann.pattern())) {
				return (Format)ann.format().getConstructor(String.class).newInstance(ann.pattern());
			} else {
				return (Format)ann.format().getConstructor().newInstance();
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new TransportException("error on create format : " + ann.format() + " (" + ann.pattern() + ")", e);
		}
	}

	private static AccessorMapping createAccessorMapping(Class<?> clazz, String fieldName, Section section, String getter, String setter, Class<?> declareClass, Class<?> implClass, Format format) {
		try {
			return new AccessorMapping(fieldName, section, clazz.getMethod(getter), clazz.getMethod(setter, declareClass), declareClass, implClass, format);
		} catch (NoSuchMethodException nsme) {
			throw new TransportException(nsme);
		}
	}
}
