package com.bigdata.java.juc;

import java.util.concurrent.CountDownLatch;

/**
 * <p>
 *   @Describe：CountDownLatch：闭锁，在完成某些运算时，只有其他所有线程的运算全部都完成以后，那么当前
 *   				线程运算才会继续执行
 *   	
 * </p>
 *
 * @author wzt
 * @date 2018年8月16日下午3:22:35
 */
public class TestCountDownLatch {
	public static void main(String[] args) {
		CountDownLatch latch=new CountDownLatch(5);
		LatchDemo latchDemo=new LatchDemo(latch);
		
		long start = System.currentTimeMillis();
		
		for(int i=0;i<5;i++) {
			new Thread(latchDemo).start();
		}
		
		try {
			
			//子线程未执行完，让主线程一直处于等待状态
			latch.await();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		
		System.out.println("耗时时间为："+(end-start));
	}
}

class LatchDemo implements Runnable{
	
	private CountDownLatch latch;
	public LatchDemo(CountDownLatch latch) {
		this.latch=latch;
	}
	
	@Override
	public void run() {
		synchronized(this) {
			try {
				for(int i=0;i<50000;i++) {
					if(i%2==0) {
						System.out.println(Thread.currentThread().getName()+":"+i);
					}
				}
			} finally {
				latch.countDown(); //数量减1
			}
		}
		
	}
	
}