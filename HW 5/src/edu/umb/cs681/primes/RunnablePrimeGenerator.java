package edu.umb.cs681.primes;

public class RunnablePrimeGenerator extends PrimeGenerator implements Runnable {

    public RunnablePrimeGenerator(long from, long to) {
        super(from, to);
    }

    public void run() {
        generatePrimes();
    }

    //generate primes number from 1 to 2000000L with 16 threads and so calculate the time taken
    public static void main(String[] args) {
    	
    	
    	thread1();
    	//2 thread
    	thread2();
    	//4 thread
    	thread4();
    	//8threads
    	thread8();
    	//16threads
        thread16();
        }
    
    public static void thread1() {
        System.out.println("1 thread");


        RunnablePrimeGenerator g1 = new RunnablePrimeGenerator(1L, 2000000L);
        
        Thread t1 = new Thread(g1);
        

        long start = System.currentTimeMillis();
        		t1.start();
            try {
            	t1.join();
            } catch (InterruptedException e) {
                System.out.println(e.toString());
                return;
            }
        long end = System.currentTimeMillis();
             g1.getPrimes().forEach((Long prime) -> System.out.print(prime + ", "));

        long total = 0;
            total += g1.getPrimes().size();

        double totalTimeTaken = (end - start);
        
        System.out.println("Total time taken for 1 threads: " + totalTimeTaken);
        System.out.println("Total number of primes: " + total);

    }
    
    public static void thread2() {
        System.out.println("2 thread");


        RunnablePrimeGenerator g1 = new RunnablePrimeGenerator(1L, 1000000L);
        RunnablePrimeGenerator g2 = new RunnablePrimeGenerator(1000001L, 2000000L);
        
        Thread t1 = new Thread(g1);
        Thread t2 = new Thread(g2);
        

        long start = System.currentTimeMillis();
        		t1.start();
        		t2.start();
            try {
            	t1.join();
            	t2.join();
            } catch (InterruptedException e) {
                System.out.println(e.toString());
                return;
            }
        long end = System.currentTimeMillis();
             g1.getPrimes().forEach((Long prime) -> System.out.print(prime + ", "));
      		g2.getPrimes().forEach((Long prime) -> System.out.print(prime + ", "));

        long total = 0;
            total += g1.getPrimes().size();
            total += g2.getPrimes().size();

        double totalTimeTaken = (end - start);
        
        System.out.println("Total time taken for 2 threads: " + totalTimeTaken);
        System.out.println("Total number of primes: " + total);

    }
    
    public static void thread4() {
        System.out.println("4 thread");
        long from = 1L;
        long to = 2000000L;
        int threadCount = 4;
        long range = (to - from) / threadCount;
        Thread[] threads = new Thread[threadCount];
        RunnablePrimeGenerator[] primeGenerators = new RunnablePrimeGenerator[threadCount];
        from = 1L;
        to = from + range;
        for (int i = 0; i < threadCount; i++) {
            System.out.println(from+" "+to);
            primeGenerators[i] = new RunnablePrimeGenerator(from, to);
            threads[i] = new Thread(primeGenerators[i]);
            threads[i].start();
            from = to + 1;
            to = from + range;
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println(e.toString());
                return;
            }
        }
        long end = System.currentTimeMillis();
         for(int i=0; i<threadCount-1; i++){
             primeGenerators[i].getPrimes().forEach((Long prime) -> System.out.print(prime + ", "));
             System.out.println();
         }
        long total = 0;
        for (int i = 0; i < threadCount; i++) {
            total += primeGenerators[i].getPrimes().size();
        }
        double totalTimeTaken = (end - start);
        System.out.println("Total time taken for 4 threads: " + totalTimeTaken);
        System.out.println("Total number of primes: " + total);

    }
    public static void thread8() {
        long from = 1L;
        long to = 2000000L;
        int threadCount = 8;
        long range = (to - from) / threadCount;
        Thread[] threads = new Thread[threadCount];
        RunnablePrimeGenerator[] primeGenerators = new RunnablePrimeGenerator[threadCount];
        from = 1L;
        to = from + range;
        for (int i = 0; i < threadCount; i++) {
            System.out.println(from+" "+to);
            primeGenerators[i] = new RunnablePrimeGenerator(from, to);
            threads[i] = new Thread(primeGenerators[i]);
            threads[i].start();
            from = to + 1;
            to = from + range;
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println(e.toString());
                return;
            }
        }
        long end = System.currentTimeMillis();
         for(int i=0; i<threadCount-1; i++){
             primeGenerators[i].getPrimes().forEach((Long prime) -> System.out.print(prime + ", "));
             System.out.println();
         }
        long total = 0;
        for (int i = 0; i < threadCount; i++) {
            total += primeGenerators[i].getPrimes().size();
        }
        double totalTimeTaken = (end - start);
        System.out.println("Total time taken for 8 threads: " + totalTimeTaken);
        System.out.println("Total number of primes: " + total);

	}
    	public static void thread16() {
    		System.out.println("16ths thread");
            long from = 1L;
            long to = 2000000L;
            int threadCount = 16;
            long range = (to - from) / threadCount;
            Thread[] threads = new Thread[threadCount];
            RunnablePrimeGenerator[] primeGenerators = new RunnablePrimeGenerator[threadCount];
            from = 1L;
            to = from + range;
            for (int i = 0; i < threadCount; i++) {
                System.out.println(from+" "+to);
                primeGenerators[i] = new RunnablePrimeGenerator(from, to);
                threads[i] = new Thread(primeGenerators[i]);
                threads[i].start();
                from = to + 1;
                to = from + range;
                }
            long start = System.currentTimeMillis();
            for (int i = 0; i < threadCount; i++) {
                try {
                    threads[i].join();
                } catch (InterruptedException e) {
                    System.out.println(e.toString());
                    return;
                }
            }
            long end = System.currentTimeMillis();
             for(int i=0; i<threadCount-1; i++){
                 primeGenerators[i].getPrimes().forEach((Long prime) -> System.out.print(prime + ", "));
                 System.out.println();
             }
            long total = 0;
            for (int i = 0; i < threadCount; i++) {
                total += primeGenerators[i].getPrimes().size();
            }
            System.out.println("Total time taken for 16 threads: " + (end - start));
            System.out.println("Total number of primes: " + total);

    	}

}
