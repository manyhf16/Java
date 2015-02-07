package zpark.test.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class MyJob extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		Object value = context.getJobDetail().getJobDataMap().get("aaa");
		System.out.println("ok..." + value);
	}

}
