package org.afc.carril.sample;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.afc.carril.annotation.AnnotatedMapping;
import org.afc.carril.annotation.AnnotatedMapping.Wire;
import org.afc.carril.message.FixMessage;
import org.afc.carril.text.UTCDateFormat;
import org.afc.carril.text.UTCDatetimeFormat;
import org.afc.carril.text.UTCTimeFormat;
import org.afc.util.ObjectComparator;
import org.afc.util.ObjectComparator.Member;
import org.afc.util.StringUtil;

public class FixObject implements FixMessage {

    private static final long serialVersionUID = -3032184418199850731L;

	private static ThreadLocal<ObjectComparator> comparator = new ThreadLocal<ObjectComparator>() {
		protected ObjectComparator initialValue() {
			return new ObjectComparator(
				FixObject.class,
				new Member(List.class, "firsts"), 
				new Member(String.class, "msgID"), 
				new Member(Integer.class, "integer"), 
				new Member(String.class, "string"), 
				new Member(BigDecimal.class, "decimal"), 
				new Member(Double.class, "floating"), 
				new Member(Date.class, "datetime"), 
				new Member(Date.class, "date"), 
				new Member(Date.class, "time"), 
				new Member(Boolean.class, "bool"), 
				new Member(byte[].class, "bytes"), 
				new Member(BigDecimal.class, "field1"), 
				new Member(BigDecimal.class, "field2"), 
				new Member(String.class, "constant"), 
				new Member(BigDecimal.class, "optional"), 
				new Member(BigDecimal.class, "prohibit") 
			);
		};
	};
    
	private Context context;

	@AnnotatedMapping(wire = Wire.Fix, name = "firsts",      getter = "getFirsts",    setter = "setFirsts",   declare = List.class, implement = First.class)
	private List<First> firsts;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "MsgID",       getter = "getMsgID",     setter = "setMsgID",    declare = String.class)
	private String msgID;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "int",         getter = "getInteger",   setter = "setInteger",  declare = Integer.class)
	private Integer integer;

	@AnnotatedMapping(wire = Wire.Fix, name = "string",      getter = "getString",    setter = "setString",   declare = String.class)
	private String string;

	@AnnotatedMapping(wire = Wire.Fix, name = "decimal",     getter = "getDecimal",   setter = "setDecimal",  declare = BigDecimal.class)
	private BigDecimal decimal;

	@AnnotatedMapping(wire = Wire.Fix, name = "double",      getter = "getFloating",  setter = "setFloating", declare = Double.class)
	private Double floating;

	@AnnotatedMapping(wire = Wire.Fix, name = "datetime",    getter = "getDatetime",  setter = "setDatetime", declare = Date.class, formatter = UTCDatetimeFormat.class)
	private Date datetime;

	@AnnotatedMapping(wire = Wire.Fix, name = "date",        getter = "getDate",      setter = "setDate",     declare = Date.class, formatter = UTCDateFormat.class)
	private Date date;

	@AnnotatedMapping(wire = Wire.Fix, name = "time",        getter = "getTime",      setter = "setTime",     declare = Date.class, formatter = UTCTimeFormat.class)
	private Date time;

	@AnnotatedMapping(wire = Wire.Fix, name = "boolean",     getter = "getBool",      setter = "setBool",     declare = Boolean.class)
	private Boolean bool;

	@AnnotatedMapping(wire = Wire.Fix, name = "bytes",       getter = "getBytes",     setter = "setBytes",    declare = byte[].class)
	private byte[] bytes;

	@AnnotatedMapping(wire = Wire.Fix, name = "getField1",   getter = "getField1",    setter = "setField1",   declare = BigDecimal.class)
	private BigDecimal field1;

	@AnnotatedMapping(wire = Wire.Fix, name = "getField2",   getter = "getField2",    setter = "setField2",   declare = BigDecimal.class)
	private BigDecimal field2;
	
	@AnnotatedMapping(wire = Wire.Fix, name = "getConst",    getter = "getConstant",  setter = "setConstant", declare = String.class)
	private String constant;

	@AnnotatedMapping(wire = Wire.Fix, name = "getOptional", getter = "getOptional",  setter = "setOptional", declare = BigDecimal.class)
	private BigDecimal optional;

	@AnnotatedMapping(wire = Wire.Fix, name = "getProhibit", getter = "getProhibit",  setter = "setProhibit", declare = BigDecimal.class)
	private BigDecimal prohibit;


	@Override
	public Context getContext() {
	    return context;
	}
	@Override
	public void setContext(Context context) {
		this.context = context;
	}
		
	public Integer getInteger() {
		return integer;
	}

	public void setInteger(Integer integer) {
		this.integer = integer;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public BigDecimal getDecimal() {
		return decimal;
	}

	public void setDecimal(BigDecimal decimal) {
		this.decimal = decimal;
	}

	public Double getFloating() {
		return floating;
	}

	public void setFloating(Double floating) {
		this.floating = floating;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Boolean getBool() {
		return bool;
	}

	public void setBool(Boolean bool) {
		this.bool = bool;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public String getMsgID() {
    	return msgID;
    }

	public void setMsgID(String msgID) {
    	this.msgID = msgID;
    }

	public BigDecimal getField1() {
    	return field1;
    }

	public void setField1(BigDecimal field1) {
    	this.field1 = field1;
    }

	public BigDecimal getField2() {
    	return field2;
    }

	public void setField2(BigDecimal field2) {
    	this.field2 = field2;
    }

	public BigDecimal getOptional() {
    	return optional;
    }

	public void setOptional(BigDecimal optional) {
    	this.optional = optional;
    }

	public BigDecimal getProhibit() {
    	return prohibit;
    }

	public void setProhibit(BigDecimal prohibit) {
    	this.prohibit = prohibit;
    }

	public String getConstant() {
    	return constant;
    }

	public void setConstant(String constant) {
    	this.constant = constant;
    }
	public List<First> getFirsts() {
    	return firsts;
    }
	public void setFirsts(List<First> firsts) {
    	this.firsts = firsts;
    }

	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		StringUtil.startToString(builder);
		StringUtil.buildToString(builder, "firsts", firsts);
		StringUtil.buildToString(builder, "msgID", msgID);
		StringUtil.buildToString(builder, "integer", integer);
		StringUtil.buildToString(builder, "string", string);
		StringUtil.buildToString(builder, "decimal", decimal);
		StringUtil.buildToString(builder, "floating", floating);
		StringUtil.buildToString(builder, "datetime", datetime);
		StringUtil.buildToString(builder, "date", date);
		StringUtil.buildToString(builder, "time", time);
		StringUtil.buildToString(builder, "bool", bool);
		StringUtil.buildToString(builder, "bytes", bytes);
		StringUtil.buildToString(builder, "field1", field1);
		StringUtil.buildToString(builder, "field2", field2);
		StringUtil.buildToString(builder, "constant", constant);
		StringUtil.buildToString(builder, "optional", optional);
		StringUtil.buildToString(builder, "prohibit", prohibit);
		StringUtil.endToString(builder);
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return comparator.get().compare(this, obj);
	}
}
