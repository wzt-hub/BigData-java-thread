package com.bigdata.java.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 *   @Describe：生产者和消费者案例
 * </p>
 *
 * @author wzt
 * @date 2018年8月16日下午7:16:29
 */
public class TestProductorAndConsumerLock {
	public static void main(String[] args) {
		Clerk2 clerk=new Clerk2(); //店员
		Productor2 productor=new Productor2(clerk);
		Consumer2 consumer=new Consumer2(clerk);
		Thread t1=new Thread(productor,"生产者A：");
		Thread t2=new Thread(consumer,"消费者C：");
		Thread t3=new Thread(productor,"生产者B：");
		Thread t4=new Thread(consumer,"消费者D：");
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}
}

//店员
class Clerk2{  
	private int product;
	private Lock lock=new ReentrantLock();
	
	//获取为生产任务提高唤醒等待的Condition对象
	Condition conditionPro = lock.newCondition();
	
	//获取为消费任务提高唤醒等待的Condition对象
	Condition conditionCon = lock.newCondition();
	
	//进货
	public void get() {
		lock.lock();
		try {
			while(product >= 1) {
				System.out.println("产品已满!");
				conditionPro.await();
			}
			System.out.println(Thread.currentThread().getName()+":"+ ++product);
			//至少进货了1件产品，通知可以卖货了
			conditionCon.signal();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	
	//卖货
	public synchronized void sale() {
		lock.lock();
		
		//为了避免虚假唤醒的问题，应该总是使用在循环中
		try {
			while(product<=0) {
				System.out.println("缺货!");
				
				//缺货,将不再卖出商品
				conditionCon.await();
			}
			System.out.println(Thread.currentThread().getName()+":"+ --product);
			//通知进货
			conditionPro.signal();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}

	}
}

class Productor2 implements Runnable{
	private Clerk2 clerk;
	
	public Productor2(Clerk2 clerk) {
		this.clerk=clerk;
	}
	@Override
	public void run() {
		for(int i=0;i<20;i++) {
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			clerk.get();
		}
	}
}

class Consumer2 implements Runnable{
	private Clerk2 clerk;
	public Consumer2(Clerk2 clerk) {
		super();
		this.clerk = clerk;
	}
	@Override
	public void run() {
		for(int i=0;i<20;i++) {
			clerk.sale();
		}
	}
	
}
