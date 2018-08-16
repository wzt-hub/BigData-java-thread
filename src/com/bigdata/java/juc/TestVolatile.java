package com.bigdata.java.juc;

/**
 * <p>
 *   @Describe：volatile关键字：当多个线程进行操作共享数据时，可以保证内存中的数据可见
 *   				相较于synchronized 是一种较为轻量级的同步策略
 *   			注意：
 *   			  1.volatile 不具备"互斥性"
 *   			  2.不能保证变量的原子性
 *   
 * 内存可见性：
 *   	1.当程序运行时，JVM会为每一个执行任务的线程，都会分配一个独立的缓存，用于提高效率
 * </p>
 *
 * @author wzt
 * @date 2018年8月14日上午8:41:21
 */
public class TestVolatile {
	public static void main(String[] args) {
		ThreadDemo td=new ThreadDemo();
		new Thread(td).start();
		
		while(true) {
			/*synchronized(td) {  //解决方式1：使用同步锁，这样每次都会刷新缓存，但是效率极低
				if(td.isFlag()) {
					System.out.println("---------------------");
					break;
				}
			}*/
			
			if(td.isFlag()) {
				System.out.println("---------------------");
				break;
			}
		}
	}
}
class ThreadDemo implements Runnable{
	
	//解决方式2：使用volatile关键字
	private volatile boolean flag=false;
	public ThreadDemo() {}
	public ThreadDemo(boolean flag) {
		super();
		this.flag = flag;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		flag=true;
		System.out.println("flag="+flag);
	}
}