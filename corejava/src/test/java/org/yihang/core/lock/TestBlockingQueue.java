package org.yihang.core.lock;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestBlockingQueue {

	public static void main(String[] args) {

		final BlockingQueue queue = new BlockingQueue(5);
		ExecutorService service = Executors.newFixedThreadPool(10);
		// Producter 生产者
		service.execute(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					try {
						Object object = new Object();
						queue.put(object);
						System.out.printf("Thread %s put product %s at %tT%n", "生产者", object.toString(), new Date());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		// Consumer 消费者
		for (int j = 0; j < 10; j++) {
			final int x = j;
			service.execute(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.printf("消费者%d waiting for product...%n", x);
						Object object = queue.take();
						System.out.printf("消费者 %d take product %s at %tT%n", x, object.toString(), new Date());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}

	}

}
