
import java.util.*;

/**
 * BucketSort sorts the data array passed by the Project1 benchmark measurement phase
 * It should sort the array values into buckets then use the same merge sort method (divide-and-conquer) from MergeSort.java
 * Bucket sort distributes elements into buckets, then sorts each bucket individually using merge sort(MergeSort.java) for smaller buckets
 * It should also concatenates the sorted buckets to produce the final sorted list
 * It can do this by distribution phase where elements are placed into buckets
 * And collection phase where the sorted buckets are combined.
 * Its critical operations are comparisons between elements during merging and the merging process itself.
 * It should calculate the elapsed time and counts using the AbstractSort
 * It should call incrementCounts() to count the critical operations
 * Reference: Code modified using old example that GeeksforGeeks provided.
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
public class BucketSort extends AbstractSort {
    
	/**
	 * Sorts the given array using the bucket sort algorithm.
	 * It should also incorporate the merge sort as well into the bucket algor with MergeSort.java
     * This method overrides the abstract sort method from AbstractSort.
	 * startSort() should Initialize timing and counter before sorting (AbstractSort)
	 * mergeSort() should Call the recursive merge sort method
	 * endSort() should Record the elapsed time and final count after sorting (AbstractSort)
	 * @param array The array to be sorted.
	 */
	@Override
    public void sort(int[] array) {
        startSort(); // Initialize timing and counter before sorting.
        bucketSort(array); // Sorts the array in bucket method
        endSort(); // End the timing
    }
	
	/**
     * Sorts the given array using the bucket sort algorithm.
     * @param array The array to be sorted.
	 */
    private void bucketSort(int[] array) {
    	// Find the maximum value in the array to determine bucket range.
        int maxVal = Arrays.stream(array).max().orElse(Integer.MAX_VALUE);
        int n = array.length;
        // Determine the number of buckets based on the square root of the array size.
        int bucketCount = (int) Math.sqrt(n);
        
        // Create an array of buckets (ArrayLists).
        List<Integer>[] buckets = new List[bucketCount];
        for (int i = 0; i < bucketCount; i++) {
            buckets[i] = new ArrayList<>();
        }
        
        // Distribution phase: Place each element of the input array into a bucket.
        for (int value : array) {
        	incrementCount(); // Critical Operation: (Distribution Phase)// Distribution phase: Place each element of the input array into a bucket.
        	// Calculate the bucket index based on the value and maximum value.
            // This ensures a roughly even distribution.
            int bucketIndex = (int) ((double) value / maxVal * (bucketCount - 1));
            buckets[bucketIndex].add(value);
        }
        
        // Index for placing sorted elements back into the original array.
        int index = 0;
        MergeSort mergeSort = new MergeSort(); // Use MergeSort to sort each bucket.
        
        // Collection phase: Iterate through the buckets, sort each bucket, and collect the elements.
        for (List<Integer> bucket : buckets) {
        	// Convert the bucket (ArrayList) to an integer array for MergeSort
            int[] bucketArray = bucket.stream().mapToInt(Integer::intValue).toArray();
            // Sort the current bucket using MergeSort from (MergeSort.java)
            mergeSort.sort(bucketArray); // (Collection Phase)
            incrementCount(mergeSort.getCount()); // Critical Operation: Add MergeSort(Comparisons and Merge) to BucketSort
            // Copy the sorted elements from the bucket back into the original array.
            for (int value : bucketArray) {
            	incrementCount(); // Critical Operation: (Collection Phase)
                array[index++] = value;
            }
        }
    }
}

