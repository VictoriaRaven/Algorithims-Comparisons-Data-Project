
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Project 1 is for Created Data with Merge and Bucket Sort
 * It uses a Main Class to create data based on Merge and Bucket sort onto text files
 * It should throw an exception if an array could not be sorted
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
public class Project1 {

    public static final int WARMUP_ITERATIONS = 10000; // Number of warm-up iterations; 10k for accuracy; you can modify it with a shorter number.
    public static final int BENCHMARK_ITERATIONS = 40; // Number of measurement iterations

    /**
     * The Main function to generate the data into the text files for merge and bucket sort
     * @param args runs the main program
     * @throws UnsortedException throws an exception message if array is unsorted with UnsortedException.java
     */
    public static void main(String[] args) throws UnsortedException {
        // Create the file writers
        String[] filenames = {"MergeSort.txt", "BucketSort.txt"};
        try (BufferedWriter writer1 = new BufferedWriter(new FileWriter(filenames[0]));
             BufferedWriter writer2 = new BufferedWriter(new FileWriter(filenames[1]))) {

            // Loop through data set sizes
            for (int size = 100; size <= 1200; size += 100) { // ex: 100,200,300,400,500,600,700,800,900,1000,1100,1200
                // Store the results of the 40 runs for this data set size
                long[] mergeSortCounts = new long[BENCHMARK_ITERATIONS];
                long[] mergeSortTimes = new long[BENCHMARK_ITERATIONS];
                long[] bucketSortCounts = new long[BENCHMARK_ITERATIONS];
                long[] bucketSortTimes = new long[BENCHMARK_ITERATIONS];
                for (int i = 0; i < BENCHMARK_ITERATIONS; i++) {
                    int[] array = generateRandomArray(size);
                    int[] copyArray = array.clone(); // copy it so that both sorts use same data
                    // Warm-up phase
                    for (int j = 0; j < WARMUP_ITERATIONS; j++) { // Warm-up loop
                        benchmarkAndDiscard(new MergeSort(), array.clone());
                        benchmarkAndDiscard(new BucketSort(), copyArray.clone());
                    }
                    // Benchmark phase
                    benchmarkAndStoreResult(new MergeSort(), array, size, mergeSortCounts, mergeSortTimes, i);
                    benchmarkAndStoreResult(new BucketSort(), copyArray, size, bucketSortCounts, bucketSortTimes, i);
                }
                // After all runs, write the raw results for this data set size
                writeRawData(writer1, mergeSortCounts, mergeSortTimes, size);
                writeRawData(writer2, bucketSortCounts, bucketSortTimes, size);
                // Write a newline after each size's results
                writer1.newLine();
                writer2.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Generates random data for each array (function)
     * @param size generates the data for each array based on rand.nextInt.
     * @return array returns random generated data for the array
     */
    private static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(size * 2); // Generate random numbers
        }
        return array;
    }
    
    /**
     * Performs the Warm-up phase JVM tests before final benchmark measurement phase
     * @param sorter takes the array and starts to sort the array either in merge or bucket
     * @param array this is the array of the randomly generated one
     */
    private static void benchmarkAndDiscard(AbstractSort sorter, int[] array) {
        sorter.sort(array); // Perform sorting for the warm-up phase
    }

    /**
     * Performs the Benchmark phase or measurement phase to put on the txt files
     * @param sorter tells what type of sort it will use; merge/bucket
     * @param array inputs the array of the randomly generated data
     * @param size inputs the size
     * @param counts inputs the critical operational counts for the sort
     * @param times inputs the overall time it takes to finish sort (elapsed time = end-start)
     * @param index inputs the index to count and store the data
     * @throws UnsortedException this is if the array could not be sorted or any exception
     */
    private static void benchmarkAndStoreResult(AbstractSort sorter, int[] array, int size, long[] counts, long[] times, int index) throws UnsortedException {
        sorter.sort(array); // Perform sorting for the measurement phase
        if (!isSorted(array)) {
            throw new UnsortedException("Array not sorted after " + sorter.getClass().getSimpleName() + ": " + array.toString());
        }
        counts[index] = sorter.getCount(); // puts critical operational counts into array
        times[index] = sorter.getTime();// puts overall time into array
    }

    /**
     * this method/function checks if array is sorted or not
     * @param array inputs the array to check if it is sorted or not
     * @return true/false this indicates a flag to throw the UnsortedException
     */
    private static boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * This writes the raw results (the size once; then critical counts and times per each trial) to the file
     * @param writer the file to write on
     * @param counts write the counts
     * @param times write the times
     * @param size write the size
     * @throws IOException this is if there is an error or exception
     */
    private static void writeRawData(BufferedWriter writer, long[] counts, long[] times, int size) throws IOException {
        writer.write(size+ " ");
    	for (int i = 0; i < BENCHMARK_ITERATIONS; i++) {
            writer.write(counts[i] + " " + times[i] + " ");
        }
    }
}

