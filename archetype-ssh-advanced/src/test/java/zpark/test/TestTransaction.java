package zpark.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={"classpath:spring/application-test2.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestTransaction {
	
	@Autowired
	private ServiceA a;
	
	@Test
	public void test() {
		a.a();
	}

}
