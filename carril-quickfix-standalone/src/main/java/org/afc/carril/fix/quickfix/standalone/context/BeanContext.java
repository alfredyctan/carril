package org.afc.carril.fix.quickfix.standalone.context;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.afc.carril.fix.quickfix.standalone.ApplicatonStartedListener;
import org.afc.carril.fix.quickfix.standalone.context.EngineProperties.Sessions;
import org.afc.carril.fix.quickfix.standalone.filter.QuickFixAttributeAccessor;
import org.afc.carril.fix.quickfix.standalone.fixman.Fixman;
import org.afc.carril.fix.quickfix.standalone.fixman.RepositoryFixman;
import org.afc.carril.fix.quickfix.standalone.listener.AutoResponseListener;
import org.afc.carril.fix.quickfix.standalone.listener.CaptureListener;
import org.afc.carril.fix.quickfix.standalone.listener.InlineListener;
import org.afc.carril.fix.quickfix.standalone.schedule.CronScheduler;
import org.afc.carril.fix.quickfix.standalone.send.AutoRepositorySender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import org.afc.carril.converter.PassthruConverter;
import org.afc.carril.fix.quickfix.embedded.EmbeddedQuickFixEngine;
import org.afc.carril.fix.quickfix.message.Crude;
import org.afc.carril.transport.Transport;
import org.afc.carril.transport.TransportListener;
import org.afc.concurrent.NamedThreadFactory;
import org.afc.filter.AttributeFilterFactory;
import org.afc.filter.ExpressionAttributeFilterFactory;
import org.afc.filter.QueryAttributeFilterFactory;

import quickfix.ConfigError;
import quickfix.DataDictionary;
import quickfix.Message;


@EnableScheduling
@Configuration
@EnableConfigurationProperties({ EngineProperties.class })
public class BeanContext {

	@Autowired
	private EngineProperties properties;
	
	@Bean
	public EmbeddedQuickFixEngine embeddedQuickFixEngine() {
		EmbeddedQuickFixEngine acceptor = new EmbeddedQuickFixEngine(
			properties.getCarril().getFile(), 
			properties.getCarril().getPrefix(), 
			new PassthruConverter<>()
		);

		for (Sessions session : properties.getSessions()) {
			acceptor.add(session.getId(), listener(), Crude.class);
		}
		return acceptor;
	}
	
	@Bean
	public ApplicationListener<? extends ApplicationEvent> applicationListener() {
		return new ApplicatonStartedListener();
	}
	
	@Bean 
	public TransportListener<Crude> listener() {
		return new InlineListener<Crude>(Arrays.asList(
			capture(),
			autoResponse()
		));
	}
	
	@Bean
	public TransportListener<Crude> capture() {
		return new CaptureListener(properties, attributeFilterFactory());
	}

	@Bean
	public TransportListener<Crude> autoResponse() {
		return new AutoResponseListener(properties, attributeFilterFactory());
	}
	
	@Bean
	public AttributeFilterFactory<Message, String> attributeFilterFactory() {
		return new QueryAttributeFilterFactory<>(new ExpressionAttributeFilterFactory<>(new QuickFixAttributeAccessor()), '[', ']');
	}
	
	@Bean
	public DataDictionary dictionary(@Value("${engine.dictionary}") String dictionary) throws ConfigError {
		return new DataDictionary(dictionary);
	}

	@Bean
	public Fixman fixPostman() {
		return new RepositoryFixman(properties);
	}
	
	@Bean
	public AutoRepositorySender autoRepositorySender(@Value("${engine.threadPool:10}") int threadPool) {
		return new AutoRepositorySender(Executors.newFixedThreadPool(threadPool, new NamedThreadFactory("auto-repo")));
	}
	
	@Bean
	public ExecutorService executor() {
		return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>(1),
			new NamedThreadFactory("outbound")
		);
	}
	
	@Bean
	public Transport transport(EmbeddedQuickFixEngine engine) {
		return engine.getTransport();
	}
	
	@Bean
	public CronScheduler cronScheduler() {
		return new CronScheduler();
	}
}
