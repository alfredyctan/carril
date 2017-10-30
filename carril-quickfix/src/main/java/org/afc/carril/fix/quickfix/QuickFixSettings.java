package org.afc.carril.fix.quickfix;

import java.util.Map;
import java.util.Properties;

import quickfix.FileStoreFactory;
import quickfix.Initiator;
import quickfix.SLF4JLogFactory;
import quickfix.Session;
import quickfix.SessionFactory;
import quickfix.SessionSettings;
import quickfix.mina.NetworkingOptions;
import quickfix.mina.ssl.SSLSupport;

public class QuickFixSettings {

	/** eg. 00:00:00 */
	private String startTime = "00:00:00";

	/** eg. 00:00:00 */
	private String endTime = "00:00:00";
	
	/** default Y */
	private boolean enableMillsTimeStamp = true;
	
	/** eg. /opt/app/cfg/Fix42.xml */
	private String dataDictionary;
	
	/** default N */
	private boolean enableSSL = false;

	/** eg. /opt/app/cfg/keystore.jks */
	private String keyStore;

	/** eg. zzzzzz */
	private String keyStorePassword;
	
	/** default */
	private boolean checkLatency = false;

	/** default 120 */
	private long maxLatency = 120;

	/** e.g. www.afc.org */
	private String host;

	/** e.g. 30100 */
	private long port;
	
	/** default 5 */
	private long reconnectInterval = 5;

	/** default 10 */
	private long logonTimeout = 10;

	/** default 30 */
	private long heartbeatInterval = 30;

	/** default Y */
	private boolean resetOnLogon = true;

	/** default Y */
	private boolean resetOnLogout = true;

	/** default 32768 */
	private long socketRecvBuffer = 32768;

	/** default 32768 */
	private long socketSendBuffer = 32768;

	/** eg. /opt/app/msg */
	private String fileStorePath;

	/** default false */
	private boolean logHeartbeat = false;
	
	/**
	 * @url
	 * http://www.quickfixj.org/quickfixj/javadoc/1.5.1/constant-values.html?bcsi_scan_BB06E4EF3AEA9F08=uF2X6z//DyEIwKG28AGCucjiCiMJAAAArM4HHQ==&bcsi_scan_filename=constant-values.html
	 */
	private Properties customProperties;  

	public SessionSettings createDefaultSessionSettings() {
		SessionSettings sessionSettings = new SessionSettings();
		sessionSettings.setString(SessionFactory.SETTING_CONNECTION_TYPE, SessionFactory.INITIATOR_CONNECTION_TYPE);
	    sessionSettings.setString(Session.SETTING_START_TIME, startTime);
	    sessionSettings.setString(Session.SETTING_END_TIME, endTime);
	    sessionSettings.setBool(Session.SETTING_MILLISECONDS_IN_TIMESTAMP, enableMillsTimeStamp);
	    sessionSettings.setBool(Session.SETTING_CHECK_LATENCY, checkLatency);
	    sessionSettings.setLong(Session.SETTING_MAX_LATENCY, maxLatency);

	    sessionSettings.setBool(Session.SETTING_USE_DATA_DICTIONARY, true);
	    sessionSettings.setString(Session.SETTING_DATA_DICTIONARY, dataDictionary);
	    sessionSettings.setBool(Session.SETTING_VALIDATE_USER_DEFINED_FIELDS, false);

	    sessionSettings.setBool(NetworkingOptions.SETTING_SOCKET_TCP_NODELAY, true);
	    sessionSettings.setLong(NetworkingOptions.SETTING_SOCKET_RECEIVE_BUFFER_SIZE, socketRecvBuffer);
	    sessionSettings.setLong(NetworkingOptions.SETTING_SOCKET_SEND_BUFFER_SIZE, socketSendBuffer);
	    
		sessionSettings.setString(Initiator.SETTING_SOCKET_CONNECT_HOST, host);
		sessionSettings.setLong(Initiator.SETTING_SOCKET_CONNECT_PORT, port);
	    sessionSettings.setLong(Initiator.SETTING_RECONNECT_INTERVAL, reconnectInterval);
		sessionSettings.setLong(Session.SETTING_LOGON_TIMEOUT, logonTimeout);
	    sessionSettings.setLong(Session.SETTING_HEARTBTINT, heartbeatInterval);	    
	    sessionSettings.setBool(Session.SETTING_RESET_ON_LOGON, resetOnLogon);
	    sessionSettings.setBool(Session.SETTING_RESET_ON_LOGOUT, resetOnLogout);
	    
	    if (enableSSL) {
		    sessionSettings.setBool(SSLSupport.SETTING_USE_SSL, enableSSL);
		    sessionSettings.setString(SSLSupport.SETTING_KEY_STORE_NAME, keyStore);
		    sessionSettings.setString(SSLSupport.SETTING_KEY_STORE_PWD, keyStorePassword);
	    }

	    if (fileStorePath != null) {
	    	sessionSettings.setString(FileStoreFactory.SETTING_FILE_STORE_PATH, fileStorePath);
	    }
	    sessionSettings.setBool(SLF4JLogFactory.SETTING_LOG_HEARTBEATS, logHeartbeat);
	    
	    if (customProperties != null) {
		    for (Map.Entry<?, ?> entry : customProperties.entrySet()) {
		    	String key = (String)entry.getKey();
		    	String value = (String)entry.getValue();
		    	sessionSettings.setString(key, value);
		    }
	    }

	    return sessionSettings;
	}

	public void setStartTime(String startTime) {
    	this.startTime = startTime;
    }

	public void setEndTime(String endTime) {
    	this.endTime = endTime;
    }

	public void setEnableMillsTimeStamp(boolean enableMillsTimeStamp) {
    	this.enableMillsTimeStamp = enableMillsTimeStamp;
    }

	public void setDataDictionary(String dataDictionary) {
    	this.dataDictionary = dataDictionary;
    }

	public void setEnableSSL(boolean enableSSL) {
    	this.enableSSL = enableSSL;
    }

	public void setKeyStore(String keyStore) {
    	this.keyStore = keyStore;
    }

	public void setKeyStorePassword(String keyStorePassword) {
    	this.keyStorePassword = keyStorePassword;
    }

	public void setCheckLatency(boolean checkLatency) {
    	this.checkLatency = checkLatency;
    }

	public void setMaxLatency(long maxLatency) {
    	this.maxLatency = maxLatency;
    }

	public void setHost(String host) {
    	this.host = host;
    }

	public void setPort(long port) {
    	this.port = port;
    }

	public void setReconnectInterval(long reconnectInterval) {
    	this.reconnectInterval = reconnectInterval;
    }

	public void setLogonTimeout(long logonTimeout) {
    	this.logonTimeout = logonTimeout;
    }

	public void setHeartbeatInterval(long heartbeatInterval) {
    	this.heartbeatInterval = heartbeatInterval;
    }

	public void setResetOnLogon(boolean resetOnLogon) {
    	this.resetOnLogon = resetOnLogon;
    }

	public void setResetOnLogout(boolean resetOnLogout) {
    	this.resetOnLogout = resetOnLogout;
    }

	public void setSocketRecvBuffer(long socketRecvBuffer) {
    	this.socketRecvBuffer = socketRecvBuffer;
    }

	public void setSocketSendBuffer(long socketSendBuffer) {
    	this.socketSendBuffer = socketSendBuffer;
    }

	public void setFileStorePath(String fileStorePath) {
    	this.fileStorePath = fileStorePath;
    }

	public void setLogHeartbeat(boolean logHeartbeat) {
    	this.logHeartbeat = logHeartbeat;
    }

	public void setCustomProperties(Properties customProperties) {
    	this.customProperties = customProperties;
    }

	public String getFileStorePath() {
    	return fileStorePath;
    }
}
