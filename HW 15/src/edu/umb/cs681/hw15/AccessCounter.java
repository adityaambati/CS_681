package edu.umb.cs681.hw15;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AccessCounter {
    HashMap<Path, Integer> map = new HashMap<Path, Integer>();
    private static AccessCounter instance = null;
    private static ReentrantLock lockStatic = new ReentrantLock();
    private ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();

    public static AccessCounter getInstance() {
        lockStatic.lock();
        try {
            if (instance == null) {
                instance = new AccessCounter();
            }
            return instance;
        } finally {
            lockStatic.unlock();
        }
    }

    public void increment(Path path) {
    	rwlock.writeLock().lock();
        try {
            if (map.containsKey(path)) {
                map.put(path, map.get(path) + 1);
            } else {
                map.put(path, 1);
            }
        } finally {
        	rwlock.writeLock().unlock();
        }
    }

    public int getCount(Path path) {
    	rwlock.readLock().lock();
        try {
            if (map.containsKey(path)) {
                return map.get(path);
            } else {
                return 0;
            }
        } finally {
        	rwlock.readLock().unlock();
        }
    }
    
}
