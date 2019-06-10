package org.afc.carril.fix.quickfix.standalone.schedule;

import java.util.concurrent.ExecutorService;

import org.afc.carril.fix.quickfix.standalone.fixman.Fixman;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import org.afc.concurrent.VerboseRunnable;

public class CronScheduler {

	private static final Logger logger = LoggerFactory.getLogger(CronScheduler.class);

	@Autowired
	private ExecutorService executor;
	
	@Autowired
	private Fixman fixPostman;
	
	/* cron = "second, minute, hour, day of month, month, day(s) of week" */
	@Scheduled(cron = "${engine.fixman.schedule.cron}")
	public void schedule() {
		try {
			executor.execute(new VerboseRunnable(
				fixPostman::collect
			));
		} catch (Exception e) {
			logger.error("Unexpected error occurred in scheduled task", e);
		}
	}
}
