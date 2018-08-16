package com.bigdata.java.juc;

import java.util.*;

/**
 * <p>
 *   @Describe：同步容器类
 *   CopyOnWriteArrayList 和 CopyOnWriteArraySet："写入并复制"
 *   
 * </p>
 *
 * @author wzt
 * @date 2018年8月16日下午2:55:13
 */
public class TestCopyOnWriteArrayList {
	public static void main(String[] args) {
		MyThread mt=new MyThread();
		for(int i=0;i<10;i++) {
			new Thread(mt).start();    //ConcurrentModificationException  发生并发修改异常
		}
	}
}

class MyThread implements Runnable{
	
	//使用集合工具提供的线程安全方法
	private static List<String> list=Collections.synchronizedList(new ArrayList<>());
	
	static {
		list.add("AA");
		list.add("BB");
		list.add("CC");
		
	}
	public void run() {
		Iterator<String> iterator=list.iterator();
		while(iterator.hasNext()) {
			System.out.println(iterator.next()); //边读
			
			list.add("添加");
			
		}
	}
}