package com.bigdata.java.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * <p>
 *   @Describe：一.创建执行线程的方式3：实现Callable接口
 *        相较于实现Runnable 接口的方式，方法可以有返回值，并且可以抛出异常
 *    
 *    二.执行Callable方式，需要FutureTask实现类的支持，用于接收运算结果
 *    		FutureTask 是实现Future接口的实现类
 * </p>
 *
 * @author wzt
 * @date 2018年8月16日上午11:28:05
 */
public class TestCallable {
	public static void main(String[] args){
		ThreadDemo1 threadDemo1=new ThreadDemo1();
		FutureTask<Integer> result=new FutureTask<>(threadDemo1);
		new Thread(result).start();
		
		//接收运算结果
		
		try {
			Integer res = result.get();  //FutureTask 可用于闭锁
			System.out.println(res);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}


class ThreadDemo1 implements Callable<Integer>{
	int sum=0;
	@Override
	public Integer call() throws Exception {
		for(int i=0;i<=100;i++) {
			System.out.println(Thread.currentThread().getName()+":"+i);
			sum+=i;
		}
		return sum;
	}
	
}

//原来的方式创建线程
/*class ThreadDemo1 implements Runnable{
	@Override
	public void run() {
		
	}
}*/


