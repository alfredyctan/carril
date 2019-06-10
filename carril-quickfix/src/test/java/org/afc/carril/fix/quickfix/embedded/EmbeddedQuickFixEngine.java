package org.afc.carril.fix.quickfix.embedded;

import java.util.LinkedList;
import java.util.List;

import org.afc.carril.converter.Converter;
import org.afc.carril.fix.quickfix.QuickFixSettings;
import org.afc.carril.fix.quickfix.QuickFixTransport;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.transport.TransportListener;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.core.io.ClassPathResource;

import org.afc.AFCException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

public class EmbeddedQuickFixEngine {

	@Data
	@AllArgsConstructor
	private static class Context {
		private String session;
		private TransportListener<? extends GenericMessage> listener;
		private Class<? extends GenericMessage> clazz;
	}

	@Getter
	private QuickFixTransport transport;

	private List<Context> contexts;

	public EmbeddedQuickFixEngine(String cfg, String prefix, Converter converter) throws AFCException {
		try {
			this.contexts = new LinkedList<>();
			YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
			yaml.setResources(new ClassPathResource(cfg));

			ConfigurationPropertySource source = new MapConfigurationPropertySource(yaml.getObject());
			Binder binder = new Binder(source);
			QuickFixSettings settings = binder.bind(prefix, QuickFixSettings.class).get();

			this.transport = new QuickFixTransport();
			this.transport.setName(prefix);
			this.transport.setConverter(converter);
			this.transport.setSettings(settings);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AFCException(e);
		}
	}

	public void add(String session, TransportListener<? extends GenericMessage> listener, Class<? extends GenericMessage> clazz) {
		contexts.add(new Context(session, listener, clazz));
	}

	public void start() {
		transport.init();
		for (Context context : contexts) {
			transport.subscribe(context.getSession(), context.getListener(), context.getClazz());
		}
		transport.start();
	}

	public void stop() {
		transport.stop();
		transport.dispose();
	}
}
