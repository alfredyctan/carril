package org.afc.ferrocarril.sample;

import java.util.List;
import java.util.Map;

import org.afc.ferrocarril.message.QuickFixMessage;
import org.afc.ferrocarril.transport.AccessorMapping;
import org.afc.util.ObjectComparator;
import org.afc.util.ObjectComparator.Member;
import org.afc.util.StringUtil;

public class First implements QuickFixMessage {

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
        
	private static ThreadLocal<Map<String, AccessorMapping>> FIX_FIELDMAP = new ThreadLocal<Map<String, AccessorMapping>>(){
		protected Map<String, AccessorMapping> initialValue() {
			return AccessorMapping.createAccessorMappingMap(
				AccessorMapping.createAccessorMapping(First.class, "firstField", "getFirstField",    "setFirstField",    String.class),
				AccessorMapping.createAccessorMapping(First.class, "seconds",       "getSeconds",        "setSeconds",        List.class, Second.class)                              
			);
		}
	};

	private Context context;
	
	private String firstField;

	private List<Second> seconds;

	@Override
	public Context getContext() {
	    return context;
	}
	@Override
	public void setContext(Context context) {
		this.context = context;
	}
		
	@Override
	public Map<String, AccessorMapping> getFixMessageMap() {
		return FIX_FIELDMAP.get();
	}
	
	@Override
	public Map<String, AccessorMapping> getFixHeaderMap() {
	    return null;
	}
	
	@Override
	public Map<String, AccessorMapping> getFixTrailerMap() {
	    return null;
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
