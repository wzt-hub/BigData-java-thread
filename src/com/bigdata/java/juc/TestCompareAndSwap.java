package com.bigdata.java.juc;

/**
 * <p>
 *   @Describe：模拟CAS算法
 * </p>
 *
 * @author wzt
 * @date 2018年8月16日下午2:49:58
 */
public class TestCompareAndSwap {
	public static void main(String[] args) {
		CompareAndSwap cas=new CompareAndSwap();
		
		for(int i=0;i<10;i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					int expectedValue = cas.get();
					boolean b=cas.compareAndSet(expectedValue, (int)(Math.random()*101));
					System.out.println(b);
				}
			}).start();
		}
	}
}

class CompareAndSwap{
	private int value;  //内存值
	
	//获取内存值
	public synchronized int get() {
		return value;
	}
	
	//比较
	public synchronized int compareAndSwap(int expectedValue,int newValue) {
		int oldValue=value;
		if(expectedValue==oldValue) {  //内存值和预估值相同
			//替换
			this.value=newValue;
		}
		return oldValue;
	}
	
	//设置
	public synchronized boolean compareAndSet(int expectedValue,int newValue) {
		return expectedValue==compareAndSwap(expectedValue, newValue);
	}
}
