package zpark.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestScope {
	
	@Test
	public void test1() {
		final ApplicationContext context = new ClassPathXmlApplicationContext("spring/application-config.xml");
		ScopeObject so1 = context.getBean(ScopeObject.class);
		System.out.println(so1);
		ScopeObject so2 = context.getBean(ScopeObject.class);
		System.out.println(so2);
		new Thread(){			
			@Override
			public void run() {
				ScopeObject so3 = context.getBean(ScopeObject.class);
				System.out.println(so3);
			}
		}.start();
	}
	
	@Test
	public void test2() {
		final ApplicationContext context = new ClassPathXmlApplicationContext("spring/application-config.xml");
		ScopeService s = context.getBean(ScopeService.class);
		System.out.println(s);
		s.test();
		new Thread(){			
			@Override
			public void run() {
				ScopeService s = context.getBean(ScopeService.class);
				System.out.println(s);
				s.test();
			}
		}.start();
	}

}
