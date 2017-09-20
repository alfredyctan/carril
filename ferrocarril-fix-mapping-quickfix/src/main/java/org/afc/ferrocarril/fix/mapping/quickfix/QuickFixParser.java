package org.afc.ferrocarril.fix.mapping.quickfix;

import java.util.ArrayList;
import java.util.List;

import org.afc.ferrocarril.fix.mapping.AccessorFactory;
import org.afc.ferrocarril.fix.mapping.FixParser;
import org.afc.ferrocarril.fix.mapping.MsgMapper;
import org.afc.ferrocarril.fix.mapping.SessionState;
import org.afc.ferrocarril.fix.mapping.impl.DefaultMsgMapper;
import org.afc.ferrocarril.fix.mapping.schema.Direction;
import org.afc.ferrocarril.fix.mapping.schema.MsgMap;
import org.afc.ferrocarril.fix.tag.FixTag;
import org.afc.ferrocarril.message.FixMessage;
import org.afc.ferrocarril.message.QuickFixMessage;
import org.afc.ferrocarril.transport.TransportException;
import org.afc.util.ObjectUtil;

import quickfix.Message;

public class QuickFixParser implements FixParser<Message, QuickFixMessage> {

	private List<MsgMapper<Message, QuickFixMessage>> mappers;
	
	public QuickFixParser(Direction fixToObj , SessionState state) {
    	AccessorFactory<Message, QuickFixMessage, Object> accessorFactory = new QuickFixToFixMessageAccessorFactory();
    	mappers = new ArrayList<MsgMapper<Message, QuickFixMessage>>();
    	if (fixToObj != null) {
    		for (MsgMap msgMap : fixToObj.getMsgMap()) {
    			try {
    				mappers.add(new DefaultMsgMapper<Message, QuickFixMessage>(accessorFactory, msgMap, state));
    			} catch (Exception e) {
    				throw new TransportException("fail to parse msg map " + msgMap.getName(), e);
    			}
    		}
    	}
    }
	
	@Override
	public QuickFixMessage parse(Message message, Class<QuickFixMessage> clazz) {
        try {
			for (MsgMapper<Message, QuickFixMessage> msgMapper : mappers) {
				String targetType = msgMapper.match(message);
				if (targetType != null) {
		        	QuickFixMessage fixFormat = ObjectUtil.newInstance(targetType);
			        fixFormat.setContext(
			        	new FixMessage.Context(
				        	message.getHeader().getString(FixTag.BeginString.id()),
				        	message.getHeader().getString(FixTag.MsgType.id()),
				        	message.getHeader().getString(FixTag.SenderCompID.id()),
				        	message.getHeader().getString(FixTag.TargetCompID.id()),
				        	message.getHeader().getInt(FixTag.MsgSeqNum.id()),
				        	message.getHeader().getUtcTimeStamp(FixTag.SendingTime.id())
				        )
			        );
					return msgMapper.map(message, fixFormat);
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
