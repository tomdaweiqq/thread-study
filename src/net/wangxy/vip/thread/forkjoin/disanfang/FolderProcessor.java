package net.wangxy.vip.thread.forkjoin.disanfang;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class FolderProcessor extends RecursiveTask<List<String>> {
	  private static final long serialVersionUID = 1L;
	  private String path;

	  private String extension;

	  public FolderProcessor(String path, String extension) {
	      this.path = path;
	      this.extension = extension;
	  }

	  @Override
	  protected List<String> compute() {
	      //用来保存存储在文件夹中的文件。
	      List<String> list = new ArrayList<>();
	      //声明一个FolderProcessor任务的数列，用来保存将要处理存储在文件夹内的子文件夹的子任务
	      List<FolderProcessor> tasks = new ArrayList<>();
	      File file = new File(path);
	      File content[] = file.listFiles();
	      if (content != null) {
	          //对于文件夹里的每个元素，如果是子文件夹，则创建一个新的FolderProcessor对象，并使用fork()方法异步地执行它。
	          for (int i = 0; i < content.length; i++) {
	              if (content[i].isDirectory()) {
	                  FolderProcessor task = new FolderProcessor(content[i].getAbsolutePath(), extension);
	                  task.fork();
	                  tasks.add(task);
	              } else {
	                  //否则，使用checkFile()方法比较这个文件的扩展名和你想要查找的扩展名
	                  // 如果它们相等，在前面声明的字符串数列中存储这个文件的全路径。
	                  if (checkFile(content[i].getName())) {
	                      list.add(content[i].getAbsolutePath());
	                  }
	              }
	          }
	      }

	      if (tasks.size() > 50) {
	          System.out.printf("%s: %d tasks ran.\n", file.getAbsolutePath(), tasks.size());
	      }
	      addResultsFromTasks(list, tasks);
	      return list;
	  }

	  private void addResultsFromTasks(List<String> list, List<FolderProcessor> tasks) {
	      //对于保存在tasks数列中的每个任务，调用join()方法，这将等待任务执行的完成，并且返回任务的结果
	      for (FolderProcessor item : tasks) {
	          list.addAll(item.join());
	      }
	  }

	  private boolean checkFile(String name) {
	      return name.endsWith(extension);
	  }

	  public static void main(String[] args) {
	      ForkJoinPool forkJoinPool = new ForkJoinPool();
	      FolderProcessor folderProcessor1 = new FolderProcessor("/Users/wuzhenyu/IdeaProjects/ares2/ares-web/src/main/web/h5/src", "css");
	      FolderProcessor folderProcessor2 = new FolderProcessor("/Users/wuzhenyu/IdeaProjects/ares2/ares-web/src/main/web/h5/node_modules/_acorn-dynamic-import@2.0.2@acorn-dynamic-import", "css");

	      forkJoinPool.execute(folderProcessor1);
	      forkJoinPool.execute(folderProcessor2);

	      do {
	          System.out.printf("******************************************\n");
	          System.out.printf("Main: Parallelism: %d\n", forkJoinPool.getParallelism());
	          System.out.printf("Main: Active Threads: %d\n", forkJoinPool.getActiveThreadCount());
	          System.out.printf("Main: Task Count: %d\n", forkJoinPool.getQueuedTaskCount());
	          System.out.printf("Main: Steal Count: %d\n", forkJoinPool.getStealCount());
	          System.out.printf("***************************************** *\n");
	          try {
	              TimeUnit.SECONDS.sleep(1);
	          } catch (InterruptedException e) {
	              e.printStackTrace();
	          }
	      } while ((!folderProcessor1.isDone()) || (!folderProcessor2.isDone()));

	      forkJoinPool.shutdown();

	      List<String> results;
	      results = folderProcessor1.join();
	      System.out.printf("Documents: %d files found.\n", results.size());
	      results = folderProcessor2.join();
	      System.out.printf("Documents: %d files found.\n", results.size());
	  }
	}
}
