package zpark.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import zpark.action.SampleAction;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-config.xml")
public class TestAction {

	@Autowired
	private SampleAction action;
	
	@Test
	public void test1() {
		String view = action.execute();
		Assert.assertEquals("input", view);
	}
	
	@Test
	public void test2() {
		action.setName("not null");
		String view = action.execute();
		Assert.assertEquals("success", view);
	}
	
}
