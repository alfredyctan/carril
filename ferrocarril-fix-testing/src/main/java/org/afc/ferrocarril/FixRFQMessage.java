package org.afc.ferrocarril;

import java.util.Date;
import java.util.Map;

import org.afc.ferrocarril.fix.tag.FIXTag;
import org.afc.ferrocarril.message.QuickFixMessage;
import org.afc.ferrocarril.text.UTCDatetimeFormat;
import org.afc.ferrocarril.transport.AccessorMapping;

public class FixRFQMessage implements QuickFixMessage {

    private static final long serialVersionUID = -3474007340352777210L;

	private static ThreadLocal<Map<String, AccessorMapping>> FIX_HEADER_MAP = new ThreadLocal<Map<String, AccessorMapping>>(){
		protected Map<String, AccessorMapping> initialValue() {
			return AccessorMapping.createAccessorMappingMap(
				AccessorMapping.createAccessorMapping(FixRFQMessage.class, FIXTag.MsgType.name(),           "getMsgType",           "setMsgType",           String.class)
			);
		}
	};

	private static ThreadLocal<Map<String, AccessorMapping>> FIX_FIELDMAP = new ThreadLocal<Map<String, AccessorMapping>>(){
		protected Map<String, AccessorMapping> initialValue() {
			return AccessorMapping.createAccessorMappingMap(
				AccessorMapping.createAccessorMapping(FixRFQMessage.class, FIXTag.QuoteReqID.name(),        "getQuoteReqID",        "setQuoteReqID",        String.class),
				AccessorMapping.createAccessorMapping(FixRFQMessage.class, FIXTag.IndirectCompTRate.name(), "getIndirectCompTRate", "setIndirectCompTRate", String.class),
				AccessorMapping.createAccessorMapping(FixRFQMessage.class, FIXTag.NoRelatedSym.name(),      "getNoRelatedSym",      "setNoRelatedSym",      String.class),
				AccessorMapping.createAccessorMapping(FixRFQMessage.class, FIXTag.Symbol.name(),            "getSymbol",            "setSymbol",            String.class),
				AccessorMapping.createAccessorMapping(FixRFQMessage.class, FIXTag.SecurityType.name(),      "getSecurityType",      "setSecurityType",      String.class),
				AccessorMapping.createAccessorMapping(FixRFQMessage.class, FIXTag.Currency.name(),          "getCurrency",          "setCurrency",          String.class),
				AccessorMapping.createAccessorMapping(FixRFQMessage.class, FIXTag.OrderQty.name(),          "getOrderQty",          "setOrderQty",          Double.class),
				AccessorMapping.createAccessorMapping(FixRFQMessage.class, FIXTag.FutSettDate.name(),       "getFutSettDate",       "setFutSettDate",       String.class),
				AccessorMapping.createAccessorMapping(FixRFQMessage.class, FIXTag.TransactTime.name(),      "getTransactTime",      "setTransactTime",      Date.class, new UTCDatetimeFormat()),
				AccessorMapping.createAccessorMapping(FixRFQMessage.class, FIXTag.Account.name(),           "getAccount",           "setAccount",           String.class)
			);
		}
	};

    private String msgType; // 35=R
	private String quoteReqID; // 131=getrate_FF_1
	private String indirectCompTRate; // 9020=Y
	private String noRelatedSym; // 146=1
	private String symbol; // 55=USD/JPY
	private String securityType; // 167=FOR
	private String currency; // 15=USD
	private Double orderQty; // 38=10
	private String futSettDate; // 64=SPT
	private Date transactTime; // 60=20071111-22:06:33.162
	private String account; // 1=CIBHKH

	private Context context;
	
	public Context getContext() {
    	return context;
    }

	public void setContext(Context context) {
    	this.context = context;
    }	
	
	@Override
	public Map<String, AccessorMapping> getFixHeaderMap() {
	    return FIX_HEADER_MAP.get();
	}

	@Override
	public Map<String, AccessorMapping> getFixMessageMap() {
	    return FIX_FIELDMAP.get();
	}

	@Override
	public Map<String, AccessorMapping> getFixTrailerMap() {
	    return null;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getQuoteReqID() {
		return quoteReqID;
	}

	public void setQuoteReqID(String quoteReqID) {
		this.quoteReqID = quoteReqID;
	}

	public String getIndirectCompTRate() {
		return indirectCompTRate;
	}

	public void setIndirectCompTRate(String indirectCompTRate) {
		this.indirectCompTRate = indirectCompTRate;
	}

	public String getNoRelatedSym() {
		return noRelatedSym;
	}

	public void setNoRelatedSym(String noRelatedSym) {
		this.noRelatedSym = noRelatedSym;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSecurityType() {
		return securityType;
	}

	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(Double orderQty) {
		this.orderQty = orderQty;
	}

	public String getFutSettDate() {
		return futSettDate;
	}

	public void setFutSettDate(String futSettDate) {
		this.futSettDate = futSettDate;
	}

	public Date getTransactTime() {
		return transactTime;
	}

	public void setTransactTime(Date transactTime) {
		this.transactTime = transactTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getMsgType() {
    	return msgType;
    }
}
