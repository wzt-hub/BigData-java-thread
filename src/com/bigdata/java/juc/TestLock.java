package com.bigdata.java.juc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 一.用于解决多线程安全问题
 * synchronized：隐式锁
 * 1.同步代码块
 * 2.同步方法
 * 
 * jdk1.5后：
 * 3.同步锁 Lock
 * 	注意：这是一个显示锁，需要通过lock()方法上锁，必须通过unlock()方法进行释放锁
 * 
 */
public class TestLock {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ticket ticket=new ticket();
		new Thread(ticket,"1号窗口：").start();
		new Thread(ticket,"2号窗口：").start();
		new Thread(ticket,"3号窗口：").start();
		
	}
}
class ticket implements Runnable{
	private int ticket=10000;
	private Lock lock=new ReentrantLock();

	@Override
	public void run() {
		lock.lock();  //上锁
		try {
			while(ticket>0) {
				Thread.sleep(200);
				System.out.println(Thread.currentThread().getName()+" 完成售票，余票为："+--ticket);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();  //必须手动释放锁
		}
	}
	
}





