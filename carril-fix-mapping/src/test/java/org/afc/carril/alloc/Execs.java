package org.afc.carril.alloc;

import java.math.BigDecimal;

import org.afc.carril.annotation.AnnotatedMapping;
import org.afc.carril.annotation.AnnotatedMapping.Wire;
import org.afc.carril.message.FixMessage;
import org.afc.util.ObjectComparator;
import org.afc.util.ObjectComparator.Member;
import org.afc.util.StringUtil;

public class Execs implements FixMessage {

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

	private Context context;

	@AnnotatedMapping(wire = Wire.Fix, name = "LastShares", getter = "getLastShares", setter = "setLastShares", declare = BigDecimal.class)
	private BigDecimal lastShares;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "ExecID",     getter = "getExecID",     setter = "setExecID",     declare = String.class)
	private String execID;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "LastPx",     getter = "getLastPx",     setter = "setLastPx",     declare = BigDecimal.class)
	private BigDecimal lastPx;
	
	
	@Override
	public Context getContext() {
	    return context;
	}
	
	@Override
	public void setContext(Context context) {
		this.context = context;
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
