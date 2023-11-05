package com.tingisweb.assignment.errorhandling.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * An exception class indicating a failed authentication attempt.
 */
public class FailedAuthenticationException extends AuthenticationException {

    /**
     * Constructs a new FailedAuthenticationException with the specified error message.
     *
     * @param msg The error message describing the authentication failure.
     */
    public FailedAuthenticationException(String msg) {
        super(msg);
    }
}
