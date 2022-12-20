package edu.umb.cs681.hw12;

import java.time.LocalDateTime;

public class Link extends FSElement {
	
	private FSElement target;

    public Link(Directory parent, String name, int size, LocalDateTime creationTime, FSElement target) {
		super(parent, name, size, creationTime);
		this.target = target;
	}

	@Override
	public Boolean isDirectory() {
		return false;
	}

    public FSElement getTarget() {
        return this.target;
    }

    public void setTarget(FSElement target) {
        this.target = target;
    }

	@Override
	public Boolean isFile() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Boolean isLink() {
		// TODO Auto-generated method stub
		return true;
	}
    
}
