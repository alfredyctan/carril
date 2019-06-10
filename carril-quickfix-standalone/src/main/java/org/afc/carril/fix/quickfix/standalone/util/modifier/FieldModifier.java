package org.afc.carril.fix.quickfix.standalone.util.modifier;

import static org.afc.carril.fix.quickfix.text.MessageUtil.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import quickfix.Message;

public class FieldModifier implements TagModifier {

	private static final Pattern FIELD = Pattern.compile("^\\s*?\\#(?<tag>\\d*)\\s*?$");

	@Override
	public Pattern pattern() {
		return FIELD;
	}
	
	@Override
	public StringBuilder modify(StringBuilder str, Message message, Matcher matcher) {
		try {
			String value = getString(message, Integer.parseInt(matcher.group("tag")));
			return replace(str, value);
		} catch (Exception e) {
			return str;
		}
	}
}

