package org.yihang.core.xml;

import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.Test;
import org.xml.sax.SAXException;
import org.yihang.core.xml.demo.SaxStrutsConfigHandler;
import org.yihang.core.xml.demo.StaxStrutsConfigHandler;
import org.yihang.core.xml.demo.tags.StrutsTag;

public class TestXmlParse {
	
	private String STRUTS_XML = "/org/yihang/core/xml/struts.xml";
	private String STRUTS_DTD = "/org/yihang/core/xml/struts-2.3.dtd";

	@Test
	public void testSaxStrutsParse() throws ParserConfigurationException, SAXException, IOException {
		SaxStrutsConfigHandler handler = new SaxStrutsConfigHandler();
		StrutsTag tag = handler.parse(STRUTS_XML, STRUTS_DTD);
		tag.debugPrint();
	}
	
	@Test
	public void testStaxStrutsParse() throws ParserConfigurationException, SAXException, IOException {
		StaxStrutsConfigHandler handler = new StaxStrutsConfigHandler();
		StrutsTag tag = handler.parse(STRUTS_XML, null);
		tag.debugPrint();
	}
	
	@Test
	public void testSpringParse() throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SchemaFactory sFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = sFactory.newSchema(new Source[] {
				new StreamSource(TestXmlParse.class.getResourceAsStream("spring-beans.xsd")),
				new StreamSource(TestXmlParse.class.getResourceAsStream("spring-context.xsd"))
		});
		factory.setNamespaceAware(true);
//		factory.setValidating(true); // 注意schema验证不用 setValidating = true
		factory.setSchema(schema);
		SpringConfigHandler handler = new SpringConfigHandler();
		SAXParser parser = factory.newSAXParser();
		parser.parse(TestXmlParse.class.getResourceAsStream("spring.xml"), handler);
	}
}
