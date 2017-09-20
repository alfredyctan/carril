package org.afc.ferrocarril.alloc;

import java.math.BigDecimal;
import java.util.Map;

import org.afc.ferrocarril.message.QuickFixMessage;
import org.afc.ferrocarril.transport.AccessorMapping;
import org.afc.util.ObjectComparator;
import org.afc.util.ObjectComparator.Member;
import org.afc.util.StringUtil;

public class Execs implements QuickFixMessage {

    private static final long serialVersionUID = -1182971755817007002L;

	private static ThreadLocal<ObjectComparator> comparator = new ThreadLocal<ObjectComparator>() {
		protected ObjectComparator initialValue() {
			return new ObjectComparator(
				Execs.class,
				new Member(BigDecimal.class, "lastShares"),
				new Member(String.class,     "execID"),
				new Member(BigDecimal.class, "lastPx")

			);
		};
	};    

	private static ThreadLocal<Map<String, AccessorMapping>> FIX_FIELDMAP = new ThreadLocal<Map<String, AccessorMapping>>(){
		protected Map<String, AccessorMapping> initialValue() {
			return AccessorMapping.createAccessorMappingMap(
				AccessorMapping.createAccessorMapping(Execs.class, "LastShares", "getLastShares", "setLastShares", BigDecimal.class),                                   
				AccessorMapping.createAccessorMapping(Execs.class, "ExecID",     "getExecID",     "setExecID",     String.class),                                   
				AccessorMapping.createAccessorMapping(Execs.class, "LastPx",     "getLastPx",     "setLastPx",     BigDecimal.class) 
			);
		}
	};	
	
	private Context context;

	private BigDecimal lastShares;
	
	private String execID;
	
	private BigDecimal lastPx;
	
	
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
	public Execs() {
    }
	
	public Execs(String execID, BigDecimal lastShares, BigDecimal lastPx) {
	    this.execID = execID;
	    this.lastShares = lastShares;
	    this.lastPx = lastPx;
    }

	public BigDecimal getLastShares() {
    	return lastShares;
    }

	public void setLastShares(BigDecimal lastShares) {
    	this.lastShares = lastShares;
    }

	public String getExecID() {
    	return execID;
    }

	public void setExecID(String execID) {
    	this.execID = execID;
    }

	public BigDecimal getLastPx() {
    	return lastPx;
    }

	public void setLastPx(BigDecimal lastPx) {
    	this.lastPx = lastPx;
    }
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		StringUtil.startToString(builder);
		StringUtil.buildToString(builder, "lastShares", lastShares);
		StringUtil.buildToString(builder, "execID", execID);
		StringUtil.buildToString(builder, "lastPx", lastPx);
		StringUtil.endToString(builder);
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return comparator.get().compare(this, obj);
	}
}
