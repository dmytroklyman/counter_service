package de.klyman.increment.exception;

public class MaxValueReachedException extends Exception {
	public MaxValueReachedException(String errorMessage) {
        super(errorMessage);
    }
}
