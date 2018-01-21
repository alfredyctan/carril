package org.afc.carril.sample;

import org.afc.carril.annotation.AnnotatedMapping;
import org.afc.carril.annotation.AnnotatedMapping.Wire;
import org.afc.carril.message.FixMessage;
import org.afc.util.ObjectComparator;
import org.afc.util.ObjectComparator.Member;
import org.afc.util.StringUtil;

public class Third implements FixMessage {

    private static final long serialVersionUID = -3032184418199850731L;

    private static ThreadLocal<ObjectComparator> comparator = new ThreadLocal<ObjectComparator>() {
		protected ObjectComparator initialValue() {
			return new ObjectComparator(
				Third.class,
				new Member(String.class, "thirdField") 
			);
		};
	};

	private Context context;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "thirdField", getter = "getThirdField",    setter = "setThirdField",    declare = String.class)
	private String thirdField;

	@Override
	public Context getContext() {
	    return context;
	}
	@Override
	public void setContext(Context context) {
		this.context = context;
	}
		
	public String getThirdField() {
    	return thirdField;
    }
	public void setThirdField(String thirdField) {
    	this.thirdField = thirdField;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		StringUtil.startToString(builder);
		StringUtil.buildToString(builder, "thirdField", thirdField);
		StringUtil.endToString(builder);
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return comparator.get().compare(this, obj);
	}
}
