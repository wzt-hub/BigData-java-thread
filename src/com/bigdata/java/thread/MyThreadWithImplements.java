package com.bigdata.java.thread;

public class MyThreadWithImplements implements Runnable{
	int x;
	public MyThreadWithImplements(int x) {
		this.x=x;
	}

	@Override
	public void run() {
		String name=Thread.currentThread().getName();
		System.out.println("线程："+name+"的run方法被调用...");
		for(int i=0;i<10;i++) {
			System.out.println(x);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
	}
	
	public static void main(String[] args) {
		Thread t1=new Thread(new MyThreadWithImplements(1),"thread-1");
		Thread t2=new Thread(new MyThreadWithImplements(2),"thread-2");
		t1.start();
		t2.start();
	}
	
}
