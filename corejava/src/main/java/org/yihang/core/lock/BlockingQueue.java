package org.yihang.core.lock;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue {
	
	private Lock lock;
	
	private Condition take;
	
	private Condition put;
	
	private LinkedList<Object> list;
	
	private int max;
	
	public BlockingQueue(int max) {
		lock = new ReentrantLock();
		take = lock.newCondition();
		put = lock.newCondition();
		list = new LinkedList<Object>();
		this.max = max;
	}
	
	public void put(Object object) throws InterruptedException {
		lock.lock();
		try {
			while(list.size() == max) {
				put.await();
			}
			list.addLast(object);
			take.signal();
		} finally {
			lock.unlock();
		}
	}
	
	public Object take() throws InterruptedException {		
		lock.lock();
		Object result;
		try {
			while(list.size() == 0) {
				take.await();
			}
			result = list.removeFirst();
			put.signal();
		} finally {
			lock.unlock();
		}
		return result;
	}

}
