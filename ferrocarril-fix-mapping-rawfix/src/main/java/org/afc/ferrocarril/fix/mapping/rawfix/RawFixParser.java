package org.afc.ferrocarril.fix.mapping.rawfix;

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
import org.afc.ferrocarril.text.UTCDatetimeFormat;
import org.afc.ferrocarril.transport.TransportException;
import org.afc.util.NumericUtil;
import org.afc.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RawFixParser implements FixParser<RawFix, FixMessage> {

	private static final Logger logger = LoggerFactory.getLogger(RawFixParser.class);

	private static ThreadLocal<UTCDatetimeFormat> dateFormat = new ThreadLocal<UTCDatetimeFormat>() {
		protected UTCDatetimeFormat initialValue() {
			return new UTCDatetimeFormat();
		}
	};
	
	private List<MsgMapper<RawFix, FixMessage>> mappers;
	
	public RawFixParser(Direction fixToObj , SessionState state) {
    	AccessorFactory<RawFix, FixMessage, Object> accessorFactory = new RawFixToFixMessageAccessorFactory();
    	mappers = new ArrayList<MsgMapper<RawFix, FixMessage>>();
    	if (fixToObj != null) {
    		for (MsgMap msgMap : fixToObj.getMsgMap()) {
    			try {
    				mappers.add(new DefaultMsgMapper<RawFix, FixMessage>(accessorFactory, msgMap, state));
    			} catch (Exception e) {
    				throw new TransportException("fail to parse msg map " + msgMap.getName(), e);
    			}
    		}
    	}
    }
	
	@Override
	public FixMessage parse(RawFix rawFix, Class<FixMessage> clazz) {
        try {
			for (MsgMapper<RawFix, FixMessage> msgMapper : mappers) {
				String targetType = msgMapper.match(rawFix); 
				if (targetType != null) {
		        	FixMessage fixFormat = ObjectUtil.<FixMessage>newInstance(targetType);
		        	msgMapper.map(rawFix, fixFormat);
		        	fixFormat.setContext(
		        		new FixMessage.Context(
		        			rawFix.findValueFrom(FixTag.BeginString.idAsString(), 0),
		        			rawFix.findValueFrom(FixTag.MsgType.idAsString(), 0),
		        			rawFix.findValueFrom(FixTag.SenderCompID.idAsString(), 0),
		        			rawFix.findValueFrom(FixTag.TargetCompID.idAsString(), 0),
		        			NumericUtil.parseInt(rawFix.findValueFrom(FixTag.MsgSeqNum.idAsString(), 0), "Error on parsing MsgSeqNum"),
		        			dateFormat.get().parse(rawFix.findValueFrom(FixTag.SendingTime.idAsString(), 0))
		        		)
	        		);
					return fixFormat; 
				}
			}
			logger.warn("cannot match any <msg-map> for the message : {}", rawFix);
			return null;
        } catch (TransportException e) {
        	throw e;
        } catch (Exception e) {
	        throw new TransportException("Fail to parse the message.", e);
        }
	}
}
