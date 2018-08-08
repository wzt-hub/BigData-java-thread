package com.bigdata.java.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 *   @Describe：lockInterruptibly()方法比较特殊，当通过这个方法去获取锁时，
 *   如果线程正在等待获取锁，则这个线程能够响应中断，即中断线程的等待状态。
 *   也就使说，当两个线程同时通过lock.lockInterruptibly()想获取某个锁时，
 *   假若此时线程A获取到了锁，而线程B只有等待，那么对线程B调用threadB.interrupt()方法能够中断线程B的等待过程。
 *   
 *   注意，当一个线程获取了锁之后，是不会被interrupt()方法中断的。
 *   因此当通过lockInterruptibly()方法获取某个锁时，如果不能获取到，只有进行等待的情况下，是可以响应中断的。
 *　    用synchronized修饰的话，当一个线程处于等待某个锁的状态，是无法被中断的，只有一直等待下去。
 * </p>
 *
 * @author wzt
 * @date 2018年8月4日下午2:55:48
 */
public class MyInterruptibly {
	private Lock lock=new ReentrantLock();
	
	public static void main(String[] args) {
		MyInterruptibly test=new MyInterruptibly();
		MyThread thread0=new MyThread(test);
		MyThread thread1=new MyThread(test);
		thread0.start();
		thread1.start();
		
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		thread1.interrupt();
		System.out.println("============================");
	}
	
	public void insert(Thread thread) throws InterruptedException {
		lock.lockInterruptibly();  
		
		try {
			System.out.println(thread.getName()+"得到了锁");
			long startTime=System.currentTimeMillis();
			for(;;) {
				if(System.currentTimeMillis()-startTime >= Integer.MAX_VALUE) {
					break;
					//插入数据
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			System.out.println(Thread.currentThread().getName()+"执行到finally块");
			lock.unlock(); //释放锁
			System.out.println(thread.getName()+"释放了锁");
		}
	}
}
class MyThread extends Thread{
	private MyInterruptibly test=null;

	public MyThread(MyInterruptibly test) {
		super();
		this.test = test;
	}
	
	@Override
	public void run() {
		try {
			test.insert(Thread.currentThread());
		} catch (InterruptedException e) {
			System.out.println(Thread.currentThread().getName()+" 被中断");
			e.printStackTrace();
		}
	}
	
}






