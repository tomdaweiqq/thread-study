package net.wangxy.vip.thread;

public class EndThread {
	private static class UseThread extends Thread {
		public UseThread(String name) {
			super(name);
		}
		
		@Override
		public void run() {
			long i = 0; 
			String threadName = getName();
			while(!isInterrupted()) {
//			while(true) { //��ѭ����
				System.out.println(threadName + " is run!");
				try {
//					sleep(50000);
					Thread.yield();
//				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					interrupt();
				} finally {
					System.out.println("finally!!!");
				}
			}
			System.out.println(isInterrupted());
		}
	}
	
	public static void main(String args[]) throws InterruptedException {
		UseThread t = new UseThread("EndThread");
		t.start();
		Thread.sleep(300);
		t.interrupt();
		Thread.sleep(3000);
	}
}
