package org.afc.carril.sample;

import java.util.List;

import org.afc.carril.annotation.AnnotatedMapping;
import org.afc.carril.annotation.AnnotatedMapping.Wire;
import org.afc.carril.message.FixMessage;
import org.afc.util.ObjectComparator;
import org.afc.util.ObjectComparator.Member;
import org.afc.util.StringUtil;

public class First implements FixMessage {

    private static final long serialVersionUID = -3032184418199850731L;

    
	private static ThreadLocal<ObjectComparator> comparator = new ThreadLocal<ObjectComparator>() {
		protected ObjectComparator initialValue() {
			return new ObjectComparator(
				First.class,
				new Member(String.class, "firstField"), 
				new Member(List.class, "seconds") 
			);
		};
	};

	private Context context;

	@AnnotatedMapping(wire = Wire.Fix, name = "firstField",    getter = "getFirstField",    setter = "setFirstField",    declare = String.class)
	private String firstField;

	@AnnotatedMapping(wire = Wire.Fix, name = "seconds",       getter = "getSeconds",       setter = "setSeconds",       declare = List.class, implement = Second.class)
	private List<Second> seconds;

	@Override
	public Context getContext() {
	    return context;
	}
	@Override
	public void setContext(Context context) {
		this.context = context;
	}
		
	public String getFirstField() {
    	return firstField;
    }
	public void setFirstField(String firstField) {
    	this.firstField = firstField;
    }
	public List<Second> getSeconds() {
    	return seconds;
    }
	public void setSeconds(List<Second> seconds) {
    	this.seconds = seconds;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		StringUtil.startToString(builder);
		StringUtil.buildToString(builder, "firstField", firstField);
		StringUtil.buildToString(builder, "seconds", seconds);
		StringUtil.endToString(builder);
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return comparator.get().compare(this, obj);
	}
}
