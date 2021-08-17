package de.klyman.increment.exception;

public class InvalidCredentialsException extends Exception {
	public InvalidCredentialsException(String errorMessage) {
        super(errorMessage);
    }
}
