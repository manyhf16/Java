package org.yihang.core.lock;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue {
	
	private Lock lock;
	
	private Condition noEmpty;
	
	private Condition noFull;
	
	private LinkedList<Object> list;
	
	private int max;
	
	public BlockingQueue(int max) {
		lock = new ReentrantLock();
		noEmpty = lock.newCondition();
		noFull = lock.newCondition();
		list = new LinkedList<Object>();
		this.max = max;
	}
	
	public void put(Object object) throws InterruptedException {
		lock.lock();
		try {
			while(list.size() == max) {
				noFull.await();
			}
			list.addLast(object);
			noEmpty.signal();
		} finally {
			lock.unlock();
		}
	}
	
	public Object take() throws InterruptedException {		
		lock.lock();
		Object result;
		try {
			while(list.size() == 0) {
				noEmpty.await();
			}
			result = list.removeFirst();
			noFull.signal();
		} finally {
			lock.unlock();
		}
		return result;
	}

}
