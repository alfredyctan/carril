package org.afc.carril.fix.quickfix.model.fix;

import org.afc.carril.annotation.AnnotatedMapping;
import org.afc.carril.message.FixMessage;

public class FixLogin implements FixMessage {

	private static final long serialVersionUID = -1152225918988882483L;

	private Context context;
	
	@AnnotatedMapping
	private Integer bodyLength;

	@AnnotatedMapping
	private Integer encryptMethod;

	@AnnotatedMapping
	private Integer heartBtInt;

	@AnnotatedMapping
	private String resetSeqNumFlag;

	@AnnotatedMapping
	private Integer checkSum;

	@Override
	public Context getContext() {
	    return context;
	}
	
	@Override
	public void setContext(Context context) {
		this.context = context;
	}

	public Integer getBodyLength() {
		return bodyLength;
	}

	public void setBodyLength(Integer bodyLength) {
		this.bodyLength = bodyLength;
	}

	public Integer getEncryptMethod() {
		return encryptMethod;
	}

	public void setEncryptMethod(Integer encryptMethod) {
		this.encryptMethod = encryptMethod;
	}

	public Integer getHeartBtInt() {
		return heartBtInt;
	}

	public void setHeartBtInt(Integer heartBtInt) {
		this.heartBtInt = heartBtInt;
	}

	public String getResetSeqNumFlag() {
		return resetSeqNumFlag;
	}

	public void setResetSeqNumFlag(String resetSeqNumFlag) {
		this.resetSeqNumFlag = resetSeqNumFlag;
	}

	public Integer getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(Integer checkSum) {
		this.checkSum = checkSum;
	}

	@Override
	public String toString() {
		return "FixLogin [" + (context != null ? "context=" + context + ", " : "")
				+ (bodyLength != null ? "bodyLength=" + bodyLength + ", " : "")
				+ (encryptMethod != null ? "encryptMethod=" + encryptMethod + ", " : "")
				+ (heartBtInt != null ? "heartBtInt=" + heartBtInt + ", " : "")
				+ (resetSeqNumFlag != null ? "resetSeqNumFlag=" + resetSeqNumFlag + ", " : "")
				+ (checkSum != null ? "checkSum=" + checkSum : "") + "]";
	}
}
