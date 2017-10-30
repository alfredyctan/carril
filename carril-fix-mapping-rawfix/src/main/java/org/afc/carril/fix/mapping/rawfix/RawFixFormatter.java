package org.afc.carril.fix.mapping.rawfix;

import java.util.ArrayList;
import java.util.List;

import org.afc.carril.fix.mapping.AccessorFactory;
import org.afc.carril.fix.mapping.FixFormatter;
import org.afc.carril.fix.mapping.MsgMapper;
import org.afc.carril.fix.mapping.SessionState;
import org.afc.carril.fix.mapping.impl.DefaultMsgMapper;
import org.afc.carril.fix.mapping.schema.Direction;
import org.afc.carril.fix.mapping.schema.MsgMap;
import org.afc.carril.message.FixMessage;
import org.afc.carril.transport.TransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RawFixFormatter implements FixFormatter<FixMessage, RawFix> {

	private static final Logger logger = LoggerFactory.getLogger(RawFixFormatter.class);
	
	private List<MsgMapper<FixMessage, RawFix>> mappers;
	
	public RawFixFormatter(Direction objToFix, SessionState state) {
		AccessorFactory<FixMessage, RawFix, Object> accessorFactory = new FixMessageToRawFixAccessorFactory();
		mappers = new ArrayList<MsgMapper<FixMessage, RawFix>>();

		if (objToFix != null) {
			for (MsgMap msgMap : objToFix.getMsgMap()) {
				try {
					mappers.add(new DefaultMsgMapper<FixMessage, RawFix>(accessorFactory, msgMap, state));
				} catch (Exception e) {
					throw new TransportException("fail to parse <msg-map> " + msgMap.getName(), e);
				}
			}
		}
	}
	
	
	@Override
	public RawFix format(FixMessage fixFormat) {
        try {
    		for (MsgMapper<FixMessage, RawFix> msgMapper : mappers) {
    			String targetType = msgMapper.match(fixFormat);
    			if (targetType != null) {
    				fixFormat.getContext().setMsgType(targetType);
    	        	RawFix rawFix = RawFix.forWrite(
    	        		fixFormat.getContext().getProtocolVersion(),
    	        		fixFormat.getContext().getMsgType(),
    	        		fixFormat.getContext().getSenderCompID(),
    	        		fixFormat.getContext().getTargetCompID()
    	        	);
    				msgMapper.map(fixFormat, rawFix);
    				rawFix.seal();
    				return rawFix;
    			}
    		}
    		logger.info("Cannot found any mapping matched the msg.");
    		return null;
        } catch (TransportException e) {
        	throw e;
        } catch (Exception e) {
	        throw new TransportException("Fail to format the message.", e);
        }
	}
}
