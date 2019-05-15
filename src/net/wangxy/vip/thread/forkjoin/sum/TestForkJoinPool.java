package net.wangxy.vip.thread.forkjoin.sum;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 这是一个简单的Join/Fork计算过程，将1—1001数字相加
 */
public class TestForkJoinPool {
 
    private static final Integer MAX = 200;
    // 存储线程名字
    private static Set<String> set = new HashSet<>();
    // 存储线程名字
    private static Set<String> set2 = new HashSet<>();
    static class MyForkJoinTask extends RecursiveTask<Integer> {
        // 子任务开始计算的值
        private Integer startValue;
 
        // 子任务结束计算的值
        private Integer endValue;
 
        public MyForkJoinTask(Integer startValue , Integer endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }
 
        @Override
        protected Integer compute() {
            // 如果条件成立，说明这个任务所需要计算的数值分为足够小了
            // 可以正式进行累加计算了
            if(endValue - startValue < MAX) {
//                System.out.println("线程名字" + Thread.currentThread().getName() +  "|| 开始计算的部分：startValue = " + startValue + ";endValue = " + endValue);
                Integer totalValue = 0;
                for(int index = this.startValue ; index <= this.endValue  ; index++) {
                    totalValue += index;
                }
                set2.add("线程名字" + Thread.currentThread().getName());
                return totalValue;
            }
            // 否则再进行任务拆分，拆分成两个任务
            else {
            	System.out.println("线程名字" + Thread.currentThread().getName());
                MyForkJoinTask subTask1 = new MyForkJoinTask(startValue, (startValue + endValue) / 2);
                set.add("线程名字" + Thread.currentThread().getName());
                // fork方法用于将新创建的子任务放入当前线程的work queue队列中
                subTask1.fork();
                MyForkJoinTask subTask2 = new MyForkJoinTask((startValue + endValue) / 2 + 1 , endValue);
                subTask2.fork();
//                invokeAll(subTask1,subTask1);
                return subTask1.join() + subTask2.join();
            }
        }
    }
 
    public static void main(String[] args) {
        // 这是Fork/Join框架的线程池
        ForkJoinPool pool = new ForkJoinPool();
        // 当你实例化一个ForkJoinPool之后，一般有三种提交任务的方式：execute、submit(返回future)、invoke(返回join操作得到的结果)。
        ForkJoinTask<Integer> taskFuture =  pool.submit(new MyForkJoinTask(1,10000001));
        try {
            Integer result = taskFuture.get();
            System.out.println("result = " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(System.out);
        }
        System.out.println("1----------------------------");
        for(String str: set) {
        	System.out.println(str);
        }
        System.out.println("1----------------------------");
        
        
        System.out.println("2----------------------------");
        for(String str: set2) {
        	System.out.println(str);
        }
        System.out.println("2----------------------------");
    }
}