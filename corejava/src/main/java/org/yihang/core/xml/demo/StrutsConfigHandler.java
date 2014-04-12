package org.yihang.core.xml.demo;

import org.yihang.core.xml.demo.tags.StrutsTag;

public interface StrutsConfigHandler {
	
	public StrutsTag parse(String classpathXML, String classpathDTD) throws ParseException ;

}
