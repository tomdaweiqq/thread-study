package net.wangxy.vip.thread.forkjoin.disanfang;

import java.util.List;
import java.util.concurrent.RecursiveAction;

import net.wangxy.vip.thread.forkjoin.sum.MakeArray;

/**
 * 你将使用Java API文档推荐的结构：
 * 你将以一种同步方式执行任务,当一个任务执行2个或2个以上的子任务时，它将等待它们的结束。
 * 通过这种方式 ，正在执行这些任务的线程（工作线程）将会查找其他任务（尚未执行的任务）来执行，充分利用它们的执行时间
 * 
 * If (problem size < default size){
		tasks=divide(task);
		execute(tasks);
	} else {
		resolve problem using another algorithm;
   }
 * @author wlg
 *
 */
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
	
	//实现这个类的构造器，初始化所有属性
	public Task(List<Product> products, int first, int last, double increment) {
		this.products = products;
		this.first = first;
		this.last = last;
		this.increment = increment;
	}

	
	/**
	 * 
	 */
	@Override
	protected void compute() {
		// 如果last和first的差小于10（任务只能更新价格小于10的产品），使用updatePrices()方法递增的设置产品的价格。
		if (last - first < 10) {
			updatePrices();
		} else { // 如果last和first的差大于或等于10，则创建两个新的Task对象，一个处理产品的前半部分，另一个处理产品的后半部分，
				 // 然后在ForkJoinPool中，使用invokeAll()方法执行它们。
			int middle = (last + first) / 2;
			System.out.printf("Task: Pending tasks: %s\n", getQueuedTaskCount());
			
			Task t1 = new Task(products, first, middle + 1, increment);
			Task t2 = new Task(products, middle + 1, last, increment);
			// invokeAll()同步方法，执行每个任务所创建的子任务。这是一个同步调用，这个任务在继续（可能完成）它的执行之前，必须等待子任务的结束
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
