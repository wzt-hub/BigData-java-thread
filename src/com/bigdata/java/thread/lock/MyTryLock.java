package com.bigdata.java.thread.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 *   @Describe：观察现象：一个线程获得锁后，另一个线程获取不到锁，不会一直等待,
 * </p>
 *
 * @author wzt
 * @date 2018年8月4日下午2:40:12
 */
public class MyTryLock {
	private static ArrayList<Integer> arrayList=new ArrayList<>();
	static Lock lock=new ReentrantLock();
	
	public static <E> void main(String [] args) {
		new Thread() {
			@Override
			public void run() {
				Thread thread = Thread.currentThread();
				/**
				 * tryLock()方法是有返回值的，它表示用来尝试获取锁，如果获取成功，则返回true，
				 * 如果获取失败（即锁已被其他线程获取），则返回false，也就说这个方法无论如何都会立即返回。
				 * 在拿不到锁时不会一直在那等待。
				 */
				boolean tryLock = lock.tryLock();
				System.out.println(thread.getName()+" "+tryLock);
				
				if(tryLock) {
					try {
						System.out.println(thread.getName()+"得到了锁...");
						for(int i=0;i<5;i++) {
							arrayList.add(i);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						lock.unlock();
						System.out.println(thread.getName()+"释放了锁...");
					}
				}
			}
		}.start();
		
		
		new Thread() {
			@Override
			public void run() {
				Thread thread = Thread.currentThread();
				
				boolean tryLock = lock.tryLock();
				System.out.println(thread.getName()+" "+tryLock);
				
				if(tryLock) {
					try {
						System.out.println(thread.getName()+"得到了锁...");
						for(int i=0;i<5;i++) {
							arrayList.add(i);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						lock.unlock();
						System.out.println(thread.getName()+"释放了锁...");
					}
				}
				
			}
		}.start();;
	}
}
