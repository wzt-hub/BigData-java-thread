package com.bigdata.java.volatiletest;

public class TestVolatile {
	private static volatile int number=0;
	
	public static void main(String[] args) {
		for(int i=0;i<100;i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					for(int i=0;i<1000;i++) {
						number++;
					}
					
				}
			}).start();
		}
		
		try {
			Thread.sleep(5000);
			System.out.println(number);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
