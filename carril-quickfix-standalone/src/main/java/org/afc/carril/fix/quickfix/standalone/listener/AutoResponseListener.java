package org.afc.carril.fix.quickfix.standalone.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.afc.carril.fix.quickfix.standalone.context.EngineProperties;
import org.afc.carril.fix.quickfix.standalone.repository.CyclicMessageRepository;
import org.afc.carril.fix.quickfix.standalone.repository.FileMessageRepository;
import org.afc.carril.fix.quickfix.standalone.repository.MessageRepository;
import org.afc.carril.fix.quickfix.standalone.send.RepositorySender;
import org.afc.carril.fix.quickfix.standalone.util.FixTagResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.afc.carril.fix.quickfix.message.Crude;
import org.afc.carril.transport.TransportListener;
import org.afc.filter.AttributeFilter;
import org.afc.filter.AttributeFilterFactory;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import quickfix.Message;

@Setter
@Accessors(chain = true)
public class AutoResponseListener implements TransportListener<Crude> {
	
	private static final Logger logger = LoggerFactory.getLogger(AutoResponseListener.class);
	
	@AllArgsConstructor
	@ToString
	private static class AutoResponse {
		AttributeFilter<Message> filter;
		String repository;
		String breakPattern;
		boolean breakInclude;
		boolean cyclic;
	}

	@Autowired
	private RepositorySender repositorySender;
	
	private Map<String, MessageRepository> repositories;

	private List<AutoResponse> autoResponses;

	public AutoResponseListener(EngineProperties properties, AttributeFilterFactory<Message, String> filterFactory) {
		this.repositories = new ConcurrentHashMap<>();
		if (properties.getAutoResponses() != null) {
			this.autoResponses = properties.getAutoResponses().stream().map(e ->
				new AutoResponse(filterFactory.create(e.getFilter()), e.getRepository(), e.getBreakPattern(), e.isBreakInclude(), e.isCyclic())
			).collect(Collectors.toList());
		} else {
			this.autoResponses = new ArrayList<>();
		}
	}
	
	@Override
	public Crude onMessage(Crude message) {
		try {
			Optional<AutoResponse> autoResponse = autoResponses.stream()
			.filter(e -> e.filter.filter(message.getCrude()))
			.findFirst();
			
			if (autoResponse.isPresent()) {
				String file = FixTagResolver.resolve(autoResponse.get().repository, message.getCrude());
				logger.info("filter matched, loading message from repository {}", file);
				MessageRepository repository = ensureRepository(file, autoResponse.get().breakPattern, autoResponse.get().breakInclude, autoResponse.get().cyclic);
				repositorySender.send(repository, message.getCrude(), () -> {}, e -> {});
			}
		} catch (Exception e) {
			logger.error("fail to handle message : [" + message + "]", e);
		}		
		return null;
	}

	private MessageRepository ensureRepository(String file, String breakPattern, boolean breakInclude, boolean cyclic) {
		MessageRepository repository = repositories.get(file); 
		if (repository == null) {
			if (cyclic) {
				repository = new CyclicMessageRepository(file, breakPattern, breakInclude);
			} else {
				repository = new FileMessageRepository(file, breakPattern, breakInclude);
			}
			repositories.put(file, repository);
		}
		return repository;
	}
}
