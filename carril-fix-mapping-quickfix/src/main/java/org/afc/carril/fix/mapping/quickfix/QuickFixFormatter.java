package org.afc.carril.fix.mapping.quickfix;

import java.util.ArrayList;
import java.util.List;

import org.afc.carril.fix.mapping.AccessorFactory;
import org.afc.carril.fix.mapping.FixFormatter;
import org.afc.carril.fix.mapping.MsgMapper;
import org.afc.carril.fix.mapping.SessionState;
import org.afc.carril.fix.mapping.impl.DefaultMsgMapper;
import org.afc.carril.fix.mapping.schema.Direction;
import org.afc.carril.fix.mapping.schema.MsgMap;
import org.afc.carril.fix.tag.FixTag;
import org.afc.carril.message.FixMessage;
import org.afc.carril.transport.TransportException;

import quickfix.Message;

public class QuickFixFormatter implements FixFormatter<FixMessage, Message> {

	private List<MsgMapper<FixMessage, Message>> mappers;
	
	public QuickFixFormatter(Direction objToFix, SessionState state) {
		AccessorFactory<FixMessage, Message, Object> accessorFactory = new FixMessageToQuickFixAccessorFactory();
		mappers = new ArrayList<MsgMapper<FixMessage, Message>>();

		if (objToFix != null) {
			for (MsgMap msgMap : objToFix.getMsgMap()) {
				try {
					mappers.add(new DefaultMsgMapper<FixMessage, Message>(accessorFactory, msgMap, state));
				} catch (Exception e) {
					throw new TransportException("fail to parse <msg-map> " + msgMap.getName(), e);
				}
			}
		}
	}
	
	
	@Override
	public Message format(FixMessage fixFormat) {
        try {
    		Message message = new Message();
    		for (MsgMapper<FixMessage, Message> msgMapper : mappers) {
    			String targetType = msgMapper.match(fixFormat);
    			if (targetType != null) {
    				message.getHeader().setString(FixTag.MsgType.id(), targetType);
    				message.getHeader().setString(FixTag.SenderCompID.id(), fixFormat.getContext().getSenderCompID());
    				message.getHeader().setString(FixTag.TargetCompID.id(), fixFormat.getContext().getTargetCompID());
//    				message.getHeader().setInt(FixTag.MsgSeqNum.id(), fixFormat.getContext().getMsgSeqNum());
//    				message.getHeader().setUtcTimeStamp(FixTag.SendingTime.id(), fixFormat.getContext().getSendingTime(), true);
    				msgMapper.map(fixFormat, message);
    				return message; 
    			}
    		}
    		return null;
        } catch (TransportException e) {
        	throw e;
        } catch (Exception e) {
	        throw new TransportException("Fail to parse the message.", e);
        }
	}
}
