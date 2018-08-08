package com.bigdata.java.thread.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyLockTest {
	
	private static ArrayList<Integer> arrayList=new ArrayList<>();
	static Lock lock=new ReentrantLock();
	
	public static <E> void main(String [] args) {
		new Thread() {
			@Override
			public void run() {
				Thread thread = Thread.currentThread();
				/**
				 * lock()方法是平常使用得最多的一个方法，就是用来获取锁。
				 * 如果锁已被其他线程获取，则进行等待
				 */
				lock.lock();
				
				try {
					System.out.println(thread.getName()+"得到了锁...");
					for(int i=0;i<5;i++) {
						arrayList.add(i);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					/**
					 * 必须主动去释放锁，并且在发生异常时，不会自动释放锁。
					 * 因此一般来说，使用Lock必须在try{}catch{}块中进行，
					 * 并且将释放锁的操作放在finally块中进行，以保证锁一定被被释放，防止死锁的发生。
					 */
					lock.unlock();
					System.out.println(thread.getName()+"释放了锁...");
				}
			}
		}.start();
		
		
		new Thread() {
			@Override
			public void run() {
				Thread thread = Thread.currentThread();
				
				lock.lock();
				
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
		}.start();;
	}
}
