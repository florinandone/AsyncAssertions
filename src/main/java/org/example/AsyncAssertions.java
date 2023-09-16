package org.example;

import java.util.function.Supplier;
import java.util.concurrent.TimeoutException;

/**
 * AsyncAssertions is similar to org.junit.jupiter.api.Assertions,
 * the method name starts with "waitFor" and has all parameters: message and waitTimeSec.
 * The implementation will check every 100 ms and throw a timeout exception with a detailed message if validation fails.
 * The implementation will not use any external dependencies, so it will not depend on JUnit.
 */
public class AsyncAssertions {

    /**
     * Wait for a condition to become true, with a timeout.
     *
     * @param condition    The condition to evaluate.
     * @param message      The message to include in the exception if the condition is not met.
     * @param waitTimeSec  The maximum time to wait for the condition (in seconds).
     * @throws TimeoutException If the condition is not met within the specified timeout.
     */
    public static void waitForTrue(Supplier<Boolean> condition, String message, long waitTimeSec) throws TimeoutException {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + (waitTimeSec * 1000);

        while (System.currentTimeMillis() < endTime) {
            if (condition.get()) {
                return; // Condition is met
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }

        throw new TimeoutException(message);
    }

    /**
     * Wait for two long values to be equal, with a timeout.
     *
     * @param expected     The expected long value.
     * @param actual       The actual long value to compare with the expected value.
     * @param message      The message to include in the exception if the values are not equal.
     * @param waitTimeSec  The maximum time to wait for the values to become equal (in seconds).
     * @throws TimeoutException If the values are not equal within the specified timeout.
     */
    public static void waitForEqual(long expected, long actual, String message, long waitTimeSec) throws TimeoutException {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + (waitTimeSec * 1000);

        while (System.currentTimeMillis() < endTime) {
            if (expected == actual) {
                return; // Values are equal
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }

        throw new TimeoutException(message + " (Expected: " + expected + ", Actual: " + actual + ")");
    }
}
