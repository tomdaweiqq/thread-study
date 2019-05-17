package net.wangxy.vip.thread.forkjoin.disanfang;

import java.util.Random;

/**
 * 创建一个Document类，它将产生用来模拟文档的字符串的二维数组。
 * 
 * @author wlg
 *
 */
public class DocumentMock {
	// 创建一个带有一些单词的字符串数组。这个数组将被用来生成字符串二维数组。
	private String words[] = { "the", "hello", "goodbye", "packt", "java", "thread", "pool", "random", "class",
			"main" };
	/**
	 * 实现generateDocument()方法。它接收以下参数：行数、每行的单词数。这个例子返回一个字符串二维数组，来表示将要查找的单词。
	 * @param numLines
	 * @param numWords
	 * @param word
	 * @return
	 */
	public String[][] generateDocument(int numLines, int numWords, String word) {
		// 创建生成这个文档必需的对象：字符串二维对象和生成随机数的Random对象。
		int counter = 0;
		String document[][] = new String[numLines][numWords];
		Random random = new Random();
		for (int i = 0; i < numLines; i++) {
			for (int j = 0; j < numWords; j++) {
				int index = random.nextInt(words.length);
				document[i][j] = words[index];
				if (document[i][j].equals(word)) {
					counter++;
				}
			}
		}
		// 将单词出现的次数写入控制台，并返回生成的二维数组。
		System.out.println("将单词出现的次数写入控制台，并返回生成的二维数组。: The word appears " + counter + " times in the document");
		return document;
	}
}
