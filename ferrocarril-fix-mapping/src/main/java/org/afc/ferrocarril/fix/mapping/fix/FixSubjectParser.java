package org.afc.ferrocarril.fix.mapping.fix;

public interface FixSubjectParser {

	public String getBeginString();

	public String getSenderCompID();

	public String getTargetCompID();

	public String getSessionID();
	
}
