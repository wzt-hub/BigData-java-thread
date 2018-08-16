package com.bigdata.java.juc;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 *   @Describe：同步容器类：消除并发修改异常
 *   CopyOnWriteArrayList 和 CopyOnWriteArraySet："写入并复制"  
 *   
 *   注意：添加操作时，效率低，因为每次添加时都会进行一次复制操作，开销非常大
 *           高并发迭代多操作时可以选择使用，可以提高效率。
 * </p>
 *
 * @author wzt
 * @date 2018年8月16日下午2:55:13
 */
public class TestCopyOnWriteArrayList2 {
	public static void main(String[] args) {
		MyThread2 mt=new MyThread2();
		for(int i=0;i<10;i++) {
			new Thread(mt).start();  
		}
	}
}

class MyThread2 implements Runnable{
	
	private static CopyOnWriteArrayList<String> list=new CopyOnWriteArrayList<>();
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