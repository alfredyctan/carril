package org.afc.carril.alloc;

import org.afc.carril.annotation.AnnotatedMapping;
import org.afc.carril.annotation.AnnotatedMapping.Wire;
import org.afc.carril.message.FixMessage;
import org.afc.util.ObjectComparator;
import org.afc.util.ObjectComparator.Member;
import org.afc.util.StringUtil;

public class Order implements FixMessage {

	private static final long serialVersionUID = 5900916430929110415L;

	private static ThreadLocal<ObjectComparator> comparator = new ThreadLocal<ObjectComparator>() {
		protected ObjectComparator initialValue() {
			return new ObjectComparator(Order.class, new Member(String.class, "ciOrdID"),
					new Member(String.class, "secondaryOrderID"));
		};
	};

	private Context context;

	@AnnotatedMapping(wire = Wire.Fix, name = "CIOrdID",          getter = "getCIOrdID",          setter = "setCIOrdID",          declare = String.class)
	private String ciOrdID;

	@AnnotatedMapping(wire = Wire.Fix, name = "SecondaryOrderID", getter = "getSecondaryOrderID", setter = "setSecondaryOrderID", declare = String.class)
	private String secondaryOrderID;

	@Override
	public Context getContext() {
		return context;
	}

	@Override
	public void setContext(Context context) {
		this.context = context;
	}

	public Order() {
	}

	public Order(String ciOrdID, String secondaryOrderID) {
		this.ciOrdID = ciOrdID;
		this.secondaryOrderID = secondaryOrderID;
	}

	public String getCIOrdID() {
		return ciOrdID;
	}

	public void setCIOrdID(String ciOrdID) {
		this.ciOrdID = ciOrdID;
	}

	public String getSecondaryOrderID() {
		return secondaryOrderID;
	}

	public void setSecondaryOrderID(String secondaryOrderID) {
		this.secondaryOrderID = secondaryOrderID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		StringUtil.startToString(builder);
		StringUtil.buildToString(builder, "ciOrdID", ciOrdID);
		StringUtil.buildToString(builder, "secondaryOrderID", secondaryOrderID);
		StringUtil.endToString(builder);
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return comparator.get().compare(this, obj);
	}
}
