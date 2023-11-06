package com.tingisweb.assignment.errorhandling.exception;

/**
 * An exception class indicating an attempt to edit another entity, which is not allowed.
 */
public class EditAnotherEntityException extends RuntimeException{

    /**
     * Constructs a new EditAnotherEntityException with no detail message.
     */
    public EditAnotherEntityException() {
    }

    /**
     * Constructs a new EditAnotherEntityException with the specified detail message.
     *
     * @param message The detail message describing the exception.
     */
    public EditAnotherEntityException(String message) {
        super(message);
    }
}
