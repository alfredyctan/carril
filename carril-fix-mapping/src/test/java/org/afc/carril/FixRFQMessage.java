package org.afc.carril;

import java.util.Date;

import org.afc.carril.annotation.AnnotatedMapping;
import org.afc.carril.annotation.AnnotatedMapping.Wire;
import org.afc.carril.message.FixMessage;
import org.afc.carril.text.UTCDatetimeFormat;

public class FixRFQMessage implements FixMessage {

    private static final long serialVersionUID = -3474007340352777210L;

    @AnnotatedMapping(wire = Wire.Fix, name = "MsgType", getter = "getMsgType", setter = "setMsgType", declare = String.class)
	private String msgType; // 35=R

	@AnnotatedMapping(wire = Wire.Fix, name = "QuoteReqID", getter = "getQuoteReqID", setter = "setQuoteReqID", declare = String.class)
	private String quoteReqID; // 131=getrate_FF_1

	@AnnotatedMapping(wire = Wire.Fix, name = "NoRelatedSym", getter = "getNoRelatedSym", setter = "setNoRelatedSym", declare = String.class)
	private String noRelatedSym; // 146=1

	@AnnotatedMapping(wire = Wire.Fix, name = "Symbol", getter = "getSymbol", setter = "setSymbol", declare = String.class)
	private String symbol; // 55=USD/JPY

	@AnnotatedMapping(wire = Wire.Fix, name = "SecurityType", getter = "getSecurityType", setter = "setSecurityType", declare = String.class)
	private String securityType; // 167=FOR

	@AnnotatedMapping(wire = Wire.Fix, name = "Currency", getter = "getCurrency", setter = "setCurrency", declare = String.class)
	private String currency; // 15=USD

	@AnnotatedMapping(wire = Wire.Fix, name = "OrderQty", getter = "getOrderQty", setter = "setOrderQty", declare = Double.class)
	private Double orderQty; // 38=10

	@AnnotatedMapping(wire = Wire.Fix, name = "FutSettDate", getter = "getFutSettDate", setter = "setFutSettDate", declare = String.class)
	private String futSettDate; // 64=SPT

	@AnnotatedMapping(wire = Wire.Fix, name = "TransactTime", getter = "getTransactTime", setter = "setTransactTime", declare = Date.class, formatter = UTCDatetimeFormat.class)
	private Date transactTime; // 60=20071111-22:06:33.162

	@AnnotatedMapping(wire = Wire.Fix, name = "Account", getter = "getAccount", setter = "setAccount", declare = String.class)
	private String account; // 1=CIBHKH

	private Context context;
	
	public Context getContext() {
    	return context;
    }

	public void setContext(Context context) {
    	this.context = context;
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
