package edu.umb.cs681.hw19;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Observable<T> {
	private ConcurrentLinkedQueue<Observer<T>> observers = new ConcurrentLinkedQueue<>();
	private boolean changed = false;

	public void addObserver(Observer<T> o) {

			observers.add(o);
	}

	public void clearObservers() {
	
			observers.clear();
			
	}
	public ConcurrentLinkedQueue<Observer<T>> getObservers(){
		return observers;
	}
	
	public int countObservers() {
	
			return observers.size();
				
	}
	public void removeObserver(Observer<T> o) {
		
			observers.remove(o);
		
	}

	public void setChanged() {
		
			changed = true;
		
	}


	public void notifyObservers(T event) {
		
			if (changed) {
				observers.forEach((Observer<T> o) -> o.update(this, event));
				changed = false;
			}
		
	}
	
	public static void main(String[] args) {
		
		StockQuoteObservable stockQuoteObservable = new StockQuoteObservable();
		stockQuoteObservable.addObserver((Observable<StockEvent> o, StockEvent e)->{
			System.out.println("Event: " + e.getTicker() + ", Sender: " + o + ", Quote: " + e.getQuote() + ", Thread: " + Thread.currentThread().getName());
		});
		stockQuoteObservable.addObserver((Observable<StockEvent> o, StockEvent e)->{
			System.out.println("Event: " + e.getQuote() + ", Sender: " + o + ", Quote: " + e.getQuote() + ", Thread: " + Thread.currentThread().getName());
		});
		stockQuoteObservable.addObserver((Observable<StockEvent> o, StockEvent e)->{
			System.out.println("Event: " + e.getTicker() + ", Sender: " + o + ", Quote: " + e.getQuote() + ", Thread: " + Thread.currentThread().getName());
		});

		ArrayList<Thread> threads = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			threads.add(new Thread(()->{
				stockQuoteObservable.changeQuote("APPL", 100);
				stockQuoteObservable.changeQuote("SGER", 596);

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}));
		}		
		for (Thread t : threads) {
			t.start();
		}

		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		

		
	}

}
