package org.afc.carril.fix.quickfix;

import java.util.List;
import java.util.Map;

import lombok.Data;
import quickfix.Acceptor;
import quickfix.FileStoreFactory;
import quickfix.Initiator;
import quickfix.SLF4JLogFactory;
import quickfix.Session;
import quickfix.SessionFactory;
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.mina.NetworkingOptions;
import quickfix.mina.ssl.SSLSupport;

@Data
public class QuickFixSettings {

	private static final SessionID DEFAULT_SESSION_ID = new SessionID("DEFAULT", "", "");

	@Data
	public static class Sessions {
		
		private String id;
		
		/**
		 * Initiator setting for reconnect interval in seconds. Only valid when session
		 * connection type is "initiator".
		 *
		 * @see quickfix.SessionFactory#SETTING_CONNECTION_TYPE
		 */
		private Long reconnectInterval;
	
		/**
		 * Initiator setting for connection protocol (defaults to "tcp").
		 */
		private String socketConnectProtocol;
	
		@Data
		public static class SocketConnect {
			/**
			 * Initiator setting for connection host. Only valid when session connection
			 * type is "initiator".
			 *
			 * @see quickfix.SessionFactory#SETTING_CONNECTION_TYPE
			 */
			String host;
		
			/**
			 * Initiator setting for connection port. Only valid when session connection
			 * type is "initiator".
			 *
			 * @see quickfix.SessionFactory#SETTING_CONNECTION_TYPE
			 */
			Long port;
		}
	
		private List<SocketConnect> socketConnects;
		
		/**
		 * Initiator setting for local/bind host. Only valid when session connection
		 * type is "initiator".
		 *
		 * @see quickfix.SessionFactory#SETTING_CONNECTION_TYPE
		 */
		private String socketLocalHost;
	
		/**
		 * Initiator setting for local/bind port. Only valid when session connection
		 * type is "initiator".
		 *
		 * @see quickfix.SessionFactory#SETTING_CONNECTION_TYPE
		 */
		private Long socketLocalPort;
	
		/**
		 * Session setting for heartbeat interval (in seconds, default 30).
		 */
		private Long heartBtInt = 30L;
	
		/**
		 * Session setting for enabling message latency checks. Values are "Y" or "N".
		 * (default "N")
		 */
		private Boolean checkLatency = false;
	
		/**
		 * If set to Y, messages must be received from the counterparty with the correct
		 * SenderCompID and TargetCompID. Some systems will send you different CompIDs
		 * by design, so you must set this to N.
		 */
		private Boolean checkCompID;
	
		/**
		 * Session setting for maximum message latency (in seconds, default 120s).
		 */
		private Long maxLatency = 120L;
	
		/**
		 * Session setting for the test delay multiplier (0-1, as fraction of Heartbeat
		 * interval)
		 */
		private Double testRequestDelayMultiplier;
	
		/**
		 * Session scheduling setting to specify that session never reset
		 */
		private Boolean nonStopSession;
	
		/**
		 * Session scheduling setting to specify first day of trading week.
		 */
		private String startDay;
	
		/**
		 * Session scheduling setting to specify last day of trading week.
		 */
		private String endDay;
	
		/**
		 * Session scheduling setting to specify time zone for the session.
		 */
		private String timeZone;
	
		/**
		 * Session scheduling setting to specify starting time of the trading day.
		 * (default "00:00:00")
		 */
		private String startTime = "00:00:00";
	
		/**
		 * Session scheduling setting to specify end time of the trading day. (default
		 * "00:00:00")
		 */
		private String endTime = "00:00:00";
	
		/**
		 * Session setting to indicate whether a data dictionary should be used. If a
		 * data dictionary is not used then message validation is not possble.
		 */
		private Boolean useDataDictionary;
	
		/**
		 * Session setting specifying the path to the data dictionary to use for this
		 * session. This setting supports the possibility of a custom data dictionary
		 * for each session. Normally, the default data dictionary for a specific FIX
		 * version will be specified.
		 * 
		 * eg. /opt/app/cfg/Fix42.xml
		 */
		private String dataDictionary;
	
		/**
		 * Session setting specifying the path to the transport data dictionary. This
		 * setting supports the possibility of a custom transport data dictionary for
		 * each session. This setting would only be used with FIXT 1.1 and new transport
		 * protocols.
		 */
		private String transportDataDictionary;
	
		/**
		 * Session setting specifying the path to the application data dictionary to use
		 * for this session. This setting supports the possibility of a custom
		 * application data dictionary for each session. This setting would only be used
		 * with FIXT 1.1 and new transport protocols. This setting can be used as a
		 * prefix to specify multiple application dictionaries for the FIXT transport.
		 * For example:
		 * 
		 * <pre>
		 * <code>
		 * DefaultApplVerID=FIX.4.2
		 * AppDataDictionary=FIX42.xml
		 * AppDataDictionary.FIX.4.4=FIX44.xml
		 * </code>
		 * </pre>
		 * 
		 * This would use FIX42.xml for the default application version ID and FIX44.xml
		 * for any FIX 4.4 messages.
		 */
		private String appDataDictionary;
	
		/**
		 * Default is "Y". If set to N, fields that are out of order (i.e. body fields
		 * in the header, or header fields in the body) will not be rejected.
		 */
		private Boolean validateFieldsOutOfOrder;
	
		/**
		 * Session validation setting for enabling whether field ordering is validated.
		 * Values are "Y" or "N". Default is "Y".
		 */
		private Boolean validateUnorderedGroupFields;
	
		/**
		 * Session validation setting for enabling whether field values are validated.
		 * Empty fields values are not allowed. Values are "Y" or "N". Default is "Y".
		 */
		private Boolean validateFieldsHaveValues;
	
		/**
		 * Allow to bypass the message validation. Default is "Y".
		 */
		private Boolean validateIncomingMessage;
	
		/**
		 * Session setting for logon timeout (in seconds, default 10s).
		 */
		private Long logonTimeout = 10L;
	
		/**
		 * Session setting for logout timeout (in seconds).
		 */
		private String logoutTimeout;
	
		/**
		 * Session setting for doing an automatic sequence number reset on logout. Valid
		 * values are "Y" or "N". Default is "Y".
		 */
		private Boolean resetOnLogout = true;
	
		/**
		 * Check the next expected target SeqNum against the received SeqNum. Default is
		 * "Y". If a mismatch is detected, apply the following logic:
		 * <ul>
		 * <li>if lower than expected SeqNum , logout</li>
		 * <li>if higher, send a resend request</li>
		 * </ul>
		 */
		private Boolean validateSequenceNumbers;
	
		/**
		 * Session setting for doing an automatic sequence number reset on disconnect.
		 * Valid values are "Y" or "N". Default is "N".
		 */
		private Boolean resetOnDisconnect;
	
		/**
		 * Session setting for doing an automatic reset when an error occurs. Valid
		 * values are "Y" or "N". Default is "N". A reset means disconnect, sequence
		 * numbers reset, store cleaned and reconnect, as for a daily reset.
		 */
		private Boolean resetOnError;
	
		/**
		 * Session setting for doing an automatic disconnect when an error occurs. Valid
		 * values are "Y" or "N". Default is "N".
		 */
		private Boolean disconnectOnError;
	
		/**
		 * Session setting to control precision in message timestamps.Valid values are "SECONDS", "MILLIS", "MICROS", "NANOS". 
		 * Default is "MILLIS".Only valid for FIX version >= 4.2.
		 */
		private String timestampPrecision;
	
		/**
		 * Controls validation of user-defined fields.
		 */
		private Boolean validateUserDefinedFields;
	
		/**
		 * Session setting that causes the session to reset sequence numbers when
		 * initiating a logon (>= FIX 4.2). (default is "Y")
		 */
		private Boolean resetOnLogon = true;
	
		/**
		 * Session description. Used by external tools.
		 */
		private String description;

		/**
		 * Requests that state and message data be refreshed from the message store at
		 * logon, if possible. This supports simple failover behavior for acceptors
		 */
		private Boolean refreshOnLogon;
		
		/**
		 * Configures the session to send redundant resend requests (off, by default).
		 */
		private Boolean sendRedundantResendRequests;
	
		/**
		 * Persist messages setting (true, by default). If set to false this will cause
		 * the Session to not persist any messages and all resend requests will be
		 * answered with a gap fill.
		 */
		private Boolean persistMessages;
	
		/**
		 * Use actual end of sequence gap for resend requests rather than using
		 * "infinity" as the end sequence of the gap. Not recommended by the FIX
		 * specification, but needed for some counterparties.
		 */
		private Boolean closedResendInterval;
	
		/**
		 * Allow unknown fields in messages. This is intended for unknown fields with
		 * tags < 5000 (not user defined fields)
		 */
		private Boolean allowUnknownMsgFields;
	
		/**
		 * For FIXT.1.1
		 */
		private String defaultApplVerID;
	
		/**
		 * Allow to disable heart beat failure detection
		 */
		private Boolean disableHeartBeatCheck;
	
		/**
		 * Return the last msg seq number processed (optional tag 369). Valid values are
		 * "Y" or "N". Default is "N".
		 */
		private Boolean enableLastMsgSeqNumProcessed;
	
		/**
		 * Return the next expected message sequence number (optional tag 789 on Logon)
		 * on sent Logon message and use value of tag 789 on received Logon message to
		 * synchronize session. Valid values are "Y" or "N". Default is "N". This should
		 * not be enabled for FIX versions lower than 4.4
		 */
		private Boolean enableNextExpectedMsgSeqNum;
	
		/**
		 * If RejectInvalidMessage is set to N, only a warning will be logged on
		 * reception of message that fails data dictionary validation.
		 */
		private Boolean rejectInvalidMessage;
	
		/**
		 * If this configuration is enabled, an uncaught Exception or Error in the
		 * application's message processing will lead to a (BusinessMessage)Reject being
		 * sent to the counterparty and the incoming message sequence number will be
		 * incremented.
		 * 
		 * If disabled (default), the problematic incoming message is discarded and the
		 * message sequence number is not incremented. Processing of the next valid
		 * message will cause detection of a sequence gap and a ResendRequest will be
		 * generated.
		 */
		private Boolean rejectMessageOnUnhandledException;
	
		/**
		 * If RequiresOrigSendingTime is set to N, PossDup messages lacking that field
		 * will not be rejected.
		 */
		private Boolean requiresOrigSendingTime;
	
		/**
		 * Fill in heartbeats on resend when reading from message store fails.
		 */
		private String forceResendWhenCorruptedStore;
	
		/**
		 * List of remote IP addresses which are allowed to connect to this acceptor.
		 */
		private String allowedRemoteAddresses;
		
		/**
		 * Setting to limit the size of a resend request in case of missing messages.
		 * This is useful when the remote FIX engine does not allow to ask for more than
		 * n message for a ResendRequest
		 */
		private String resendRequestChunkSize;
	
		/**
		 * When the keepalive option is set for a TCP socket and no data has been
		 * exchanged across the socket in either direction for 2 hours (NOTE: the actual
		 * value is implementation dependent), TCP automatically sends a keepalive probe
		 * to the peer. This probe is a TCP segment to which the peer must respond. One
		 * of three responses is expected:
		 * 
		 * The peer responds with the expected ACK. The application is not notified
		 * (since everything is OK). TCP will send another probe following another 2
		 * hours of inactivity. The peer responds with an RST, which tells the local TCP
		 * that the peer host has crashed and rebooted. The socket is closed. There is
		 * no response from the peer. The socket is closed.
		 * 
		 * The purpose of this option is to detect if the peer host crashes.
		 */
		private Boolean socketKeepAlive;
	
		/**
		 * When the OOBINLINE option is set, any TCP urgent data received on the socket
		 * will be received through the socket input stream. When the option is disabled
		 * (which is the default) urgent data is silently discarded.
		 */
		private Boolean socketOobInline;
	
		/**
		 * Set a hint the size of the underlying buffers used by the platform for
		 * incoming network I/O. When used in set, this is a suggestion to the kernel
		 * from the application about the size of buffers to use for the data to be
		 * received over the socket.
		 */
		private Long socketReceiveBufferSize;
	
		/**
		 * Sets SO_REUSEADDR for a socket. This is used only for MulticastSockets in
		 * java, and it is set by default for MulticastSockets.
		 */
		private Boolean socketReuseAddress;
	
		/**
		 * Set a hint the size of the underlying buffers used by the platform for
		 * outgoing network I/O. When used in set, this is a suggestion to the kernel
		 * from the application about the size of buffers to use for the data to be sent
		 * over the socket.
		 */
		private Long socketSendBufferSize;
	
		/**
		 * Specify a linger-on-close timeout. This option disables/enables immediate
		 * return from a close() of a TCP Socket. Enabling this option with a non-zero
		 * Integer timeout means that a close() will block pending the transmission and
		 * acknowledgement of all data written to the peer, at which point the socket is
		 * closed gracefully. Upon reaching the linger timeout, the socket is closed
		 * forcefully, with a TCP RST. Enabling the option with a timeout of zero does a
		 * forceful close immediately. If the specified timeout value exceeds 65,535 it
		 * will be reduced to 65,535.
		 */
		private Long socketLinger;
	
		/**
		 * Disable Nagle's algorithm for this connection. Written data to the network is
		 * not buffered pending acknowledgement of previously written data.
		 * 
		 */
		private Boolean socketTcpNoDelay;
	
		/**
		 * Sets traffic class or type-of-service octet in the IP header for packets sent
		 * from this Socket. As the underlying network implementation may ignore this
		 * value applications should consider it a hint.
		 * 
		 * The tc must be in the range 0 <= tc <= 255 or an IllegalArgumentException
		 * will be thrown.
		 * 
		 * Notes:
		 * 
		 * for Internet Protocol v4 the value consists of an octet with precedence and
		 * TOS fields as detailed in RFC 1349. The TOS field is bitset created by
		 * bitwise-or'ing values such the following :-
		 * 
		 * IPTOS_LOWCOST (0x02) IPTOS_RELIABILITY (0x04) IPTOS_THROUGHPUT (0x08)
		 * IPTOS_LOWDELAY (0x10)
		 * 
		 * The last low order bit is always ignored as this corresponds to the MBZ (must
		 * be zero) bit.
		 * 
		 * Setting bits in the precedence field may result in a SocketException
		 * indicating that the operation is not permitted.
		 */
		private Long socketTrafficClass;
	
		/**
		 * Write messages synchronously. This is not generally recommended as it may
		 * result in performance degradation. The MINA communication layer is
		 * asynchronous by design, but this option will override that behavior if
		 * needed.
		 */
		private Boolean socketSynchronousWrites;
	
		/**
		 * The time in milliseconds to wait for a write to complete.
		 */
		private Long socketSynchronousWriteTimeout;
	
		/**
		 * KeyStore password
		 */
		private String socketKeyStorePassword;
	
		/**
		 * KeyStore to use with SSL
		 */
		private String socketKeyStore;
	
		/**
		 * Enables SSL usage for QFJ acceptor or initiator.
		 * 
		 */
		private Boolean socketUseSSL;
	
		/**
		 * Protocols enabled for use with the SSL engine.
		 * https://docs.oracle.com/javase/6/docs/technotes/guides/security/SunProviders.html
		 */
		private String enabledProtocols;
	
		/**
		 * Cipher suites enabled for use with the SSL engine.
		 * https://docs.oracle.com/javase/6/docs/technotes/guides/security/SunProviders.html
		 */
		private String cipherSuites;
	
		/**
		 * Log category for events.
		 */
		private String slf4jLogEventCategory;
	
		/**
		 * Log category for error events.
		 */
		private String slf4jLogErrorEventCategory;
	
		/**
		 * Log category for incoming messages.
		 */
		private String slf4jLogIncomingMessageCategory;
	
		/**
		 * Log category for outgoing messages.
		 */
		private String slf4jLogOutgoingMessageCategory;
	
		/**
		 * Flag for prepending session ID to log output
		 */
		private String slf4jLogPrependSessionID;
	
		/**
		 * Controls logging of heartbeats (Y or N)
		 */
		private String slf4jLogHeartbeats;

	}
	
	/**
	 * Specifies the connection type for a session. Valid values are "initiator" and
	 * "acceptor".
	 */
	private String connectionType;

	/**
	 * Acceptor setting specifying the socket protocol used to accept connections.
	 */
	private String socketAcceptProtocol;

	/**
	 * Acceptor setting specifying port for accepting FIX client connections.
	 */
	private Long socketAcceptPort;

	/**
	 * Acceptor setting specifying local IP interface address for accepting
	 * connections.
	 */
	private String socketAcceptAddress;

	/**
	 * Acceptor setting specifying local IP interface address for accepting
	 * connections.
	 */
	private String acceptorTemplate;

	/**
	 * File path for writing the message store.
	 */
	private String fileStorePath;

	/**
	 * Boolean option for controlling whether the FileStore syncs to the hard drive
	 * on every write. It's safer to sync, but it's also much slower (100x or more
	 * slower in some cases).
	 */
	private Boolean fileStoreSync;

	/**
	 * Numeric option limiting the number of messages stored in the in-memory
	 * message index. If, during recovery, one or more messages are requested whose
	 * offset/size is not cached in memory, the on-disk header file will be
	 * searched. Values can be from 0 to Integer.MAX_VALUE (default), inclusive.
	 */
	private Long fileStoreMaxCachedMsgs;

	private Map<String, Sessions> sessions;
	
	/**
	 * @url http://www.quickfixj.org/quickfixj/javadoc/1.5.1/constant-values.html?bcsi_scan_BB06E4EF3AEA9F08=uF2X6z//DyEIwKG28AGCucjiCiMJAAAArM4HHQ==&bcsi_scan_filename=constant-values.html
	 */
	public SessionSettings createDefaultSessionSettings() {
		final SessionSettings sessionSettings = new SessionSettings();
		
		configure(sessionSettings, DEFAULT_SESSION_ID, SessionFactory.SETTING_CONNECTION_TYPE, connectionType);
		configure(sessionSettings, DEFAULT_SESSION_ID, FileStoreFactory.SETTING_FILE_STORE_PATH, fileStorePath);
		configure(sessionSettings, DEFAULT_SESSION_ID, FileStoreFactory.SETTING_FILE_STORE_SYNC, fileStoreSync);
		configure(sessionSettings, DEFAULT_SESSION_ID, FileStoreFactory.SETTING_FILE_STORE_MAX_CACHED_MSGS, fileStoreMaxCachedMsgs);
		configure(sessionSettings, DEFAULT_SESSION_ID, Acceptor.SETTING_SOCKET_ACCEPT_PROTOCOL, socketAcceptProtocol);
		configure(sessionSettings, DEFAULT_SESSION_ID, Acceptor.SETTING_SOCKET_ACCEPT_PORT, socketAcceptPort);
		configure(sessionSettings, DEFAULT_SESSION_ID, Acceptor.SETTING_SOCKET_ACCEPT_ADDRESS, socketAcceptAddress);
		configure(sessionSettings, DEFAULT_SESSION_ID, Acceptor.SETTING_ACCEPTOR_TEMPLATE, acceptorTemplate);

		sessions.forEach((key, setting) -> {
			SessionID sessionId = key.equalsIgnoreCase("default") ? DEFAULT_SESSION_ID : QuickFixUtil.createSessionID(setting.id);
			
			configure(sessionSettings, sessionId, Initiator.SETTING_RECONNECT_INTERVAL, setting.reconnectInterval);
			configure(sessionSettings, sessionId, Initiator.SETTING_SOCKET_CONNECT_PROTOCOL, setting.socketConnectProtocol);
			if (setting.socketConnects != null) {
				int size = setting.socketConnects.size();
				for (int i = 0; i < size; i++) {
					final String suffix = (i == 0) ? "" : String.valueOf(i);
					setting.socketConnects.forEach(sc-> {
						configure(sessionSettings, sessionId, Initiator.SETTING_SOCKET_CONNECT_HOST + suffix, sc.host);
						configure(sessionSettings, sessionId, Initiator.SETTING_SOCKET_CONNECT_PORT + suffix, sc.port);
					});
				}
			}
			configure(sessionSettings, sessionId, Initiator.SETTING_SOCKET_LOCAL_HOST, setting.socketLocalHost);
			configure(sessionSettings, sessionId, Initiator.SETTING_SOCKET_LOCAL_PORT, setting.socketLocalPort);


			configure(sessionSettings, sessionId, Session.SETTING_HEARTBTINT, setting.heartBtInt);
			configure(sessionSettings, sessionId, Session.SETTING_CHECK_LATENCY, setting.checkLatency);
			configure(sessionSettings, sessionId, Session.SETTING_CHECK_COMP_ID, setting.checkCompID);
			configure(sessionSettings, sessionId, Session.SETTING_MAX_LATENCY, setting.maxLatency);
			configure(sessionSettings, sessionId, Session.SETTING_TEST_REQUEST_DELAY_MULTIPLIER, setting.testRequestDelayMultiplier);
			configure(sessionSettings, sessionId, Session.SETTING_NON_STOP_SESSION, setting.nonStopSession);
			configure(sessionSettings, sessionId, Session.SETTING_START_DAY, setting.startDay);
			configure(sessionSettings, sessionId, Session.SETTING_END_DAY, setting.endDay);
			configure(sessionSettings, sessionId, Session.SETTING_TIMEZONE, setting.timeZone);
			configure(sessionSettings, sessionId, Session.SETTING_START_TIME, setting.startTime);
			configure(sessionSettings, sessionId, Session.SETTING_END_TIME, setting.endTime);
			configure(sessionSettings, sessionId, Session.SETTING_USE_DATA_DICTIONARY, setting.useDataDictionary);
			configure(sessionSettings, sessionId, Session.SETTING_DATA_DICTIONARY, setting.dataDictionary);
			configure(sessionSettings, sessionId, Session.SETTING_TRANSPORT_DATA_DICTIONARY, setting.transportDataDictionary);
			configure(sessionSettings, sessionId, Session.SETTING_APP_DATA_DICTIONARY, setting.appDataDictionary);
			configure(sessionSettings, sessionId, Session.SETTING_VALIDATE_FIELDS_OUT_OF_ORDER, setting.validateFieldsOutOfOrder);
			configure(sessionSettings, sessionId, Session.SETTING_VALIDATE_UNORDERED_GROUP_FIELDS, setting.validateUnorderedGroupFields);
			configure(sessionSettings, sessionId, Session.SETTING_VALIDATE_FIELDS_HAVE_VALUES, setting.validateFieldsHaveValues);
			configure(sessionSettings, sessionId, Session.SETTING_VALIDATE_INCOMING_MESSAGE, setting.validateIncomingMessage);
			configure(sessionSettings, sessionId, Session.SETTING_LOGON_TIMEOUT, setting.logonTimeout);
			configure(sessionSettings, sessionId, Session.SETTING_LOGOUT_TIMEOUT, setting.logoutTimeout);
			configure(sessionSettings, sessionId, Session.SETTING_RESET_ON_LOGOUT, setting.resetOnLogout);
			configure(sessionSettings, sessionId, Session.SETTING_VALIDATE_SEQUENCE_NUMBERS, setting.validateSequenceNumbers);
			configure(sessionSettings, sessionId, Session.SETTING_RESET_ON_DISCONNECT, setting.resetOnDisconnect);
			configure(sessionSettings, sessionId, Session.SETTING_RESET_ON_ERROR, setting.resetOnError);
			configure(sessionSettings, sessionId, Session.SETTING_DISCONNECT_ON_ERROR, setting.disconnectOnError);
			configure(sessionSettings, sessionId, Session.SETTING_TIMESTAMP_PRECISION, setting.timestampPrecision);
			configure(sessionSettings, sessionId, Session.SETTING_VALIDATE_USER_DEFINED_FIELDS, setting.validateUserDefinedFields);
			configure(sessionSettings, sessionId, Session.SETTING_RESET_ON_LOGON, setting.resetOnLogon);
			configure(sessionSettings, sessionId, Session.SETTING_DESCRIPTION, setting.description);
			configure(sessionSettings, sessionId, Session.SETTING_REFRESH_ON_LOGON, setting.refreshOnLogon);
			configure(sessionSettings, sessionId, Session.SETTING_SEND_REDUNDANT_RESEND_REQUEST, setting.sendRedundantResendRequests);
			configure(sessionSettings, sessionId, Session.SETTING_PERSIST_MESSAGES, setting.persistMessages);
			configure(sessionSettings, sessionId, Session.SETTING_USE_CLOSED_RESEND_INTERVAL, setting.closedResendInterval);
			configure(sessionSettings, sessionId, Session.SETTING_ALLOW_UNKNOWN_MSG_FIELDS, setting.allowUnknownMsgFields);
			configure(sessionSettings, sessionId, Session.SETTING_DEFAULT_APPL_VER_ID, setting.defaultApplVerID);
			configure(sessionSettings, sessionId, Session.SETTING_DISABLE_HEART_BEAT_CHECK, setting.disableHeartBeatCheck);
			configure(sessionSettings, sessionId, Session.SETTING_ENABLE_LAST_MSG_SEQ_NUM_PROCESSED, setting.enableLastMsgSeqNumProcessed);
			configure(sessionSettings, sessionId, Session.SETTING_ENABLE_NEXT_EXPECTED_MSG_SEQ_NUM, setting.enableNextExpectedMsgSeqNum);
			configure(sessionSettings, sessionId, Session.SETTING_REJECT_INVALID_MESSAGE, setting.rejectInvalidMessage);
			configure(sessionSettings, sessionId, Session.SETTING_REJECT_MESSAGE_ON_UNHANDLED_EXCEPTION, setting.rejectMessageOnUnhandledException);
			configure(sessionSettings, sessionId, Session.SETTING_REQUIRES_ORIG_SENDING_TIME, setting.requiresOrigSendingTime);
			configure(sessionSettings, sessionId, Session.SETTING_FORCE_RESEND_WHEN_CORRUPTED_STORE, setting.forceResendWhenCorruptedStore);
			configure(sessionSettings, sessionId, Session.SETTING_ALLOWED_REMOTE_ADDRESSES, setting.allowedRemoteAddresses);
			configure(sessionSettings, sessionId, Session.SETTING_RESEND_REQUEST_CHUNK_SIZE, setting.resendRequestChunkSize);

			configure(sessionSettings, sessionId, NetworkingOptions.SETTING_SOCKET_KEEPALIVE, setting.socketKeepAlive);
			configure(sessionSettings, sessionId, NetworkingOptions.SETTING_SOCKET_OOBINLINE, setting.socketOobInline);
			configure(sessionSettings, sessionId, NetworkingOptions.SETTING_SOCKET_RECEIVE_BUFFER_SIZE, setting.socketReceiveBufferSize);
			configure(sessionSettings, sessionId, NetworkingOptions.SETTING_SOCKET_REUSE_ADDRESS, setting.socketReuseAddress);
			configure(sessionSettings, sessionId, NetworkingOptions.SETTING_SOCKET_SEND_BUFFER_SIZE, setting.socketSendBufferSize);
			configure(sessionSettings, sessionId, NetworkingOptions.SETTING_SOCKET_LINGER, setting.socketLinger);
			configure(sessionSettings, sessionId, NetworkingOptions.SETTING_SOCKET_TCP_NODELAY, setting.socketTcpNoDelay);
			configure(sessionSettings, sessionId, NetworkingOptions.SETTING_SOCKET_TRAFFIC_CLASS, setting.socketTrafficClass);
			configure(sessionSettings, sessionId, NetworkingOptions.SETTING_SOCKET_SYNCHRONOUS_WRITES, setting.socketSynchronousWrites);
			configure(sessionSettings, sessionId, NetworkingOptions.SETTING_SOCKET_SYNCHRONOUS_WRITE_TIMEOUT, setting.socketSynchronousWriteTimeout);

			configure(sessionSettings, sessionId, SSLSupport.SETTING_KEY_STORE_PWD, setting.socketKeyStorePassword);
			configure(sessionSettings, sessionId, SSLSupport.SETTING_KEY_STORE_NAME, setting.socketKeyStore);
			configure(sessionSettings, sessionId, SSLSupport.SETTING_USE_SSL, setting.socketUseSSL);
			configure(sessionSettings, sessionId, SSLSupport.SETTING_ENABLED_PROTOCOLS, setting.enabledProtocols);
			configure(sessionSettings, sessionId, SSLSupport.SETTING_CIPHER_SUITES, setting.cipherSuites);

			configure(sessionSettings, sessionId, SLF4JLogFactory.SETTING_EVENT_CATEGORY, setting.slf4jLogEventCategory);
			configure(sessionSettings, sessionId, SLF4JLogFactory.SETTING_ERROR_EVENT_CATEGORY, setting.slf4jLogErrorEventCategory);
			configure(sessionSettings, sessionId, SLF4JLogFactory.SETTING_INMSG_CATEGORY, setting.slf4jLogIncomingMessageCategory);
			configure(sessionSettings, sessionId, SLF4JLogFactory.SETTING_OUTMSG_CATEGORY, setting.slf4jLogOutgoingMessageCategory);
			configure(sessionSettings, sessionId, SLF4JLogFactory.SETTING_PREPEND_SESSION_ID, setting.slf4jLogPrependSessionID);
			configure(sessionSettings, sessionId, SLF4JLogFactory.SETTING_LOG_HEARTBEATS, setting.slf4jLogHeartbeats);
		});
		

		return sessionSettings;
	}

	private static void configure(SessionSettings sessionSettings, SessionID sessionId, String key, Object value) {
		if (value != null) {
			if (value instanceof String) {
				sessionSettings.setString(sessionId, key, (String) value);
			} else if (value instanceof Boolean) {
				sessionSettings.setBool(sessionId, key, (Boolean) value);
			} else if (value instanceof Double) {
				sessionSettings.setDouble(sessionId, key, (Double) value);
			} else if (value instanceof Long) {
				sessionSettings.setLong(sessionId, key, (Long) value);
			}
		}
	}
}
