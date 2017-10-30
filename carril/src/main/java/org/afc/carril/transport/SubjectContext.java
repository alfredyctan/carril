package org.afc.carril.transport;

import java.util.List;

import org.afc.carril.subscriber.Subscriber;

public interface SubjectContext {

	public String getSubject();
	
	public List<Subscriber> getSubscribers();
	
	public void addSubscriber(Subscriber subscription);

	public void removeSubscriber(Subscriber subscription);

}
