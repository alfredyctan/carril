package org.afc.carril.fix.quickfix.standalone.util.modifier;

import static org.afc.util.OptionalUtil.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.afc.AFCException;

import quickfix.Message;

public class XPathModifier implements TagModifier {
	
	private static final Pattern XPATH = Pattern.compile("^\\s*?\\$xpath\\s*?\\(\\s*?(?<tag>#\\d*\\.?\\d*.?\\d*)\\s*?(:\\s*?(?<mode>.)\\s*?)?:\\s*?(?<xpath>.*)\\s*?\\)\\s*?$");
	
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private XPathFactory xpathFactory;
    private Transformer transformer;
	private TagModifier fieldModifier;
	private TagModifier groupModifier;
	private XPath xpath;
	
	public XPathModifier() {
		try {
			this.factory = DocumentBuilderFactory.newInstance();
			this.builder = factory.newDocumentBuilder();
			this.xpathFactory = XPathFactory.newInstance();
			this.xpath = xpathFactory.newXPath();
	        this.transformer = TransformerFactory.newInstance().newTransformer();
	        this.transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			this.fieldModifier = new FieldModifier();
			this.groupModifier = new GroupModifier();
		} catch (ParserConfigurationException | TransformerConfigurationException | TransformerFactoryConfigurationError e) {
			throw new AFCException(e.getMessage());
		}
	}
	
	@Override
	public Pattern pattern() {
		return XPATH;
	}
	
	@Override
	public StringBuilder modify(StringBuilder str, Message message, Matcher matcher) {
		try {
			String tag = matcher.group("tag");
			String mode = iifNotEmptyElse(matcher.group("mode"), m -> m, () -> "v");
			String query = matcher.group("xpath");
			
			String xml = resolveXml(str, message, tag);
			Node node = resolveNode(query, xml);
			String value = resolveValue(node, mode);

	        return replace(str, value == null ? "" : value);
		} catch(Exception e) {
			return str;
		}
	}

	private String resolveValue(Node node, String mode) throws TransformerException {
    	StringWriter writer = new StringWriter();
		switch (mode) {
			case "v":
				if (node.getFirstChild() != null && node.getFirstChild().getNodeType() == Node.TEXT_NODE) {
					return node.getFirstChild().getNodeValue();
				} else {
		        	NodeList child = node.getChildNodes();
		        	for (int i = 0; i < child.getLength(); i++) {
		        		transformer.transform(new DOMSource(child.item(i)), new StreamResult(writer));
		        	}
				}
				break;
			case "f":
				transformer.transform(new DOMSource(node), new StreamResult(writer));
				break;
			default:
				return null;
		}
		return writer.toString();
	}

	private Node resolveNode(String query, String xml) throws SAXException, IOException, XPathExpressionException {
		Document doc = builder.parse(new InputSource(new StringReader(xml)));
		XPathExpression expr = xpath.compile(query);
		return (Node)expr.evaluate(doc, XPathConstants.NODE);
	}

	private String resolveXml(StringBuilder str, Message message, String tag) {
		Matcher matcher;
		if ((matcher = fieldModifier.pattern().matcher(tag)).matches()) {
			return fieldModifier.modify(str, message, matcher).toString();
		} else if ((matcher = groupModifier.pattern().matcher(tag)).matches()) {
			return groupModifier.modify(str, message, matcher).toString();
		} else {
			return str.toString();
		}
	}
}	


