package org.afc.ferrocarril;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.afc.ferrocarril.fix.tag.FixTag;
import org.afc.ferrocarril.message.QuickFixMessage;
import org.afc.ferrocarril.text.UTCDateFormat;
import org.afc.ferrocarril.text.UTCDatetimeFormat;
import org.afc.ferrocarril.text.UTCTimeFormat;
import org.afc.ferrocarril.transport.AccessorMapping;

public class FixBaseMessage implements QuickFixMessage {

    private static final long serialVersionUID = -3474007340352777210L;

	private static ThreadLocal<Map<String, AccessorMapping>> Fix_HEADER_MAP = new ThreadLocal<Map<String, AccessorMapping>>(){
		protected Map<String, AccessorMapping> initialValue() {
			return AccessorMapping.createAccessorMappingMap(
				AccessorMapping.createAccessorMapping(FixBaseMessage.class, FixTag.MsgType.name(), "getMsgType", "setMsgType", String.class),
				AccessorMapping.createAccessorMapping(FixBaseMessage.class, FixTag.TransactTime.name(), "getTradeTime",   "setTradeTime",   Date.class, new UTCTimeFormat())
			);
		}
	};

	private static ThreadLocal<Map<String, AccessorMapping>> Fix_FIELDMAP = new ThreadLocal<Map<String, AccessorMapping>>(){
		protected Map<String, AccessorMapping> initialValue() {
			return AccessorMapping.createAccessorMappingMap(
				AccessorMapping.createAccessorMapping(FixBaseMessage.class, FixTag.OrderQty.name(),     "getAmount",      "setAmount",      BigDecimal.class),
				AccessorMapping.createAccessorMapping(FixBaseMessage.class, FixTag.Price.name(),     "getRate", "setRate", Double.class),
				AccessorMapping.createAccessorMapping(FixBaseMessage.class, FixTag.FutSettDate.name(), "getValueDate",   "setValueDate",   Date.class, new UTCDateFormat()),
				AccessorMapping.createAccessorMapping(FixBaseMessage.class, FixTag.TradeDate.name(), "getTradeDate",   "setTradeDate",   Date.class, new UTCDatetimeFormat()),
				AccessorMapping.createAccessorMapping(FixBaseMessage.class, FixTag.Product.name(),     "getProduct",     "setProduct",     String.class),
				AccessorMapping.createAccessorMapping(FixBaseMessage.class, FixTag.NoOrder.name(), "getMessages",   "setMessages",   List.class, FixBaseMessage.class),
				AccessorMapping.createAccessorMapping(FixBaseMessage.class, FixTag.LinesOfText.name(), "getLists",   "setLists",   List.class, FixBaseMessage.class)
			);
		}
	};

	private static ThreadLocal<Map<String, AccessorMapping>> Fix_TRAILER_MAP = new ThreadLocal<Map<String, AccessorMapping>>(){
		protected Map<String, AccessorMapping> initialValue() {
			return AccessorMapping.createAccessorMappingMap(
				AccessorMapping.createAccessorMapping(FixBaseMessage.class, FixTag.CIOrdID.name(),     "getReferenceID", "setReferenceID", Integer.class)
			);
		}
	};

    private String product;
    
    private Double rate;

    private BigDecimal amount;

    private String msgType ;

    private Integer referenceID;

    private Date valueDate;

    private Date tradeDate;

    private Date tradeTime;

    private List<FixBaseMessage> messages;

    private List<FixBaseMessage> lists;
    
	private Context context;
	
	public Context getContext() {
    	return context;
    }

	public void setContext(Context context) {
    	this.context = context;
    }	

	@Override
	public Map<String, AccessorMapping> getFixHeaderMap() {
	    return Fix_HEADER_MAP.get();
	}
	
	@Override
	public Map<String, AccessorMapping> getFixMessageMap() {
	    return Fix_FIELDMAP.get();
	}
	
	@Override
	public Map<String, AccessorMapping> getFixTrailerMap() {
	    return Fix_TRAILER_MAP.get();
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
