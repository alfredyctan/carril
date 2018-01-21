package org.afc.carril.alloc;

import java.math.BigDecimal;

import org.afc.carril.annotation.AnnotatedMapping;
import org.afc.carril.annotation.AnnotatedMapping.Wire;
import org.afc.carril.message.FixMessage;
import org.afc.util.ObjectComparator;
import org.afc.util.ObjectComparator.Member;
import org.afc.util.StringUtil;

public class Allocs implements FixMessage {

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
	
	private Context context;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "AllocAccount", getter = "getAllocAccount", setter = "setAllocAccount", declare = String.class)
	private String allocAccount;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "AllocShares",  getter = "getAllocShares",  setter = "setAllocShares",  declare = BigDecimal.class)
	private BigDecimal allocShares;
	
	@Override
	public Context getContext() {
	    return context;
	}
	
	@Override
	public void setContext(Context context) {
		this.context = context;
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
