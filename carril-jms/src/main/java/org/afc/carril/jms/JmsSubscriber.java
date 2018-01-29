package org.afc.carril.jms;


import org.afc.carril.converter.Converter;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.subscriber.AbstractSubscriber;
import org.afc.carril.transport.SubjectRegistry;
import org.afc.carril.transport.TransportException;
import org.afc.carril.transport.TransportListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmsSubscriber extends AbstractSubscriber<JmsSubjectContext> {

	protected static final Logger logger = LoggerFactory.getLogger(JmsSubscriber.class);

	protected <W, G extends GenericMessage> JmsSubscriber(SubjectRegistry<JmsSubjectContext> registry, String subject, TransportListener transportListener, Class<G> clazz, Converter<W, G> converter) {
		super(registry, subject, transportListener, clazz, converter);
	}

	@Override
	public void subscribe() throws TransportException {
		
	}

	@Override
	public void unsubscribe() throws TransportException {
	}

	
	
//	private String subject;
//	private MessageConsumer messageConsumer;
//	private TransportListener transportListener;
//	private Converter<Object, GenericMessage> convertor;
//	private Map<String, List<Subscriber>> SubscriberMap;
//	private Class<? extends GenericMessage> classObj;
//
//	public JmsSubscriber(String subject, MessageConsumer messageConsumer, Convertor<Object, GenericMessage> convertor, TransportListener transportListener,
//	                         Class<? extends GenericMessage> classObj, Map<String, List<Subscriber>> SubscriberMap) {
//		this.subject = subject;
//		this.messageConsumer = messageConsumer;
//		this.transportListener = transportListener;
//		this.convertor = convertor;
//		this.classObj = classObj;
//		this.SubscriberMap = SubscriberMap;
//	}
//
//	@Override
//	public void onMessage(final Message message) {
//		class DataListener implements Runnable {
//			public void run() {
//				try {
//					logger.debug("Parsing the message for {}", subject);
//					GenericMessage fmtObj = convertor.parse(message, classObj);
//					logger.debug("Handling the message for {}", subject);
//					transportListener.handle(fmtObj);
//					logger.debug("Completed handling the message for {}", subject);
//				} catch (TransportException te) {
//					te.printStackTrace();
//					logger.error("Fail to parse the message: ", te);
//				} catch (Exception e) {
//					logger.error("Fail to handle the message: ", e);
//				}
//			}
//		}
//
//		ExecutorService executorService = transportListener.getExecutorService();
//		if (executorService != null) {
//			logger.debug("Received subject: {} using provided executor service to handle.", subject);
//			executorService.execute(new DataListener());
//		} else {
//			logger.debug("Received subject: {} using transport thread to handle.", subject);
//			new DataListener().run();
//		}
//	}
//
//	@Override
//	public void subscribe() throws TransportException {
//		try {
//			messageConsumer.setMessageListener(this);
//			logger.info("Start to subscribe message -- subject: {}", subject);
//		} catch (JMSException jmsException) {
//			throw new TransportException("Subscriber error: ", jmsException);
//		}
//	}
//
//	@Override
//	public void unsubscribe() throws TransportException {
//		try {
//			messageConsumer.close();
//
//			List<Subscriber> Subscribers = SubscriberMap.get(subject);
//			if (Subscribers != null) {
//				Subscribers.remove(this);
//				if (Subscribers.size() == 0) {
//					SubscriberMap.remove(subject);
//				}
//			}
//		} catch (JMSException jmsException) {
//			logger.error("Error on stopping messageConsumer.", jmsException);
//		}
//		logger.info("Unsubscribe message -- subject: {}", subject);
//	}
}
