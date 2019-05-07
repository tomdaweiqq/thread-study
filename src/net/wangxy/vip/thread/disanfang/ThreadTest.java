package net.wangxy.vip.thread.disanfang;

public class ThreadTest {

	public static void main(String[] args) {
        final Counter counter = new Counter();
        for (int i = 0; i < 10; i++) {
//        	System.out.println(i + "");
            new Thread(new Runnable() {
                @Override
                public void run() {
                	
                    counter.inc();
                }
            }).start();
        }
        System.out.println(counter);
    }
//	
//	private static class Counter {
//
//	    private volatile int count = 0;
//
//	    public void inc() {
//	        try {
//	            Thread.sleep(3);
//	        } catch (InterruptedException e) {
//	            e.printStackTrace();
//	        }
//	        count++;
//	    }
//	       @Override
//	    public String toString() {
//	        return "[count=" + count + "]";
//	    }
//	}

	private static class Counter {

	    public static int count = 0;

	    public synchronized void inc() {
	        count++;
	    }

	    public void run() {
	        for (int i = 0; i < 10; i++) {
	            try {
	                inc();// n=count+1改成了inc()
	                Thread.sleep(3);// 为了使运行结果更随即，延迟3毫秒
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    @Override
	    public String toString() {
	        return "[count=" + count + "]";
	    }
	}

}
