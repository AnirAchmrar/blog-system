package com.tingisweb.assignment.errorhandling.exception;

/**
 * An exception class indicating that an object or entity was not found.
 */
public class ObjectNotFoundException extends RuntimeException{

    /**
     * Constructs a new ObjectNotFoundException with no detail message.
     */
    public ObjectNotFoundException() {
    }

    /**
     * Constructs a new ObjectNotFoundException with the specified detail message.
     *
     * @param message The detail message describing the exception.
     */
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
