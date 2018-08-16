package com.bigdata.java.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *   @Describe：
 * 一. i++的原子性问题：i++操作实际上分为三个步骤
 *  int i=10;
 *  i=i++;     //运算之后 i=10
 *  
 *  i++的操作在计算机中分为三步：读 --> 改 --> 写
 *    > int temp=i;  
 *    > i=i+1;      
 *    > i=temp;     
 * 二.原子变量：jdk1.5以后 java.util.concurrent.atomic 包下提供了常用的原子变量：
 * 		1.用volatile 保证内存可见性
 * 	    2.CAS算法(Compare-And-Swap)算法保证数据的原子性
 *         CAS算法是硬件对于并发操作共享数据的支持
 *         CAS包含了三个操作数：
 *                              内存值 V：
 *                              预估值 A：
 *                              更新的值 B：
 *                              当且仅当 V==A 时，V=B，否则不做任何操作
 *               
 * </p>
 *
 * @author wzt
 * @date 2018年8月16日上午11:54:26
 */
public class TestAtomicity {
	public static void main(String[] args) {
		AtomicDemo ad=new AtomicDemo();
		for(int i=0;i<10;i++) {
			new Thread(ad).start();
		}
		
	}
}

class AtomicDemo implements Runnable{
	//private int serialNumber=0;
	
	//使用原子变量
	private AtomicInteger serialNumber=new AtomicInteger();

	@Override
	public void run() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(Thread.currentThread().getName()+":"+getSerialNumber());
	}
	
	public int getSerialNumber() {
		//return serialNumber++;
		//使用原子变量进行后++操作
		return serialNumber.getAndIncrement();
	}
}
