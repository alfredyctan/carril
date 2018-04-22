package org.afc.carril.message;

import java.util.Date;
import java.util.Map;

import org.afc.carril.transport.AccessorMapping;

public interface FixMessage extends GenericMessage {
	
	/*
	 * Context object to keep all header compulsory fields in Fix protocol 
	 */
	public class Context {

		private String protocolVersion;

		private String senderCompID;

		private String targetCompID;

		private String msgType;

		private int msgSeqNum;

		private Date sendingTime;
		
		public Context(String protocolVersion, String msgType, String senderCompID, String targetCompID, int msgSeqNum, Date sendingTime) {
	        this.protocolVersion = protocolVersion;
	        this.msgType = msgType;
	        this.senderCompID = senderCompID;
	        this.targetCompID = targetCompID;
	        this.msgSeqNum = msgSeqNum;
	        this.sendingTime = sendingTime;
        }

		public Context(String protocolVersion, String senderCompID, String targetCompID) {
			this(protocolVersion, null, senderCompID, targetCompID, 0, null);
        }

		public void setMsgType(String msgType) {
			this.msgType = msgType;
		}
		
		public String getProtocolVersion() {
        	return protocolVersion;
        }

		public String getSenderCompID() {
        	return senderCompID;
        }

		public String getTargetCompID() {
        	return targetCompID;
        }

		public String getMsgType() {
        	return msgType;
        }

		public int getMsgSeqNum() {
        	return msgSeqNum;
        }

		public Date getSendingTime() {
        	return sendingTime;
        }

		@Override
		public String toString() {
			return "Context [" + (protocolVersion != null ? "protocolVersion=" + protocolVersion + ", " : "")
					+ (senderCompID != null ? "senderCompID=" + senderCompID + ", " : "")
					+ (targetCompID != null ? "targetCompID=" + targetCompID + ", " : "")
					+ (msgType != null ? "msgType=" + msgType + ", " : "") + "msgSeqNum=" + msgSeqNum + ", "
					+ (sendingTime != null ? "sendingTime=" + sendingTime : "") + "]";
		}
	}

	public void setContext(Context context);
	
	public Context getContext();
	
}
