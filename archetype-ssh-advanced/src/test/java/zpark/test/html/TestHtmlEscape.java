package zpark.test.html;

import org.junit.Test;
import org.springframework.web.util.HtmlUtils;

public class TestHtmlEscape {
	
	@Test
	public void test() {
		System.out.println(HtmlUtils.htmlEscape("<html test=\"aa\">ok</html>"));
	}

}
