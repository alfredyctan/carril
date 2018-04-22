package org.afc.carril.fix.quickfix.model;

public interface Logon {

	public Integer getBodyLength();

	public void setBodyLength(Integer bodyLength);

	public Integer getEncryptMethod();

	public void setEncryptMethod(Integer encryptMethod);

	public Integer getHeartBtInt();

	public void setHeartBtInt(Integer heartBtInt);

	public String getResetSeqNumFlag();

	public void setResetSeqNumFlag(String resetSeqNumFlag);

	public Integer getCheckSum();

	public void setCheckSum(Integer checkSum);
}
