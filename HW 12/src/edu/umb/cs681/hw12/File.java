package edu.umb.cs681.hw12;

import java.time.LocalDateTime;

public class File extends FSElement {
    
    public File(Directory parent, String name, int size, LocalDateTime creationTime) {
        super(parent, name, size, creationTime);
    }
   
    @Override
    public Boolean isDirectory() {
		lock.lock();
		try {
        	return false;
		} finally {
			lock.unlock();
		}
    }

	@Override
	public Boolean isFile() {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			return true;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Boolean isLink() {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			return false;
		} finally {
			lock.unlock();
		}
	}
    
}

