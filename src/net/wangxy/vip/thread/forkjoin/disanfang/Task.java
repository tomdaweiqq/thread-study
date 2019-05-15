package net.wangxy.vip.thread.forkjoin.disanfang;

import java.util.List;
import java.util.concurrent.RecursiveAction;

import net.wangxy.vip.thread.forkjoin.sum.MakeArray;

public class Task extends RecursiveAction {
	private static final long serialVersionUID = 1L;
	
	// 问题分解的最小阈值
	private final static int THRESHOLD = 10;
	
	private List<Product> products;
	// 声明两个私有的、int类型的属性first和last。这些属性将决定这个任务产品的阻塞过程。
	private int first;
	private int last;
	// 存储产品价格的增长
	private double increment;
	
	
	public Task(List<Product> products, int first, int last, double increment) {
		this.products = products;
		this.first = first;
		this.last = last;
		this.increment = increment;
	}

	@Override
	protected void compute() {
		if (last - first < 10) {
			updatePrices();
		} else {
			int middle = (last + first) / 2;
			System.out.printf("Task: Pending tasks: %s\n", getQueuedTaskCount());
			
			Task t1 = new Task(products, first, middle + 1, increment);
			Task t2 = new Task(products, middle + 1, last, increment);
			invokeAll(t1, t2);
		}
	}
	

	/**
	 * 更新产品队列中位于first值和last值之间的产品
	 */
	private void updatePrices() {
		for (int i = first; i < last; i++) {
			Product product = products.get(i);
			product.setPrice(product.getPrice() * (1 + increment));
		}
	}
}
