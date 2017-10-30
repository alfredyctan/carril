package org.afc.carril.alloc;

import java.util.Map;

import org.afc.carril.message.QuickFixMessage;
import org.afc.carril.transport.AccessorMapping;
import org.afc.util.ObjectComparator;
import org.afc.util.ObjectComparator.Member;
import org.afc.util.StringUtil;

public class Order implements QuickFixMessage {

	private static final long serialVersionUID = 5900916430929110415L;

	private static ThreadLocal<ObjectComparator> comparator = new ThreadLocal<ObjectComparator>() {
		protected ObjectComparator initialValue() {
			return new ObjectComparator(Order.class, new Member(String.class, "ciOrdID"),
					new Member(String.class, "secondaryOrderID"));
		};
	};

	private static ThreadLocal<Map<String, AccessorMapping>> Fix_FIELDMAP = new ThreadLocal<Map<String, AccessorMapping>>() {
		protected Map<String, AccessorMapping> initialValue() {
			return AccessorMapping.createAccessorMappingMap(
					AccessorMapping.createAccessorMapping(Order.class, "CIOrdID", "getCIOrdID", "setCIOrdID",
							String.class),
					AccessorMapping.createAccessorMapping(Order.class, "SecondaryOrderID", "getSecondaryOrderID",
							"setSecondaryOrderID", String.class));
		}
	};

	private Context context;

	private String ciOrdID;

	private String secondaryOrderID;

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
