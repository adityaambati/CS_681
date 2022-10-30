package edu.umb.cs681.hw03;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import edu.umb.cs681.hw03.Car;

public class PriceCompLambdaTest {

	@Test
	public void priceComparatorlambdaAscendTest() {
		LinkedList<Car> usedCars = new LinkedList<>();
		Car car1 = new Car("BMW", "X2-m", 30000, 2014, 26000.0f);
		Car car2 = new Car("BMW", "X1", 20000, 2012, 23000.0f);
		Car car3 = new Car("BMW", "X6", 10000, 2018, 34000.0f);
		usedCars.add(car1);
		usedCars.add(car2);
		usedCars.add(car3);
		LinkedList<Car> expected = new LinkedList<>();
		expected.add(car2);
		expected.add(car1);
		expected.add(car3);
		List<Car> sortedCars = usedCars.stream()
				.sorted( Comparator.comparing((Car car)-> car.getPrice()))
				.collect(Collectors.toList());
		assertArrayEquals(expected.toArray(), sortedCars.toArray());
	}
	
	@Test
	void medianPriceTest() {
		LinkedList<Car> usedCars = new LinkedList<>();
		Car car1 = new Car("BMW", "X2-m", 30000, 2014, 26000.0f);
		Car car2 = new Car("BMW", "X1", 20000, 2012, 23000.0f);
		Car car3 = new Car("BMW", "X6", 10000, 2018, 34000.0f);
		usedCars.add(car1);
		usedCars.add(car2);
		usedCars.add(car3);
		float expected = 26000.0f;
		float actualMedianPrice = usedCars.stream()
				.map((Car car) -> car.getPrice())
				.sorted()
				.collect(Collectors.toList())
				.get(usedCars.size() / 2);
		assertEquals(expected, actualMedianPrice);

	}
}