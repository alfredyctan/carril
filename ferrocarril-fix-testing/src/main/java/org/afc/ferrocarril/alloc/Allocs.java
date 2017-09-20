package org.afc.ferrocarril.alloc;

import java.math.BigDecimal;
import java.util.Map;

import org.afc.ferrocarril.message.QuickFixMessage;
import org.afc.ferrocarril.transport.AccessorMapping;
import org.afc.util.ObjectComparator;
import org.afc.util.ObjectComparator.Member;
import org.afc.util.StringUtil;

public class Allocs implements QuickFixMessage {

    private static final long serialVersionUID = 1898843663659201444L;

	private static ThreadLocal<ObjectComparator> comparator = new ThreadLocal<ObjectComparator>() {
		protected ObjectComparator initialValue() {
			return new ObjectComparator(
				Allocs.class,
				new Member(String.class, "allocAccount"),
				new Member(BigDecimal.class, "allocShares")

			);
		};
	};    
    
    private static ThreadLocal<Map<String, AccessorMapping>> FIX_FIELDMAP = new ThreadLocal<Map<String, AccessorMapping>>(){
		protected Map<String, AccessorMapping> initialValue() {
			return AccessorMapping.createAccessorMappingMap(
				AccessorMapping.createAccessorMapping(Allocs.class, "AllocAccount", "getAllocAccount", "setAllocAccount", String.class),                                   
				AccessorMapping.createAccessorMapping(Allocs.class, "AllocShares",  "getAllocShares",  "setAllocShares",  BigDecimal.class) 
			);
		}
	};	
	
	private Context context;
	
	private String allocAccount;
	
	private BigDecimal allocShares;
	
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
	
	public Allocs() {
    }
	
	public Allocs(String allocAccount, BigDecimal allocShares) {
	    this.allocAccount = allocAccount;
	    this.allocShares = allocShares;
    }

	public String getAllocAccount() {
    	return allocAccount;
    }

	public void setAllocAccount(String allocAccount) {
    	this.allocAccount = allocAccount;
    }

	public BigDecimal getAllocShares() {
    	return allocShares;
    }

	public void setAllocShares(BigDecimal allocShares) {
    	this.allocShares = allocShares;
    }
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		StringUtil.startToString(builder);
		StringUtil.buildToString(builder, "allocAccount", allocAccount);
		StringUtil.buildToString(builder, "allocShares", allocShares);
		StringUtil.endToString(builder);
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return comparator.get().compare(this, obj);
	}
}
