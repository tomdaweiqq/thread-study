package net.wangxy.vip.thread.countdownlatch;

import java.util.concurrent.CountDownLatch;
import net.wangxy.vip.thread.SleepTools;

public class UseCountDownLatch {
	static CountDownLatch countDownLatch = new CountDownLatch(6);
	
	// 初始化线程
	private static class InitClass implements Runnable {
		public void run() {
			System.out.println("Thread " + Thread.currentThread().getId() + " Ready init work....");
			countDownLatch.countDown(); // 初始化线程完成工作了
			for(int i = 0; i < 2; i++) {
				System.out.println("Thread " + Thread.currentThread().getId() + " Continue do it's work....");
			}
		}
	}
	
	// 业务线程要等待初始化线程完成工作后才开始
	private static class BusiClass implements Runnable {
		public void run() {
			try {
				// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				countDownLatch.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			countDownLatch.countDown();
			for(int i = 0; i < 3; i++) {
				System.out.println("BusiThread " + Thread.currentThread().getId() + " Continue do it's work....");
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		// 单独的初始化线程，初始化为2步，扣除2次
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				SleepTools.ms(1);
				System.out.println("Thread " + Thread.currentThread().getId() + " ready init word step 1st........");
				countDownLatch.countDown();// 每完成一步扣除一次
				
				SleepTools.ms(1);
				System.out.println("Thread " + Thread.currentThread().getId() + " ready init word step 2st........");
				countDownLatch.countDown();
				
//				SleepTools.ms(1);
//				System.out.println("Thread " + Thread.currentThread().getId() + "ready init word step 3st........");
//				countDownLatch.countDown();
			}
		}).start();
		
		// 开始业务线程，直接等待。因为run中调用了await()
		new Thread(new BusiClass()).start();
		for(int i = 0; i <= 3; i++) {
			new Thread(new InitClass()).start();
		}
		countDownLatch.await(); 
		System.out.println("Main do ites work...............!");
		
	}
			

}
