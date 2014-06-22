package zpark.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import zpark.entity.SampleEntity;
import zpark.service.SampleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-config.xml")
public class TestService {

	@Autowired
	private SampleService sampleService;

	@Test
	public void test() {
		List<SampleEntity> list = sampleService.findAll();
		Assert.assertEquals(8, list.size());
	}

}
