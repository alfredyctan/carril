package org.afc.carril.annotation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.afc.carril.annotation.AnnotatedMapping.Wire;
import org.afc.carril.message.GenericMessage;

public class ExampleMessage implements GenericMessage {

	@AnnotatedMapping(name = "PRODUCT", declare = String.class, getter = "getProduct", setter = "setProduct")
	private String product;

	@AnnotatedMapping(name = "TRADE_DATE", declare = Date.class, getter = "getTradeDate", setter = "setTradeDate", formatter = SimpleDateFormat.class, format = "YYYY-MM-DD")
	@AnnotatedMapping(wire = Wire.Fix, name = "TradeDate", declare = Date.class, getter = "getTradeDate", setter = "setTradeDate", formatter = SimpleDateFormat.class, format = "YYYY-MM-DD")
	private Date tradeDate;

	@AnnotatedMapping(name = "COMMENTS", declare = List.class, implement = ArrayList.class, getter = "getComments", setter = "setComments")
	private List<String> comments;

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}
}
