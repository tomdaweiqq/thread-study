package net.wangxy.vip.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class NewThread {
//	static public void main(String args[] ) {
	
//		Runnable r = () -> {
//			System.out.println("===========");
//		};
//		Thread thread = new Thread(r);
//		thread.start();
//	}
	/** 使用Runnable***/
	private static class UseRunnable implements Runnable {
		@Override
		public void run() {
			System.out.println("===========Runnable");
		}
	}
	
	/** 使用Callable***/
	private static class UseCallable implements Callable<String> {

		@Override
		public String call() throws Exception {
			System.out.println("===========Callable");
			return "Callable";
		}
		
	}
	static public void main(String args[] ) throws InterruptedException, ExecutionException {
//		Runnable r = new UseRunnable();
//		Thread t = new Thread(r);
//		t.start();
		
//		UseRunnable useRun = new UseRunnable();
//		new Thread(useRun).start();
//		Thread t = new Thread(useRun);
//		t.interrupt();
////		t.interrupted();
		UseCallable useCallable = new UseCallable();
		FutureTask<String> futureTask = new FutureTask<>(useCallable);
		Thread t2 = new Thread(futureTask);
		t2.start();
		System.out.println("----------------");
		System.out.println(futureTask.get());
	}
}
