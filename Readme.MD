## Requirement used to generate this

Create a java class with below specs and associated junit using jupiter.
The package name is org.example

Method list:

public static void waitForTrue(Supplier<Boolean> condition, String message, long waitTimeSec) throws TimeoutException



public static void waitForEqual(long expected, LongSupplier
actual, String message, long waitTimeSec) throws TimeoutException


waitForEqual will have in the exception message expected and actual.

Also, add to javadoc all the below.

AsyncAssertions is similar to org.junit.jupiter.api.Assertions, the method name start wait and has all parameters message and waitTimeSec.
The implementation will check each 100 ms and throw a timeout exception with a detailed message if validation fails.

The implementation will not use any external dependencies, so will not depend on junit.
 


