package org.afc.carril.transport;

import java.util.List;

import org.afc.carril.converter.Converter;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.subscriber.Subscriber;
import org.afc.carril.subscriber.SubscriberRegistry;
import org.afc.carril.transport.ExceptionListener;
import org.afc.carril.transport.SubjectContext;
import org.afc.carril.transport.TransportException;
import org.afc.carril.transport.TransportListener;
import org.afc.carril.transport.impl.AbstractTransport;
import org.afc.carril.transport.impl.DefaultSubjectContext;


@SuppressWarnings("rawtypes")
class MockTransport extends AbstractTransport {

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
    public void publish(String subject, GenericMessage fmtObj, Converter<Object, GenericMessage> converter) throws TransportException {
		publish = true;
		if (subject == null) {
			fireOnException(new TransportException("null"));
		}
    }

	@SuppressWarnings("unchecked")
	@Override
    public <G extends GenericMessage> G publishRequest(String subject, GenericMessage fmtObj, Class<? extends GenericMessage> clazz,
                                        Converter<Object, GenericMessage> converter, int timeout) throws TransportException {
	    return (G) new MockMessage();
    }

	@Override
    public Subscriber createSubscriber(String subject, TransportListener transportListener,
                                           Class<? extends GenericMessage> clazz,
                                           Converter<Object, GenericMessage> converter) {
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

	public SubscriberRegistry getRegistry() {
    	return registry;
    }

	@SuppressWarnings("unchecked")
	public void setRegistry(SubscriberRegistry registry) {
    	this.registry = registry;
    }

	@SuppressWarnings("unchecked")
	public List<ExceptionListener> getExptListeners() {
    	return exptListeners;
    }

	@SuppressWarnings("unchecked")
	public void setExptListeners(List<ExceptionListener> exptListeners) {
    	this.exptListeners = exptListeners;
    }

	@SuppressWarnings("unchecked")
	public Converter<Object, GenericMessage> getConverter() {
    	return converter;
    }

}
