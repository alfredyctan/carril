package org.afc.carril.transport;

import java.util.Collection;
import java.util.Map;

import org.afc.carril.subscriber.Subscriber;


public interface SubjectRegistry<C extends SubjectContext> {

//	public void register(String subject, Subscriber subscription);
//
	public void register(String subject, C subjectContext);

	public C unregister(String subject);
	
	public C getSubjectContext(String subject);

	public Collection<C> getSubjectContexts();
	
	public Map<String, C> unregisterAll();
	
//	public void setSubjectContextFactory(SubjectContextFactory<C> factory);
}
