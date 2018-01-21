package org.afc.carril.alloc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.afc.carril.annotation.AnnotatedMapping;
import org.afc.carril.annotation.AnnotatedMapping.Wire;
import org.afc.carril.message.FixMessage;
import org.afc.carril.text.UTCDateFormat;
import org.afc.carril.text.UTCDateTimestampFormat;
import org.afc.util.DateUtil;
import org.afc.util.ObjectComparator;
import org.afc.util.ObjectComparator.Member;
import org.afc.util.StringUtil;

public class Allocation implements FixMessage {

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
    

	private Context context;

	@AnnotatedMapping(wire = Wire.Fix, name = "MsgID",          getter = "getMsgID",          setter = "setMsgID",          declare = String.class)
	private String msgID;

	@AnnotatedMapping(wire = Wire.Fix, name = "ProductType",    getter = "getProductType",    setter = "setProductType",    declare = String.class)
	private String productType;

	@AnnotatedMapping(wire = Wire.Fix, name = "AllocID",        getter = "getAllocID",        setter = "setAllocID",        declare = String.class)
	private String allocID;

	@AnnotatedMapping(wire = Wire.Fix, name = "AllocTransType", getter = "getAllocTransType", setter = "setAllocTransType", declare = Character.class)
	private Character allocTransType;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "AllocLinkID",    getter = "getAllocLinkID",    setter = "setAllocLinkID",    declare = String.class)
	private String allocLinkID;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "AllocLinkType",  getter = "getAllocLinkType",  setter = "setAllocLinkType",  declare = Integer.class)
	private Integer allocLinkType;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "SenderSubID",    getter = "getSenderSubID",    setter = "setSenderSubID",    declare = String.class)
	private String senderSubID;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "Uuid",           getter = "getUUID",           setter = "setUUID",           declare = String.class)
	private String uuid;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "NoOrder",        getter = "getNoOrder",        setter = "setNoOrder",        declare = List.class, implement = Order.class)
	private List<Order> noOrder;

	@AnnotatedMapping(wire = Wire.Fix, name = "NoExecs",        getter = "getNoExecs",        setter = "setNoExecs",        declare = List.class, implement = Execs.class)
	private List<Execs> noExecs;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "Side",           getter = "getSide",           setter = "setSide",           declare = Character.class)
	private Character side;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "Symbol",         getter = "getSymbol",         setter = "setSymbol",         declare = String.class)
	private String symbol;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "SecurityID",     getter = "getSecurityID",     setter = "setSecurityID",     declare = String.class)
	private String securityID;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "IdSource",       getter = "getIdSource",       setter = "setIdSource",       declare = String.class)
	private String idSource;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "Product",        getter = "getProduct",        setter = "setProduct",        declare = Integer.class)
	private Integer product;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "CFICode",        getter = "getCFICode",        setter = "setCFICode",        declare = String.class)
	private String cfiCode;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "Shares",         getter = "getShares",         setter = "setShares",         declare = BigDecimal.class)
	private BigDecimal shares;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "AvgPx",          getter = "getAvgPx",          setter = "setAvgPx",          declare = BigDecimal.class)
	private BigDecimal avgPx;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "Currency",       getter = "getCurrency",       setter = "setCurrency",       declare = String.class)
	private String currency;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "SettlementType", getter = "getSettlementType", setter = "setSettlementType", declare = Character.class)
	private Character settlementType;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "FutSettDate",    getter = "getFutSettDate",    setter = "setFutSettDate",    declare = Date.class, implement = UTCDateFormat.class)
	private Date futSettDate;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "NoAllocs",       getter = "getNoAllocs",       setter = "setNoAllocs",       declare = List.class, implement = Allocs.class)
	private List<Allocs> noAllocs;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "NetMoney",       getter = "getNetMoney",       setter = "setNetMoney",       declare = BigDecimal.class)
	private BigDecimal netMoney;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "TradeDate",      getter = "getTradeDate",      setter = "setTradeDate",      declare = Date.class, implement = UTCDateFormat.class)
	private Date tradeDate;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "TransactTime",   getter = "getTransactTime",   setter = "setTransactTime",   declare = Date.class, implement = UTCDateTimestampFormat.class)
	private Date transactTime;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "Text",           getter = "getText",           setter = "setText",           declare = String.class)
	private String text;
	
	@Override
	public Context getContext() {
	    return context;
	}
	
	@Override
	public void setContext(Context context) {
		this.context = context;
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
