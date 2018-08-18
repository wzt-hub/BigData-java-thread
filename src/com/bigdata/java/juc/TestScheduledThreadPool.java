package com.bigdata.java.juc;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *   @Describe：public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize)：创建固定大小的线城池，可以延迟或者定时去执行任务
 * </p>
 *
 * @author wzt
 * @date 2018年8月18日上午8:42:33
 */
public class TestScheduledThreadPool {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ScheduledExecutorService pool=Executors.newScheduledThreadPool(5);
		
		for(int i=0;i<6;i++) {
			ScheduledFuture<Integer> result = pool.schedule(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					int sum=new Random().nextInt(100);
					System.out.println(Thread.currentThread().getName()+":"+sum);
					return sum;
				}
			}, 1, TimeUnit.SECONDS);  //延迟3 秒
			
			System.out.println(result.get());
		}
		
		//关闭线城池
		pool.shutdown();
	}
}
