package org.afc.carril.fix.mapping.quickfix;

import java.util.ArrayList;
import java.util.List;

import org.afc.carril.fix.mapping.AccessorFactory;
import org.afc.carril.fix.mapping.FixFormatter;
import org.afc.carril.fix.mapping.MsgMapper;
import org.afc.carril.fix.mapping.SessionState;
import org.afc.carril.fix.mapping.impl.DefaultMsgMapper;
import org.afc.carril.fix.mapping.schema.Direction;
import org.afc.carril.fix.mapping.schema.FixConv;
import org.afc.carril.fix.mapping.schema.MsgMap;
import org.afc.carril.fix.tag.FixTag;
import org.afc.carril.message.FixMessage;
import org.afc.carril.transport.TransportException;

import quickfix.Message;

public class QuickFixFormatter implements FixFormatter<FixMessage, Message> {

	private List<MsgMapper<Object, Message>> mappers;
	
	public QuickFixFormatter(FixConv fixConv, Direction objToFix, SessionState state) {
		AccessorFactory<Object, Message, Object> accessorFactory = new FixMessageToQuickFixAccessorFactory();
		mappers = new ArrayList<MsgMapper<Object, Message>>();

		if (objToFix != null) {
			for (MsgMap msgMap : objToFix.getMsgMap()) {
				try {
					mappers.add(new DefaultMsgMapper<Object, Message>(fixConv, accessorFactory, msgMap, state));
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
    		for (MsgMapper<Object, Message> msgMapper : mappers) {
    			String targetType = msgMapper.match(fixFormat);
    			if (targetType != null) {
    				message.getHeader().setString(FixTag.SenderCompID.id(), fixFormat.getContext().getSenderCompID());
    				message.getHeader().setString(FixTag.TargetCompID.id(), fixFormat.getContext().getTargetCompID());
    				message.getHeader().setString(FixTag.MsgType.id(), targetType);
    				msgMapper.map(fixFormat, message);
    				return message; 
    			}
    		}
    		throw new TransportException("schema conversion mapping not found for the message " + fixFormat);
        } catch (TransportException e) {
        	throw e;
        } catch (Exception e) {
	        throw new TransportException("Fail to parse the message.", e);
        }
	}
}
