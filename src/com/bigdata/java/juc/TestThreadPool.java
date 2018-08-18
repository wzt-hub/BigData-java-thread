package com.bigdata.java.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <p>
 *   @Describe：线程池
 *   一.线程池：提供了一个线程队列，队列中保存着所有等待状态的线程，避免了创建与销毁额外的开销，提高了响应的速度
 *   二.线程池的体系结构：
 *   	java.util.concurrent.Executor：负责线程的使用与调用的根接口
 *   		| --ExecutorService   子接口：线程池的主要接口
 *                | -- ThreadPoolExecutor  线程池的实现类
 *                      |-- ScheduleExecutorService 子接口：负责线程的调度
 *                      	 |-- ScheduleThreadPoolExecutor ：继承ThreadPoolExecutor，实现ScheduleExecutorService
 *   三.工具类：Executors
 *       public static ExecutorService newFixedThreadPool(int nThreads)：创建固定大小的线程池                  
 * 		 public static ExecutorService newCachedThreadPool(ThreadFactory threadFactory)：缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量
 * 		 public static ExecutorService newSingleThreadExecutor()：创建单个线程池。线城池中只有一个线程
 * 		 public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize)：创建固定大小的线城池，可以延迟或者定时去执行任务
 * 
 * 
 * </p>
 *
 * @author wzt
 * @date 2018年8月17日下午1:41:23
 */
public class TestThreadPool {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//1.使用Runnable方式创建线城池
		ExecutorService pool=Executors.newFixedThreadPool(5);
		/*ThreadPoolDemo tpd=new ThreadPoolDemo();
		
		//2.为线程池中的线程分配任务
		for(int i=0;i<10;i++) { //分配10个任务
			pool.submit(tpd);
		}*/
		
		
		//2.使用Callable 方式创建线城池
		List<Future<Integer>> list=new ArrayList<>();
		
		for(int i=0;i<10;i++) {
			Future<Integer> future=pool.submit(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					int sum=0;
					for(int i=0;i<=100;i++) {
						sum+=i;
					}
					return sum;
				}
			});
			
			list.add(future);
		}
		
		for(Future<Integer> future:list) {
			System.out.println(future.get());
		}
	
		//3.关闭线城池
		pool.shutdown();
		
	}
}

class ThreadPoolDemo implements Runnable{
	private int i=0;

	@Override
	public void run() {
		while(i<=100) {
			System.out.println(Thread.currentThread().getName()+":"+ i++);
		}
	}
}
