package edu.umb.cs681.hw02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import edu.umb.cs681.hw02.Euclidean;

class EuclideanTest {

	@Test
	public void distanceTestWithP1P2() {
		List<Double> point_1 = Arrays.asList(3.0, 4.0, 6.0);
		List<Double> point_2= Arrays.asList(3.0, 6.0, 7.0);
		double expectedResult = Math.sqrt(5.0);
		double actualResult = new Euclidean().distance(point_1, point_2);
		assertEquals(expectedResult, actualResult);
	}

}
