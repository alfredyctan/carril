package org.afc.carril.fix.quickfix;

import java.util.List;

import org.afc.carril.fix.quickfix.text.QuickFixMsgStringWrapper;
import org.afc.carril.fix.tag.FixMsgType;
import org.afc.carril.fix.tag.FixTag;
import org.afc.carril.transport.SubjectRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.afc.logging.SDC;
import org.afc.util.ClockUtil;

import quickfix.Application;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.RejectLogon;
import quickfix.SessionID;
import quickfix.UnsupportedMessageType;

public class QuickFixApplication implements Application {

	private static final Logger logger = LoggerFactory.getLogger(QuickFixTransport.class);

	private SubjectRegistry<QuickFixSubjectContext> registry;
	
	public QuickFixApplication(SubjectRegistry<QuickFixSubjectContext> registry) {
		this.registry = registry;
	}
	
	@Override
	public void toAdmin(Message message, SessionID sessionId) {
		SDC.auto(message);
		logger.debug("[OUT][FIX] {}", new QuickFixMsgStringWrapper(message));
	}

	@Override
	public void toApp(Message message, SessionID sessionId) throws DoNotSend {
		SDC.auto(message);
		logger.debug("[OUT][APP] {}", new QuickFixMsgStringWrapper(message));
	}
	
	@Override
	public void fromAdmin(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
		SDC.set(message);
		logger.debug("[INC][FIX] {}", new QuickFixMsgStringWrapper(message));
		onMessage(message);
	}

	@Override
	public void fromApp(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
		SDC.set(message);
		logger.debug("[INC][APP] {}", new QuickFixMsgStringWrapper(message));
		onMessage(message);
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
		Message logout = new Message();
		logout.getHeader().setString(FixTag.BeginString.id(), sessionId.getBeginString());
		logout.getHeader().setString(FixTag.MsgType.id(), FixMsgType._5.id());		
		logout.getHeader().setString(FixTag.SenderCompID.id(), sessionId.getTargetCompID());
		logout.getHeader().setString(FixTag.TargetCompID.id(), sessionId.getSenderCompID());
		logout.getHeader().setUtcTimeStamp(FixTag.SendingTime.id(), ClockUtil.localDateTime());
		logger.debug("Fix session logout, {}", new QuickFixMsgStringWrapper(logout));
		try {
			onMessage(logout);
		} catch (FieldNotFound e) {
            logger.error("Error on invoking logout callback, message:[" + QuickFixUtil.toString(logout) + ']', e);
		}
	}
	
	private void onMessage(final Message message) throws FieldNotFound {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<QuickFixMessageListener> handlers = (List)registry.getSubjectContext(QuickFixUtil.createOpponentSubject(message)).getSubscribers();
		synchronized(handlers) {
			for (QuickFixMessageListener handler : handlers) {
	    		try {
	    			handler.onMessage(message);
                } catch (Exception e) {
                    logger.error("Error on invoking callback, message:[" + QuickFixUtil.toString(message) + ']', e);
                }
			}
        }
	}
}
