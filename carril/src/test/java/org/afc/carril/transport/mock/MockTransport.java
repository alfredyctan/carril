package org.afc.carril.transport.mock;

import java.util.List;

import org.afc.carril.converter.Converter;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.publisher.Publisher;
import org.afc.carril.subscriber.Subscriber;
import org.afc.carril.transport.ExceptionListener;
import org.afc.carril.transport.SubjectContext;
import org.afc.carril.transport.SubjectRegistry;
import org.afc.carril.transport.TransportException;
import org.afc.carril.transport.TransportListener;
import org.afc.carril.transport.impl.AbstractTransport;
import org.afc.carril.transport.impl.DefaultSubjectContext;


@SuppressWarnings("rawtypes")
public class MockTransport extends AbstractTransport {

	private boolean init;
	
	private boolean start;
	
	private boolean stop;
	
	private boolean dispose;
	
	private boolean publish;
	
	@Override
    protected void doInit() {
	    init = true;
    }

	@Override
    protected void doStart() {
	    start = true;
    }

	@Override
    protected void doStop() {
		fireOnException(new TransportException("error on stop"));
	    stop = true;
    }

	@Override
    protected void doDispose() {
		dispose = true;
    }

	@Override
    public SubjectContext createSubjectContext(String subject) {
	    return new DefaultSubjectContext(subject);
    }

	@Override
    public Object getBaseImplementation() {
	    return "IMPL";
    }

	@Override
    public Subscriber createSubscriber(String subject, TransportListener transportListener, Class<? extends GenericMessage> clazz, Converter<Object, GenericMessage> converter) {
	    return new MockSubscriber();
    }

	public boolean isInit() {
    	return init;
    }

	public void setInit(boolean init) {
    	this.init = init;
    }

	public boolean isStart() {
    	return start;
    }

	public void setStart(boolean start) {
    	this.start = start;
    }

	public boolean isStop() {
    	return stop;
    }

	public void setStop(boolean stop) {
    	this.stop = stop;
    }

	public boolean isDispose() {
    	return dispose;
    }

	public void setDispose(boolean dispose) {
    	this.dispose = dispose;
    }

	public boolean isPublish() {
    	return publish;
    }

	public void setPublish(boolean publish) {
    	this.publish = publish;
    }

	public SubjectRegistry getRegistry() {
    	return registry;
    }

	@SuppressWarnings("unchecked")
	public void setRegistry(SubjectRegistry registry) {
    	this.registry = registry;
    }

	@SuppressWarnings("unchecked")
	public List<ExceptionListener> getExptListeners() {
    	return exceptionListeners;
    }

	@SuppressWarnings("unchecked")
	public void setExptListeners(List<ExceptionListener> exptListeners) {
    	this.exceptionListeners = exptListeners;
    }

	@SuppressWarnings("unchecked")
	public Converter<Object, GenericMessage> getConverter() {
    	return converter;
    }

	@Override
	public Publisher createPublisher(String subject) {
		return new MockPublisher(registry, subject);
	}
}
