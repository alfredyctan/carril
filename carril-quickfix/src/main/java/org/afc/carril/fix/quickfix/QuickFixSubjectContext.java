package org.afc.carril.fix.quickfix;

import java.util.List;

import org.afc.carril.fix.quickfix.text.QuickFixMsgStringWrapper;
import org.afc.carril.fix.tag.FixMsgType;
import org.afc.carril.fix.tag.FixTag;
import org.afc.carril.subscriber.Subscriber;
import org.afc.carril.transport.Transport.State;
import org.afc.carril.transport.impl.DefaultSubjectContext;
import org.afc.util.ClockUtil;
import org.afc.util.SDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.Application;
import quickfix.ConfigError;
import quickfix.DefaultMessageFactory;
import quickfix.DefaultSessionFactory;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.FileStoreFactory;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Initiator;
import quickfix.MemoryStoreFactory;
import quickfix.Message;
import quickfix.RejectLogon;
import quickfix.RuntimeError;
import quickfix.SLF4JLogFactory;
import quickfix.SessionFactory;
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;
import quickfix.UnsupportedMessageType;

class QuickFixSubjectContext extends DefaultSubjectContext implements Application {

	private static final Logger logger = LoggerFactory.getLogger(QuickFixSubjectContext.class);
	
	private SessionFactory sessionFactory; 
	
	private Initiator initiator;

	private SessionID sessionID;
	
	public QuickFixSubjectContext(String subject, SessionSettings sessionSettings, String fileStorePath, State state) throws ConfigError {
		super(subject);
		this.sessionID = QuickFixUtil.createSessionID(sessionSettings, subject);
		this.sessionFactory = new DefaultSessionFactory(
			this, 
			(fileStorePath == null)?new MemoryStoreFactory():new FileStoreFactory(sessionSettings), 
			new SLF4JLogFactory(sessionSettings), 
			new DefaultMessageFactory()
		);
		
		this.initiator = new SocketInitiator(sessionFactory, sessionSettings, 1024);
		if (state == State.START) {
			initiator.start();
			logger.debug("initator started.");
		}
	}

	@Override
	public void removeSubscriber(Subscriber subscriber) {
	    super.removeSubscriber(subscriber);
	    if (subscriptions.size() == 0) {
	    	initiator.stop();
	    }
	}

	
	@Override
	public void toAdmin(Message message, SessionID sessionId) {
		logger.debug("[OUT][GFix] {}", new QuickFixMsgStringWrapper(message));
	}

	@Override
	public void toApp(Message message, SessionID sessionId) throws DoNotSend {
		logger.debug("[OUT][APP] {}", new QuickFixMsgStringWrapper(message));
	}
	
	@Override
	public void fromAdmin(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
		SDC.set("GF." + SDC.hash(message));
		logger.debug("[INC][GFix] {}", new QuickFixMsgStringWrapper(message));
		onMessage(message, sessionId);
	}

	@Override
	public void fromApp(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
		SDC.set("AF." + SDC.hash(message));
		logger.debug("[INC][APP] {}", new QuickFixMsgStringWrapper(message));
		onMessage(message, sessionId);
	}

	@Override
	public void onCreate(SessionID sessionId) {
		logger.debug("Fix session created, {}", sessionId);
	}

	@Override
	public void onLogon(SessionID sessionId) {
		logger.debug("Fix session logon, {}", sessionId);
	}

	@Override
	public void onLogout(SessionID sessionId) {
		initiator.stop(true);
		Message logout = new Message();
		logout.getHeader().setString(FixTag.BeginString.id(), sessionId.getBeginString());
		logout.getHeader().setString(FixTag.MsgType.id(), FixMsgType.TYPE_5.id());		
		logout.getHeader().setString(FixTag.SenderCompID.id(), sessionId.getSenderCompID());
		logout.getHeader().setString(FixTag.TargetCompID.id(), sessionId.getTargetCompID());
		logout.getHeader().setUtcTimeStamp(FixTag.SendingTime.id(), ClockUtil.currentDate());
		logger.debug("Fix session logout, {}", new QuickFixMsgStringWrapper(logout));
		onMessage(logout, sessionId);
	}
	
	private void onMessage(final Message message, final SessionID sessionId) {
		List<QuickFixMessageHandler> handlers = (List)subscriptions;
		synchronized(handlers) {
			for (QuickFixMessageHandler handler:handlers) {
	    		try {
	    			handler.onMessage(message, sessionId);
                } catch (Exception e) {
                    logger.error("Error on invoking callback", e);
                }
			}
        }
	}

	public void start() throws RuntimeError, ConfigError  {
        initiator.start();
	}

	public void stop() {
        initiator.stop(true);
	}

	public SessionID getSessionID() {
    	return sessionID;
    }
}
