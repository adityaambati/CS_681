package edu.umb.cs681.primes;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class RunnableCancellableInterruptiblePrimeFactorizer extends RunnableCancellablePrimeFactorizer {

    private ReentrantLock lock = new ReentrantLock();
	private boolean done = false;
	
	public RunnableCancellableInterruptiblePrimeFactorizer(long dividend, long from, long to) {
		super(dividend, from, to);
		// TODO Auto-generated constructor stub
	}

    public void setDone() {
    	lock.lock();
    	try {
    		done = true;
    	} finally {
    		lock.unlock();
    	}
    }


    public void generatePrimeFactors() {
       long divisor = from;
         while(1 <= dividend) {
     	   lock.lock();
     	   try {
     		   if (done) {
     			   System.out.println("Stopped generating prime factors for " + dividend);
     			   break;
     		   }
     	   } finally {
     		   lock.unlock();
     	   }
     	   if(divisor > 2 && isEven(divisor)) {
     		   divisor++;
     		   continue;
     	   }
     	   if(dividend % divisor == 0) {
     		   factors.add(divisor);
     		   dividend /= divisor;
     	   } else {
     		   if(divisor > 2) {
     			   divisor += 2;
     		   } else {
     			   divisor++;
     		   }
     	   }
     	   try {
     		   Thread.sleep(100);
     	   } catch (InterruptedException e) {
     		   System.out.println("Stopped generating prime factors for " + dividend);
     		   break;
     	   } finally {
            lock.unlock();
           }
         } 

    }

     public static void main(String[] args) {
        // Factorization of 84 with two threads with interrupt second thread    
        System.out.println("Factorization of 84");
		LinkedList<RunnableCancellablePrimeFactorizer> runnables = new LinkedList<RunnableCancellablePrimeFactorizer>();
		LinkedList<Thread> threads = new LinkedList<Thread>();

		runnables.add( new RunnableCancellablePrimeFactorizer(84, 2, (long)Math.sqrt(84)/2 ));
		runnables.add( new RunnableCancellablePrimeFactorizer(84, 1+(long)Math.sqrt(84)/2, (long)Math.sqrt(84) ));

        threads.add( new Thread(runnables.get(0)));
        threads.add( new Thread(runnables.get(1)));

        threads.get(0).start();
        threads.get(1).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        runnables.get(1).setDone();
        threads.get(1).interrupt();
        try {
            threads.get(0).join();
            threads.get(1).join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        runnables.get(0).getPrimeFactors().forEach((Long prime) -> System.out.print(prime + ", "));
        System.out.println();
        runnables.get(1).getPrimeFactors().forEach((Long prime) -> System.out.print(prime + ", "));
        System.out.println();

        


    }
}
