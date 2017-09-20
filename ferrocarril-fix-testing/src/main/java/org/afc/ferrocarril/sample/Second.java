package org.afc.ferrocarril.sample;

import java.util.List;
import java.util.Map;

import org.afc.ferrocarril.message.QuickFixMessage;
import org.afc.ferrocarril.transport.AccessorMapping;
import org.afc.util.ObjectComparator;
import org.afc.util.ObjectComparator.Member;
import org.afc.util.StringUtil;

public class Second implements QuickFixMessage {

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
        
	private static ThreadLocal<Map<String, AccessorMapping>> Fix_FIELDMAP = new ThreadLocal<Map<String, AccessorMapping>>(){
		protected Map<String, AccessorMapping> initialValue() {
			return AccessorMapping.createAccessorMappingMap(
				AccessorMapping.createAccessorMapping(Second.class, "secondField", "getSecondField",    "setSecondField",    String.class),
				AccessorMapping.createAccessorMapping(Second.class, "thirds",       "getThirds",        "setThirds",        List.class, Third.class)                              
			);
		}
	};

	private Context context;
	
	private String secondField;

	private List<Third> thirds;

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
		return Fix_FIELDMAP.get();
	}
	
	@Override
	public Map<String, AccessorMapping> getFixHeaderMap() {
	    return null;
	}
	
	@Override
	public Map<String, AccessorMapping> getFixTrailerMap() {
	    return null;
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
