package com.tingisweb.assignment.errorhandling.exception;

/**
 * An exception class indicating that no content is found for a given request.
 */
public class NoContentException extends RuntimeException{

    /**
     * Constructs a new NoContentException with no detail message.
     */
    public NoContentException() {
    }

    /**
     * Constructs a new NoContentException with the specified detail message.
     *
     * @param message The detail message describing the exception.
     */
    public NoContentException(String message) {
        super(message);
    }
}
