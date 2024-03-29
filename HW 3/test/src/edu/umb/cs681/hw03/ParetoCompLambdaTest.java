package edu.umb.cs681.hw03;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;

import edu.umb.cs681.hw03.Car;

public class ParetoCompLambdaTest {
	private static Car car1;
	private static Car car2;
	private static Car car3;
	private static LinkedList<Car> usedCars;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		usedCars = new LinkedList<>();
		car1 = new Car("BMW", "X1", 70000, 2011, 7000.0f);
		car2 = new Car("BMW", "X3", 80000, 2010, 8000.0f);
		car3 = new Car("BMW", "X5", 60000, 2012, 6000.0f);
		usedCars.add(car1);
		usedCars.add(car2);
		usedCars.add(car3);
	}
	
	@Test
	public void car1DominationCountTestCase() {
		int expected = 1;
		car1.setDominationCount(usedCars);
		int actual = car1.getDominationCount();
		assertEquals(expected, actual);
	}
	
	@Test
	public void car2DominationCountTestCase() {
		int expected = 2;
		car2.setDominationCount(usedCars);
		int actual = car2.getDominationCount();
		assertEquals(expected, actual);
	}
	
	@Test
	public void car3DominationCountTestCase() {
		int expected = 0;
		car3.setDominationCount(usedCars);
		int actual = car3.getDominationCount();
		assertEquals(expected, actual);
	}

	@Test
	public void paretoComparatorAscendTestcase() {
		LinkedList<Car> expectedCars = new LinkedList<>();
		expectedCars.add(car3);
		expectedCars.add(car1);
		expectedCars.add(car2);
		car1.setDominationCount(usedCars);
		car2.setDominationCount(usedCars);
		car3.setDominationCount(usedCars);
		List<Car> sortedCars = usedCars.stream()
				.sorted( Comparator.comparing((Car car) -> car.getDominationCount()))
				.collect(Collectors.toList());
		Assert.assertArrayEquals(expectedCars.toArray(), sortedCars.toArray());
	}

	@Test
	public void medianDominationCountTestcase() {
	    int expected = 1;
		car1.setDominationCount(usedCars);
		car2.setDominationCount(usedCars);
		car3.setDominationCount(usedCars);
		List<Car> sortedCars = usedCars.stream()
				.sorted( Comparator.comparing((Car car) -> car.getDominationCount()))
				.collect(Collectors.toList());
		int actual = sortedCars.get(1).getDominationCount();
		System.out.println(sortedCars.get(1).getModel());
		assertEquals(expected, actual);

	}
}