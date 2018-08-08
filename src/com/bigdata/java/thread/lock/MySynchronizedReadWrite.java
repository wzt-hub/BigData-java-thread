package com.bigdata.java.thread.lock;

/**
 * <p>
 *   @Describe：一个线程既要读操作，又要写操作，用synchronized 来实现的话，读写操作都只能锁住后，
 *   	一个线程一个线程的进行
 * </p>
 *
 * @author wzt
 * @date 2018年8月4日下午3:40:55
 */
public class MySynchronizedReadWrite {
	public static void main(String[] args) {
		final MySynchronizedReadWrite test=new MySynchronizedReadWrite();
		
		new Thread() {
			@Override
			public void run() {
				test.get(Thread.currentThread());
			}
		}.start();
		
		new Thread() {
			@Override
			public void run() {
				test.get(Thread.currentThread());
			}
		}.start();
	}

	public synchronized void get(Thread thread) {
		long start=System.currentTimeMillis();
		int i=0;
		while(System.currentTimeMillis()-start <= 1) {
			i++;
			if(i%4==0) {
				System.out.println(thread.getName()+"正在进行写操作");
			}else {
				System.out.println(thread.getName()+"正在进行读操作");
			}
		}
		System.out.println(thread.getName()+"读写操作完毕");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
