package net.wangxy.vip.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class OnlyMain {
	static public void main(String args[] ) {
		System.out.println("��ӡ�����̵����е��߳���");
		// Java ������߳�ϵͳ�Ĺ���ӿ� Java ��������д˽ӿڵ�ʵ����ĵ�һʵ����ʵ�ִ˽ӿڵ�ʵ����һ�� MXBean��
		// ����ͨ������ ManagementFactory.getThreadMXBean() �������ƽ̨ MBeanServer �����������
		ThreadMXBean tMxBean = ManagementFactory.getThreadMXBean();
		
		ThreadInfo[] tInfos = tMxBean.dumpAllThreads(false, false);
		System.out.println(tInfos.length);
		/*
		Thread ID = 5; ThteadName = Attach Listener ��ȡ��ǰ�������е���Ϣ
		Thread ID = 4; ThteadName = Signal Dispatcher ��������� 
		Thread ID = 3; ThteadName = Finalizer �������Finalizer������
		Thread ID = 2; ThteadName = Reference Handler ���������á���������õ��߳�?��
		Thread ID = 1; ThteadName = main �����߳�
		
		����GC�̣߳�����ʱ��̣�������GC�̡߳�
		*/

		for(ThreadInfo info: tInfos) {
			System.out.println("Thread ID = [" + info.getThreadId() + "]; ThteadName = " + info.getThreadName());
			
		}
	}
}
