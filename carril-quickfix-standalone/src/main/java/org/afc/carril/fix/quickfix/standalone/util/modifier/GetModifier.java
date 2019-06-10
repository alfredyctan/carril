package org.afc.carril.fix.quickfix.standalone.util.modifier;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import quickfix.Message;

public class GetModifier implements TagModifier {

	private static final Pattern GET = Pattern.compile("^\\s*?\\$get\\s*?\\(\\s*?(?<key>\\S*?)\\s*?\\)\\s*?$");
	
	@Override
	public Pattern pattern() {
		return GET;
	}

	@Override
	public StringBuilder modify(StringBuilder str, Message message, Matcher matcher) {
		try {
			String value = ModifierContext.get(matcher.group("key"));
			value = (value == null) ? "" : value;
			return replace(str, value);
		} catch(Exception e) {
			return str;
		}
	}
}
