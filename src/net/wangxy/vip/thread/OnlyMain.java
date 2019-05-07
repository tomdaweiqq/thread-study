package net.wangxy.vip.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class OnlyMain {
	static public void main(String args[] ) {
		System.out.println("打印本进程的所有的线程数");
		// Java 虚拟机线程系统的管理接口 Java 虚拟机具有此接口的实现类的单一实例。实现此接口的实例是一个 MXBean，
		// 可以通过调用 ManagementFactory.getThreadMXBean() 方法或从平台 MBeanServer 方法获得它。
		ThreadMXBean tMxBean = ManagementFactory.getThreadMXBean();
		
		ThreadInfo[] tInfos = tMxBean.dumpAllThreads(false, false);
		System.out.println(tInfos.length);
		/*
		Thread ID = 5; ThteadName = Attach Listener 获取当前程序运行的信息
		Thread ID = 4; ThteadName = Signal Dispatcher 发送虚拟机 
		Thread ID = 3; ThteadName = Finalizer 负责调用Finalizer方法。
		Thread ID = 2; ThteadName = Reference Handler 负责处理引用。（清除引用的线程?）
		Thread ID = 1; ThteadName = main 是主线程
		
		还有GC线程，由于时间短，看不到GC线程。
		*/

		for(ThreadInfo info: tInfos) {
			System.out.println("Thread ID = [" + info.getThreadId() + "]; ThteadName = " + info.getThreadName());
			
		}
	}
}
