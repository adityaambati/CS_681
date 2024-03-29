package edu.umb.cs681.hw04;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.umb.cs681.hw04.Car;

public class PriceCompLambdaTest {
	private static Car car1;
	private static Car car2;
	private static Car car3;
	private static LinkedList<Car> usedCars;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		usedCars = new LinkedList<>();
		car1 = new Car("BMW", "X2-m", 30000, 2014, 26000);
		car2 = new Car("BMW", "X1", 20000, 2012, 23000);
		car3 = new Car("BMW", "X6", 10000, 2018, 34000);
		usedCars.add(car1);
		usedCars.add(car2);
		usedCars.add(car3);
	}

	@Test
	public void minCarPriceTest() {
		int expectedMinPrice = 23000;
		int minPrice = usedCars.stream().mapToInt( (Car car)-> car.getPrice() ).min() .getAsInt(); 
		assertEquals(expectedMinPrice, minPrice);
	}
	
	@Test
	public void maxCarPriceTest() {
		int expectedMinPrice = 34000;
		int minPrice = usedCars.stream().mapToInt( (Car car)-> car.getPrice() ).max() .getAsInt(); 
		assertEquals(expectedMinPrice, minPrice);
	}

	@Test
	public void averageCarPriceTest() {
		int expectedMinPrice = 27666;
		int actualMinPrice = usedCars.stream().map( (Car car)-> car.getPrice() ).reduce( new int[2], (result,price)->{result[0]+=price; result[1]++; return result;},(finalResult, intermediateResult)-> finalResult)[0]/usedCars.size();
		assertEquals(expectedMinPrice, actualMinPrice);
	}
}