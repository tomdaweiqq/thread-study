package net.wangxy.vip.thread.forkjoin.sum;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;


public class SumArray {
    private static class SumTask extends RecursiveTask<Long>{

        private final static int THRESHOLD = MakeArray.ARRAY_LENGTH/4;
        private int[] src; //表示我们要实际统计的数组
        private int fromIndex;//开始统计的下标
        private int toIndex;//统计到哪里结束的下标

        public SumTask(int[] src, int fromIndex, int toIndex) {
            this.src = src;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

		@Override
		protected Long compute() {
			if(toIndex-fromIndex < THRESHOLD) {
				long count = 0l;
				for(int i=fromIndex;i<=toIndex;i++) {
			    	//SleepTools.ms(1);
			    	count = count + src[i];
				}
				
				System.out.println("线程名字 " + Thread.currentThread().getName());
				return count;
			}else {
				//fromIndex....mid....toIndex
				//1...................70....100
				int mid = (fromIndex+toIndex)/2;
				SumTask left = new SumTask(src,fromIndex,mid);
				SumTask right = new SumTask(src,mid+1,toIndex);
//				fork();
//				invokeAll(left,right);
				left.fork();
				right.fork();
				return left.join()+right.join();
			}
		}
    }


    public static void main(String[] args) {
    	System.out.println("Task is 啊啊啊啊啊啊啊啊啊啊啊啊啊啊.....");
        ForkJoinPool pool = new ForkJoinPool();
        int[] src = MakeArray.makeArray();

        SumTask innerFind = new SumTask(src,0,src.length-1);

        long start = System.currentTimeMillis();

        pool.invoke(innerFind);//同步调用
        System.out.println("Task is Running.....");

        System.out.println("The count is "+innerFind.join()
                +" spend time:"+(System.currentTimeMillis()-start)+"ms");

    }
}
