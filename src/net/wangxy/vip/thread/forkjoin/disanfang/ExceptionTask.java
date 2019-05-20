package net.wangxy.vip.thread.forkjoin.disanfang;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class ExceptionTask extends RecursiveTask<Integer>{
	// 它将模拟在这个指南中，你将要处理的数据的数组。
	private int array[];
	
	private int start, end;
	
	public ExceptionTask(int array[], int start, int end) {
		this.array=array;
		this.start=start;
		this.end=end;
	}

	public static void main(String[] args) {
		// 创建一个Task对象来处理这个数组。
		int array[]=new int[100];
		ExceptionTask task=new ExceptionTask(array,0,100);
		ForkJoinPool pool=new ForkJoinPool();
		pool.execute(task);
		pool.shutdown();
		
		// 使用awaitTermination()方法等待任务的结束。如果你想要等待任务的结束，
		// 无论它花多长时间结束，将值1和TimeUnit.DAYS作为参数传给这个方法。
		try {
			pool.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 使用isCompletedAbnormally()方法，检查这个任务或它的子任务是否已经抛出异常。在这种情况下，将抛出的异常写入到控制台。使用ForkJoinTask类的getException()方法获取那个异常。
		if (task.isCompletedAbnormally()) {
			System.out.printf("Main: An exception has ocurred\n");
			System.out.printf("Main: %s\n",task.getException());
		}
		System.out.printf("Main: Result: %d",task.join());

	}

	@Override
	protected Integer compute() {
		System.out.printf(Thread.currentThread().getName() + " Task: Start from %d to %d\n",start,end);
		// 如果这个任务将要处理的，由start和end属性决定的元素块的大小小于10，
		if(end - start < 10) {
			System.out.printf(Thread.currentThread().getName() + " Task: starting form %d to %d\n",start,end);
			// 为了主动抛出异常。检查数组的第4位置（索引号3）的元素是否在那个块中。
			// 如果是这种情况，抛出一个RuntimeException异常。然后，令这个任务睡眠1秒。
//			System.out.println(Thread.currentThread().getName());
			if((3>start) && (3<end)){
				// 方式1，抛出异常
//				throw new RuntimeException("This task throws an"+ "Exception: Task from "+start+" to "+end);
				// 方式2，也可以获取与这个例子相同的结果，如果不是抛出异常，你可以使用ForkJoinTask类的completeExceptionally()方法
				Exception e=new Exception("This task throws an Exception: "+ "Task from "+start+" to "+end);
				completeExceptionally(e);
			}
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		} else {
			// 否则（这个任务将要处理的元素块的大小等于或大于10），将这个元素块分成两个部分，
			// 创建2个Task对象来处理这些块，在池中使用invokeAll()方法执行它们。
			int mid=(end+start)/2;
			ExceptionTask task1=new ExceptionTask(array,start,mid);
			ExceptionTask task2=new ExceptionTask(array,mid,end);
			invokeAll(task1, task2);
		}
		// 写入一条信息（start和end属性值）到控制台，表明任务的结束。
		System.out.printf(Thread.currentThread().getName() + " Task: End form %d to %d\n",start,end);
		return 0;
	}

}
