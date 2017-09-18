package org.afc.ferrocarril.transport;

public interface SubjectContextFactory<C extends SubjectContext> {

	public C createSubjectContext(String subject);
	
}
