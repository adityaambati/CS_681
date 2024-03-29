package edu.umb.cs681.hw02;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.umb.cs681.hw02.Distance;
import edu.umb.cs681.hw02.Euclidean;
import edu.umb.cs681.hw02.DistanceMetric;

public class DistanceTest {
	private static List<Double> point1, point2, point3, point4, point5;
	private static List<List<Double>> listOfPoints = new LinkedList<>();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		point1 = Arrays.asList(2.0, 3.0, 5.0);
		point2 = Arrays.asList(2.0, 5.0, 6.0);
		point3 = Arrays.asList(3.0, 4.0, 8.0);
		point4 = Arrays.asList(4.0, 2.0, 1.0);
		point5 = Arrays.asList(5.0, 5.0, 6.0);
		listOfPoints.add(point1);
		listOfPoints.add(point2);
		listOfPoints.add(point3);
		listOfPoints.add(point4);
		listOfPoints.add(point5);

	}

	@Test
	public void lambdaValidation() {
		double expected = Math.sqrt(9.0);
		double actual = Distance.get(point1, point2, (List<Double> p1, List<Double> p2) -> {
			double distance = 0;
			for (int i = 0; i < p1.size(); i++) {
				distance += Math.abs(p1.get(i) - p2.get(i));
			}
			return distance;
		});
		assertEquals(expected, actual);
	}

	@Test
	public void existingDistancePoint1Point2() {
		double expected = Math.sqrt(5.0);
		double actual = Distance.get(point1, point2);
		assertEquals(expected, actual);
	}

	@Test
	public void getPoint1Point2Euclidean() {
		double expected = Math.sqrt(5.0);
		double actual = Distance.get(point1, point2, new Euclidean());
		assertEquals(expected, actual);
	}

	 @Test
	 public void getPoint1Point2Manhattan() {
	 double expected = 3.0;
	 double actual = Distance.get(point1, point2, (List<Double> p1, List<Double> p2) -> {
         double distance = 0;
         for (int i = 0; i < p1.size(); i++) {
             distance += Math.abs(p1.get(i) - p2.get(i));
         }
         return distance;
     });
	 assertEquals(expected, actual);
	 }

	@Test
	public void existingMatrixListOfPoints() {
		Double[][] expected = { { 0.0, Math.sqrt(5.0), Math.sqrt(11.0), Math.sqrt(21.0), Math.sqrt(14.0) },
				{ Math.sqrt(5.0), 0.0, Math.sqrt(6.0), Math.sqrt(38.0), 3.0 },
				{ Math.sqrt(11.0), Math.sqrt(6.0), 0.0, Math.sqrt(54.0), 3.0 },
				{ Math.sqrt(21.0), Math.sqrt(38.0), Math.sqrt(54.0), 0.0, Math.sqrt(35.0) },
				{ Math.sqrt(14.0), 3.0, 3.0, Math.sqrt(35.0), 0.0 } };
		List<List<Double>> actual = Distance.matrix(listOfPoints);
		for (int i = 0; i < expected.length; i++) {
			Double[] temp = new Double[5];
			assertArrayEquals(expected[i], actual.get(i).toArray(temp));
		}
	}

	@Test
	public void matrixListOfPointsEuclidean() {
		Double[][] expected = { { 0.0, Math.sqrt(5.0), Math.sqrt(11.0), Math.sqrt(21.0), Math.sqrt(14.0) },
				{ Math.sqrt(5.0), 0.0, Math.sqrt(6.0), Math.sqrt(38.0), 3.0 },
				{ Math.sqrt(11.0), Math.sqrt(6.0), 0.0, Math.sqrt(54.0), 3.0 },
				{ Math.sqrt(21.0), Math.sqrt(38.0), Math.sqrt(54.0), 0.0, Math.sqrt(35.0) },
				{ Math.sqrt(14.0), 3.0, 3.0, Math.sqrt(35.0), 0.0 } };
		List<List<Double>> actual = Distance.matrix(listOfPoints, new Euclidean());
		for (int i = 0; i < expected.length; i++) {
			Double[] temp = new Double[5];
			assertArrayEquals(expected[i], actual.get(i).toArray(temp));
		}
	}

	 @Test
	 public void matrixListOfPointsManhattan() {
	 Double[][] expected = { { 0.0, 3.0, 5.0, 7.0, 6.0 },
	 { 3.0, 0.0, 4.0, 10.0, 3.0 },
	 { 5.0, 4.0, 0.0, 10.0, 5.0 },
	 { 7.0, 10.0, 10.0, 0.0, 9.0 },
	 { 6.0, 3.0, 5.0, 9.0, 0.0 } };
	 List<List<Double>> actual = Distance.matrix(listOfPoints, (List<Double> p1, List<Double> p2) -> {
         double distance = 0;
         for (int i = 0; i < p1.size(); i++) {
             distance += Math.abs(p1.get(i) - p2.get(i));
         }
         return distance;
     });
	 for (int i = 0; i < expected.length; i++) {
	 Double[] temp = new Double[5];
	 assertArrayEquals(expected[i], actual.get(i).toArray(temp));
	 }
	 }

}