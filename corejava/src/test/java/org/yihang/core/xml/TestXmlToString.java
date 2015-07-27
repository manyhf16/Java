package org.yihang.core.xml;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class TestXmlToString {
    
    private String STRUTS_XML = "/org/yihang/core/xml/struts.xml";
    
    @Test
    public void test() throws Exception{
        DocumentBuilder db1 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = db1.parse(TestXmlToString.class.getResourceAsStream(STRUTS_XML));
        OutputFormat format = new OutputFormat(doc,"UTF-8",true);  
        
        DocumentFragment df = doc.createDocumentFragment();
        Node root = doc.getElementsByTagName("struts").item(0).cloneNode(false);
        NodeList list = doc.getElementsByTagName("package");
//        for(int i = 0; i < list.getLength() ; i ++) {
            root.appendChild(list.item(0).cloneNode(true));
//        }
            df.appendChild(root);
        //format.setIndenting(true);//设置是否缩进，默认为true  
        //format.setIndent(4);//设置缩进字符数  
        //format.setPreserveSpace(false);//设置是否保持原来的格式,默认为 false  
        //format.setLineWidth(500);//设置行宽度  
        StringWriter sw = new StringWriter();
        XMLSerializer serializer = new XMLSerializer(sw,format);  
//        serializer.asDOMSerializer();  
        serializer.serialize(df);  
        System.out.println(sw.toString());
    }

}
