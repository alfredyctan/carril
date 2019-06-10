package org.afc.carril.fix.quickfix.standalone.repository;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CyclicMessageRepository extends FileMessageRepository {

	private static final Logger logger = LoggerFactory.getLogger(CyclicMessageRepository.class);
	
	private ScheduledExecutorService refresh = Executors.newSingleThreadScheduledExecutor();
	
	public CyclicMessageRepository(String path) {
		this(path, "\\r\\n|\\n", false);
	}
	
	public CyclicMessageRepository(String path, String breakPattern, boolean breakInclude) {
		super(path, breakPattern, breakInclude);
		
		
	}
	
	@Override
	public String next() {
		if (index >= buffer.length) {
			load();
			index = 0;
		}
		return buffer[index++];
	}
}
