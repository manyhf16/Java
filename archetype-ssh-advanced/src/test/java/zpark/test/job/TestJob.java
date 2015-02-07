package zpark.test.job;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestJob {
	
	public static void main(String[] args) throws SchedulerException {
		ApplicationContext spring = new ClassPathXmlApplicationContext("spring/application-test3.xml");
		Scheduler scheduler = spring.getBean(Scheduler.class);
//		scheduler.start();
	}

}
