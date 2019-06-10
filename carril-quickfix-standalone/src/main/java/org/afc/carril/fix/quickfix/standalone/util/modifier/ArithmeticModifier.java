package org.afc.carril.fix.quickfix.standalone.util.modifier;

import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import nl.bigo.curta.Curta;
import quickfix.Message;

public class ArithmeticModifier implements TagModifier {

	private static final String MATHS = Arrays.asList(Math.class.getMethods()).stream().filter(m -> Modifier.isStatic(m.getModifiers())).map(m -> m.getName()).distinct().collect(Collectors.joining("|"));

	private static final String DATE_NEGATIVE_LOOKAHEAD = "(?!\\d\\d\\d\\d-\\d\\d-\\d\\d)";
	
	private static final Pattern ARTH = Pattern.compile(DATE_NEGATIVE_LOOKAHEAD + "([\\+\\-\\*/\\s\\d\\.\\(\\),]*(" + MATHS + ")*[\\+\\-\\*/\\s\\d\\.\\(\\),]*)*");
	
	private Curta curta;
	
	public ArithmeticModifier() {
		this.curta = new Curta();
	}
	
	@Override
	public Pattern pattern() {
		return ARTH;
	}
	
	@Override
	public StringBuilder modify(StringBuilder str, Message message, Matcher matcher) {
		try {
			return replace(str, new BigDecimal(curta.eval(str.toString()).toString()).stripTrailingZeros().toPlainString());
		} catch(Exception e) {
			return str;
		}
	}
}	

