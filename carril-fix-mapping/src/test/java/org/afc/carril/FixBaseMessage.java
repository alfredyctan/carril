package org.afc.carril;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.afc.carril.annotation.AnnotatedMapping;
import org.afc.carril.annotation.AnnotatedMapping.Section;
import org.afc.carril.annotation.AnnotatedMapping.Wire;
import org.afc.carril.message.FixMessage;
import org.afc.carril.text.UTCDateFormat;
import org.afc.carril.text.UTCDatetimeFormat;
import org.afc.carril.text.UTCTimeFormat;

public class FixBaseMessage implements FixMessage {

    private static final long serialVersionUID = -3474007340352777210L;
   
	@AnnotatedMapping(wire = Wire.Fix, name = "Product", getter = "getProduct", setter = "setProduct", declare = String.class)
	private String product;

	@AnnotatedMapping(wire = Wire.Fix, name = "Price", getter = "getRate", setter = "setRate", declare = Double.class)
	private Double rate;

	@AnnotatedMapping(wire = Wire.Fix, name = "OrderQty", getter = "getAmount", setter = "setAmount", declare = BigDecimal.class)
	private BigDecimal amount;

	@AnnotatedMapping(wire = Wire.Fix, section = Section.Header, name = "MsgType", getter = "getMsgType", setter = "setMsgType", declare = String.class)
	private String msgType;

	@AnnotatedMapping(wire = Wire.Fix, section = Section.Trailer, name = "CIOrdID", getter = "getReferenceID", setter = "setReferenceID", declare = Integer.class)
	private Integer referenceID;

	@AnnotatedMapping(wire = Wire.Fix, name = "FutSettDate", getter = "getValueDate", setter = "setValueDate", declare = Date.class, formatter = UTCDateFormat.class)
	private Date valueDate;

	@AnnotatedMapping(wire = Wire.Fix, name = "TradeDate", getter = "getTradeDate", setter = "setTradeDate", declare = Date.class, formatter = UTCDatetimeFormat.class)
	private Date tradeDate;

	@AnnotatedMapping(wire = Wire.Fix, section = Section.Header, name = "TransactTime", getter = "getTradeTime", setter = "setTradeTime", declare = Date.class, formatter = UTCTimeFormat.class)
	private Date tradeTime;

	@AnnotatedMapping(wire = Wire.Fix, name = "NoOrder", getter = "getMessages", setter = "setMessages", declare = List.class, implement = FixBaseMessage.class)
	private List<FixBaseMessage> messages;

	@AnnotatedMapping(wire = Wire.Fix, name = "LinesOfText", getter = "getLists", setter = "setLists", declare = List.class, implement = FixBaseMessage.class)
	private List<FixBaseMessage> lists;
  
	private Context context;
	
	public Context getContext() {
    	return context;
    }

	public void setContext(Context context) {
    	this.context = context;
    }	


	public String getProduct() {
    	return product;
    }

	public void setProduct(String product) {
    	this.product = product;
    }

	public BigDecimal getAmount() {
    	return amount;
    }

	public void setAmount(BigDecimal amount) {
    	this.amount = amount;
    }

	public Integer getReferenceID() {
    	return referenceID;
    }

	public void setReferenceID(Integer referenceID) {
    	this.referenceID = referenceID;
    }

	public Date getValueDate() {
    	return valueDate;
    }

	public void setValueDate(Date valueDate) {
    	this.valueDate = valueDate;
    }

	public Double getRate() {
    	return rate;
    }

	public void setRate(Double rate) {
    	this.rate = rate;
    }

	public void setMsgType(String msgType) {
    	this.msgType = msgType;
    }

	public Date getTradeDate() {
    	return tradeDate;
    }

	public void setTradeDate(Date tradeDate) {
    	this.tradeDate = tradeDate;
    }

	public Date getTradeTime() {
    	return tradeTime;
    }

	public void setTradeTime(Date tradeTime) {
    	this.tradeTime = tradeTime;
    }

	public List<FixBaseMessage> getMessages() {
    	return messages;
    }

	public void setMessages(List<FixBaseMessage> messages) {
    	this.messages = messages;
    }

	public List<FixBaseMessage> getLists() {
    	return lists;
    }

	public void setLists(List<FixBaseMessage> lists) {
    	this.lists = lists;
    }

	public String getMsgType() {
    	return msgType;
    }
}
