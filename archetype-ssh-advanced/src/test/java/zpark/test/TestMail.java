package zpark.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-mail.xml")
public class TestMail {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Test
	public void test1() {
		SimpleMailMessage h = new SimpleMailMessage();
		h.setTo("zhangsan@localhost.com");
		h.setFrom("postmaster@localhost.com");
		h.setSubject("测试简单邮件");
		h.setText("邮件内容");
		mailSender.send(h );
	}
	
	@Test
	public void test2() throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper h = new MimeMessageHelper(mimeMessage);
		h.setTo("zhangsan@localhost.com");
		h.setFrom("postmaster@localhost.com");
		h.setSubject("测试html邮件");
		h.setText("<html><head></head><body><h1>邮件内容</h1></body></html>", true);
		mailSender.send(mimeMessage );
	}
	
	@Autowired
	FreeMarkerConfigurer c;
	
	@Test
	public void test3() throws IOException, TemplateException {
		Template t = c.getConfiguration().getTemplate("mail.ftl");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("content", "邮件内容");
		map.put("now", new Date());
		map.put("price", 13728.3);
		t.process(map, new PrintWriter(System.out));
	}

}
