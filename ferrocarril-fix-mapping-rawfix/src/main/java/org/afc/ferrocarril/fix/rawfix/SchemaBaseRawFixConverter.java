package org.afc.ferrocarril.fix.rawfix;

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
import org.afc.ferrocarril.fix.mapping.rawfix.RawFix;
import org.afc.ferrocarril.fix.mapping.rawfix.RawFixFormatter;
import org.afc.ferrocarril.fix.mapping.rawfix.RawFixParser;
import org.afc.ferrocarril.fix.mapping.schema.FixConv;
import org.afc.ferrocarril.message.FixMessage;
import org.afc.ferrocarril.transport.TransportException;
import org.afc.util.IOUtil;
import org.afc.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchemaBaseRawFixConverter implements Converter<RawFix, FixMessage> {

	private static Logger logger = LoggerFactory.getLogger(SchemaBaseRawFixConverter.class);

	private FixConv fixConv;

	private FixParser<RawFix, FixMessage> fixParser;

	private FixFormatter<FixMessage, RawFix> fixFormatter;

	private SessionState state;

	public SchemaBaseRawFixConverter(String schemaFile) {
		this.state = new SimpleSessionState();
		InputStream is = null;
		try {
			Unmarshaller unmarshaller = JAXBContext.newInstance(FixConv.class).createUnmarshaller();
			File file = new File(schemaFile);
			if (file.isFile()) {
				logger.info("using schema in path {}", schemaFile);
				is = new FileInputStream(file);
			} else {
				logger.info("using schema in classpath {}", schemaFile);
				is = getClass().getResourceAsStream(schemaFile);
			}

			fixConv = (FixConv) unmarshaller.unmarshal(is);

			fixFormatter = new RawFixFormatter(fixConv.getObjToFix(), state);
			fixParser = new RawFixParser(fixConv.getFixToObj(), state);
		} catch (Exception e) {
			throw new TransportException("Unable to compile conversion schema.", e);
		} finally {
			IOUtil.close(is);
		}
	}

	@Override
	public FixMessage parse(RawFix object, Class<? extends FixMessage> clazz) throws TransportException {
		try {
			return fixParser.parse(object, ObjectUtil.<Class<FixMessage>> cast(clazz));
		} catch (Exception e) {
			throw new TransportException("Fail to parse raw fix string.", e);
		}
	}

	@Override
	public RawFix format(FixMessage object) throws TransportException {
		FixMessage fixFormat = (FixMessage) object;
		return fixFormatter.format(fixFormat);
	}
}
