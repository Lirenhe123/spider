package priv.lirenhe.thread;

import priv.lirenhe.spy.Spy;

public class SpyThread extends Thread{
	Spy spy;
	String result;
	public SpyThread(Spy spy){
		this.spy=spy;
	}
	@Override
	public void run() {
		super.run();
		for(int i = 0; i<10; i++){
			result=spy.sendGet();
			System.out.println("当前线程："+this.currentThread().getName()+result);
		}
	}
}
