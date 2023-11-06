package com.tingisweb.assignment.errorhandling.exception;

/**
 * An exception class indicating an unauthorized action or access.
 */
public class UnauthorizedException extends RuntimeException{

    /**
     * Constructs a new UnauthorizedException with no detail message.
     */
    public UnauthorizedException() {
    }

    /**
     * Constructs a new UnauthorizedException with the specified detail message.
     *
     * @param message The detail message describing the unauthorized action or access.
     */
    public UnauthorizedException(String message) {
        super(message);
    }
}
