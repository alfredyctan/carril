package org.afc.carril.fix.quickfix.standalone.util.modifier;

import static java.lang.Integer.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import quickfix.Message;


public class ScaleModifier implements TagModifier {

	private static final Pattern SCALE = Pattern.compile("^\\s*?\\$scale\\s*?\\(\\s*?(?<num>\\d*\\.?\\d*)\\s*?,\\s*?(?<scale>\\d*|STZ)\\s*?\\)\\s*?$");

	@Override
	public Pattern pattern() {
		return SCALE;
	}
	
	@Override
	public StringBuilder modify(StringBuilder str, Message message, Matcher matcher) {
		try {
			BigDecimal value = new BigDecimal(matcher.group("num"));
			value = matcher.group("scale").equals("STZ") ? value.stripTrailingZeros() : value.setScale(parseInt(matcher.group("scale")), RoundingMode.HALF_UP); 
			return replace(str, value.toPlainString());
		} catch(Exception e) {
			return str;
		}
	}
}
