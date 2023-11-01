package com.tingisweb.assignment.errorhandling.exception;

public class MissingIdException extends RuntimeException{

    public MissingIdException() {
    }

    public MissingIdException(String message) {
        super(message);
    }
}
