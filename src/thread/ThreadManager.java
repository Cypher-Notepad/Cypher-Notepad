package thread;

import java.util.ArrayList;

public class ThreadManager {

	private Thread initThread = null;
	private Thread keyLoadingThread = null;
	private ArrayList<Thread> listTr;

	private static class LazyHolder {
		private static final ThreadManager INSTANCE = new ThreadManager();
	}

	private ThreadManager() {
		listTr = new ArrayList<Thread>();
	}

	public static ThreadManager getInstance() {
		return ThreadManager.LazyHolder.INSTANCE;
	}
	
	public void setInitThead(Thread t) {
		if(initThread == null) {
			initThread = t;
		}
	}
	
	public void joinInitThread() {
		if(initThread != null) {
			try {
				initThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setKeyLoadingThead(Thread t) {
		if(keyLoadingThread == null) {
			keyLoadingThread = t;
		}
	}
	
	public void joinKeyLoadingThread() {
		if(keyLoadingThread != null) {
			try {
				keyLoadingThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			joinInitThread();
			joinKeyLoadingThread();
		}
	}
	
	public void addThread(Thread t) {
		listTr.add(t);
	}
	
	public void joinThreads() {
		if(listTr.isEmpty()) {
			joinInitThread();
		}
		
		for(Thread t : listTr) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		listTr.clear();
		
		System.out.println("[ThreadManager]Finish Join");
	}

}
