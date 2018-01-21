package org.afc.carril.transport.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.Format;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.afc.carril.annotation.AnnotatedMapping;
import org.afc.carril.annotation.AnnotatedMapping.Section;
import org.afc.carril.annotation.AnnotatedMapping.Wire;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.transport.AccessorMapping;
import org.afc.carril.transport.TransportException;

public class AccessorMappingRegistry {

	private static final int GENERIC = 0;
	
	private static final int FIX = 1;
	
	private static final int HEADER = 0;
	
	private static final int BODY = 1;

	private static final int TRAILER = 2;

	private static ThreadLocal<Map<Class<?>, Map<String, AccessorMapping>>>[][] mappings = new ThreadLocal[2][3];
	static {
		for (int i = 0; i < mappings.length; i++) {
			for (int j = 0; j < mappings[i].length; j++) {
				mappings[i][j] = new ThreadLocal<Map<Class<?>, Map<String, AccessorMapping>>>() {
					@Override
					protected Map<Class<?>, Map<String, AccessorMapping>> initialValue() {
						return new ConcurrentHashMap<>();
					}
				};
			}
		}
	}
	
	public static <T extends GenericMessage> AccessorMapping getHeaderMapping(T message, String index) {
		return getMapping(mappings[GENERIC][HEADER].get(), Wire.Generic, Section.Header, message, index);
	}

	public static <T extends GenericMessage> AccessorMapping getBodyMapping(T message, String index) {
		return getMapping(mappings[GENERIC][BODY].get(), Wire.Generic, Section.Body, message, index);
	}

	public static <T extends GenericMessage> AccessorMapping getTrailerMapping(T message, String index) {
		return getMapping(mappings[GENERIC][TRAILER].get(), Wire.Generic, Section.Trailer, message, index);
	}

	public static <T extends GenericMessage> AccessorMapping getFixHeaderMapping(T message, String index) {
		return getMapping(mappings[FIX][HEADER].get(), Wire.Fix, Section.Header, message, index);
	}

	public static <T extends GenericMessage> AccessorMapping getFixBodyMapping(T message, String index) {
		return getMapping(mappings[FIX][BODY].get(), Wire.Fix, Section.Body, message, index);
	}

	public static <T extends GenericMessage> AccessorMapping getFixTrailerMapping(T message, String index) {
		return getMapping(mappings[FIX][TRAILER].get(), Wire.Fix, Section.Trailer, message, index);
	}

	public static <T extends GenericMessage> Map<String, AccessorMapping> getHeaderMapping(T message) {
		return getMappings(mappings[GENERIC][HEADER].get(), Wire.Generic, Section.Header, message);
	}

	public static <T extends GenericMessage> Map<String, AccessorMapping> getBodyMapping(T message) {
		return getMappings(mappings[GENERIC][BODY].get(), Wire.Generic, Section.Body, message);
	}

	public static <T extends GenericMessage> Map<String, AccessorMapping> getTrailerMapping(T message) {
		return getMappings(mappings[GENERIC][TRAILER].get(), Wire.Generic, Section.Trailer, message);
	}

	public static <T extends GenericMessage> Map<String, AccessorMapping> getFixHeaderMapping(T message) {
		return getMappings(mappings[FIX][HEADER].get(), Wire.Fix, Section.Header, message);
	}

	public static <T extends GenericMessage> Map<String, AccessorMapping> getFixBodyMapping(T message) {
		return getMappings(mappings[FIX][BODY].get(), Wire.Fix, Section.Body, message);
	}

	public static <T extends GenericMessage> Map<String, AccessorMapping> getFixTrailerMapping(T message) {
		return getMappings(mappings[FIX][TRAILER].get(), Wire.Fix, Section.Trailer, message);
	}


	private static <T extends GenericMessage> AccessorMapping getMapping(Map<Class<?>, Map<String, AccessorMapping>> classMappings, Wire wire, Section section, T message, String index) {
		return getMappings(classMappings, wire, section, message).get(index);
	}
	
	private static <T extends GenericMessage> Map<String, AccessorMapping> getMappings(Map<Class<?>, Map<String, AccessorMapping>> classMappings, Wire wire, Section section, T message) {
		Map<String, AccessorMapping> indexMapping = classMappings.get(message.getClass());
		if (indexMapping == null) {
			indexMapping = createMappings(wire, section, message);
			classMappings.put(message.getClass(), indexMapping);
		}
		return indexMapping;
	}

	private static Map<String, AccessorMapping> createMappings(Wire wire, Section section, Object object) {
		return createMappings(wire, section, object.getClass(), new HashMap<>());
	}

	private static Map<String, AccessorMapping> createMappings(Wire wire, Section section, Class<?> clazz, Map<String, AccessorMapping> indexMappings) {
		if (clazz.getSuperclass() != null) {
			createMappings(wire, section, clazz.getSuperclass(), indexMappings);
		}
		for (Field field : clazz.getDeclaredFields()) {
			AnnotatedMapping ann = field.getAnnotation(AnnotatedMapping.class);
			if (ann == null || ann.section() != section) {
				continue;
			}
			if (ann.wire() == Wire.Generic || ann.wire() == wire) {
				indexMappings.put(
					ann.name(), 
					createAccessorMapping(
						clazz, ann.name(), ann.getter(), ann.setter(), ann.declare(), ann.implement(), createFormat(ann)
					)
				);
			}
		}
		return indexMappings;
	}
	
	private static Format createFormat(AnnotatedMapping ann) {
		try {
			if (Void.class.equals(ann.formatter())) {
				return null;
			}
			
			if (!"".equals(ann.format())) {
				return (Format)ann.formatter().getConstructor(String.class).newInstance(ann.format());
			} else {
				return (Format)ann.formatter().getConstructor().newInstance();
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new TransportException("error on create format : " + ann.formatter() + " (" + ann.format() + ")", e);
		}
	}

	private static AccessorMapping createAccessorMapping(Class<?> clazz, String fieldName, String getter, String setter, Class<?> declareClass, Class<?> implClass, Format format) {
		try {
			return new AccessorMapping(fieldName, clazz.getMethod(getter), clazz.getMethod(setter, declareClass), declareClass, implClass, format);
		} catch (NoSuchMethodException nsme) {
			throw new TransportException(nsme);
		}
	}
	
	private static Map<String, AccessorMapping> createAccessorMappingMap(AccessorMapping... mappings) {
		return Arrays.asList(mappings).stream().collect(Collectors.toMap(AccessorMapping::getFieldName, v -> v));
	}
}
