package org.afc.carril.fix.quickfix.standalone.util.modifier;

import static org.afc.util.OptionalUtil.*;
import static java.lang.Double.*;
import static java.lang.Integer.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import quickfix.Message;

public class RandModifier implements TagModifier {

	private static final Pattern RAND  = Pattern.compile("^\\s*?\\$rand\\s*?\\(\\s*?(?<from>\\d*\\.*?\\d*)\\s*?~?\\s*?(?<to>\\d*\\.*?\\d*)*?\\s*?(:\\s*?(?<scale>\\d*)*?\\s*?)*\\)\\s*?$");

	@Override
	public Pattern pattern() {
		return RAND;
	}

	@Override
	public StringBuilder modify(StringBuilder str, Message message, Matcher matcher) {
		try {
			int scale = iifNotEmptyElse(matcher.group("scale"), s -> parseInt(s), () -> 4); 
			double to = iifNotEmptyElse(matcher.group("to"), s -> parseDouble(s), () -> parseDouble(matcher.group("from")));
			double from = iifNotEmptyElse(matcher.group("to"), s -> parseDouble(matcher.group("from")), () -> 0d);
			BigDecimal value = new BigDecimal((Math.random() * (to - from)) + from).setScale(scale, RoundingMode.HALF_UP);
			return replace(str, value.toPlainString());
		} catch(Exception e) {
			return str;
		}
	}
}
