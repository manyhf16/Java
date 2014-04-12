package org.yihang.core.xml.demo;

import java.io.IOException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import org.yihang.core.xml.demo.tags.ActionTag;
import org.yihang.core.xml.demo.tags.ConstantTag;
import org.yihang.core.xml.demo.tags.PackageTag;
import org.yihang.core.xml.demo.tags.ResultTag;
import org.yihang.core.xml.demo.tags.StrutsTag;

public class SaxStrutsConfigHandler extends DefaultHandler implements StrutsConfigHandler {
	private static SAXParserFactory factory;
	static {
		factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(false);
		factory.setValidating(true);
	}

	@Override
	public void warning(SAXParseException e) throws SAXException {
		printInfo(e);
	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		printInfo(e);
	}

	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		printInfo(e);
	}

	private void printInfo(SAXParseException e) {
		System.out.printf("Public ID: %s, System ID: %s, [%d,%d]: %s%n", e.getPublicId(), e.getSystemId(), e.getLineNumber(), e.getColumnNumber(),
				e.getMessage());
	}

	private InputSource dtdInputSource;

	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException {
		return dtdInputSource;
	}

	private StrutsTag strutsTag;
	private PackageTag packageTag; // 最近一次的packageTag
	private ActionTag actionTag; // 最近一次的actionTag
	private ResultTag resultTag; // 最近一次的resultTag

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if ("struts".equals(qName)) {
			strutsTag = new StrutsTag();
		} else if ("constant".equals(qName)) {
			ConstantTag cTag = new ConstantTag(attributes.getValue("name"), attributes.getValue("value"));
			strutsTag.addConstantTag(cTag);
		} else if ("package".equals(qName)) {
			packageTag = new PackageTag(attributes.getValue("name"), attributes.getValue("extends"));
			strutsTag.addPackageTag(packageTag);
		} else if ("action".equals(qName)) {
			actionTag = new ActionTag(attributes.getValue("name"), attributes.getValue("class"));
			packageTag.addActionTag(actionTag);
		} else if ("result".equals(qName)) {
			resultTag = new ResultTag(attributes.getValue("name"), null);
			actionTag.addResultTag(resultTag);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String tmpString = new String(ch, start, length).trim();
		if (resultTag != null && tmpString.length() > 0) {
			resultTag.setLocation(new String(ch, start, length).trim());
		}
	}

	@Override
	public StrutsTag parse(String classpathXML, String classpathDTD) throws ParseException {
		try {
			this.dtdInputSource = new InputSource(SaxStrutsConfigHandler.class.getResourceAsStream(classpathDTD));
			SAXParser parser = factory.newSAXParser();
			parser.parse(SaxStrutsConfigHandler.class.getResourceAsStream(classpathXML), this);
			return strutsTag;
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}
}
