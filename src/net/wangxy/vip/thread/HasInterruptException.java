package net.wangxy.vip.thread;

import java.text.SimpleDateFormat;

public class HasInterruptException {
	private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss_SSS");
	
	private static class UseRunnable implements Runnable {

		@Override
		public void run() {
			while(!Thread.currentThread().isInterrupted()) {
				try {
					System.out.println("in " + Thread.currentThread().isInterrupted());
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("catch " + Thread.currentThread().isInterrupted());
					
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
				System.out.println("while in " + Thread.currentThread().isInterrupted());
			}
			System.out.println("while out " + Thread.currentThread().isInterrupted());
			
		}
		
	}

	public static void main(String[] args) throws InterruptedException {
		Runnable r = new UseRunnable();
		Thread t = new Thread(r);
		t.start();
		Thread.sleep(500);
		t.interrupt();
	}

}
