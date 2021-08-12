package de.klyman.increment.exception;

public class MinValueReachedException extends Exception {
	public MinValueReachedException(String errorMessage) {
        super(errorMessage);
    }
}
