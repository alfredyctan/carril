package org.afc.carril.message;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface FixMessage extends GenericMessage {

	/*
	 * Context object to keep all header compulsory fields in Fix protocol
	 */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public class Context {

		private String protocolVersion;

		private String senderCompID;

		private String targetCompID;

		private String msgType;

		private Integer msgSeqNum;

		private LocalDateTime sendingTime;

		/* for schema mapper */
		public Context(String protocolVersion, String senderCompID, String targetCompID) {
			this(protocolVersion, senderCompID, targetCompID, null, 0, null);
		}

		/* for static mapper */
		public Context(String protocolVersion, String senderCompID, String targetCompID, String msgType) {
			this(protocolVersion, senderCompID, targetCompID, msgType, 0, null);
		}
		/**
		 * create reversed context for response
		 * 
		 * @return
		 */
		public Context reverse() {
			return new Context(protocolVersion, targetCompID, senderCompID);
		}
	}

	public void setContext(Context context);

	public Context getContext();
	
	/**
	 * setter for chained operation
	 * eg.
	 * <pre>
	 *     new Quote().&lt;Quote&gt;with(quoteRequest.getContext().reverse()).setQuoteID(quoteRequest.getQuoteReqID())
	 * </pre>
	 * 
	 * @param context - Context to be set
	 * @return
	 */
	default public <T extends FixMessage> T with(Context context) {
		this.setContext(context);
		return (T)this;
	}

}
