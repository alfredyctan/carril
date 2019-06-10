package org.afc.carril.fix.quickfix.standalone.send;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import org.afc.carril.fix.quickfix.standalone.listener.MessageAugmentor;
import org.afc.carril.fix.quickfix.standalone.repository.MessageRepository;
import org.afc.carril.fix.quickfix.standalone.util.FixTagResolver;
import org.afc.carril.fix.quickfix.standalone.util.Serializer;
import org.afc.carril.fix.quickfix.standalone.util.modifier.ModifierContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import quickfix.DataDictionary;
import quickfix.FieldNotFound;
import quickfix.Message;
import quickfix.SessionNotFound;

@Setter
@Accessors(chain = true)
public class AutoRepositorySender implements RepositorySender {

	private static final Logger logger = LoggerFactory.getLogger(AutoRepositorySender.class);
	
	private static List<MessageAugmentor> plugins = new LinkedList<>();

	@Autowired
	private DataDictionary dictionary;

	private ExecutorService executors;
	
	private SessionSender sender;
	
	public AutoRepositorySender(ExecutorService executors) {
		this.sender = new SessionSender() {};
		this.executors = executors;
	}

	@AllArgsConstructor
	private static class Task {
		Map<String, String> instructions;
		String line;
	}
	
	@Override
	public void send(MessageRepository repository, Message message, Runnable complete, Consumer<Exception> listener) {
		executors.execute(() -> {
			try {
				List<Task> tasks = new ArrayList<>();
				synchronized (repository) {
					String line = null;
					Task task = null;
					boolean next = true;
					while (next && (line = repository.next()) != null) {
						task = parseLine(line, message);
						next = task.instructions.get("next").equalsIgnoreCase("Y");
						tasks.add(task);
					}
				}
				int i = 0;
				int total = tasks.size();
				for (Task task : tasks) {
					i++;
					send(task);
					logger.info("message [{}/{}] sent, proceed next message", i, total);
				}
				if (i > 0) {
					logger.info("send complete, total {} message(s) sent.", i);
				}
			} catch (SessionNotFound | FieldNotFound | NumberFormatException | InterruptedException e) {
				logger.error("error on publishing auto response", e);
				listener.accept(e);
			} finally {
				ModifierContext.clear();
				complete.run();
			}
		});
	}

	private static Task parseLine(String line, Message message) throws NumberFormatException, InterruptedException, SessionNotFound, FieldNotFound {
		line = FixTagResolver.resolve(line, message);
		Map<String, String> instructions = parse(line.substring(0, line.indexOf('|')));
		line = line.substring(line.indexOf('|') + 1);
		return new Task(instructions, line);
	}


	private static Map<String, String> parse(String instructionLine) {
		Map<String, String> instructions = new HashMap<>();
		String[] splits = instructionLine.split(",");
		for (String split : splits) {
			instructions.put(split.substring(0, split.indexOf('=')), split.substring(split.indexOf('=') + 1));
		}
		return instructions;
	}

	private void send(Task task) throws NumberFormatException, InterruptedException, SessionNotFound, FieldNotFound {
		logger.info("wait {} ms", task.instructions.get("wait"));
		Thread.sleep(Integer.parseInt(task.instructions.get("wait")));
		
		Message fixMessage = Serializer.deserialize(task.line, dictionary);
		plugins.forEach(p -> p.augment(fixMessage));
		sender.send(fixMessage);
	}
	
	public static void addPluginAugmentor(MessageAugmentor plugin) {
		plugins.add(plugin);
	}
}
