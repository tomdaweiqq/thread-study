package net.wangxy.vip.thread.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch的伪代码如下所示：
	//Main thread start
	//Create CountDownLatch for N threads
	//Create and start N threads
	//Main thread wait on latch
	//N threads completes there tasks are returns
	//Main thread resume execution
 */
public class CountDownLatchDemo {
	
	// 构造器中的计数值（count）实际上就是闭锁需要等待的线程数量(此说法不准确，可以大于线程数，也就是扣除点)。
	// 这个值只能被设置一次，而且CountDownLatch没有提供任何机制去重新设置这个计数值。
	// 1
	CountDownLatch countDownLatch = new CountDownLatch(3);
	
	public static void main(String args[]) {
		
	}
}
