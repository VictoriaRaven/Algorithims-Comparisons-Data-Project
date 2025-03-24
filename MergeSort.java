
/**
 * MergeSort sorts the data array passed by the Project1 benchmark measurement phase
 * It should sort the array values with a merge sort method (divide-and-conquer)
 * Merge sort recursively divides a list into sublists until each sublist contains only one element
 * Then repeatedly merges these sorted sublists back together until a single sorted list is obtained; 
 * Its critical operations are comparisons between elements during merging and the merging process itself.
 * It should calculate the elapsed time and counts using the AbstractSort
 * It should call incrmentCounts() to count the critical operations
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
public class MergeSort extends AbstractSort {
    
	/**
	 * Sorts the given array using the merge sort algorithm.
     * This method overrides the abstract sort method from AbstractSort.
	 * startSort() should Initialize timing and counter before sorting (AbstractSort)
	 * mergeSort() should Call the recursive merge sort method
	 * endSort() should Record the elapsed time and final count after sorting (AbstractSort)
	 * @param array The array to be sorted.
	 */
	@Override
    public void sort(int[] array) {
        startSort(); // Initialize timing and counter before sorting.
        mergeSort(array, 0, array.length - 1); // Call the recursive merge sort method.
        endSort(); //End the timing
    }

    /**
     * mergeSort() starts the method to sort the array
     * @param array passes in the data of the array to sort
     * @param left data in left or starting index
     * @param right data in right or ending index
     */
    private void mergeSort(int[] array, int left, int right) {
        if (left < right) {  // Base case: If the subarray has more than one element.
            int middle = (left + right) / 2;  // Calculate the middle index.

            mergeSort(array, left, middle); // Recursively sort the left half.
            mergeSort(array, middle + 1, right); // Recursively sort the right half.
            merge(array, left, middle, right); // Merge the two sorted halves.
        }
    }
    
    /**
     * merge() starts the merging of the elements and comparisons to sort it out
     * Merges two sorted sub arrays into a single sorted sub array.
     * @param array pass in the data of the two sub arrays to sort
     * @param left data in the left or starting index of the left sub array
     * @param middle data in the middle or ending index of the left sub array
     * @param right data in the right or ending index of the right sub array
     */
    private void merge(int[] array, int left, int middle, int right) {
        int n1 = middle - left + 1; // Size of the left sub array.
        int n2 = right - middle; // Size of the right sub array.

        // Create temporary arrays to hold the left and right sub arrays.
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];
        
        // Copy the elements from the original array into the temporary arrays.
        System.arraycopy(array, left, leftArray, 0, n1);
        System.arraycopy(array, middle + 1, rightArray, 0, n2);

        int i = 0, j = 0; // Indices for the left and right sub arrays
        int k = left; // Index for the merged sub array in the original array

        // Merge the two sub arrays back into the original array.
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) { // Compare elements from both subarrays
                array[k] = leftArray[i];
                i++;
                incrementCount(); // Critical Operation: (Comparison and Merging)
            } else {
                array[k] = rightArray[j];
                j++;
                incrementCount(); // Critical Operation: (Comparison and Merging)
            }
            k++;
        }
        
        // Copy any remaining elements from the left subarray.
        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }
        
        // Copy any remaining elements from the right subarray.
        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }
}

