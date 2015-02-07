package zpark.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import net.sf.ehcache.CacheManager;

import org.slf4j.Logger;

import zpark.ext.log.AsyncContexts;
import zpark.ext.log.WebLogAppender;

@WebListener
public class BackgroundListener implements ServletContextListener {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(BackgroundListener.class);
	private static volatile boolean done = false;
	private static Runnable task = new Runnable() {

		@Override
		public void run() {
			while (!done) {
				logger.trace("测试..., 当前AsyncContexts大小为：" + AsyncContexts.size());
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace(); 
				}
			}
			System.out.println("thread:" + Thread.currentThread().getName() + " is stopped.");
		}
	}; 
	private Thread backgroundThread;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		done = false;
		backgroundThread = new Thread(task);
		backgroundThread.start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		done = true;
		try {
			backgroundThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		CacheManager.getInstance().shutdown();
		WebLogAppender.shutdown();
	}

}
