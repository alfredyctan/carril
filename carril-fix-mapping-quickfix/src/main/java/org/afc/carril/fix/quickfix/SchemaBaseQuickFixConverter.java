package org.afc.carril.fix.quickfix;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.afc.carril.converter.Converter;
import org.afc.carril.fix.mapping.FixFormatter;
import org.afc.carril.fix.mapping.FixParser;
import org.afc.carril.fix.mapping.SessionState;
import org.afc.carril.fix.mapping.impl.SimpleSessionState;
import org.afc.carril.fix.mapping.quickfix.QuickFixFormatter;
import org.afc.carril.fix.mapping.quickfix.QuickFixParser;
import org.afc.carril.fix.mapping.schema.FixConv;
import org.afc.carril.message.FixMessage;
import org.afc.carril.transport.TransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.afc.util.FileUtil;
import org.afc.util.ObjectUtil;

import quickfix.Message;

public class SchemaBaseQuickFixConverter implements Converter<Message, FixMessage> {

	private static Logger logger = LoggerFactory.getLogger(SchemaBaseQuickFixConverter.class);
	
	private FixConv fixConv;

	private FixParser<Message, FixMessage> fixParser;

	private FixFormatter<FixMessage, Message> fixFormatter;
	
	private SessionState state;
	
	public SchemaBaseQuickFixConverter(String schemaFile) {
		this.state = new SimpleSessionState();
		try (InputStream is = FileUtil.resolveResourceAsStream(schemaFile)){
			logger.info("using schema {}", schemaFile);
			
			Unmarshaller unmarshaller = JAXBContext.newInstance(FixConv.class).createUnmarshaller();
			fixConv = (FixConv) unmarshaller.unmarshal(is);
			
			fixFormatter = new QuickFixFormatter(fixConv, fixConv.getFormatter(), state);
			fixParser = new QuickFixParser(fixConv, fixConv.getParser(), state);
		} catch (Exception e) {
			throw new TransportException("Unable to compile conversion schema.", e);
		}
	}

    @Override
	public FixMessage parse(Message object, Class<? extends FixMessage> clazz) throws TransportException {
		try {
			Message message = (Message)object;
	        return fixParser.parse(message, ObjectUtil.<Class<FixMessage>>cast(clazz));
        } catch (Exception e) {
			throw new TransportException ("Fail to parse quickfix message.", e);
        }
	}

	@Override
	public Message format(FixMessage object) throws TransportException {
	    return fixFormatter.format(object);
	}
}
