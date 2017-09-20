package org.afc.ferrocarril.fix.tag;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public enum FIXMsgType {

	TYPE_h  ("h",   "Trading Session Status"               ),
	TYPE_U4 ("U4",  "Initialize"                           ),
	TYPE_U1 ("U1",  "Application Ping"                     ),
	TYPE_U2 ("U2",  "Application Ping Reply"               ),
	TYPE_UB ("UB",  "TradeInfo Request"                    ),
	TYPE_B  ("B",   "News"                                 ),
	TYPE_R  ("R",   "Quote Request"                        ),
	TYPE_S  ("S",   "Quote"                                ),
	TYPE_Z  ("Z",   "Quote Cancel"                         ),
	TYPE_V  ("V",   "Market Data Request"                  ),
	TYPE_W  ("W",   "Market Data Snapshot Full Refresh"    ),
	TYPE_Y  ("Y",   "Market Data Request Reject"           ),
	TYPE_U3 ("U3",  "Order Timeout"                        ),
	TYPE_Q  ("Q",   "Don't Know Trade"                     ),
	TYPE_b  ("b",   "Quote Acknowledgement"                ),
	TYPE_D  ("D",   "New Order - Single"                   ),
	TYPE_8  ("8",   "Execution Report / Order Status Reply"),
	TYPE_j  ("j",   "Business Message Reject"              ),
	TYPE_H  ("H",   "Order Status Request"                 ),
	TYPE_UAF("UAF", "Order Mass Status Request"            ),
	TYPE_J  ("J",   "Allocation"                           ),
	TYPE_P  ("P",   "Allocation ACK"                       ),
	TYPE_A  ("A",   "Logon"                                ),
	TYPE_5  ("5",   "Logout"                               ),
	TYPE_0  ("0",   "Heartbeat"                            ),
	TYPE_1  ("1",   "TestRequest"                          );

	private static final Map<String, FIXMsgType> ALL_TYPES = new ConcurrentHashMap<String, FIXMsgType>();
	
	static {
		for(FIXMsgType type : FIXMsgType.values()) {
			ALL_TYPES.put(type.id, type);
		}
	}	

	private final String id;

	private final String desc;

	private final String text;
	
	private FIXMsgType(String id, String desc) {
		this.id = id;
		this.desc = desc;
		this.text = id  + '(' + desc + ')';
    }

    public static FIXMsgType fromID(String v) {
    	return ALL_TYPES.get(v);
	}

    public String id() {
    	return id;
    }

    public String desc() {
    	return desc;
    }

	public String toString() {
		return text;
	}
}
