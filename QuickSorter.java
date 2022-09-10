package cs3345_project3;

import java.time.*;
import java.util.*;

public class QuickSorter {
	
	// private QuickSorter() {}
	
	// PivotStrategy type for calls
	public static enum PivotStrategy {
		FIRST_ELEMENT,
		RANDOM_ELEMENT,
		MEDIAN_OF_TWO_ELEMENTS,
		MEDIAN_OF_THREE_ELEMENTS,
		MEDIAN_OF_THREE_RANDOM_ELEMENTS
	}
	
	/**
	 * Function takes in an integer, size, and returns a randomly generated ArrayList<Integer> 
	 * with size elements
	 * @param size
	 * @return
	 */
	public static ArrayList<Integer> generateRandomList(int size) throws IllegalArgumentException {
		
		// if argument is 0 or negative, throw exception
		if (size < 1) {
			throw new IllegalArgumentException("Size cannot be negative or 0.");
		}
		
		// create new ArrayList<Integer>
		ArrayList<Integer> list = new ArrayList<Integer>(size);
		Random r = new Random();
		
		// generate random data for ArrayList
		for (int i = 0; i < size; i++) {
			int data = r.nextInt();
			list.add(data);
		}
		
		return list;
		
	} // generateRandomList
	
	/**
	 * Function performs a swap of two elements in ArrayList<Integer> a and b
	 * @param <E>
	 * @param list
	 * @param a
	 * @param b
	 */
	private static <E extends Comparable<E>> void swap(ArrayList<E> list, int a, int b) {
		
		E temp = list.get(a);
		list.set(a, list.get(b));
		list.set(b, temp);
		
	} // swap
	
	/**
	 * Function performs a quickSort on two elements and places all smaller elements to left of the pivot and all 
	 * larger elements to the right of the pivot 
	 * @param <E>
	 * @param list
	 * @param lowerElement
	 * @param higherElement
	 * @return
	 */
	private static <E extends Comparable<E>> int quickSort(ArrayList<E> list, int lowerElement, int higherElement) {
		
		// get value of higherElement 
		E pivotValue = list.get(higherElement);
		int i = lowerElement - 1;
		
		// compare all elements and sort by swap function
		for (int j = lowerElement; j < higherElement; j++) {
			if (list.get(j).compareTo(pivotValue) <= 0) {
				i++;
				swap(list, i, j);
			}
		}
		
		// place higherElement in new spot and return the index of the higherElement
		swap(list, i + 1, higherElement);
		return i + 1;
		
	} // quickSort
	
	/**
	 * Function performs quickSort using the first element
	 * @param <E>
	 * @param list
	 * @param lowerElement
	 * @param higherElement
	 */
	private static <E extends Comparable<E>> void quickSort_firstElement(ArrayList<E> list, int lowerElement, int higherElement) {
		
		// if lowerElement is greater, do not sort
		if (lowerElement >= higherElement) 
			return;
		
		// perform the swap
		swap(list, lowerElement, higherElement);
		int pivot = quickSort(list, lowerElement, higherElement);
		
		// recursively call the function to continue sorting
		quickSort_firstElement(list, lowerElement, pivot - 1);
		quickSort_firstElement(list, pivot + 1, higherElement);
		
	} // quickSort_firstElement
	
	/**
	 * Function creates a new random integer given a min and max value
	 * @param min
	 * @param max
	 * @return
	 * @throws IllegalArgumentException
	 */
	private static int rand (int min, int max) throws IllegalArgumentException {
	
		// min and max are not valid
		if (min > max) 
			throw new IllegalArgumentException("Invalid min and max values");
		
		return new Random().nextInt(max - min + 1) + min;
		
	} // rand
	
	/**
	 * Function performs quickSort with random element indexes
	 * @param <E>
	 * @param list
	 * @param lowerElement
	 * @param higherElement
	 */
	private static <E extends Comparable<E>> void quickSort_randomElement(ArrayList<E> list, int lowerElement, int higherElement) {
		
		// if lowerElement is greater, do not sort
		if (lowerElement >= higherElement) 
			return;
		
		// pivot and new random element index
		int randomPivot = rand(lowerElement, higherElement);
		swap(list, randomPivot, higherElement);
		
		// perform quickSort
		int pivot = quickSort(list, lowerElement, higherElement);
		quickSort_randomElement(list, lowerElement, pivot - 1);
		quickSort_randomElement(list, pivot + 1, higherElement);
		
	} // quickSort_randomElement
	
	
	// http://www.java2s.com/example/java-utility-method/median/median-arraylist-double-values-82543.html
	/**
	 * Function finds median of given list for two elements
	 * @param <E>
	 * @param list
	 * @return
	 */
	private static <E extends Comparable<E>> int median(ArrayList<E> list) {
		
		Collections.sort(list);
		
		if (list.size() % 2 == 1) {
			return ((list.size() + 1) / 2 - 1);
		}
		
		else {
			int lower = (list.size() / 2 - 1);
			int higher = (list.size() / 2 - 1);
			return ((lower + higher) / 2);
		}
		
	} // median
	
	/**
	 * Function uses the median of the two given elements to quickSort
	 * @param <E>
	 * @param list
	 * @param lowerElement
	 * @param higherElement
	 */
	private static <E extends Comparable<E>> void quickSort_medianOfTwo(ArrayList<E> list, int lowerElement, int higherElement) {
		
		// if lowerElement is greater, do not sort
		if (lowerElement >= higherElement) 
			return;
		
		// find median for pivot and swap if median is not equal to higherElement
		int medianPivot = median(list);
		if (medianPivot != higherElement) 
			swap(list, medianPivot, higherElement);
		
		// perform quickSort
		int pivot = quickSort(list, lowerElement, higherElement);
		quickSort_medianOfTwo(list, lowerElement, pivot - 1);
		quickSort_medianOfTwo(list, pivot + 1, higherElement);
		
	} // quickSort_medianOfTwo
	
	// https://stackoverflow.com/questions/7559608/median-of-three-values-strategy
	/**
	 * Function returns the median of three elements
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	private static int medianOfThree(int a, int b, int c) {
		
		if ((a > b) ^ (a > c)) 
			return a;
		else if ((b < a) ^ (b < c))
			return b;
		else
			return c;
		
	} // medianOfThree
	
	/**
	 * Function performs quickSort using the median of three numbers dictated by the lower and higher elements
	 * @param <E>
	 * @param list
	 * @param lowerElement
	 * @param higherElement
	 */
	private static <E extends Comparable<E>> void quickSort_medianOfThree(ArrayList<E> list, int lowerElement, int higherElement) {
		
		// if lowerElement is greater, do not sort
		if (lowerElement >= higherElement) 
			return;
				
		// find median for pivot and swap if median is not equal to higherElement
		int medianPivot = medianOfThree(lowerElement, higherElement, (lowerElement + higherElement) / 2);
		if (medianPivot != higherElement) 
			swap(list, medianPivot, higherElement);
				
		// perform quickSort
		int pivot = quickSort(list, lowerElement, higherElement);
		quickSort_medianOfThree(list, lowerElement, pivot - 1);
		quickSort_medianOfThree(list, pivot + 1, higherElement);
		
	} // quickSort_medianOfThree
	
	/**
	 * Function performs quickSort using the median of three random numberss
	 * @param <E>
	 * @param list
	 * @param lowerElement
	 * @param higherElement
	 */
	private static <E extends Comparable<E>> void quickSort_medianOfThreeRandomElements(ArrayList<E> list, int lowerElement, int higherElement) {
		
		// if lowerElement is greater, do not sort
		if (lowerElement >= higherElement) 
			return;
		
		// create random numbers
		int a = rand(lowerElement, higherElement);
		int b = rand(lowerElement, higherElement);
		int c = rand(lowerElement, higherElement);
		
		// create median pivot
		int medianPivot = medianOfThree(a, b, c);
		if (medianPivot != higherElement)
			swap(list, medianPivot, higherElement);
		
		// perform quickSort
		int pivot = quickSort(list, lowerElement, higherElement);
		quickSort_medianOfThreeRandomElements(list, lowerElement, pivot - 1);
		quickSort_medianOfThreeRandomElements(list, pivot + 1, higherElement);
		
		
	} // quickSort_medianOfThreeRandomElements
	
	/**
	 * Function controls flow of quickSort functions and times
	 * @param <E>
	 * @param list
	 * @param pivotStrategy
	 * @return
	 * @throws NullPointerException
	 */
	public static <E extends Comparable<E>> Duration timedQuickSort(ArrayList<E> list, PivotStrategy pivotStrategy) throws NullPointerException {
		
		// null argument exceptions
		if (list.isEmpty()) {
			throw new NullPointerException("List is empty.");
		}
		if (pivotStrategy == null) {
			throw new NullPointerException("Pivot strategy could not be found.");
		}
		
		long startTime = System.nanoTime();
		
		if (pivotStrategy == PivotStrategy.FIRST_ELEMENT) {
			quickSort_firstElement(list, 0, list.size() - 1);
		}
		
		else if (pivotStrategy == PivotStrategy.RANDOM_ELEMENT) {
			quickSort_randomElement(list, 0, list.size() - 1);
		}
		
		else if (pivotStrategy == PivotStrategy.MEDIAN_OF_TWO_ELEMENTS) {
			quickSort_medianOfTwo(list, 0, list.size() - 1);
		}
		
		else if (pivotStrategy == PivotStrategy.MEDIAN_OF_THREE_ELEMENTS) {
			quickSort_medianOfThree(list, 0, list.size() - 1);
		}
		
		else if (pivotStrategy == PivotStrategy.MEDIAN_OF_THREE_RANDOM_ELEMENTS) {
			quickSort_medianOfThreeRandomElements(list, 0, list.size() - 1);
		}
		
		long endTime = System.nanoTime();
		return (Duration.ofNanos(endTime - startTime));
		
	} // timedQuickSort
	
} // public class QuickSorter
