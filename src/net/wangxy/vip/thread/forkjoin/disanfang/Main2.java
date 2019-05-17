package net.wangxy.vip.thread.forkjoin.disanfang;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Main2 {
	public static void main(String[] args) {
		// 使用DocumentMock类，创建一个带有100行，每行1000个单词的Document。
		DocumentMock mock = new DocumentMock();
		String[][] document = mock.generateDocument(100, 1000, "the");
		// 创建一个新的DocumentTask对象，用来更新整个文档的产品。参数start值为0，参数end值为100。
		DocumentTask task = new DocumentTask(document, 0, 100, "the");
		
		ForkJoinPool pool = new ForkJoinPool();
		
		pool.execute(task);
		
		// 	打印单词在文档中出现的次数。检查这个数是否与DocumentMock类中写入的数一样。
		do {
			System.out.printf("******************************************\n");
			System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
			System.out.printf("Main: Active Threads: %d\n", pool.getActiveThreadCount());
			System.out.printf("Main: Task Count: %d\n", pool.getQueuedTaskCount());
			System.out.printf("Main: Steal Count: %d\n", pool.getStealCount());
			System.out.printf("******************************************\n");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (!task.isDone());
		
		pool.shutdown();
		// 使用awaitTermination()方法等待任务的结束。
		try {
			System.out.printf("Main: The word appears %d in the document", task.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		// 打印单词在文档中出现的次数。检查这个数是否与DocumentMock类中写入的数一样。
		try {
			System.out.printf("Main: The word appears %d in the document", task.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

	}
}
