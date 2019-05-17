package net.wangxy.vip.thread.forkjoin.disanfang;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

public class LineTask extends RecursiveTask<Integer> {
	private static final long serialVersionUID = 1L;
	private String line[];
	private int start, end;
	private String word;

	public LineTask(String line[], int start, int end, String word) {
		this.line = line;
		this.start = start;
		this.end = end;
		this.word = word;
	}

	@Override
	protected Integer compute() {
		Integer result = null;
		if (end - start < 100) {
			result = count(line, start, end, word);
		} else {
			int mid = (start + end) / 2;
			LineTask task1 = new LineTask(line, start, mid, word);
			LineTask task2 = new LineTask(line, mid, end, word);
			invokeAll(task1, task2);
			try {
				result = groupResults(task1.get(), task2.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// 以下参数：完整行的字符串数组、start属性、end属性、任务将要查找的word属性。
	private Integer count(String[] line, int start, int end, String word) {
		int counter;
		counter = 0;
		// 比较这个任务将要查找的word属性中的在start和end属性之间的位置的单词，如果它们相等，则增加count变量。
		for (int i = start; i < end; i++) {
			if (line[i].equals(word)) {
				counter++;
			}
		}
		// 为了显示示例的执行，令任务睡眠10毫秒。
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return counter;
	}

	// 它合计两个数的值，并返回这个结果。
	private Integer groupResults(Integer number1, Integer number2) {
		Integer result;
		result = number1 + number2;
		return result;
	}

}
