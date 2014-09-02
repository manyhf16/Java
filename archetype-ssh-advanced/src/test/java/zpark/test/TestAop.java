package zpark.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import zpark.service.SampleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-test.xml")
public class TestAop {
	
	@Resource(name="service")
	private SampleService service;
	
	@Test
	public void test1() {
		service.findAll();
	}

}
