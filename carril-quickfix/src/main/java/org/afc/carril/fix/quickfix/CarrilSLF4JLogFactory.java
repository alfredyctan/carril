package org.afc.carril.fix.quickfix;

import quickfix.Log;
import quickfix.SLF4JLogFactory;
import quickfix.SessionID;
import quickfix.SessionSettings;

public class CarrilSLF4JLogFactory extends SLF4JLogFactory {

    public CarrilSLF4JLogFactory(SessionSettings settings) {
        super(settings);
    }

    
    public Log create(SessionID sessionID) {
        return new CarrilSLF4JLog(super.create(sessionID));
    }

    /**
     * This supports use of this log in a CompositeLogFactory.
     */

    public Log create(SessionID sessionID, String callerFQCN) {
    	return new CarrilSLF4JLog(super.create(sessionID, callerFQCN));
    }
}