package com.tingisweb.assignment.errorhandling.exception;

public class EditAnotherEntityException extends RuntimeException{

    public EditAnotherEntityException() {
    }

    public EditAnotherEntityException(String message) {
        super(message);
    }
}
