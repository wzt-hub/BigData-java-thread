package com.bigdata.java.thread;

public class MySynchronized {
	
	public static void main(String[] args) {
		final MySynchronized mySynchronized=new MySynchronized();
		final MySynchronized mySynchronized2=new MySynchronized();
		
		new Thread("thread-1") {
			public void run() {
				synchronized(mySynchronized) {
					try {
						System.out.println(this.getName()+" start...");
						//int i=1/0;  //如果发生异常，JVM会立即释放锁
						Thread.sleep(1000);
						System.out.println(this.getName()+" end...");

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
			
		new Thread("thread-2") {
			public void run(){
				synchronized(mySynchronized2) {
					

					System.out.println(this.getName()+" start...");
					System.out.println(this.getName()+" end...");
				}
			};
		}.start();
	}
}
