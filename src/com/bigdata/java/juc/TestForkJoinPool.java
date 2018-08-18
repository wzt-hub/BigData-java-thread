package com.bigdata.java.juc;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * <p>
 *   @Describe：ForkJoinPool 分支合并框架--->工作窃取
 * </p>
 *
 * @author wzt
 * @date 2018年8月18日上午9:00:51
 */
public class TestForkJoinPool {
	public static void main(String[] args) {
		ForkJoinPool pool=new ForkJoinPool();
		
		ForkJoinTask<Long> task = new  ForkJoinSumCalculate(0L,1000000L);
		Long result = pool.invoke(task);
		System.out.println(result);
	
	}
}

//以递归的形式拆成小任务
class ForkJoinSumCalculate extends RecursiveTask<Long>{
	private static final long serialVersionUID = 1L;
	
	private long start;
	private long end;
	
	private static final long THUSHOLD=10000L;  //临界值
	public ForkJoinSumCalculate(long start, long end) {
		super();
		this.start = start;
		this.end = end;
	}

	@Override
	protected Long compute() {
		long length=end-start;
		if(length<=THUSHOLD) {  //到达临界值，运算总和
			long sum=0L;
			for(long i=start;i<=end;i++) {
				sum+=i;
			}
			return sum;
		}else {  //没到临界值，拆分成小任务
			long middle=(start+end)/2;
			ForkJoinSumCalculate left=new ForkJoinSumCalculate(start, middle);
			left.fork();  //进行拆分，压入线程队列
			
			ForkJoinSumCalculate right=new ForkJoinSumCalculate(middle+1, end);
			left.fork();
			
			return left.join()+right.join();
		}
		
	}
	
}