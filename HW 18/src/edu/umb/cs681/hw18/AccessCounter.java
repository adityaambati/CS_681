package edu.umb.cs681.hw18;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class AccessCounter {
    ConcurrentHashMap<Path, Integer> map = new ConcurrentHashMap<Path, Integer>();
    private static AccessCounter instance = null;
    private static ReentrantLock lockStatic = new ReentrantLock();

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
        map.compute(path, (k, v) -> (v == null) ? 1 : v + 1);

    }
    
    public int getCount(Path path) {
        return map.computeIfPresent(path, (k, v) -> v);
    }

    
}
