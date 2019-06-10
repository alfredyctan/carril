package org.afc.carril.fix.quickfix.standalone.util.modifier;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import quickfix.Message;

public class PutModifier implements TagModifier {

	private static final Pattern PUT = Pattern.compile("^\\s*?\\$put\\s*?\\(\\s*?(?<key>\\S*?)\\s*?,\\s*?(?<value>\\S*?)\\s*?\\)\\s*?$");

	@Override
	public Pattern pattern() {
		return PUT;
	}

	@Override
	public StringBuilder modify(StringBuilder str, Message message, Matcher matcher) {
		try {
			return replace(str, ModifierContext.put(matcher.group("key"), matcher.group("value")));
		} catch(Exception e) {
			return str;
		}
	}
}
