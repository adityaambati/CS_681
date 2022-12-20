package edu.umb.cs681.hw19;

public interface Observer<T> {
	public void update(Observable<T> sender, T event);
}
