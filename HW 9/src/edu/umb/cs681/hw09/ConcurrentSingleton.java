package edu.umb.cs681.hw09;

import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentSingleton {
	private ConcurrentSingleton(){};
	private static ConcurrentSingleton instance = null; 
	private static ReentrantLock lock = new ReentrantLock();
	// Factory method to create or return the singleton instance 
	public static ConcurrentSingleton getInstance() {
		lock.lock();
		try {
			if (instance == null) {
				instance = new ConcurrentSingleton();
			}
			return instance;
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Main thread started");
		Runnable runnable = () -> {
			System.out.println("Thread started: " + Thread.currentThread().getName());
			ConcurrentSingleton instance = ConcurrentSingleton.getInstance();
			System.out.println("Thread ended: " + Thread.currentThread().getName());
		};
		for (int i = 0; i < 4; i++) {
			Thread thread = new Thread(runnable);
			thread.start();
		}
		System.out.println("Main thread ended");
	}

}
