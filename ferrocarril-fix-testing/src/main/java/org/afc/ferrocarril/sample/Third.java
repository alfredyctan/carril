package org.afc.ferrocarril.sample;

import java.util.Map;

import org.afc.ferrocarril.message.QuickFixMessage;
import org.afc.ferrocarril.transport.AccessorMapping;
import org.afc.util.ObjectComparator;
import org.afc.util.ObjectComparator.Member;
import org.afc.util.StringUtil;

public class Third implements QuickFixMessage {

    private static final long serialVersionUID = -3032184418199850731L;

    private static ThreadLocal<ObjectComparator> comparator = new ThreadLocal<ObjectComparator>() {
		protected ObjectComparator initialValue() {
			return new ObjectComparator(
				Third.class,
				new Member(String.class, "thirdField") 
			);
		};
	};
	
    private static ThreadLocal<Map<String, AccessorMapping>> Fix_FIELDMAP = new ThreadLocal<Map<String, AccessorMapping>>(){
		protected Map<String, AccessorMapping> initialValue() {
			return AccessorMapping.createAccessorMappingMap(
				AccessorMapping.createAccessorMapping(Third.class, "thirdField", "getThirdField",    "setThirdField",    String.class)
			);
		}
	};

	private Context context;
	
	private String thirdField;

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
