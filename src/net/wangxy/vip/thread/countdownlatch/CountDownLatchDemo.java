package net.wangxy.vip.thread.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch的伪代码如下所示： //Main thread start //Create CountDownLatch for N
 * threads //Create and start N threads //Main thread wait on latch //N threads
 * completes there tasks are returns //Main thread resume execution
 */
public class CountDownLatchDemo {

	public static void main(String[] args) {

		// 构造器中的计数值（count）实际上就是闭锁需要等待的线程数量(此说法不准确，可以大于线程数，也就是扣除点)。
		// 这个值只能被设置一次，而且CountDownLatch没有提供任何机制去重新设置这个计数值。
		final CountDownLatch latch = new CountDownLatch(2);

		new Thread() {
			public void run() {
				try {
					System.out.println("子线程" + Thread.currentThread().getName() + "正在执行");
					Thread.sleep(3000);
					System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕");
					latch.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();

		new Thread() {
			public void run() {
				try {
					System.out.println("子线程" + Thread.currentThread().getName() + "正在执行");
					Thread.sleep(3000);
					System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕");
					latch.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();

		try {
			System.out.println("等待2个子线程执行完毕...");
			latch.await();
			System.out.println("2个子线程已经执行完毕");
			System.out.println("继续执行主线程");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
