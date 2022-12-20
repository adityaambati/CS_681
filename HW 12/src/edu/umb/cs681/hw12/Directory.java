package edu.umb.cs681.hw12;

import java.time.LocalDateTime;
import java.util.LinkedList;

import edu.umb.cs681.hw12.Directory;
import edu.umb.cs681.hw12.FSElement;

public class Directory extends FSElement {
    
    LinkedList<FSElement> children;

    public Directory(Directory parent, String name, int size, LocalDateTime creationTime) {
        super(parent, name, size, creationTime);
        this.children = new LinkedList<FSElement>();
    }

    public LinkedList<FSElement> getChildren() {
    	lock.lock();
        try {
            return this.children;
        } finally {
            lock.unlock();
        }
    }

    public void appendChild(FSElement child) {
        this.children.add(child);
        child.setParent(this);
    }

    public int countChildren() {
        lock.lock();
        try {
            return this.children.size();
        } finally {
            lock.unlock();
        }
    }

    public LinkedList<Directory> getSubDirectories() {
        lock.lock();
        try {
            LinkedList<Directory> subDirectories = new LinkedList<Directory>();
            for (FSElement child : this.children) {
                if (child.isDirectory()) {
                    subDirectories.add((Directory) child);
                }
            }
            return subDirectories;
        } finally {
            lock.unlock();
        }
    }

    public LinkedList<File> getFiles(){
        lock.lock();
        try {
            LinkedList<File> files = new LinkedList<>();
            for (FSElement child : this.children) {
                if (child.isFile()) {
                    files.add((File) child);
                }
            }
            return files;
        } finally {
            lock.unlock();
        }
	}

    public int getTotalSize() {
        lock.lock();
        try {
            int totalSize = 0;
            for (FSElement child : this.children) {
                if (child.isDirectory()) {
                    Directory subDir = (Directory) child;
                    totalSize += subDir.getTotalSize();
                } else {
                    totalSize += child.getSize();
                }
            }
            return totalSize;
        } finally {
            lock.unlock();
        }
    }
    
    public LinkedList<Link> getLinks(){
        lock.lock();
        try {
            LinkedList<Link> links = new LinkedList<>();
            for (FSElement child : this.children) {
                if (child.isLink()) {
                    links.add((Link) child);
                }
            }
            return links;
        } finally {
            lock.unlock();
        }
	}
	
	@Override
    public Boolean isDirectory() {
        lock.lock();
        try {
            return true;
        } finally {
            lock.unlock();
        }
    }

	@Override
	public Boolean isFile() {
        lock.lock();
        try {
            return false;
        } finally {
            lock.unlock();
        }
	}

	@Override
	public Boolean isLink() {
        lock.lock();
        try {
            return false;
        } finally {
            lock.unlock();
        }
	}
	
}
