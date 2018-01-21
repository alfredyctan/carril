package org.afc.carril.sample;

import java.util.List;

import org.afc.carril.annotation.AnnotatedMapping;
import org.afc.carril.annotation.AnnotatedMapping.Wire;
import org.afc.carril.message.FixMessage;
import org.afc.util.ObjectComparator;
import org.afc.util.ObjectComparator.Member;
import org.afc.util.StringUtil;

public class Second implements FixMessage {

    private static final long serialVersionUID = -3032184418199850731L;

    private static ThreadLocal<ObjectComparator> comparator = new ThreadLocal<ObjectComparator>() {
		protected ObjectComparator initialValue() {
			return new ObjectComparator(
				Second.class,
				new Member(String.class, "secondField"), 
				new Member(List.class, "thirds") 
			);
		};
	};
        
	private Context context;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "secondField", getter = "getSecondField",    setter = "setSecondField",    declare = String.class)
	private String secondField;

	@AnnotatedMapping(wire = Wire.Fix, name = "thirds",      getter = "getThirds",         setter = "setThirds",         declare = List.class, implement = Third.class)
	private List<Third> thirds;

	@Override
	public Context getContext() {
	    return context;
	}
	@Override
	public void setContext(Context context) {
		this.context = context;
	}
		
	public String getSecondField() {
    	return secondField;
    }
	public void setSecondField(String secondField) {
    	this.secondField = secondField;
    }
	public List<Third> getThirds() {
    	return thirds;
    }
	public void setThirds(List<Third> thirds) {
    	this.thirds = thirds;
    }
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		StringUtil.startToString(builder);
		StringUtil.buildToString(builder, "secondField", secondField);
		StringUtil.buildToString(builder, "thirds", thirds);
		StringUtil.endToString(builder);
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return comparator.get().compare(this, obj);
	}
}
