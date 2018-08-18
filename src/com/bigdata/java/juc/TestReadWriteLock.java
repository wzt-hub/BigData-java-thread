package com.bigdata.java.juc;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock：读写锁
 * 
 * 写写/读写 需要互斥
 * 读读  不需要互斥
 */
public class TestReadWriteLock {
	public static void main(String[] args) {
		ReadWriteLockDemo rwd=new ReadWriteLockDemo();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				rwd.set((int)(Math.random()*101));
			}
		},"write:").start();
		
		for(int i=0;i<100;i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					rwd.get();
				}
			}).start();
		}
		
		
	}
}
class ReadWriteLockDemo{
	
	private int number=0;
	private ReadWriteLock lock=new ReentrantReadWriteLock();
	
	//读
	public void get() {
		lock.readLock().lock();  //上锁
		
		try {
			System.out.println(Thread.currentThread().getName()+":"+number);
		} finally {
			lock.readLock().unlock(); //解锁
		}
	}
	
	//写
	public void set(int number) {
		lock.writeLock().lock();
		try {
			this.number=number;
		}finally {
			lock.writeLock().unlock();
		}
		
	}
	
}