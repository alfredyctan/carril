package org.afc.carril.fix.quickfix.standalone.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.afc.carril.fix.quickfix.standalone.context.EngineProperties;
import org.afc.carril.fix.quickfix.standalone.util.FixTagResolver;
import org.afc.carril.fix.quickfix.standalone.util.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.afc.AFCException;
import org.afc.carril.fix.quickfix.message.Crude;
import org.afc.carril.transport.TransportListener;
import org.afc.filter.AttributeFilter;
import org.afc.filter.AttributeFilterFactory;

import lombok.AllArgsConstructor;
import lombok.ToString;
import quickfix.Message;

public class CaptureListener implements TransportListener<Crude> {
	
	private static final Logger logger = LoggerFactory.getLogger(CaptureListener.class);

	@AllArgsConstructor
	@ToString
	private static class Capture {
		AttributeFilter<Message> filter;
		String file;
	}

	private List<Capture> captures; 

	public CaptureListener(EngineProperties properties, AttributeFilterFactory<Message, String> filterFactory) {
		if (properties.getCaptures() != null) {
			this.captures = properties.getCaptures().stream().map(e ->
				new Capture(filterFactory.create(e.getFilter()), e.getFile())
			).collect(Collectors.toList());
			logger.info("enable filtered message capturing: {}", properties.getCaptures());
		} else {
			this.captures = new ArrayList<>();
			logger.info("disable filtered message capturing.");
		}
	}

	@Override
	public Crude onMessage(Crude message) {
		Optional<Capture> capture = captures.stream()
		.filter(e -> e.filter.filter(message.getCrude()))
		.findFirst();
		
		if (capture.isPresent()) {
			Message crude  = message.getCrude();

			File outFile = new File(FixTagResolver.resolve(capture.get().file, crude));
			if (!outFile.getParentFile().exists() && !outFile.getParentFile().mkdirs()) {
				throw new AFCException("failed to create captured directory");
			}
			
			try (PrintWriter out = new PrintWriter(new FileWriter(outFile, true))) {
				out.println(Serializer.serialize(crude));
			} catch (Exception e) {
				logger.info("error on capturing message", e);
			}
		}
		return null;
	}
}
