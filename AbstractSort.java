
/**
 * AbstractSort is for sorting algorithms, requiring subclasses to implement the actual sorting logic
 * It offers built-in methods to track the time and count of critical operations.
 * This allows for standardized performance measurement across different sorting implementations.
 * It should store the values: count;startTime;endTime;elapsedTime;
 * <p>
 * Course: CMSC 451
 * <p>
 * Date: 1/31/2025
 * <p>
 * Project: Project 1
 *
 * @author Victoria Lee
 *
 * @version JRE17
 */
public abstract class AbstractSort {
	
	/**
	 * count stores the count of critical operations
	 */
	private long count;
	
	/**
	 * startTime stores the start time
	 */
    private long startTime;
    
	/**
	 * endTime stores the end time
	 */
    private long endTime;
    
	/**
	 * elapsedTime stores the calculated time
	 */
    private long elapsedTime;

    /**
     * 
     * @param array
     */
    public abstract void sort(int[] array);

    /**
     * startSort() starts count at 0 and then reset counter to 0.
     * It also starts the time using System.nanoTime() for accuracy
     */
    public void startSort() {
        count = 0;
        startTime = System.nanoTime();
    }

    /**
     * endSort() ends the time using System.nanoTime() for accuracy
     * It also stores and calculates the elapsed time with endTime - startTime
     */
    public void endSort() {
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;
    }

    /**
     * incrementCount() increments count by 1 and stores the count of critical operations
     */
    public void incrementCount() {
        count++;
    }
    
    /**
     * incrementCount() can pass a number to add onto the count of critical operations
     */
    public void incrementCount(long i) {
    	count+=i;
    }

    /**
     * getCount() returns the total count of critical operations
     */
    public long getCount() {
        return count;
    }

    /**
     * getCount() returns the total time or elapsed time
     */
    public long getTime() {
        return elapsedTime; // Return the STORED time
    }
}