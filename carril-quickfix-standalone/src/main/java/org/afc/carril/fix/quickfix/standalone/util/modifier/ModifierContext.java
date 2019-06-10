package org.afc.carril.fix.quickfix.standalone.util.modifier;

import java.util.HashMap;
import java.util.Map;

public class ModifierContext {

	private static ThreadLocal<Map<String, String>> contexts = ThreadLocal.withInitial(HashMap::new); 
	
	public static String get(String key) {
		return contexts.get().get(key);
	}

	public static String put(String key, String value) {
		contexts.get().put(key, value);
		return value;
	}
	
	public static void clear() {
		contexts.get().clear();
	}
}
