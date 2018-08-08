package com.bigdata.java.thread;

public class MyThreadWithExtends extends Thread{
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+"线程的run方法被调用...");
	}
	
	public static void main(String[] args) {
		Thread t=new MyThreadWithExtends();
		/**
		 * 如果是调用Thread的run方法，则只是一个普通的方法调用，不会开启新的线程
		 */
		t.start();                               // Thread-0线程的run方法被调用... 
		t.run();     // main线程的run方法被调用...
	}
}
