package org.afc.ferrocarril.fix.quickfix;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.afc.ferrocarril.converter.Converter;
import org.afc.ferrocarril.fix.mapping.FixFormatter;
import org.afc.ferrocarril.fix.mapping.FixParser;
import org.afc.ferrocarril.fix.mapping.SessionState;
import org.afc.ferrocarril.fix.mapping.impl.SimpleSessionState;
import org.afc.ferrocarril.fix.mapping.quickfix.QuickFixFormatter;
import org.afc.ferrocarril.fix.mapping.quickfix.QuickFixParser;
import org.afc.ferrocarril.fix.mapping.schema.FixConv;
import org.afc.ferrocarril.message.QuickFixMessage;
import org.afc.ferrocarril.transport.TransportException;
import org.afc.util.ObjectUtil;
import org.codehaus.plexus.util.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.Message;

public class SchemaBaseQuickFixConverter implements Converter<Message, QuickFixMessage> {

	private static Logger logger = LoggerFactory.getLogger(SchemaBaseQuickFixConverter.class);
	
	private FixConv fixConv;

	private FixParser<Message, QuickFixMessage> fixParser;

	private FixFormatter<QuickFixMessage, Message> fixFormatter;
	
	private SessionState state;
	
	public SchemaBaseQuickFixConverter(String schemaFile) {
		this.state = new SimpleSessionState();
		InputStream is = null;
		try {
			logger.info("using schema {}", schemaFile);
			Unmarshaller unmarshaller = JAXBContext.newInstance(FixConv.class).createUnmarshaller();
			File file = new File(schemaFile);
			if (file.isFile()) {
				is = new FileInputStream(file);
			} else {
				is = getClass().getResourceAsStream(schemaFile);
			}
			
//			Schema schema;
//			SchemaFactory schemaFactory = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
//			schema = schemaFactory.newSchema(new File(schemaFile));
//			unmarshaller.setSchema(schema);
			fixConv = (FixConv) unmarshaller.unmarshal(is);
			
			fixFormatter = new QuickFixFormatter(fixConv.getObjToFix(), state);
			fixParser = new QuickFixParser(fixConv.getFixToObj(), state);
		} catch (Exception e) {
			throw new TransportException("Unable to compile conversion schema.", e);
		} finally {
			IOUtil.close(is);
		}
	}

    @Override
	public QuickFixMessage parse(Message object, Class<? extends QuickFixMessage> clazz) throws TransportException {
		try {
			Message message = (Message)object;
	        return fixParser.parse(message, ObjectUtil.<Class<QuickFixMessage>>cast(clazz));
        } catch (Exception e) {
			throw new TransportException ("Fail to parse quickfix message.", e);
        }
	}

	@Override
	public Message format(QuickFixMessage object) throws TransportException {
	    return fixFormatter.format(object);
	}
}
