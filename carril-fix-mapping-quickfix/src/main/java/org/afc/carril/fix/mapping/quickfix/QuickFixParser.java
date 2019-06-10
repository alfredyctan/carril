package org.afc.carril.fix.mapping.quickfix;

import static org.afc.util.ExceptionUtil.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.afc.carril.fix.mapping.AccessorFactory;
import org.afc.carril.fix.mapping.FixParser;
import org.afc.carril.fix.mapping.MsgMapper;
import org.afc.carril.fix.mapping.SessionState;
import org.afc.carril.fix.mapping.impl.DefaultMsgMapper;
import org.afc.carril.fix.mapping.schema.Direction;
import org.afc.carril.fix.mapping.schema.FixConv;
import org.afc.carril.fix.mapping.schema.MsgMap;
import org.afc.carril.fix.tag.FixTag;
import org.afc.carril.message.FixMessage;
import org.afc.carril.transport.TransportException;

import org.afc.util.ObjectUtil;

import quickfix.Message;

public class QuickFixParser implements FixParser<Message, FixMessage> {
	
	private FixConv fixConv;
	
	private List<MsgMapper<Message, Object>> mappers;
	
	private Map<String, Class<FixMessage>> targetTypes;
	
	public QuickFixParser(FixConv fixConv, Direction fixToObj , SessionState state) {
		this.fixConv = fixConv;
		this.targetTypes = new ConcurrentHashMap<>();
    	AccessorFactory<Message, Object, Object> accessorFactory = new QuickFixToFixMessageAccessorFactory();
    	mappers = new ArrayList<MsgMapper<Message, Object>>();
    	if (fixToObj != null) {
    		for (MsgMap msgMap : fixToObj.getMsgMap()) {
    			try {
    				mappers.add(new DefaultMsgMapper<Message, Object>(fixConv, accessorFactory, msgMap, state));
    			} catch (Exception e) {
    				throw new TransportException("fail to parse msg map " + msgMap.getName(), e);
    			}
    		}
    	}
    }
	
	@Override
	public FixMessage parse(Message message, Class<FixMessage> clazz) {
        try {
			for (MsgMapper<Message, Object> msgMapper : mappers) {
				String targetType = msgMapper.match(message);
				if (targetType != null) {
					Class<FixMessage> targetTypeClazz = targetTypes.get(targetType);
					if (targetTypeClazz == null) {
						targetTypeClazz = tryTo(
							() -> ObjectUtil.forName(targetType),
							() -> ObjectUtil.forName(fixConv.getPackagePrefix() + '.' + targetType)
						);
						targetTypes.put(targetType, targetTypeClazz);
					}
					FixMessage fixFormat = ObjectUtil.newInstance(targetTypeClazz);

					fixFormat.setContext(
			        	new FixMessage.Context(
				        	message.getHeader().getString(FixTag.BeginString.id()),
				        	message.getHeader().getString(FixTag.SenderCompID.id()),
				        	message.getHeader().getString(FixTag.TargetCompID.id()),
				        	message.getHeader().getString(FixTag.MsgType.id()),
				        	message.getHeader().getInt(FixTag.MsgSeqNum.id()),
				        	message.getHeader().getUtcTimeStamp(FixTag.SendingTime.id())
				        )
			        );
					return (FixMessage)msgMapper.map(message, fixFormat);
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
