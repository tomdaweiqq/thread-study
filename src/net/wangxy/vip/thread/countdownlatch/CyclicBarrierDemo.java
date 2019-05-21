package net.wangxy.vip.thread.countdownlatch;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {

	public static void main(String[] args) {
//		CyclicBarrier barrier = new CyclicBarrier(4);
		CyclicBarrier barrier  = new CyclicBarrier(4, new Runnable() {
            @Override
            public void run() {
                System.out.println("当前线程"+Thread.currentThread().getName() + "进行后继处理！");   
            }
        });
		for (int i = 0; i < 4; i++)
			new Writer(barrier).start();

	}

	static class Writer extends Thread {
		private CyclicBarrier cyclicBarrier;

		public Writer(CyclicBarrier cyclicBarrier) {
			this.cyclicBarrier = cyclicBarrier;
		}

		@Override
		public void run() {
			System.out.println("线程" + Thread.currentThread().getName() + "正在写入数据...");
			try {
				Thread.sleep(5000); // 以睡眠来模拟写入数据操作
				System.out.println("线程" + Thread.currentThread().getName() + "写入数据完毕，等待其他线程写入完毕");
				cyclicBarrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
			System.out.println("所有线程写入完毕，继续处理其他任务...");
		}
	}

}
