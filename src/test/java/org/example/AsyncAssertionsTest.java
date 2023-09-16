package org.example;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

import static org.example.AsyncAssertions.waitForEqual;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AsyncAssertionsTest {

    @Test
    void testWaitForTrue() {
        // Test a condition that becomes true within the timeout
        assertDoesNotThrow(() -> AsyncAssertions.waitForTrue(() -> true, "Condition is true", 5));

        // Test a condition that never becomes true within the timeout
        assertThrows(TimeoutException.class, () -> {
            boolean condition = false;
            AsyncAssertions.waitForTrue(() -> condition, "Condition is never true", 2);
        });
    }

    @Test
    void testWaitForEqual() {
        // Test equal values within the timeout
        long expected = 42;
        long actual = 42;

        assertDoesNotThrow(() -> waitForEqual(expected, ()->actual, "Values are equal", 5));

        // Test unequal values that become equal within the timeout
        long expected2 = 10;
       AtomicLong actual2 = new AtomicLong(0); // Create an effectively final array to hold the actual value

        assertDoesNotThrow(() -> {
            Thread t = new Thread(() -> {
                try {
                    Thread.sleep(200);
                    actual2.set(10); // Update the value in the array
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
            });
            t.start();
            waitForEqual(expected2, actual2::get, "Values become equal", 5);
        });

        // Test values that never become equal within the timeout
        assertThrows(TimeoutException.class, () -> waitForEqual(5, ()->10, "Values never become equal", 2));
    }
}
