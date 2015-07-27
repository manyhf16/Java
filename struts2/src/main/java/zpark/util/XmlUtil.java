package zpark.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

@SuppressWarnings("restriction")
public class XmlUtil {
    
    public static String getXmlStringFromStrutsDefault() {
        return getXMLPart("/struts-default.xml", "struts-default");
    }
    
    private static String getXMLPart(String xmlFilename, String packageName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            // 忽略DTD验证
            db.setEntityResolver(new EntityResolver() {
                public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                    return new InputSource(new StringReader(""));
                }
            });
            Document doc = db.parse(XmlUtil.class.getResourceAsStream(xmlFilename));
            OutputFormat format = new OutputFormat(doc, "UTF-8", true);

            DocumentFragment df = doc.createDocumentFragment();
            Node root = doc.getElementsByTagName("struts").item(0).cloneNode(false);
            NodeList list = doc.getElementsByTagName("package");
            for (int i = 0; i < list.getLength(); i++) {
                Element e = (Element) list.item(i);
                if (e.getAttribute("name").equals(packageName)) {
                    root.appendChild(e.cloneNode(true));
                }
            }
            df.appendChild(root);
            // format.setIndenting(true);//设置是否缩进，默认为true
            // format.setIndent(4);//设置缩进字符数
            // format.setPreserveSpace(false);//设置是否保持原来的格式,默认为 false
            format.setLineWidth(120);// 设置行宽度
            StringWriter sw = new StringWriter(512);
            XMLSerializer serializer = new XMLSerializer(sw, format);
            // serializer.asDOMSerializer();
            serializer.serialize(df);
            return sw.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getXmlStringFromStrutsPackageName(String packageName) {
        return getXMLPart("/struts.xml", packageName);
    }

}
