package net.wangxy.vip.thread.forkjoin.disanfang;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Main {
	public static void main(String[] args) {
		ProductListGenerator generator=new ProductListGenerator();
		List<Product> products=generator.generate(10000);
		// last参数使用值10000（产品数列的大小）
		Task task=new Task(products, 0, products.size(), 0.20);
		ForkJoinPool pool = new ForkJoinPool();
		// execute(异步，不返回结果)
		pool.execute(task);
		// 实现一个显示关于每隔5毫秒池中的变化信息的代码块。将池中的一些参数值写入到控制台，直到任务完成它的执行。
		do {
			System.out.printf("Main: Thread Count: %d\n", pool.getActiveThreadCount());
			System.out.printf("Main: Thread Steal: %d\n", pool.getStealCount());
			System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
			try {
				TimeUnit.MILLISECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (!task.isDone());
		// 使用shutdown()方法关闭这个池。与执行者框架一样，你应该使用shutdown()方法结束ForkJoinPool。
		pool.shutdown();
		// 使用isCompletedNormally()方法检查假设任务完成时没有出错
		if (task.isCompletedNormally()){
			System.out.printf("Main: The process has completed normally.\n");
		} else {
			System.out.printf("错误啦!!!!!!!!!!!!!");
		}
		// 在增长之后，所有产品的价格应该是12。将价格不是12的所有产品的名称和价格写入到控制台，用来检查它们错误地增长它们的价格
		for (int i = 0; i < products.size(); i++) {
			Product product = products.get(i);
			if (product.getPrice() != 12) {
				System.out.printf("Product %s: %f\n", product.getName(), product.getPrice());
			}
		}

		System.out.println("Main: End of the program.\n");

	}
}
