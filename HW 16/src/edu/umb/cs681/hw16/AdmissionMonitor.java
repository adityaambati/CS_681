package edu.umb.cs681.hw16;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AdmissionMonitor {	

	private int currentVisitors;
	private ReentrantReadWriteLock rWriteLock = new ReentrantReadWriteLock();
	private Condition sufficientVisitors = rWriteLock.writeLock().newCondition();

	public void enter() {
		rWriteLock.writeLock().lock();
		try {
			while (currentVisitors >= 5) {
				try {
					sufficientVisitors.await();
				} catch (InterruptedException e) {
					continue;
				}
			}
			currentVisitors++;
		} finally {
			rWriteLock.writeLock().unlock();
		}
	}

	public void exit() {
		rWriteLock.writeLock().lock();
		try {
			currentVisitors--;
			sufficientVisitors.signalAll();
		} finally {
			rWriteLock.writeLock().unlock();
		}
	}

	public int countCurrentVisitors() {
		rWriteLock.readLock().lock();
		try {
			return currentVisitors;
		} finally {
			rWriteLock.readLock().unlock();
		}
	}


	public static void main(String[] args) {
		AdmissionMonitor monitor = new AdmissionMonitor();
		EntranceHandler entranceHandler = new EntranceHandler(monitor);
		ExitHandler exitHandler = new ExitHandler(monitor);
		StatsHandler statsHandler = new StatsHandler(monitor);

		Thread entranceThread = new Thread(entranceHandler);
		Thread exitThread = new Thread(exitHandler);
		Thread statsThread = new Thread(statsHandler);

		entranceThread.start();
		exitThread.start();
		statsThread.start();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		entranceHandler.setDone();
		exitHandler.setDone();
		statsHandler.setDone();

		entranceThread.interrupt();
		exitThread.interrupt();
		statsThread.interrupt();

		try {
			entranceThread.join();
			exitThread.join();
			statsThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
