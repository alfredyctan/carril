package org.afc.carril.fix.quickfix;

import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import quickfix.Acceptor;
import quickfix.FileStoreFactory;
import quickfix.Initiator;
import quickfix.SLF4JLogFactory;
import quickfix.Session;
import quickfix.SessionFactory;
import quickfix.SessionSettings;
import quickfix.mina.NetworkingOptions;
import quickfix.mina.ssl.SSLSupport;

@ConfigurationProperties(prefix = "carril.quickfix")
public class QuickFixSettings {

	/**
	 * Specifies the connection type for a session. Valid values are "initiator" and
	 * "acceptor".
	 */
	private String connectionType;

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

	/**
	 * Initiator setting for connection host. Only valid when session connection
	 * type is "initiator".
	 *
	 * @see quickfix.SessionFactory#SETTING_CONNECTION_TYPE
	 */
	private String socketConnectHost;

	/**
	 * Initiator setting for connection port. Only valid when session connection
	 * type is "initiator".
	 *
	 * @see quickfix.SessionFactory#SETTING_CONNECTION_TYPE
	 */
	private Long socketConnectPort;

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
	private String validateSequenceNumbers;

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
	 * Session setting to enable milliseconds in message timestamps. Valid values
	 * are "Y" or "N". Default is "Y". Only valid for FIX version >= 4.2.
	 */
	private Boolean millisecondsInTimeStamp;

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

	// /** default Y */
	// private boolean enableMillsTimeStamp = true;
	//
	//
	// /** default N */
	// private boolean enableSSL = false;
	//
	// /** eg. /opt/app/cfg/keystore.jks */
	// private String keyStore;
	//
	// /** eg. zzzzzz */
	// private String keyStorePassword;
	//
	// /** default */
	//
	// /** e.g. www.afc.org */
	// private String host;
	//
	// /** e.g. 30100 */
	// private long port;
	//
	// /** default 5 */
	// private long reconnectInterval = 5;
	//
	// /** default 32768 */
	// private long socketRecvBuffer = 32768;
	//
	// /** default 32768 */
	// private long socketSendBuffer = 32768;
	//
	// /** eg. /opt/app/msg */
	// private String fileStorePath;
	//
	// /** default false */
	// private boolean logHeartbeat = false;

	/**
	 * @url http://www.quickfixj.org/quickfixj/javadoc/1.5.1/constant-values.html?bcsi_scan_BB06E4EF3AEA9F08=uF2X6z//DyEIwKG28AGCucjiCiMJAAAArM4HHQ==&bcsi_scan_filename=constant-values.html
	 */
	private Properties customProperties;

	public SessionSettings createDefaultSessionSettings() {
		SessionSettings sessionSettings = new SessionSettings();
		sessionSettings.setString(SessionFactory.SETTING_CONNECTION_TYPE, connectionType);

		configure(sessionSettings, Initiator.SETTING_RECONNECT_INTERVAL, reconnectInterval);
		configure(sessionSettings, Initiator.SETTING_SOCKET_CONNECT_PROTOCOL, socketConnectProtocol);
		configure(sessionSettings, Initiator.SETTING_SOCKET_CONNECT_HOST, socketConnectHost);
		configure(sessionSettings, Initiator.SETTING_SOCKET_CONNECT_PORT, socketConnectPort);
		configure(sessionSettings, Initiator.SETTING_SOCKET_LOCAL_HOST, socketLocalHost);
		configure(sessionSettings, Initiator.SETTING_SOCKET_LOCAL_PORT, socketLocalPort);

		configure(sessionSettings, Acceptor.SETTING_SOCKET_ACCEPT_PROTOCOL, socketAcceptProtocol);
		configure(sessionSettings, Acceptor.SETTING_SOCKET_ACCEPT_PORT, socketAcceptPort);
		configure(sessionSettings, Acceptor.SETTING_SOCKET_ACCEPT_ADDRESS, socketAcceptAddress);
		configure(sessionSettings, Acceptor.SETTING_ACCEPTOR_TEMPLATE, acceptorTemplate);

		configure(sessionSettings, Session.SETTING_HEARTBTINT, heartBtInt);
		configure(sessionSettings, Session.SETTING_CHECK_LATENCY, checkLatency);
		configure(sessionSettings, Session.SETTING_CHECK_COMP_ID, checkCompID);
		configure(sessionSettings, Session.SETTING_MAX_LATENCY, maxLatency);
		configure(sessionSettings, Session.SETTING_TEST_REQUEST_DELAY_MULTIPLIER, testRequestDelayMultiplier);
		configure(sessionSettings, Session.SETTING_NON_STOP_SESSION, nonStopSession);
		configure(sessionSettings, Session.SETTING_START_DAY, startDay);
		configure(sessionSettings, Session.SETTING_END_DAY, endDay);
		configure(sessionSettings, Session.SETTING_TIMEZONE, timeZone);
		configure(sessionSettings, Session.SETTING_START_TIME, startTime);
		configure(sessionSettings, Session.SETTING_END_TIME, endTime);
		configure(sessionSettings, Session.SETTING_USE_DATA_DICTIONARY, useDataDictionary);
		configure(sessionSettings, Session.SETTING_DATA_DICTIONARY, dataDictionary);
		configure(sessionSettings, Session.SETTING_TRANSPORT_DATA_DICTIONARY, transportDataDictionary);
		configure(sessionSettings, Session.SETTING_APP_DATA_DICTIONARY, appDataDictionary);
		configure(sessionSettings, Session.SETTING_VALIDATE_FIELDS_OUT_OF_ORDER, validateFieldsOutOfOrder);
		configure(sessionSettings, Session.SETTING_VALIDATE_UNORDERED_GROUP_FIELDS, validateUnorderedGroupFields);
		configure(sessionSettings, Session.SETTING_VALIDATE_FIELDS_HAVE_VALUES, validateFieldsHaveValues);
		configure(sessionSettings, Session.SETTING_VALIDATE_INCOMING_MESSAGE, validateIncomingMessage);
		configure(sessionSettings, Session.SETTING_LOGON_TIMEOUT, logonTimeout);
		configure(sessionSettings, Session.SETTING_LOGOUT_TIMEOUT, logoutTimeout);
		configure(sessionSettings, Session.SETTING_RESET_ON_LOGOUT, resetOnLogout);
		configure(sessionSettings, Session.SETTING_VALIDATE_SEQUENCE_NUMBERS, validateSequenceNumbers);
		configure(sessionSettings, Session.SETTING_RESET_ON_DISCONNECT, resetOnDisconnect);
		configure(sessionSettings, Session.SETTING_RESET_ON_ERROR, resetOnError);
		configure(sessionSettings, Session.SETTING_DISCONNECT_ON_ERROR, disconnectOnError);
		configure(sessionSettings, Session.SETTING_MILLISECONDS_IN_TIMESTAMP, millisecondsInTimeStamp);
		configure(sessionSettings, Session.SETTING_VALIDATE_USER_DEFINED_FIELDS, validateUserDefinedFields);
		configure(sessionSettings, Session.SETTING_RESET_ON_LOGON, resetOnLogon);
		configure(sessionSettings, Session.SETTING_DESCRIPTION, description);
		configure(sessionSettings, Session.SETTING_REFRESH_ON_LOGON, refreshOnLogon);
		configure(sessionSettings, Session.SETTING_SEND_REDUNDANT_RESEND_REQUEST, sendRedundantResendRequests);
		configure(sessionSettings, Session.SETTING_PERSIST_MESSAGES, persistMessages);
		configure(sessionSettings, Session.SETTING_USE_CLOSED_RESEND_INTERVAL, closedResendInterval);
		configure(sessionSettings, Session.SETTING_ALLOW_UNKNOWN_MSG_FIELDS, allowUnknownMsgFields);
		configure(sessionSettings, Session.SETTING_DEFAULT_APPL_VER_ID, defaultApplVerID);
		configure(sessionSettings, Session.SETTING_DISABLE_HEART_BEAT_CHECK, disableHeartBeatCheck);
		configure(sessionSettings, Session.SETTING_ENABLE_LAST_MSG_SEQ_NUM_PROCESSED, enableLastMsgSeqNumProcessed);
		configure(sessionSettings, Session.SETTING_ENABLE_NEXT_EXPECTED_MSG_SEQ_NUM, enableNextExpectedMsgSeqNum);
		configure(sessionSettings, Session.SETTING_REJECT_INVALID_MESSAGE, rejectInvalidMessage);
		configure(sessionSettings, Session.SETTING_REJECT_MESSAGE_ON_UNHANDLED_EXCEPTION,
				rejectMessageOnUnhandledException);
		configure(sessionSettings, Session.SETTING_REQUIRES_ORIG_SENDING_TIME, requiresOrigSendingTime);
		configure(sessionSettings, Session.SETTING_FORCE_RESEND_WHEN_CORRUPTED_STORE, forceResendWhenCorruptedStore);
		configure(sessionSettings, Session.SETTING_ALLOWED_REMOTE_ADDRESSES, allowedRemoteAddresses);
		configure(sessionSettings, Session.SETTING_RESEND_REQUEST_CHUNK_SIZE, resendRequestChunkSize);

		configure(sessionSettings, NetworkingOptions.SETTING_SOCKET_KEEPALIVE, socketKeepAlive);
		configure(sessionSettings, NetworkingOptions.SETTING_SOCKET_OOBINLINE, socketOobInline);
		configure(sessionSettings, NetworkingOptions.SETTING_SOCKET_RECEIVE_BUFFER_SIZE, socketReceiveBufferSize);
		configure(sessionSettings, NetworkingOptions.SETTING_SOCKET_REUSE_ADDRESS, socketReuseAddress);
		configure(sessionSettings, NetworkingOptions.SETTING_SOCKET_SEND_BUFFER_SIZE, socketSendBufferSize);
		configure(sessionSettings, NetworkingOptions.SETTING_SOCKET_LINGER, socketLinger);
		configure(sessionSettings, NetworkingOptions.SETTING_SOCKET_TCP_NODELAY, socketTcpNoDelay);
		configure(sessionSettings, NetworkingOptions.SETTING_SOCKET_TRAFFIC_CLASS, socketTrafficClass);
		configure(sessionSettings, NetworkingOptions.SETTING_SOCKET_SYNCHRONOUS_WRITES, socketSynchronousWrites);
		configure(sessionSettings, NetworkingOptions.SETTING_SOCKET_SYNCHRONOUS_WRITE_TIMEOUT,
				socketSynchronousWriteTimeout);

		configure(sessionSettings, SSLSupport.SETTING_KEY_STORE_PWD, socketKeyStorePassword);
		configure(sessionSettings, SSLSupport.SETTING_KEY_STORE_NAME, socketKeyStore);
		configure(sessionSettings, SSLSupport.SETTING_USE_SSL, socketUseSSL);
		configure(sessionSettings, SSLSupport.SETTING_ENABLE_PROTOCOLE, enabledProtocols);
		configure(sessionSettings, SSLSupport.SETTING_CIPHER_SUITES, cipherSuites);

		configure(sessionSettings, FileStoreFactory.SETTING_FILE_STORE_PATH, fileStorePath);
		configure(sessionSettings, FileStoreFactory.SETTING_FILE_STORE_SYNC, fileStoreSync);
		configure(sessionSettings, FileStoreFactory.SETTING_FILE_STORE_MAX_CACHED_MSGS, fileStoreMaxCachedMsgs);

		configure(sessionSettings, SLF4JLogFactory.SETTING_EVENT_CATEGORY, slf4jLogEventCategory);
		configure(sessionSettings, SLF4JLogFactory.SETTING_ERROR_EVENT_CATEGORY, slf4jLogErrorEventCategory);
		configure(sessionSettings, SLF4JLogFactory.SETTING_INMSG_CATEGORY, slf4jLogIncomingMessageCategory);
		configure(sessionSettings, SLF4JLogFactory.SETTING_OUTMSG_CATEGORY, slf4jLogOutgoingMessageCategory);
		configure(sessionSettings, SLF4JLogFactory.SETTING_PREPEND_SESSION_ID, slf4jLogPrependSessionID);
		configure(sessionSettings, SLF4JLogFactory.SETTING_LOG_HEARTBEATS, slf4jLogHeartbeats);

		// if (customProperties != null) {
		// for (Map.Entry<?, ?> entry : customProperties.entrySet()) {
		// String key = (String)entry.getKey();
		// String value = (String)entry.getValue();
		// sessionSettings.setString(key, value);
		// }
		// }

		return sessionSettings;
	}

	private static void configure(SessionSettings sessionSettings, String key, Object value) {
		if (value != null) {
			if (value instanceof String) {
				sessionSettings.setString(key, (String) value);
			} else if (value instanceof Boolean) {
				sessionSettings.setBool(key, (Boolean) value);
			} else if (value instanceof Double) {
				sessionSettings.setDouble(key, (Double) value);
			} else if (value instanceof Long) {
				sessionSettings.setLong(key, (Long) value);
			}
		}
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public Long getReconnectInterval() {
		return reconnectInterval;
	}

	public void setReconnectInterval(Long reconnectInterval) {
		this.reconnectInterval = reconnectInterval;
	}

	public String getSocketConnectProtocol() {
		return socketConnectProtocol;
	}

	public void setSocketConnectProtocol(String socketConnectProtocol) {
		this.socketConnectProtocol = socketConnectProtocol;
	}

	public String getSocketConnectHost() {
		return socketConnectHost;
	}

	public void setSocketConnectHost(String socketConnectHost) {
		this.socketConnectHost = socketConnectHost;
	}

	public Long getSocketConnectPort() {
		return socketConnectPort;
	}

	public void setSocketConnectPort(Long socketConnectPort) {
		this.socketConnectPort = socketConnectPort;
	}

	public String getSocketLocalHost() {
		return socketLocalHost;
	}

	public void setSocketLocalHost(String socketLocalHost) {
		this.socketLocalHost = socketLocalHost;
	}

	public Long getSocketLocalPort() {
		return socketLocalPort;
	}

	public void setSocketLocalPort(Long socketLocalPort) {
		this.socketLocalPort = socketLocalPort;
	}

	public String getSocketAcceptProtocol() {
		return socketAcceptProtocol;
	}

	public void setSocketAcceptProtocol(String socketAcceptProtocol) {
		this.socketAcceptProtocol = socketAcceptProtocol;
	}

	public Long getSocketAcceptPort() {
		return socketAcceptPort;
	}

	public void setSocketAcceptPort(Long socketAcceptPort) {
		this.socketAcceptPort = socketAcceptPort;
	}

	public String getSocketAcceptAddress() {
		return socketAcceptAddress;
	}

	public void setSocketAcceptAddress(String socketAcceptAddress) {
		this.socketAcceptAddress = socketAcceptAddress;
	}

	public String getAcceptorTemplate() {
		return acceptorTemplate;
	}

	public void setAcceptorTemplate(String acceptorTemplate) {
		this.acceptorTemplate = acceptorTemplate;
	}

	public Long getHeartBtInt() {
		return heartBtInt;
	}

	public void setHeartBtInt(Long heartBtInt) {
		this.heartBtInt = heartBtInt;
	}

	public Boolean getCheckLatency() {
		return checkLatency;
	}

	public void setCheckLatency(Boolean checkLatency) {
		this.checkLatency = checkLatency;
	}

	public Boolean getCheckCompID() {
		return checkCompID;
	}

	public void setCheckCompID(Boolean checkCompID) {
		this.checkCompID = checkCompID;
	}

	public Long getMaxLatency() {
		return maxLatency;
	}

	public void setMaxLatency(Long maxLatency) {
		this.maxLatency = maxLatency;
	}

	public Double getTestRequestDelayMultiplier() {
		return testRequestDelayMultiplier;
	}

	public void setTestRequestDelayMultiplier(Double testRequestDelayMultiplier) {
		this.testRequestDelayMultiplier = testRequestDelayMultiplier;
	}

	public Boolean getNonStopSession() {
		return nonStopSession;
	}

	public void setNonStopSession(Boolean nonStopSession) {
		this.nonStopSession = nonStopSession;
	}

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getEndDay() {
		return endDay;
	}

	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Boolean getUseDataDictionary() {
		return useDataDictionary;
	}

	public void setUseDataDictionary(Boolean useDataDictionary) {
		this.useDataDictionary = useDataDictionary;
	}

	public String getDataDictionary() {
		return dataDictionary;
	}

	public void setDataDictionary(String dataDictionary) {
		this.dataDictionary = dataDictionary;
	}

	public String getTransportDataDictionary() {
		return transportDataDictionary;
	}

	public void setTransportDataDictionary(String transportDataDictionary) {
		this.transportDataDictionary = transportDataDictionary;
	}

	public String getAppDataDictionary() {
		return appDataDictionary;
	}

	public void setAppDataDictionary(String appDataDictionary) {
		this.appDataDictionary = appDataDictionary;
	}

	public Boolean getValidateFieldsOutOfOrder() {
		return validateFieldsOutOfOrder;
	}

	public void setValidateFieldsOutOfOrder(Boolean validateFieldsOutOfOrder) {
		this.validateFieldsOutOfOrder = validateFieldsOutOfOrder;
	}

	public Boolean getValidateUnorderedGroupFields() {
		return validateUnorderedGroupFields;
	}

	public void setValidateUnorderedGroupFields(Boolean validateUnorderedGroupFields) {
		this.validateUnorderedGroupFields = validateUnorderedGroupFields;
	}

	public Boolean getValidateFieldsHaveValues() {
		return validateFieldsHaveValues;
	}

	public void setValidateFieldsHaveValues(Boolean validateFieldsHaveValues) {
		this.validateFieldsHaveValues = validateFieldsHaveValues;
	}

	public Boolean getValidateIncomingMessage() {
		return validateIncomingMessage;
	}

	public void setValidateIncomingMessage(Boolean validateIncomingMessage) {
		this.validateIncomingMessage = validateIncomingMessage;
	}

	public Long getLogonTimeout() {
		return logonTimeout;
	}

	public void setLogonTimeout(Long logonTimeout) {
		this.logonTimeout = logonTimeout;
	}

	public String getLogoutTimeout() {
		return logoutTimeout;
	}

	public void setLogoutTimeout(String logoutTimeout) {
		this.logoutTimeout = logoutTimeout;
	}

	public Boolean getResetOnLogout() {
		return resetOnLogout;
	}

	public void setResetOnLogout(Boolean resetOnLogout) {
		this.resetOnLogout = resetOnLogout;
	}

	public String getValidateSequenceNumbers() {
		return validateSequenceNumbers;
	}

	public void setValidateSequenceNumbers(String validateSequenceNumbers) {
		this.validateSequenceNumbers = validateSequenceNumbers;
	}

	public Boolean getResetOnDisconnect() {
		return resetOnDisconnect;
	}

	public void setResetOnDisconnect(Boolean resetOnDisconnect) {
		this.resetOnDisconnect = resetOnDisconnect;
	}

	public Boolean getResetOnError() {
		return resetOnError;
	}

	public void setResetOnError(Boolean resetOnError) {
		this.resetOnError = resetOnError;
	}

	public Boolean getDisconnectOnError() {
		return disconnectOnError;
	}

	public void setDisconnectOnError(Boolean disconnectOnError) {
		this.disconnectOnError = disconnectOnError;
	}

	public Boolean getMillisecondsInTimeStamp() {
		return millisecondsInTimeStamp;
	}

	public void setMillisecondsInTimeStamp(Boolean millisecondsInTimeStamp) {
		this.millisecondsInTimeStamp = millisecondsInTimeStamp;
	}

	public Boolean getValidateUserDefinedFields() {
		return validateUserDefinedFields;
	}

	public void setValidateUserDefinedFields(Boolean validateUserDefinedFields) {
		this.validateUserDefinedFields = validateUserDefinedFields;
	}

	public Boolean getResetOnLogon() {
		return resetOnLogon;
	}

	public void setResetOnLogon(Boolean resetOnLogon) {
		this.resetOnLogon = resetOnLogon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getRefreshOnLogon() {
		return refreshOnLogon;
	}

	public void setRefreshOnLogon(Boolean refreshOnLogon) {
		this.refreshOnLogon = refreshOnLogon;
	}

	public Boolean getSendRedundantResendRequests() {
		return sendRedundantResendRequests;
	}

	public void setSendRedundantResendRequests(Boolean sendRedundantResendRequests) {
		this.sendRedundantResendRequests = sendRedundantResendRequests;
	}

	public Boolean getPersistMessages() {
		return persistMessages;
	}

	public void setPersistMessages(Boolean persistMessages) {
		this.persistMessages = persistMessages;
	}

	public Boolean getClosedResendInterval() {
		return closedResendInterval;
	}

	public void setClosedResendInterval(Boolean closedResendInterval) {
		this.closedResendInterval = closedResendInterval;
	}

	public Boolean getAllowUnknownMsgFields() {
		return allowUnknownMsgFields;
	}

	public void setAllowUnknownMsgFields(Boolean allowUnknownMsgFields) {
		this.allowUnknownMsgFields = allowUnknownMsgFields;
	}

	public String getDefaultApplVerID() {
		return defaultApplVerID;
	}

	public void setDefaultApplVerID(String defaultApplVerID) {
		this.defaultApplVerID = defaultApplVerID;
	}

	public Boolean getDisableHeartBeatCheck() {
		return disableHeartBeatCheck;
	}

	public void setDisableHeartBeatCheck(Boolean disableHeartBeatCheck) {
		this.disableHeartBeatCheck = disableHeartBeatCheck;
	}

	public Boolean getEnableLastMsgSeqNumProcessed() {
		return enableLastMsgSeqNumProcessed;
	}

	public void setEnableLastMsgSeqNumProcessed(Boolean enableLastMsgSeqNumProcessed) {
		this.enableLastMsgSeqNumProcessed = enableLastMsgSeqNumProcessed;
	}

	public Boolean getEnableNextExpectedMsgSeqNum() {
		return enableNextExpectedMsgSeqNum;
	}

	public void setEnableNextExpectedMsgSeqNum(Boolean enableNextExpectedMsgSeqNum) {
		this.enableNextExpectedMsgSeqNum = enableNextExpectedMsgSeqNum;
	}

	public Boolean getRejectInvalidMessage() {
		return rejectInvalidMessage;
	}

	public void setRejectInvalidMessage(Boolean rejectInvalidMessage) {
		this.rejectInvalidMessage = rejectInvalidMessage;
	}

	public Boolean getRejectMessageOnUnhandledException() {
		return rejectMessageOnUnhandledException;
	}

	public void setRejectMessageOnUnhandledException(Boolean rejectMessageOnUnhandledException) {
		this.rejectMessageOnUnhandledException = rejectMessageOnUnhandledException;
	}

	public Boolean getRequiresOrigSendingTime() {
		return requiresOrigSendingTime;
	}

	public void setRequiresOrigSendingTime(Boolean requiresOrigSendingTime) {
		this.requiresOrigSendingTime = requiresOrigSendingTime;
	}

	public String getForceResendWhenCorruptedStore() {
		return forceResendWhenCorruptedStore;
	}

	public void setForceResendWhenCorruptedStore(String forceResendWhenCorruptedStore) {
		this.forceResendWhenCorruptedStore = forceResendWhenCorruptedStore;
	}

	public String getAllowedRemoteAddresses() {
		return allowedRemoteAddresses;
	}

	public void setAllowedRemoteAddresses(String allowedRemoteAddresses) {
		this.allowedRemoteAddresses = allowedRemoteAddresses;
	}

	public String getResendRequestChunkSize() {
		return resendRequestChunkSize;
	}

	public void setResendRequestChunkSize(String resendRequestChunkSize) {
		this.resendRequestChunkSize = resendRequestChunkSize;
	}

	public Boolean getSocketKeepAlive() {
		return socketKeepAlive;
	}

	public void setSocketKeepAlive(Boolean socketKeepAlive) {
		this.socketKeepAlive = socketKeepAlive;
	}

	public Boolean getSocketOobInline() {
		return socketOobInline;
	}

	public void setSocketOobInline(Boolean socketOobInline) {
		this.socketOobInline = socketOobInline;
	}

	public Long getSocketReceiveBufferSize() {
		return socketReceiveBufferSize;
	}

	public void setSocketReceiveBufferSize(Long socketReceiveBufferSize) {
		this.socketReceiveBufferSize = socketReceiveBufferSize;
	}

	public Boolean getSocketReuseAddress() {
		return socketReuseAddress;
	}

	public void setSocketReuseAddress(Boolean socketReuseAddress) {
		this.socketReuseAddress = socketReuseAddress;
	}

	public Long getSocketSendBufferSize() {
		return socketSendBufferSize;
	}

	public void setSocketSendBufferSize(Long socketSendBufferSize) {
		this.socketSendBufferSize = socketSendBufferSize;
	}

	public Long getSocketLinger() {
		return socketLinger;
	}

	public void setSocketLinger(Long socketLinger) {
		this.socketLinger = socketLinger;
	}

	public Boolean getSocketTcpNoDelay() {
		return socketTcpNoDelay;
	}

	public void setSocketTcpNoDelay(Boolean socketTcpNoDelay) {
		this.socketTcpNoDelay = socketTcpNoDelay;
	}

	public Long getSocketTrafficClass() {
		return socketTrafficClass;
	}

	public void setSocketTrafficClass(Long socketTrafficClass) {
		this.socketTrafficClass = socketTrafficClass;
	}

	public Boolean getSocketSynchronousWrites() {
		return socketSynchronousWrites;
	}

	public void setSocketSynchronousWrites(Boolean socketSynchronousWrites) {
		this.socketSynchronousWrites = socketSynchronousWrites;
	}

	public Long getSocketSynchronousWriteTimeout() {
		return socketSynchronousWriteTimeout;
	}

	public void setSocketSynchronousWriteTimeout(Long socketSynchronousWriteTimeout) {
		this.socketSynchronousWriteTimeout = socketSynchronousWriteTimeout;
	}

	public String getSocketKeyStorePassword() {
		return socketKeyStorePassword;
	}

	public void setSocketKeyStorePassword(String socketKeyStorePassword) {
		this.socketKeyStorePassword = socketKeyStorePassword;
	}

	public String getSocketKeyStore() {
		return socketKeyStore;
	}

	public void setSocketKeyStore(String socketKeyStore) {
		this.socketKeyStore = socketKeyStore;
	}

	public Boolean getSocketUseSSL() {
		return socketUseSSL;
	}

	public void setSocketUseSSL(Boolean socketUseSSL) {
		this.socketUseSSL = socketUseSSL;
	}

	public String getEnabledProtocols() {
		return enabledProtocols;
	}

	public void setEnabledProtocols(String enabledProtocols) {
		this.enabledProtocols = enabledProtocols;
	}

	public String getCipherSuites() {
		return cipherSuites;
	}

	public void setCipherSuites(String cipherSuites) {
		this.cipherSuites = cipherSuites;
	}

	public String getFileStorePath() {
		return fileStorePath;
	}

	public void setFileStorePath(String fileStorePath) {
		this.fileStorePath = fileStorePath;
	}

	public Boolean getFileStoreSync() {
		return fileStoreSync;
	}

	public void setFileStoreSync(Boolean fileStoreSync) {
		this.fileStoreSync = fileStoreSync;
	}

	public Long getFileStoreMaxCachedMsgs() {
		return fileStoreMaxCachedMsgs;
	}

	public void setFileStoreMaxCachedMsgs(Long fileStoreMaxCachedMsgs) {
		this.fileStoreMaxCachedMsgs = fileStoreMaxCachedMsgs;
	}

	public String getSlf4jLogEventCategory() {
		return slf4jLogEventCategory;
	}

	public void setSlf4jLogEventCategory(String slf4jLogEventCategory) {
		this.slf4jLogEventCategory = slf4jLogEventCategory;
	}

	public String getSlf4jLogErrorEventCategory() {
		return slf4jLogErrorEventCategory;
	}

	public void setSlf4jLogErrorEventCategory(String slf4jLogErrorEventCategory) {
		this.slf4jLogErrorEventCategory = slf4jLogErrorEventCategory;
	}

	public String getSlf4jLogIncomingMessageCategory() {
		return slf4jLogIncomingMessageCategory;
	}

	public void setSlf4jLogIncomingMessageCategory(String slf4jLogIncomingMessageCategory) {
		this.slf4jLogIncomingMessageCategory = slf4jLogIncomingMessageCategory;
	}

	public String getSlf4jLogOutgoingMessageCategory() {
		return slf4jLogOutgoingMessageCategory;
	}

	public void setSlf4jLogOutgoingMessageCategory(String slf4jLogOutgoingMessageCategory) {
		this.slf4jLogOutgoingMessageCategory = slf4jLogOutgoingMessageCategory;
	}

	public String getSlf4jLogPrependSessionID() {
		return slf4jLogPrependSessionID;
	}

	public void setSlf4jLogPrependSessionID(String slf4jLogPrependSessionID) {
		this.slf4jLogPrependSessionID = slf4jLogPrependSessionID;
	}

	public String getSlf4jLogHeartbeats() {
		return slf4jLogHeartbeats;
	}

	public void setSlf4jLogHeartbeats(String slf4jLogHeartbeats) {
		this.slf4jLogHeartbeats = slf4jLogHeartbeats;
	}

	public Properties getCustomProperties() {
		return customProperties;
	}

	public void setCustomProperties(Properties customProperties) {
		this.customProperties = customProperties;
	}

	@Override
	public String toString() {
		return "QuickFixSettings [" + (connectionType != null ? "connectionType=" + connectionType + ", " : "")
				+ (reconnectInterval != null ? "reconnectInterval=" + reconnectInterval + ", " : "")
				+ (socketConnectProtocol != null ? "socketConnectProtocol=" + socketConnectProtocol + ", " : "")
				+ (socketConnectHost != null ? "socketConnectHost=" + socketConnectHost + ", " : "")
				+ (socketConnectPort != null ? "socketConnectPort=" + socketConnectPort + ", " : "")
				+ (socketLocalHost != null ? "socketLocalHost=" + socketLocalHost + ", " : "")
				+ (socketLocalPort != null ? "socketLocalPort=" + socketLocalPort + ", " : "")
				+ (socketAcceptProtocol != null ? "socketAcceptProtocol=" + socketAcceptProtocol + ", " : "")
				+ (socketAcceptPort != null ? "socketAcceptPort=" + socketAcceptPort + ", " : "")
				+ (socketAcceptAddress != null ? "socketAcceptAddress=" + socketAcceptAddress + ", " : "")
				+ (acceptorTemplate != null ? "acceptorTemplate=" + acceptorTemplate + ", " : "")
				+ (heartBtInt != null ? "heartBtInt=" + heartBtInt + ", " : "")
				+ (checkLatency != null ? "checkLatency=" + checkLatency + ", " : "")
				+ (checkCompID != null ? "checkCompID=" + checkCompID + ", " : "")
				+ (maxLatency != null ? "maxLatency=" + maxLatency + ", " : "")
				+ (testRequestDelayMultiplier != null
						? "testRequestDelayMultiplier=" + testRequestDelayMultiplier + ", "
						: "")
				+ (nonStopSession != null ? "nonStopSession=" + nonStopSession + ", " : "")
				+ (startDay != null ? "startDay=" + startDay + ", " : "")
				+ (endDay != null ? "endDay=" + endDay + ", " : "")
				+ (timeZone != null ? "timeZone=" + timeZone + ", " : "")
				+ (startTime != null ? "startTime=" + startTime + ", " : "")
				+ (endTime != null ? "endTime=" + endTime + ", " : "")
				+ (useDataDictionary != null ? "useDataDictionary=" + useDataDictionary + ", " : "")
				+ (dataDictionary != null ? "dataDictionary=" + dataDictionary + ", " : "")
				+ (transportDataDictionary != null ? "transportDataDictionary=" + transportDataDictionary + ", " : "")
				+ (appDataDictionary != null ? "appDataDictionary=" + appDataDictionary + ", " : "")
				+ (validateFieldsOutOfOrder != null ? "validateFieldsOutOfOrder=" + validateFieldsOutOfOrder + ", "
						: "")
				+ (validateUnorderedGroupFields != null
						? "validateUnorderedGroupFields=" + validateUnorderedGroupFields + ", "
						: "")
				+ (validateFieldsHaveValues != null ? "validateFieldsHaveValues=" + validateFieldsHaveValues + ", "
						: "")
				+ (validateIncomingMessage != null ? "validateIncomingMessage=" + validateIncomingMessage + ", " : "")
				+ (logonTimeout != null ? "logonTimeout=" + logonTimeout + ", " : "")
				+ (logoutTimeout != null ? "logoutTimeout=" + logoutTimeout + ", " : "")
				+ (resetOnLogout != null ? "resetOnLogout=" + resetOnLogout + ", " : "")
				+ (validateSequenceNumbers != null ? "validateSequenceNumbers=" + validateSequenceNumbers + ", " : "")
				+ (resetOnDisconnect != null ? "resetOnDisconnect=" + resetOnDisconnect + ", " : "")
				+ (resetOnError != null ? "resetOnError=" + resetOnError + ", " : "")
				+ (disconnectOnError != null ? "disconnectOnError=" + disconnectOnError + ", " : "")
				+ (millisecondsInTimeStamp != null ? "millisecondsInTimeStamp=" + millisecondsInTimeStamp + ", " : "")
				+ (validateUserDefinedFields != null ? "validateUserDefinedFields=" + validateUserDefinedFields + ", "
						: "")
				+ (resetOnLogon != null ? "resetOnLogon=" + resetOnLogon + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (refreshOnLogon != null ? "refreshOnLogon=" + refreshOnLogon + ", " : "")
				+ (sendRedundantResendRequests != null
						? "sendRedundantResendRequests=" + sendRedundantResendRequests + ", "
						: "")
				+ (persistMessages != null ? "persistMessages=" + persistMessages + ", " : "")
				+ (closedResendInterval != null ? "closedResendInterval=" + closedResendInterval + ", " : "")
				+ (allowUnknownMsgFields != null ? "allowUnknownMsgFields=" + allowUnknownMsgFields + ", " : "")
				+ (defaultApplVerID != null ? "defaultApplVerID=" + defaultApplVerID + ", " : "")
				+ (disableHeartBeatCheck != null ? "disableHeartBeatCheck=" + disableHeartBeatCheck + ", " : "")
				+ (enableLastMsgSeqNumProcessed != null
						? "enableLastMsgSeqNumProcessed=" + enableLastMsgSeqNumProcessed + ", "
						: "")
				+ (enableNextExpectedMsgSeqNum != null
						? "enableNextExpectedMsgSeqNum=" + enableNextExpectedMsgSeqNum + ", "
						: "")
				+ (rejectInvalidMessage != null ? "rejectInvalidMessage=" + rejectInvalidMessage + ", " : "")
				+ (rejectMessageOnUnhandledException != null
						? "rejectMessageOnUnhandledException=" + rejectMessageOnUnhandledException + ", "
						: "")
				+ (requiresOrigSendingTime != null ? "requiresOrigSendingTime=" + requiresOrigSendingTime + ", " : "")
				+ (forceResendWhenCorruptedStore != null
						? "forceResendWhenCorruptedStore=" + forceResendWhenCorruptedStore + ", "
						: "")
				+ (allowedRemoteAddresses != null ? "allowedRemoteAddresses=" + allowedRemoteAddresses + ", " : "")
				+ (resendRequestChunkSize != null ? "resendRequestChunkSize=" + resendRequestChunkSize + ", " : "")
				+ (socketKeepAlive != null ? "socketKeepAlive=" + socketKeepAlive + ", " : "")
				+ (socketOobInline != null ? "socketOobInline=" + socketOobInline + ", " : "")
				+ (socketReceiveBufferSize != null ? "socketReceiveBufferSize=" + socketReceiveBufferSize + ", " : "")
				+ (socketReuseAddress != null ? "socketReuseAddress=" + socketReuseAddress + ", " : "")
				+ (socketSendBufferSize != null ? "socketSendBufferSize=" + socketSendBufferSize + ", " : "")
				+ (socketLinger != null ? "socketLinger=" + socketLinger + ", " : "")
				+ (socketTcpNoDelay != null ? "socketTcpNoDelay=" + socketTcpNoDelay + ", " : "")
				+ (socketTrafficClass != null ? "socketTrafficClass=" + socketTrafficClass + ", " : "")
				+ (socketSynchronousWrites != null ? "socketSynchronousWrites=" + socketSynchronousWrites + ", " : "")
				+ (socketSynchronousWriteTimeout != null
						? "socketSynchronousWriteTimeout=" + socketSynchronousWriteTimeout + ", "
						: "")
				+ (socketKeyStorePassword != null ? "socketKeyStorePassword=" + socketKeyStorePassword + ", " : "")
				+ (socketKeyStore != null ? "socketKeyStore=" + socketKeyStore + ", " : "")
				+ (socketUseSSL != null ? "socketUseSSL=" + socketUseSSL + ", " : "")
				+ (enabledProtocols != null ? "enabledProtocols=" + enabledProtocols + ", " : "")
				+ (cipherSuites != null ? "cipherSuites=" + cipherSuites + ", " : "")
				+ (fileStorePath != null ? "fileStorePath=" + fileStorePath + ", " : "")
				+ (fileStoreSync != null ? "fileStoreSync=" + fileStoreSync + ", " : "")
				+ (fileStoreMaxCachedMsgs != null ? "fileStoreMaxCachedMsgs=" + fileStoreMaxCachedMsgs + ", " : "")
				+ (slf4jLogEventCategory != null ? "slf4jLogEventCategory=" + slf4jLogEventCategory + ", " : "")
				+ (slf4jLogErrorEventCategory != null
						? "slf4jLogErrorEventCategory=" + slf4jLogErrorEventCategory + ", "
						: "")
				+ (slf4jLogIncomingMessageCategory != null
						? "slf4jLogIncomingMessageCategory=" + slf4jLogIncomingMessageCategory + ", "
						: "")
				+ (slf4jLogOutgoingMessageCategory != null
						? "slf4jLogOutgoingMessageCategory=" + slf4jLogOutgoingMessageCategory + ", "
						: "")
				+ (slf4jLogPrependSessionID != null ? "slf4jLogPrependSessionID=" + slf4jLogPrependSessionID + ", "
						: "")
				+ (slf4jLogHeartbeats != null ? "slf4jLogHeartbeats=" + slf4jLogHeartbeats + ", " : "")
				+ (customProperties != null ? "customProperties=" + customProperties : "") + "]";
	}

	
}
