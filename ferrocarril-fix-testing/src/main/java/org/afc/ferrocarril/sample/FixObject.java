package org.afc.ferrocarril.sample;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.afc.ferrocarril.message.QuickFixMessage;
import org.afc.ferrocarril.text.UTCDateFormat;
import org.afc.ferrocarril.text.UTCDatetimeFormat;
import org.afc.ferrocarril.text.UTCTimeFormat;
import org.afc.ferrocarril.transport.AccessorMapping;
import org.afc.util.ObjectComparator;
import org.afc.util.ObjectComparator.Member;
import org.afc.util.StringUtil;

public class FixObject implements QuickFixMessage {

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
    
	private static ThreadLocal<Map<String, AccessorMapping>> FIX_FIELDMAP = new ThreadLocal<Map<String, AccessorMapping>>(){
		protected Map<String, AccessorMapping> initialValue() {
			return AccessorMapping.createAccessorMappingMap(
				AccessorMapping.createAccessorMapping(FixObject.class, "MsgID",     "getMsgID",    "setMsgID",    String.class),
				AccessorMapping.createAccessorMapping(FixObject.class, "int",       "getInteger",  "setInteger",  Integer.class),
				AccessorMapping.createAccessorMapping(FixObject.class, "string",    "getString",   "setString",   String.class),
				AccessorMapping.createAccessorMapping(FixObject.class, "decimal",   "getDecimal",  "setDecimal",  BigDecimal.class),
				AccessorMapping.createAccessorMapping(FixObject.class, "double",    "getFloating", "setFloating", Double.class),
				AccessorMapping.createAccessorMapping(FixObject.class, "datetime",  "getDatetime", "setDatetime", Date.class, new UTCDatetimeFormat()),
				AccessorMapping.createAccessorMapping(FixObject.class, "date",      "getDate",     "setDate",     Date.class, new UTCDateFormat()),
				AccessorMapping.createAccessorMapping(FixObject.class, "time",      "getTime",     "setTime",     Date.class, new UTCTimeFormat()),
				AccessorMapping.createAccessorMapping(FixObject.class, "boolean",   "getBool",     "setBool",     Boolean.class),
				AccessorMapping.createAccessorMapping(FixObject.class, "bytes",     "getBytes",    "setBytes",    byte[].class),
				AccessorMapping.createAccessorMapping(FixObject.class, "getField1",   "getField1",  "setField1",  BigDecimal.class),
				AccessorMapping.createAccessorMapping(FixObject.class, "getField2",   "getField2",  "setField2",  BigDecimal.class),
				AccessorMapping.createAccessorMapping(FixObject.class, "getConst",   "getConstant",  "setConstant",  String.class),
				AccessorMapping.createAccessorMapping(FixObject.class, "getOptional", "getOptional",  "setOptional",  BigDecimal.class),
				AccessorMapping.createAccessorMapping(FixObject.class, "getProhibit", "getProhibit",  "setProhibit",  BigDecimal.class),
				AccessorMapping.createAccessorMapping(FixObject.class, "firsts",       "getFirsts",        "setFirsts",        List.class, First.class)                              
			);
		}
	};

	private Context context;

	private List<First> firsts;
	
	private String msgID;
	
	private Integer integer;

	private String string;

	private BigDecimal decimal;

	private Double floating;

	private Date datetime;

	private Date date;

	private Date time;

	private Boolean bool;

	private byte[] bytes;

	private BigDecimal field1;

	private BigDecimal field2;
	
	private String constant;

	private BigDecimal optional;

	private BigDecimal prohibit;


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
		return FIX_FIELDMAP.get();
	}
	
	@Override
	public Map<String, AccessorMapping> getFixHeaderMap() {
	    return null;
	}
	
	@Override
	public Map<String, AccessorMapping> getFixTrailerMap() {
	    return null;
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
