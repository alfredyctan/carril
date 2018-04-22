package org.afc.carril.fix.quickfix.sell;

import org.afc.carril.converter.Converter;
import org.afc.carril.fix.quickfix.QuickFixSettings;
import org.afc.carril.fix.quickfix.QuickFixTransport;
import org.afc.carril.fix.quickfix.SchemaBaseQuickFixConverter;
import org.afc.carril.message.FixMessage;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.transport.Transport;
import org.afc.carril.transport.TransportListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Seller implements CommandLineRunner {

	@Autowired
	private Transport transport;
	
	public static void main(String[] args) {
		SpringApplication.run(Seller.class, "--spring.profiles.active=sell");
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		transport.init();
		transport.subscribe("FIX.4.2:SELL-BUY", new TransportListener() {
			
			@Override
			public GenericMessage onMessage(GenericMessage message) {
				System.out.println("message received " + message);
				return null;
			}
		}, FixMessage.class);

		transport.start();
		while(true) { Thread.sleep(10000); }
	}

	@Bean
	public Transport transport(Converter converter, QuickFixSettings quickFixSettings) {
		QuickFixTransport transport = new QuickFixTransport();
		transport.setConverter(converter);
		transport.setName("sell");
		transport.setSettings(quickFixSettings);
		return transport;
	}

	@Bean
	@ConfigurationProperties("carril.sell.quickfix")
	public QuickFixSettings quickFixSettings() {
		return new QuickFixSettings();
	}
	
	@Bean
	public Converter converter() {
		return new SchemaBaseQuickFixConverter("src/test/resources/schema/schema.xml");
	}
	
}
