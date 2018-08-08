package com.bigdata.java.thread.lock;

public class Test {
	
	public static void main(String[] args) {
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		//获取CPU的核数
		System.out.println(availableProcessors);
	
	}
}
