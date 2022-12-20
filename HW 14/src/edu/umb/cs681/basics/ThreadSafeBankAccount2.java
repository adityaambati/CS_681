package edu.umb.cs681.basics;

import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;

public class ThreadSafeBankAccount2 implements BankAccount{
	private double balance = 0;
	private ReentrantLock lock = new ReentrantLock();
	private Condition sufficientFundsCondition = lock.newCondition();
	private Condition belowUpperLimitFundsCondition = lock.newCondition();
	
	public void deposit(double amount){
		lock.lock();
		try{
			System.out.println("Lock obtained");
			System.out.println(Thread.currentThread().getId() + 
					" (d): current balance: " + balance);
			while(balance >= 300){
				System.out.println(Thread.currentThread().getId() + 
						" (d): await(): Balance exceeds the upper limit.");
				belowUpperLimitFundsCondition.await();
			}
			balance += amount;
			System.out.println(Thread.currentThread().getId() + 
					" (d): new balance: " + balance);
			sufficientFundsCondition.signalAll();
		}
		catch (InterruptedException exception){
			exception.printStackTrace();
		}
		finally{
			lock.unlock();
			System.out.println("Lock released");
		}
	}
	
	public void withdraw(double amount){
		lock.lock();
		try{
			System.out.println("Lock obtained");
			System.out.println(Thread.currentThread().getId() + 
					" (w): current balance: " + balance);
			while(balance <= 0){
				System.out.println(Thread.currentThread().getId() + 
						" (w): await(): Insufficient funds");
				sufficientFundsCondition.await();
			}
			balance -= amount;
			System.out.println(Thread.currentThread().getId() + 
					" (w): new balance: " + balance);
			belowUpperLimitFundsCondition.signalAll();
		}
		catch (InterruptedException exception){
			exception.printStackTrace();
		}
		finally{
			lock.unlock();
			System.out.println("Lock released");
		}
	}
	
	public static void main(String[] args){
		ThreadSafeBankAccount2 bankAccount = new ThreadSafeBankAccount2();
		List<DepositRunnable> depositRunnables = new ArrayList<DepositRunnable>();
		List<WithdrawRunnable> withdrawRunnables = new ArrayList<WithdrawRunnable>();
		List<Thread> depositThreads = new ArrayList<Thread>();
		List<Thread> withdrawThreads = new ArrayList<Thread>();

		for (int i = 0; i < 3; i++) {
			DepositRunnable depositRunnable = new DepositRunnable(bankAccount);
			depositRunnables.add(depositRunnable);
			Thread depositThread = new Thread(depositRunnable);
			depositThreads.add(depositThread);
			depositThread.start();
		}

		for (int i = 0; i < 3; i++) {
			WithdrawRunnable withdrawRunnable = new WithdrawRunnable(bankAccount);
			withdrawRunnables.add(withdrawRunnable);
			Thread withdrawThread = new Thread(withdrawRunnable);
			withdrawThreads.add(withdrawThread);
			withdrawThread.start();
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}

		for (DepositRunnable depositRunnable : depositRunnables) {
			depositRunnable.setDone();
		}

		for (WithdrawRunnable withdrawRunnable : withdrawRunnables) {
			withdrawRunnable.setDone();
		}

		// interrupt all threads
		for (Thread depositThread : depositThreads) {
			depositThread.interrupt();
		}

		for (Thread withdrawThread : withdrawThreads) {
			withdrawThread.interrupt();
		}

		for (Thread depositThread : depositThreads) {
			try {
				depositThread.join();
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}
		}

		for (Thread withdrawThread : withdrawThreads) {
			try {
				withdrawThread.join();
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}
		}

	}
}
