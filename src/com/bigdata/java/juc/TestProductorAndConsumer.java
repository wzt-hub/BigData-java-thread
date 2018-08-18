package com.bigdata.java.juc;

/**
 * <p>
 *   @Describe：生产者和消费者案例
 * </p>
 *
 * @author wzt
 * @date 2018年8月16日下午7:16:29
 */
public class TestProductorAndConsumer {
	
	public static void main(String[] args) {
		Clerk clerk=new Clerk(); //店员
		
		Productor productor=new Productor(clerk);
		Consumer consumer=new Consumer(clerk);
		
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
class Clerk{  
	private int product;
	
	//进货
	public synchronized void get() {
		while(product >= 1) {
			System.out.println("产品已满!");
			
			//产品已满，则不在进货
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName()+":"+ ++product);
		
		//至少进货了1件产品，通知可以卖货了
		this.notifyAll();
	}
	
	//卖货
	public synchronized void sale() {
		
		//为了避免虚假唤醒的问题，应该总是使用在循环中
		while(product<=0) {
			System.out.println("缺货!");
			
			//缺货,将不再卖出商品
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(Thread.currentThread().getName()+":"+ --product);
		
		//通知进货
		this.notifyAll();
	
	}
	
}

class Productor implements Runnable{
	private Clerk clerk;
	
	public Productor(Clerk clerk) {
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

class Consumer implements Runnable{
	private Clerk clerk;
	

	public Consumer(com.bigdata.java.juc.Clerk clerk) {
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
