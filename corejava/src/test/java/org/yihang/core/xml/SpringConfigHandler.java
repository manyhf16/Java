package org.yihang.core.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class SpringConfigHandler extends DefaultHandler {
	@Override
	public void warning(SAXParseException e) throws SAXException {
		printInfo("warn",e);
	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		printInfo("error",e);
	}

	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		printInfo("fatal",e);
	}

	private void printInfo(String level, SAXParseException e) {
		System.out.printf("[%s] Public ID: %s, System ID: %s, [%d,%d]: %s%n", level, e.getPublicId(), e.getSystemId(),
				e.getLineNumber(), e.getColumnNumber(), e.getMessage());
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		System.out.println("--------------------------------");
		System.out.println(uri);
		System.out.println(localName);
		System.out.println(qName);
	}

}
