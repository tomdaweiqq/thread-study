package net.wangxy.vip.thread;


public class EndRunnable {
	private static class UseRunnable implements Runnable {
		@Override
		public void run() {
			long i = 0; 
			String threadName = Thread.currentThread().getName();
			while(!Thread.currentThread().isInterrupted()) {
//			while(true) { //À¿—≠ª∑¡À
				System.out.println(threadName + " is run!");
			}
			System.out.println(Thread.currentThread().isInterrupted());
		}
	}
	
	public static void main(String args[]) throws InterruptedException {
		Runnable r = new UseRunnable();
		Thread t = new Thread(r, "EndRunnable");
		t.start();
		Thread.sleep(3000);
		t.interrupt();
	}
}
