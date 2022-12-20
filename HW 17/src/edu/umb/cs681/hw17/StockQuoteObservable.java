package edu.umb.cs681.hw17;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class StockQuoteObservable extends Observable<StockEvent>{

    private Map<String, Double> quotes = new HashMap<String, Double>();
    private ReentrantLock lock = new ReentrantLock();

    public void changeQuote(String ticker, double quote) {
        lock.lock();
            quotes.put(ticker, quote);
            setChanged();
            lock.unlock();
            notifyObservers(new StockEvent(ticker, quote));
    }

}
