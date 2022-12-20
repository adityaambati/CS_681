package edu.umb.cs681.hw13;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class AccessCounter {
    HashMap<Path, Integer> map = new HashMap<Path, Integer>();
    private static AccessCounter instance = null;
    private static ReentrantLock lockStatic = new ReentrantLock();
    private ReentrantLock lockNonStatic = new ReentrantLock();

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
        lockNonStatic.lock();
        try {
            if (map.containsKey(path)) {
                map.put(path, map.get(path) + 1);
            } else {
                map.put(path, 1);
            }
        } finally {
            lockNonStatic.unlock();
        }
    }

    public int getCount(Path path) {
        lockNonStatic.lock();
        try {
            if (map.containsKey(path)) {
                return map.get(path);
            } else {
                return 0;
            }
        } finally {
            lockNonStatic.unlock();
        }
    }
    
}
