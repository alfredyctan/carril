package org.afc.carril.transport;

public interface SubjectContextFactory<C extends SubjectContext> {

	public C createSubjectContext(String subject);
	
}
