package com.tingisweb.assignment.errorhandling.exception;

/**
 * An exception class indicating a missing entity ID, which is required for an operation.
 */
public class MissingIdException extends RuntimeException{

    /**
     * Constructs a new MissingIdException with no detail message.
     */
    public MissingIdException() {
    }

    /**
     * Constructs a new MissingIdException with the specified detail message.
     *
     * @param message The detail message describing the exception.
     */
    public MissingIdException(String message) {
        super(message);
    }
}
