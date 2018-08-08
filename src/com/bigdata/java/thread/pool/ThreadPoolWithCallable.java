package com.bigdata.java.thread.pool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * Callable 跟Runnable的区别：
 * Runnable的run方法不会有任何返回结果，所以主线程无法获得任务线程的返回值
 * 
 * Callable的call方法可以返回结果，但是主线程在获取时是被阻塞，需要等待任务线程返回才能拿到结果
 * @author
 *
 */
public class ThreadPoolWithCallable {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		/**
		 * Fixed Thread Pool : 拥有固定线程数的线程池，如果没有任务执行，那么线程会一直等待
		 * 代码：Executors.newFixedThreadPool(4)
		 * 在构造函数中的参数4是线程池的大小，你可以随意设置，也可以和cpu的核数量保持一致，
		 * 获取cpu的核数量int cpuNums = Runtime.getRuntime().availableProcessors();
		 */
		ExecutorService pool = Executors.newFixedThreadPool(4); 
		
		for(int i = 0; i < 10; i++){
			Future<String> submit = pool.submit(new Callable<String>(){
				@Override
				public String call() throws Exception {
					//System.out.println("a");
					Thread.sleep(1000);
					return "b--"+Thread.currentThread().getName();
				}			   
			   });
			//从Future中get结果，这个方法是会被阻塞的，一直要等到线程任务返回结果
			System.out.println(submit.get());
		} 
			pool.shutdown();

	}

}
