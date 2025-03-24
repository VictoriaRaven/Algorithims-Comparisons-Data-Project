
/**
 * The UnsortedException class defines a custom exception to be thrown when
 * operations are attempted on unsorted data.  This allows calling code to
 * specifically handle situations where data is not in the expected sorted order.
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
public class UnsortedException extends Exception {
	
	/**
	 * Constructs a new UnsortedException with the specified detail message.
     * The message can be retrieved later by the Throwable.getMessage() method.
	 * @param message the detail message of the exception error
	 */
    public UnsortedException(String message) {
        super(message); // Call the superclass (Exception) constructor to initialize the message.
    }
}

