package zpark.ext.web;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.junit.Test;

/*
 maven 依赖
 <dependency>
 <groupId>org.apache.pdfbox</groupId>
 <artifactId>pdfbox</artifactId>
 <version>1.8.7</version>
 </dependency>
 */
public class TestPdf {

    @Test
    // 异常自己处理一下吧
    public void test01() throws Exception {
        /*File file = new File("F:\\文档\\面试题\\java_web_面试题.pdf");
        FileInputStream fis = new FileInputStream(file);
        // 创建一个PDF解析器
        PDFParser parser = new PDFParser(fis);
        // 进行解析
        parser.parse();
        // 得到PDF文档对象
        PDDocument document = parser.getPDDocument();
        // 创建一个切分器
        Splitter splitter = new Splitter();
        // 从第一页开始切分, 到第一页为止
        splitter.setStartPage(1);
        splitter.setEndPage(1);
        // splitter.setSplitAtPage(1);
        List<PDDocument> list = splitter.split(document);        
        // 处理每一页切分结果
        for (int i = 0; i < list.size(); i++) {
            PDDocument doc = list.get(i);
            // 这里用输出到了文件，它也支持写到OutputStream, 要下载，就写到response的outputStream中
            doc.save("F:\\文档\\面试题\\aa" + i + ".pdf");
            doc.close();
        }
        fis.close();
        document.close();*/
    }

}
