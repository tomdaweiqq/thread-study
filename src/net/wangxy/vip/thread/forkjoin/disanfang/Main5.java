package net.wangxy.vip.thread.forkjoin.disanfang;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * ForkJoinTask提供cancel()方法，允许你取消一个还未执行的任务。这是一个非常重要的点。
 * 如果任务已经开始它的执行，那么调用cancel()方法对它没有影响。这个方法接收一个Boolean值，
 * 名为mayInterruptIfRunning的参数。这个名字可能让你觉得，如果你传入一个true值给这个方法，
 * 这个任务将被取消，即使它正在运行。
 * @author wlg
 *
 */
public class Main5 {
	public static void main(String[] args) {
		// 使用ArrayGenerator类，创建一个有1000个数字的数组。
		ArrayGenerator generator=new ArrayGenerator();
		int array[]=generator.generateArray(1000);
		
		TaskManager manager=new TaskManager();
		ForkJoinPool pool = new ForkJoinPool();
		
		// 任务
		SearchNumberTask task=new SearchNumberTask(array,0,1000,5,manager);
		// 在池中异步执行任务。
		pool.execute(task);
		pool.shutdown();
		try {
			// ForkJoinPool类的awaitTermination()方法，等待任务的结束。
			pool.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.printf("Main: The program has finished\n");

	}
}
