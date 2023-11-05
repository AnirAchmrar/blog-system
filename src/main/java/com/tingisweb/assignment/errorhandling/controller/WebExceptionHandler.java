package com.tingisweb.assignment.errorhandling.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.tingisweb.assignment.errorhandling.ErrorMessage;
import com.tingisweb.assignment.errorhandling.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

/**
 * A controller advice class that handles exceptions and provides responses for various exceptions.
 */
@RestControllerAdvice
public class WebExceptionHandler {

    /**
     * Handles ObjectNotFoundException and returns a BAD_REQUEST response.
     *
     * @param exception The ObjectNotFoundException to handle.
     * @return A response entity with an error message.
     */
    @ExceptionHandler(value = {ObjectNotFoundException.class})
    public ResponseEntity<Object> objectNotFoundException(ObjectNotFoundException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .statusCode(400).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles MethodArgumentNotValidException and returns a BAD_REQUEST response.
     *
     * @param exception The MethodArgumentNotValidException to handle.
     * @return A response entity with an error message.
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .statusCode(400).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles MethodArgumentTypeMismatchException and returns a BAD_REQUEST response.
     *
     * @param exception The MethodArgumentTypeMismatchException to handle.
     * @return A response entity with an error message.
     */
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .statusCode(400).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles MissingPathVariableException and returns a BAD_REQUEST response.
     *
     * @param exception The MissingPathVariableException to handle.
     * @return A response entity with an error message.
     */
    @ExceptionHandler(value = {MissingPathVariableException.class})
    public ResponseEntity<Object> missingPathVariableException(MissingPathVariableException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message("Missing path variable")
                .timestamp(LocalDateTime.now())
                .statusCode(400).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles IllegalArgumentException and returns a BAD_REQUEST response.
     *
     * @param exception The IllegalArgumentException to handle.
     * @return A response entity with an error message.
     */
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> illegalArgumentException(IllegalArgumentException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .statusCode(400).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles HttpMessageNotReadableException and returns a BAD_REQUEST response.
     *
     * @param exception The HttpMessageNotReadableException to handle.
     * @return A response entity with an error message.
     */
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<Object> httpMessageNotReadableException(HttpMessageNotReadableException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message("Empty body request")
                .timestamp(LocalDateTime.now())
                .statusCode(400).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles HttpMessageConversionException and returns a BAD_REQUEST response.
     *
     * @param exception The HttpMessageConversionException to handle.
     * @return A response entity with an error message.
     */
    @ExceptionHandler(value = {HttpMessageConversionException.class})
    public ResponseEntity<Object> httpMessageConversionException(HttpMessageConversionException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message("Empty body request")
                .timestamp(LocalDateTime.now())
                .statusCode(400).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles FailedAuthenticationException and returns an UNAUTHORIZED response.
     *
     * @param exception The FailedAuthenticationException to handle.
     * @return A response entity with an error message.
     */
    @ExceptionHandler(value = {FailedAuthenticationException.class})
    public ResponseEntity<Object> failedAuthenticationException(FailedAuthenticationException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .statusCode(401).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles HttpRequestMethodNotSupportedException and returns a BAD_REQUEST response.
     *
     * @param exception The HttpRequestMethodNotSupportedException to handle.
     * @return A response entity with an error message.
     */
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<Object> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .statusCode(400).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles JWTVerificationException and returns an UNAUTHORIZED response.
     *
     * @param exception The JWTVerificationException to handle.
     * @return A response entity with an error message.
     */
    @ExceptionHandler(value = {JWTVerificationException.class})
    public ResponseEntity<Object> jwtVerificationException(JWTVerificationException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .statusCode(400).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles NoContentException and returns a NO_CONTENT response.
     *
     * @param exception The NoContentException to handle.
     * @return A response entity with an error message.
     */
    @ExceptionHandler(value = {NoContentException.class})
    public ResponseEntity<Object> noContentException(NoContentException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message("No content found!")
                .timestamp(LocalDateTime.now())
                .statusCode(204).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.NO_CONTENT);
    }

    /**
     * Handles EditAnotherEntityException and returns a BAD_REQUEST response.
     *
     * @param exception The EditAnotherEntityException to handle.
     * @return A response entity with an error message.
     */
    @ExceptionHandler(value = {EditAnotherEntityException.class})
    public ResponseEntity<Object> editAnotherEntityException(EditAnotherEntityException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message("Trying to edit another entity")
                .timestamp(LocalDateTime.now())
                .statusCode(400).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles UnauthorizedException and returns an UNAUTHORIZED response.
     *
     * @param exception The UnauthorizedException to handle.
     * @return A response entity with an error message.
     */
    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<Object> unauthorizedException(UnauthorizedException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message("Unauthorized action")
                .timestamp(LocalDateTime.now())
                .statusCode(401).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles MissingIdException and returns an BAD_REQUEST response.
     *
     * @param exception The MissingIdException to handle.
     * @return A response entity with an error message.
     */
    @ExceptionHandler(value = {MissingIdException.class})
    public ResponseEntity<Object> missingIdException(MissingIdException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message("Missing entity id")
                .timestamp(LocalDateTime.now())
                .statusCode(400).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

}
