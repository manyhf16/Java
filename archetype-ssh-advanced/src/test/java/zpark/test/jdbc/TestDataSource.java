package zpark.test.jdbc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import zpark.entity.City;

public class TestDataSource {
	
	@Test	
	public void test1() {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/application-test.xml");
		CityService service = context.getBean("cityService", CityService.class);
		
		City c = new City();
		c.setId(3);
		c.setName("山东");
		service.save(c);
	}

}
