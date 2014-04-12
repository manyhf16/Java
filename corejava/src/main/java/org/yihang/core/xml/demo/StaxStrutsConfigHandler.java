package org.yihang.core.xml.demo;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.yihang.core.xml.demo.tags.ActionTag;
import org.yihang.core.xml.demo.tags.ConstantTag;
import org.yihang.core.xml.demo.tags.PackageTag;
import org.yihang.core.xml.demo.tags.ResultTag;
import org.yihang.core.xml.demo.tags.StrutsTag;

public class StaxStrutsConfigHandler implements StrutsConfigHandler {

	private static XMLInputFactory factory;
	static {
		factory = XMLInputFactory.newInstance();
	}

	private XMLStreamReader reader;

	private StrutsTag strutsTag;
	private PackageTag packageTag; // 最近一次的packageTag
	private ActionTag actionTag; // 最近一次的actionTag
	private ResultTag resultTag; // 最近一次的resultTag

	public StrutsTag parse(String classpathXML, String classpathDTD) throws ParseException {
		try {
			reader = factory.createXMLStreamReader(StaxStrutsConfigHandler.class.getResourceAsStream(classpathXML));
			while (reader.hasNext()) {
				int eventType = reader.next();
				if (XMLStreamReader.START_ELEMENT == eventType) {
					String tag = reader.getLocalName();
					if (tag.equals("struts")) {
						strutsTag = new StrutsTag();
					} else if (tag.equals("constant")) {
						ConstantTag constantTag = new ConstantTag(reader.getAttributeValue(null, "name"), reader.getAttributeValue(null, "value"));
						strutsTag.addConstantTag(constantTag);
					} else if (tag.equals("package")) {
						packageTag = new PackageTag(reader.getAttributeValue(null, "name"), reader.getAttributeValue(null, "extends"));
						strutsTag.addPackageTag(packageTag);
					} else if (tag.equals("action")) {
						actionTag = new ActionTag(reader.getAttributeValue(null, "name"), reader.getAttributeValue(null, "class"));
						packageTag.addActionTag(actionTag);
					} else if (tag.equals("result")) {
						resultTag = new ResultTag(reader.getAttributeValue(null, "name"), null);
						actionTag.addResultTag(resultTag);
					}
				} else if (XMLStreamReader.CHARACTERS == eventType) {
					if (reader.getText() != null)
						resultTag.setLocation(reader.getText().trim());
				}
			}
			return strutsTag;
		} catch (XMLStreamException e) {
			throw new ParseException(e);
		}
	}

}
