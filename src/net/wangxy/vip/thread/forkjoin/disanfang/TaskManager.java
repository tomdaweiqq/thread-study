package net.wangxy.vip.thread.forkjoin.disanfang;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;

/**
 * 使用这个类来存储在ForkJoinPool中执行的所有任务。
 * 由于ForkJoinPool和ForkJoinTask类的局限性，
 * 你将使用这个类来取消ForkJoinPool类的所有任务。
 * 
 * TaskManager类。它存储被提到池中的所有任务。它有一个方法取消它存储的所有任务。
 * 如果一个任务由于它正在运行或已经完成而不能被取消
 * ，cancel()方法返回false值，所以，你可以尝试取消所有任务，而不用担心可能有间接的影响。
 * @author wlg
 *
 */
public class TaskManager {
	// 对象参数化为ForkJoinTask类型的数列，其中ForkJoinTask类参数化为Integer类型。
	private List<ForkJoinTask<Integer>> tasks;
	
	/**
	 * 实现这个类的构造器，它初始化任务数列
	 */
	public TaskManager() {
		tasks=new ArrayList<>();
	}
	
	/**
	 * 添加ForkJoinTask对象到任务数列。
	 * @param task
	 */
	public void addTask(ForkJoinTask<Integer> task){
		tasks.add(task);
	}
	
	/**
	 * .实现cancelTasks()方法。它将使用cancel()方法取消在数列中的所有ForkJoinTask对象。
	 * 它接收一个想要取消剩余任务的ForkJoinTask对象作为参数。这个方法取消所有任务。
	 * @param cancelTask
	 */
	public void cancelTasks(ForkJoinTask<Integer> cancelTask){
		for (ForkJoinTask<Integer> task :tasks) {
			if (task!=cancelTask) {
			task.cancel(true);
			((SearchNumberTask)task).writeCancelMessage();
			}
		}
	}

}
