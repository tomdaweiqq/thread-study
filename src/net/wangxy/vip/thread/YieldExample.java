package net.wangxy.vip.thread;

/**
 * 调用yield()方法时，两个线程依次打印，然后将执行机会交给对方，一直这样进行下去。】
 * 如果不调用yield()方法，则先执行完producer.start();在执行consumer.start();
 * 
 * @author wlg
 *
 */
public class YieldExample {
	public static void main(String[] args) {
		Thread producer = new Producer();
		Thread consumer = new Consumer();

		producer.setPriority(Thread.MIN_PRIORITY); // Min Priority
		consumer.setPriority(Thread.MAX_PRIORITY); // Max Priority

		producer.start();
		consumer.start();
	}
}

class Producer extends Thread {
	public void run() {
		for (int i = 0; i < 5; i++) {
			System.out.println("I am Producer : Produced Item " + i);
			Thread.yield();
		}
	}
}

class Consumer extends Thread {
	public void run() {
		for (int i = 0; i < 5; i++) {
			System.out.println("I am Consumer : Consumed Item " + i);
			Thread.yield();
		}
	}
}