package org.afc.carril.alloc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.afc.carril.message.QuickFixMessage;
import org.afc.carril.text.UTCDateFormat;
import org.afc.carril.text.UTCDateTimestampFormat;
import org.afc.carril.transport.AccessorMapping;
import org.afc.util.DateUtil;
import org.afc.util.ObjectComparator;
import org.afc.util.ObjectComparator.Member;
import org.afc.util.StringUtil;

public class Allocation implements QuickFixMessage {

    private static final long serialVersionUID = 5318146244813281884L;

	private static ThreadLocal<ObjectComparator> comparator = new ThreadLocal<ObjectComparator>() {
		protected ObjectComparator initialValue() {
			return new ObjectComparator(
				Allocation.class,
				new Member(String.class, "msgID"),
				new Member(String.class, "productType"),
				new Member(String.class, "allocID"),
				new Member(Character.class, "allocTransType"),
				new Member(String.class, "allocLinkID"),
				new Member(Integer.class, "allocLinkType"),
				new Member(String.class, "senderSubID"),
				new Member(String.class, "uuid"),
				new Member(List.class, "noOrder"),
				new Member(List.class, "noExecs"),
				new Member(Character.class, "side"),
				new Member(String.class, "symbol"),
				new Member(String.class, "securityID"),
				new Member(String.class, "idSource"),
				new Member(Integer.class, "product"),
				new Member(String.class, "cfiCode"),
				new Member(BigDecimal.class, "shares"),
				new Member(BigDecimal.class, "avgPx"),
				new Member(String.class, "currency"),
				new Member(Character.class, "settlementType"),
				new Member(Date.class, "futSettDate"),
				new Member(List.class, "noAllocs"),
				new Member(BigDecimal.class, "netMoney"),
				new Member(Date.class, "tradeDate"),
				new Member(Date.class, "transactTime"),
				new Member(String.class, "text")
			);
		};
	};    
    
    private static ThreadLocal<Map<String, AccessorMapping>> Fix_FIELDMAP = new ThreadLocal<Map<String, AccessorMapping>>(){
		protected Map<String, AccessorMapping> initialValue() {
			return AccessorMapping.createAccessorMappingMap(
				AccessorMapping.createAccessorMapping(Allocation.class, "MsgID",         "getMsgID",          "setMsgID",        String.class),                                   
				AccessorMapping.createAccessorMapping(Allocation.class, "ProductType",   "getProductType",    "setProductType",    String.class),                                   
				AccessorMapping.createAccessorMapping(Allocation.class, "AllocID",       "getAllocID",        "setAllocID",        String.class),                                   
				AccessorMapping.createAccessorMapping(Allocation.class, "AllocTransType","getAllocTransType", "setAllocTransType", Character.class),                                  
				AccessorMapping.createAccessorMapping(Allocation.class, "AllocLinkID",   "getAllocLinkID",    "setAllocLinkID",    String.class),                                  
				AccessorMapping.createAccessorMapping(Allocation.class, "AllocLinkType", "getAllocLinkType",  "setAllocLinkType",  Integer.class),                              
				AccessorMapping.createAccessorMapping(Allocation.class, "SenderSubID",   "getSenderSubID",    "setSenderSubID",    String.class),                               
				AccessorMapping.createAccessorMapping(Allocation.class, "Uuid",          "getUUID",           "setUUID",           String.class),                               
				AccessorMapping.createAccessorMapping(Allocation.class, "NoOrder",       "getNoOrder",        "setNoOrder",        List.class, Order.class),                              
				AccessorMapping.createAccessorMapping(Allocation.class, "NoExecs",       "getNoExecs",        "setNoExecs",        List.class, Execs.class),                              
				AccessorMapping.createAccessorMapping(Allocation.class, "Side",          "getSide",           "setSide",           Character.class),                            
				AccessorMapping.createAccessorMapping(Allocation.class, "Symbol",        "getSymbol",         "setSymbol",         String.class),                               
				AccessorMapping.createAccessorMapping(Allocation.class, "SecurityID",    "getSecurityID",     "setSecurityID",     String.class),                               
				AccessorMapping.createAccessorMapping(Allocation.class, "IdSource",      "getIdSource",       "setIdSource",       String.class),                               
				AccessorMapping.createAccessorMapping(Allocation.class, "Product",       "getProduct",        "setProduct",        Integer.class),                              
				AccessorMapping.createAccessorMapping(Allocation.class, "CFICode",       "getCFICode",        "setCFICode",        String.class),                               
				AccessorMapping.createAccessorMapping(Allocation.class, "Shares",        "getShares",         "setShares",         BigDecimal.class),                           
				AccessorMapping.createAccessorMapping(Allocation.class, "AvgPx",         "getAvgPx",          "setAvgPx",          BigDecimal.class),                           
				AccessorMapping.createAccessorMapping(Allocation.class, "Currency",      "getCurrency",       "setCurrency",       String.class),                               
				AccessorMapping.createAccessorMapping(Allocation.class, "SettlementType","getSettlementType", "setSettlementType", Character.class),                
				AccessorMapping.createAccessorMapping(Allocation.class, "FutSettDate",   "getFutSettDate",    "setFutSettDate",    Date.class, new UTCDateFormat()),                
				AccessorMapping.createAccessorMapping(Allocation.class, "NoAllocs",      "getNoAllocs",       "setNoAllocs",       List.class, Allocs.class),                              
				AccessorMapping.createAccessorMapping(Allocation.class, "NetMoney",      "getNetMoney",       "setNetMoney",       BigDecimal.class),                                  
				AccessorMapping.createAccessorMapping(Allocation.class, "TradeDate",     "getTradeDate",      "setTradeDate",      Date.class, new UTCDateFormat()),                                  
				AccessorMapping.createAccessorMapping(Allocation.class, "TransactTime",  "getTransactTime",   "setTransactTime",   Date.class, new UTCDateTimestampFormat()),                                  
				AccessorMapping.createAccessorMapping(Allocation.class, "Text",          "getText",           "setText",           String.class) 
			);
		}
	};	

	private Context context;

	private String msgID;

	private String productType;

	private String allocID;

	private Character allocTransType;
	
	private String allocLinkID;
	
	private Integer allocLinkType;
	
	private String senderSubID;
	
	private String uuid;
	
	private List<Order> noOrder;

	private List<Execs> noExecs;
	
	private Character side;
	
	private String symbol;
	
	private String securityID;
	
	private String idSource;
	
	private Integer product;
	
	private String cfiCode;
	
	private BigDecimal shares;
	
	private BigDecimal avgPx;
	
	private String currency;
	
	private Character settlementType;
	
	private Date futSettDate;
	
	private List<Allocs> noAllocs;
	
	private BigDecimal netMoney;
	
	private Date tradeDate;
	
	private Date transactTime;
	
	private String text;
	
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

	public Allocation() {
    }
	
	public String getMsgID() {
    	return msgID;
    }

	public void setMsgID(String msgID) {
    	this.msgID = msgID;
    }

	public String getProductType() {
    	return productType;
    }

	public void setProductType(String productType) {
    	this.productType = productType;
    }

	public String getAllocID() {
    	return allocID;
    }

	public void setAllocID(String allocID) {
    	this.allocID = allocID;
    }

	public Character getAllocTransType() {
    	return allocTransType;
    }

	public void setAllocTransType(Character allocTransType) {
    	this.allocTransType = allocTransType;
    }

	public String getAllocLinkID() {
    	return allocLinkID;
    }

	public void setAllocLinkID(String allocLinkID) {
    	this.allocLinkID = allocLinkID;
    }

	public Integer getAllocLinkType() {
    	return allocLinkType;
    }

	public void setAllocLinkType(Integer allocLinkType) {
    	this.allocLinkType = allocLinkType;
    }

	public String getSenderSubID() {
    	return senderSubID;
    }

	public void setSenderSubID(String senderSubID) {
    	this.senderSubID = senderSubID;
    }

	public String getUUID() {
    	return uuid;
    }

	public void setUUID(String uuid) {
    	this.uuid = uuid;
    }

	public List<Order> getNoOrder() {
    	return noOrder;
    }

	public void setNoOrder(List<Order> noOrder) {
    	this.noOrder = noOrder;
    }

	public List<Execs> getNoExecs() {
    	return noExecs;
    }

	public void setNoExecs(List<Execs>  noExecs) {
    	this.noExecs = noExecs;
    }

	public Character getSide() {
    	return side;
    }

	public void setSide(Character side) {
    	this.side = side;
    }

	public String getSymbol() {
    	return symbol;
    }

	public void setSymbol(String symbol) {
    	this.symbol = symbol;
    }

	public String getSecurityID() {
    	return securityID;
    }

	public void setSecurityID(String securityID) {
    	this.securityID = securityID;
    }

	public String getIdSource() {
    	return idSource;
    }

	public void setIdSource(String idSource) {
    	this.idSource = idSource;
    }

	public Integer getProduct() {
    	return product;
    }

	public void setProduct(Integer product) {
    	this.product = product;
    }

	public String getCFICode() {
    	return cfiCode;
    }

	public void setCFICode(String cfiCode) {
    	this.cfiCode = cfiCode;
    }

	public BigDecimal getShares() {
    	return shares;
    }

	public void setShares(BigDecimal shares) {
    	this.shares = shares;
    }

	public BigDecimal getAvgPx() {
    	return avgPx;
    }

	public void setAvgPx(BigDecimal avgPx) {
    	this.avgPx = avgPx;
    }

	public String getCurrency() {
    	return currency;
    }

	public void setCurrency(String currency) {
    	this.currency = currency;
    }

	public Character getSettlementType() {
    	return settlementType;
    }

	public void setSettlementType(Character settlementType) {
    	this.settlementType = settlementType;
    }

	public Date getFutSettDate() {
    	return futSettDate;
    }

	public void setFutSettDate(Date futSettDate) {
    	this.futSettDate = futSettDate;
    }

	public List<Allocs> getNoAllocs() {
    	return noAllocs;
    }

	public void setNoAllocs(List<Allocs> noAllocs) {
    	this.noAllocs = noAllocs;
    }

	public BigDecimal getNetMoney() {
    	return netMoney;
    }

	public void setNetMoney(BigDecimal netMoney) {
    	this.netMoney = netMoney;
    }

	public Date getTradeDate() {
    	return tradeDate;
    }

	public void setTradeDate(Date tradeDate) {
    	this.tradeDate = tradeDate;
    }

	public Date getTransactTime() {
    	return transactTime;
    }

	public void setTransactTime(Date transactTime) {
    	this.transactTime = transactTime;
    }

	public String getText() {
    	return text;
    }

	public void setText(String text) {
    	this.text = text;
    }
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		StringUtil.startToString(builder);
		StringUtil.buildToString(builder, "allocID", allocID);
		StringUtil.buildToString(builder, "allocLinkID", allocLinkID);
		StringUtil.buildToString(builder, "allocLinkType", allocLinkType);
		StringUtil.buildToString(builder, "allocTransType", allocTransType);
		StringUtil.buildToString(builder, "avgPx", avgPx);
		StringUtil.buildToString(builder, "cfiCode", cfiCode);
		StringUtil.buildToString(builder, "context", context);
		StringUtil.buildToString(builder, "currency", currency);
		StringUtil.buildToString(builder, "futSettDate", futSettDate);
		StringUtil.buildToString(builder, "idSource", idSource);
		StringUtil.buildToString(builder, "msgID", msgID);
		StringUtil.buildToString(builder, "netMoney", netMoney);
		StringUtil.buildToString(builder, "noAllocs", noAllocs);
		StringUtil.buildToString(builder, "noExecs", noExecs);
		StringUtil.buildToString(builder, "noOrder", noOrder);
		StringUtil.buildToString(builder, "product", product);
		StringUtil.buildToString(builder, "productType", productType);
		StringUtil.buildToString(builder, "securityID", securityID);
		StringUtil.buildToString(builder, "senderSubID", senderSubID);
		StringUtil.buildToString(builder, "settlementType", settlementType);
		StringUtil.buildToString(builder, "shares", shares);
		StringUtil.buildToString(builder, "side", side);
		StringUtil.buildToString(builder, "symbol", symbol);
		StringUtil.buildToString(builder, "text", text);
		StringUtil.buildToString(builder, "tradeDate", tradeDate);
		StringUtil.buildToString(builder, "transactTime", DateUtil.formatUTCTimeStamp(transactTime));
		StringUtil.buildToString(builder, "uuid", uuid);
		StringUtil.endToString(builder);
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return comparator.get().compare(this, obj);
	}
	
}
