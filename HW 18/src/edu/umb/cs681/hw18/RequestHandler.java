package edu.umb.cs681.hw18;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class RequestHandler implements Runnable{
   
    private ReentrantLock lock = new ReentrantLock();
    private boolean done = false;


    private void setDone() {
        lock.lock();
        try {
            this.done = true;
        } finally {
            lock.unlock();
        }
    }

    public void run() {
        ArrayList<String> filesList = new ArrayList<>();
        filesList.add("a.html");
        filesList.add("b.txt");
        filesList.add("c.html");
        filesList.add("d.txt");
        filesList.add("e.html");
        while (true) {
            lock.lock();
            try {
                if (done) {
                    System.out.println("Done");
                    break;
                }

            int randomNumber = (int) (Math.random() * filesList.size());
            String file = filesList.get(randomNumber);
            AccessCounter.getInstance().increment(Path.of(file));

            } finally {
                lock.unlock();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                continue;
            }
        }
    }

    public static void main(String[] args) {
        List<RequestHandler> requestHandlers = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            RequestHandler requestHandler = new RequestHandler();
            requestHandlers.add(requestHandler);
            Thread thread = new Thread(requestHandler);
            threads.add(thread);
            thread.start();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (RequestHandler requestHandler : requestHandlers) {
            requestHandler.setDone();
        }
        for (Thread thread : threads) {
            thread.interrupt();
        }

        System.out.println("a.html: " + AccessCounter.getInstance().getCount(Path.of("a.html")));
        System.out.println("b.txt: " + AccessCounter.getInstance().getCount(Path.of("b.txt")));
        System.out.println("c.html: " + AccessCounter.getInstance().getCount(Path.of("c.html")));
        System.out.println("d.txt: " + AccessCounter.getInstance().getCount(Path.of("d.txt")));
        System.out.println("e.html: " + AccessCounter.getInstance().getCount(Path.of("e.html")));

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }



}
