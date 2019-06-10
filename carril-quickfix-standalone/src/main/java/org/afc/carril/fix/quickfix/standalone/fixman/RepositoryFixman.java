package org.afc.carril.fix.quickfix.standalone.fixman;

import java.io.File;

import org.afc.carril.fix.quickfix.standalone.context.EngineProperties;
import org.afc.carril.fix.quickfix.standalone.repository.FileMessageRepository;
import org.afc.carril.fix.quickfix.standalone.repository.MessageRepository;
import org.afc.carril.fix.quickfix.standalone.send.RepositorySender;
import org.afc.carril.fix.quickfix.standalone.send.SessionSender;
import org.afc.carril.fix.quickfix.standalone.util.FixTagResolver;
import org.afc.carril.fix.quickfix.standalone.util.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.afc.AFCException;
import org.afc.util.FileUtil;

import lombok.Setter;
import lombok.experimental.Accessors;
import quickfix.DataDictionary;
import quickfix.Message;

@Setter
@Accessors(chain = true)
public class RepositoryFixman implements Fixman {

	private static final Logger logger = LoggerFactory.getLogger(RepositoryFixman.class);

	@Autowired
	private RepositorySender repositorySender;
	
	@Autowired
	private DataDictionary dictionary;
	
	private SessionSender sender;
	
	private EngineProperties properties;

	public RepositoryFixman(EngineProperties properties) {
		this.properties = properties;
		this.sender = new SessionSender() {};
	}
	
	@Override
	public void collect() {
		if (properties.getFixman() == null || !new File(properties.getFixman().getDraft()).exists()) {
			return;
		}
		
		MessageRepository repository = properties.getFixman().getBreakPattern() != null ? 
			new FileMessageRepository(properties.getFixman().getDraft(), properties.getFixman().getBreakPattern(), properties.getFixman().isBreakInclude()): 
			new FileMessageRepository(properties.getFixman().getDraft());

		try {
			repositorySender.send(repository, new Message(), () -> { 
				FileUtil.move(properties.getFixman().getDraft(), properties.getFixman().getSent(), false);
			}, e -> {
				FileUtil.move(properties.getFixman().getDraft(), properties.getFixman().getError(), false);
			});
		} catch (Exception e) {
			logger.error("failed to send message in repository, " + repository + ", move all message to error directory", e);
			FileUtil.move(properties.getFixman().getDraft(), properties.getFixman().getError(), false);
		}
	}
	
	
	
	@Override
	public void send(String fix) {
		try {
			String resolvedFix = FixTagResolver.resolve(fix, new Message());
			Message fixMessage = Serializer.deserialize(resolvedFix, dictionary);
			logger.info("sending [{}]", Serializer.escape(fixMessage));
			sender.send(fixMessage);
		} catch (Exception e) {
			throw new AFCException("failed to send fix", e);
		}
	}
}
