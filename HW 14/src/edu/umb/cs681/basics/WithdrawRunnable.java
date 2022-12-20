package edu.umb.cs681.basics;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class WithdrawRunnable implements Runnable {
	private BankAccount bankAccount;
	private AtomicBoolean done = new AtomicBoolean(false);
	private ReentrantLock lock = new ReentrantLock();

	public void setDone() {
		done.set(true);
	}

	public WithdrawRunnable(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public void run() {
		while (true) {
			lock.lock();
			try {
				bankAccount.withdraw(100);
				Thread.sleep(100);
				if (done.get()) {
					System.out.println("Done withdrawing");
					break;
				}
			} catch (InterruptedException exception) {
				System.out.println("Interrupted!");
				break;
			} finally {
				lock.unlock();
			}
		}
	}


}
