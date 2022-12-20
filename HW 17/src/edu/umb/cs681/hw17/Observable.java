package edu.umb.cs681.hw17;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Observable<T> {
	private LinkedList<Observer<T>> observers = new LinkedList<>();
	private ReentrantLock lock = new ReentrantLock();
	private boolean changed = false;

	public void addObserver(Observer<T> o) {
		lock.lock();
		try {
			observers.add(o);
		} finally {
			lock.unlock();
		}
	}

	public void clearObservers() {
		lock.lock();
		try {
			observers.clear();
		} finally {
			lock.unlock();
		}		
	}
	public List<Observer<T>> getObservers(){
		return observers;
	}
	
	public int countObservers() {
		lock.lock();
		try {
			return observers.size();
		} finally {
			lock.unlock();
		}		
	}
	public void removeObserver(Observer<T> o) {
		lock.lock();
		try {
			observers.remove(o);
		} finally {
			lock.unlock();
		}
	}

	public void setChanged() {
		lock.lock();
		try {
			changed = true;
		} finally {
			lock.unlock();
		}
	}


	public void notifyObservers(T event) {
		LinkedList<Observer<T>> observersCopy = new LinkedList<>();
		lock.lock();
		observersCopy = (LinkedList<Observer<T>>) observers.clone();
		lock.unlock();
			observersCopy.forEach( (observer)->{observer.update(this, event);} );
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
