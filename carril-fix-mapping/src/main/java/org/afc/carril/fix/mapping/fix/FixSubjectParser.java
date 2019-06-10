package org.afc.carril.fix.mapping.fix;

import org.afc.carril.message.FixMessage.Context;

public interface FixSubjectParser {

	public String getBeginString();

	public String getSenderCompID();

	public String getTargetCompID();

	public String getSessionID();
	
	public Context toContext();
}
