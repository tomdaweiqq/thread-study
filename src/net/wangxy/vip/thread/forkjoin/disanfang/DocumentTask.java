package net.wangxy.vip.thread.forkjoin.disanfang;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

/**
 * 创建一个DocumentTask类，指定它继承RecursiveTask类，并参数化为Integer类型。该类将实现统计单词在一组行中出现的次数的任务。
 *  
 * 范式
 * If (problem size < size){
 *  tasks=Divide(task); 
 *  execute(tasks); 
 *  groupResults();
 * return result; 
 * } else { 
 * resolve problem; return result; 
 * }
 * 
 * @author wlg
 *
 */
public class DocumentTask extends RecursiveTask<Integer> {
	// 声明一个私有的String类型的二维数组document，
	// 两个私有的int类型的属性名为start和end，一个私有的String类型的属性名为word。
	private String document[][];
	private int start, end;
	private String word;

	private static final long serialVersionUID = 1L;

	public DocumentTask(String document[][], int start, int end, String word) {
		this.document = document;
		this.start = start;
		this.end = end;
		this.word = word;
	}

	@Override
	protected Integer compute() {
		// TODO Auto-generated method stub
		int result = 0;
		// 如果属性end和start的差小于10，那么这个任务统计单词位于行在调用processLines()方法的这些位置中出现的次数。
		if (end - start < 10) {
			result = processLines(document, start, end, word);
		} else {
			// 否则，用两个对象分解行组，创建两个新的DocumentTask对象用来处理这两个组，并且在池中使用invokeAll()方法来执行它们。
			int mid = (start + end) / 2;
			DocumentTask task1 = new DocumentTask(document, start, mid, word);
			DocumentTask task2 = new DocumentTask(document, mid, end, word);
			invokeAll(task1, task2);
			// 使用groupResults()方法将这两个任务返回的结果相加。最后，返回这个任务统计的结果。
			try {
				result = groupResults(task1.get(), task2.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 实现processLines()方法。它接收以下参数：字符串二维数组、start属性、end属性、任务将要查找的word属性
	 * a
	 * @param document
	 * @param start
	 * @param end
	 * @param word
	 * @return
	 */
	private Integer processLines(String[][] document, int start, int end, String word) {
		// .对于任务要处理的每行，创建LineTask对象来处理整行，并且将它们存储在任务数列中。
		List<LineTask> tasks = new ArrayList<LineTask>();
		for (int i = start; i < end; i++) {
			LineTask task = new LineTask(document[i], 0, document[i].length, word);
			tasks.add(task);
		}
		// 在那个数列中使用invokeAll()执行所有任务
		invokeAll(tasks);
		
		// 合计所有这些任务返回的值，并返回这个结果。
		int result = 0;
		for (int i = 0; i < tasks.size(); i++) {
			LineTask task = tasks.get(i);
			try {
				result = result + task.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		return new Integer(result);
	}

	private Integer groupResults(Integer number1, Integer number2) {
		Integer result;
		result = number1 + number2;
		return result;
	}
}
