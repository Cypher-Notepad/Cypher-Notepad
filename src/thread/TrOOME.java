package thread;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryNotificationInfo;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.util.ArrayList;
import java.util.Collection;

import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;

/**
 * This class is modified version of MemoryWarningSystem.
 * @modifier : LeeDongGeon1996 on github
 * 
 * you can see the original version on the link below.
 * https://www.javaspecialists.eu/archive/Issue092.html
 */
public class TrOOME {

	private static double memoryThreshold = 0.8;
	private final Collection<ExceedListener> exceedListeners = new ArrayList<ExceedListener>();
	private final Collection<MemorySafeListener> memSafeListeners = new ArrayList<MemorySafeListener>();
	
	private MemoryCheckThread memchkThread = null;
	private int memoryCheckInterval = 3;
	
	public interface ExceedListener {
		public void memoryUsageExceeded(long usedMemory, long maxMemory);
	}
	public interface MemorySafeListener {
		public void memoryUsageSafe(long usedMemory, long maxMemory);
	}

	public TrOOME() {
		MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
		NotificationEmitter emitter = (NotificationEmitter) mbean;
		emitter.addNotificationListener(new NotificationListener() {
			public void handleNotification(Notification n, Object hb) {
				if (n.getType().equals(MemoryNotificationInfo.MEMORY_THRESHOLD_EXCEEDED)) {
					long maxMemory = tenuredGenPool.getUsage().getMax();
					long usedMemory = tenuredGenPool.getUsage().getUsed();
					for (ExceedListener listener : exceedListeners) {
						listener.memoryUsageExceeded(usedMemory, maxMemory);
					}
					startMemoryCheckThread();
				}
			}
		}, null, null);
	}

	public boolean addExceedListener(ExceedListener listener) {
		return exceedListeners.add(listener);
	}

	public boolean removeExceedListener(ExceedListener listener) {
		return exceedListeners.remove(listener);
	}
	
	public boolean addMemSafeListener(MemorySafeListener listener) {
		return memSafeListeners.add(listener);
	}

	public boolean removeMemSafeListener(MemorySafeListener listener) {
		return memSafeListeners.remove(listener);
	}

	private static final MemoryPoolMXBean tenuredGenPool = findTenuredGenPool();

	public static void setPercentageUsageThreshold(double percentage) {
		if (percentage <= 0.0 || percentage > 1.0) {
			throw new IllegalArgumentException("Percentage not in range");
		}
		
		memoryThreshold = percentage;
		long maxMemory = tenuredGenPool.getUsage().getMax();
		long warningThreshold = (long) (maxMemory * percentage);
		tenuredGenPool.setUsageThreshold(warningThreshold);
	}

	public double getCurrentPercentageUsage() {
		return ((double) tenuredGenPool.getUsage().getUsed()) / tenuredGenPool.getUsage().getMax();
	}

	/**
	 * Tenured Space Pool can be determined by it being of type HEAP and by it being
	 * possible to set the usage threshold.
	 */
	private static MemoryPoolMXBean findTenuredGenPool() {
		for (MemoryPoolMXBean pool : ManagementFactory.getMemoryPoolMXBeans()) {
			// I don't know whether this approach is better, or whether
			// we should rather check for the pool name "Tenured Gen"?
			if (pool.getType() == MemoryType.HEAP && pool.isUsageThresholdSupported()) {
				return pool;
			}
		}
		throw new AssertionError("Could not find tenured space");
	}
	
	private void startMemoryCheckThread() {
		if(memchkThread == null) {
			memchkThread = new MemoryCheckThread();
			memchkThread.start();
		} else {
			memchkThread.extendThread();
		}
	}
	
	private class MemoryCheckThread extends Thread{
		
		private int sleepCnt = memoryCheckInterval;
		
		@Override
		public void run() {
			for (; sleepCnt > 0; sleepCnt--) {
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			/**
			 * do something when the memory is stable.
			 * */
			long maxMemory = tenuredGenPool.getUsage().getMax();
			long usedMemory = tenuredGenPool.getUsage().getUsed();
			for (MemorySafeListener listener : memSafeListeners) {
				listener.memoryUsageSafe(usedMemory, maxMemory);
			}
			memchkThread = null;
		}
		
		public void extendThread() {
			sleepCnt = memoryCheckInterval;
		}
		
	}
}