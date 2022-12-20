package edu.umb.cs681.hw11;

import java.util.concurrent.atomic.AtomicReference;

public class AircraftTest implements Runnable {

	@Override
	public void run() {
		Aircraft aircraft = new Aircraft(new Position( 24.0, 45.0, 100.0));
		Position pos2 = new Position(57.23, 72.0, 100.0);
		Position position = aircraft.getPosition();
		System.out.println("Position: " + position);
		aircraft.setPosition(38.0, 49.0, 100.0);
		position = aircraft.getPosition();
		System.out.println("Position: " + position);
		System.out.println("Distance between two points: " + position.distanceTo(pos2));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		AircraftTest test = new AircraftTest();
		Thread thread = new Thread(test);
		Thread thread2 = new Thread(new AircraftTest());
		Thread thread3 = new Thread(new AircraftTest());
		thread.start();
		thread2.start();
		thread3.start();

		thread.join();
		thread2.join();
		thread3.join();


	}

}
