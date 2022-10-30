package edu.umb.cs681.hw01;

public class DJIAObservable extends Observable<Double>{
	private double quote;
	
	public void changeQuote(double q) {
		quote = q;
		notifyObservers(quote);
	}
	
	public static void main(String args[]) {
		DJIAObservable observable = new DJIAObservable();
		observable.addObserver( new LineChartObserver() );
		observable.addObserver( new TableObserver() );
		System.out.println("Count of Observer objects before adding one  "+observable.countObservers());
		//Observer class is the interface with update method as abtract method so LE will be linked to it.
		Observer<Double> objToadd = ((Observable<Double> o, Double arg) -> {System.out.println("Event: " + arg + ", Sender: " + o);;});
		observable.addObserver(objToadd);
		observable.changeQuote(30000.44);
		observable.changeQuote(30050.99);
		System.out.println("Count of Observer objects after adding one  "+observable.countObservers());
		observable.removeObserver(objToadd);
		System.out.println("Count of Observer objects after removing one "+observable.countObservers());

	}

}
