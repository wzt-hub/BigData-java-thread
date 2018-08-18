package com.bigdata.java.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 编写一个程序，开启三个线程，这三个线程的ID分别为A,B,C,每个线程将自己的ID在屏幕上打印10遍，要求输出
 * 的结果必须按照顺序显示。如：AAAAA BBBBBBBBBB CCCCCCCCCCCCCCC AAAAA BBBBBBBBBB CCCCCCCCCCCCCCC  ...
 */
public class TestAlternate {
	public static void main(String[] args) {
		AlternateDemo ad=new AlternateDemo();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=1;i<=20;i++) {
					ad.loopA(i);
					System.out.println("----------------------------");
				}
			}
		},"A").start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=1;i<=20;i++) {
					ad.loopB(i);
					System.out.println("----------------------------");
				}
			}
		},"B").start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=1;i<=20;i++) {
					ad.loopC(i);
					System.out.println("----------------------------");
				}
			}
		},"C").start();
	}
}
class AlternateDemo{
	private int number=1;  //当前正在执行线程的标记
	
	private Lock lock=new ReentrantLock();
	private Condition conditionA=lock.newCondition();
	private Condition conditionB=lock.newCondition();
	private Condition conditionC=lock.newCondition();
	
	/**
	 * @Describe：循环打印A
	 * @author wzt	
	 * @date 2018年8月16日下午8:39:42
	 * @param totalLoop 循环第几轮
	 */
	public void loopA(int totalLoop) {
		lock.lock();
		try {
			//判断
			if(number!=1) {
				conditionA.await();
			}
			//执行打印操作
			for(int i=0;i<5;i++) {
				System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalLoop);
			}
			
			//唤醒
			number=2; 
			conditionB.signal();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	
	
	/**
	 * @Describe：循环打印B
	 * @author wzt	
	 * @date 2018年8月16日下午8:39:42
	 * @param totalLoop 循环第几轮
	 */
	public void loopB(int totalLoop) {
		lock.lock();
		try {
			
			//判断
			if(number!=2) {
				conditionB.await();
			}
			//执行打印操作
			for(int i=0;i<10;i++) {
				System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalLoop);
			}
			
			//唤醒
			number=3; 
			conditionC.signal();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	
	
	/**
	 * @Describe：循环打印C
	 * @author wzt	
	 * @date 2018年8月16日下午8:39:42
	 * @param totalLoop 循环第几轮
	 */
	public void loopC(int totalLoop) {
		lock.lock();
		try {
			
			//判断
			if(number!=3) {
				conditionC.await();
			}
			//执行打印操作
			for(int i=0;i<15;i++) {
				System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalLoop);
			}
			
			//唤醒
			number=1; 
			conditionA.signal();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
}