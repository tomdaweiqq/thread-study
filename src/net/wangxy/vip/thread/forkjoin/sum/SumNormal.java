package net.wangxy.vip.thread.forkjoin.sum;


public class SumNormal {
	
	public static void main(String[] args) {
	    long count = 0;
	    int[] src = MakeArray.makeArray();

	    long start = System.currentTimeMillis();
	    for(int i= 0;i< src.length;i++){
	    	//SleepTools.ms(1);
	    	count = count + src[i];
	    }
	    System.out.println("The count is "+count
	            +" spend time:"+(System.currentTimeMillis()-start)+"ms");		
	}

}
