package net.wangxy.vip.thread.forkjoin.disanfang;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class SearchNumberTask extends RecursiveTask<Integer>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numbers[];
	private int start, end;
	// 存储你将要查找的数。
	private int number;
	// 使用这个对象来取消所有任务。
	private TaskManager manager;
	// 当任务没有找到这个数时，它将作为任务的返回值。
	private final static int NOT_FOUND=-1;

	@Override
	protected Integer compute() {
		System.out.println("Task: " + start + ":" + end);
		int ret;
		if (end - start > 10) {
			ret = launchTasks();
		} else {
			ret = lookForNumber();
		}

		return ret;
	}
	
	/**
	 * 對于任务要处理的元素块中的所有元素，将你想要查找的数与存储在元素中的值进行比较。
	 * 如果他们相等，写入一条信息到控制台表明这种情形，使用TaskManager对象的cancelTasks()
	 * 方法来取消所有任务，并返回你已经找到的这个数对应元素的位置。
	 * @return
	 */
	private int lookForNumber() {
		for (int i=start; i<end; i++){
			if (numbers[i]==number) {
				System.out.printf("Task: Number %d found in position %d\n",number,i);
				manager.cancelTasks(this);
				return i;
			}
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return NOT_FOUND;
	}
	
	/**
	 * 首先，将这个任务要处理的数块分成两个部分，然后，创建两个Task对象来处理它们。
	 * @return
	 */
	private int launchTasks() {
		int mid = (start + end) / 2;
		SearchNumberTask task1 = new SearchNumberTask(numbers, start, mid, number, manager);
		SearchNumberTask task2 = new SearchNumberTask(numbers, mid, end, number, manager);
		manager.addTask(task1);
		manager.addTask(task2);
		

		// 使用fork()方法异步执行这两个任务。
		task1.fork();
		task2.fork();
		// 等待这个任务的结束，返回第一个任务的结果（如果它不等于-1），或第二个任务的结果。
		int returnValue;
		
		returnValue = task1.join();
		if (returnValue != -1) {
			return returnValue;
		}
		returnValue = task2.join();
		return returnValue;

	}
	public void writeCancelMessage(){
		System.out.printf("Task: Canceled task from %d to %d \n",start,end);
	}

	public SearchNumberTask(int numbers[], int start, int end, int number,
				TaskManager manager){
		this.numbers=numbers;
		this.start=start;
		this.end=end;
		this.number=number;
		this.manager=manager;
	}
}
