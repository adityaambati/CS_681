package edu.umb.cs681.primes;

import java.util.concurrent.locks.ReentrantLock;

public class RunnableCancellablePrimeGenerator extends RunnablePrimeGenerator {
	private ReentrantLock lock = new ReentrantLock();
	private boolean done = false;
	
	public RunnableCancellablePrimeGenerator(long from, long to) {
		super(from, to);
	}
	

	public void setDone() {
		lock.lock();
		try {
			done = true;
		} finally {
			lock.unlock();
		}
	}

	public void generatePrimes() {
		for (long n = from; n <= to; n++) {
			lock.lock();
			try {
				if (done) {
					System.out.println("Stopped generating primes");
					break;
				}
				if (isPrime(n)) {
					primes.add(n);
				}
			} finally {
				lock.unlock();
			}
		}
	}

	public static void main(String[] args) {
		RunnableCancellablePrimeGenerator gen = new RunnableCancellablePrimeGenerator(1,100);
		Thread thread = new Thread(gen);
		thread.start();
		gen.setDone();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gen.getPrimes().forEach( (Long prime)-> System.out.print(prime + ", ") );
		System.out.println("\n" + gen.getPrimes().size() + " prime numbers are found.");
		
		
		System.out.println("thread without setdone call");
		RunnableCancellablePrimeGenerator gen2 = new RunnableCancellablePrimeGenerator(1,100);
		Thread thread1 = new Thread(gen2);
		thread1.start();
		gen.setDone();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gen2.getPrimes().forEach( (Long prime)-> System.out.print(prime + ", ") );
		System.out.println("\n" + gen2.getPrimes().size() + " prime numbers are found.");
	}
}
