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

@RestControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(value = {ObjectNotFoundException.class})
    public ResponseEntity<Object> objectNotFoundException(ObjectNotFoundException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .statusCode(400).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .statusCode(400).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .statusCode(400).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MissingPathVariableException.class})
    public ResponseEntity<Object> missingPathVariableException(MissingPathVariableException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message("Missing path variable")
                .timestamp(LocalDateTime.now())
                .statusCode(400).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> illegalArgumentException(IllegalArgumentException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .statusCode(400).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<Object> httpMessageNotReadableException(HttpMessageNotReadableException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message("Empty body request")
                .timestamp(LocalDateTime.now())
                .statusCode(400).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {HttpMessageConversionException.class})
    public ResponseEntity<Object> httpMessageConversionException(HttpMessageConversionException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message("Empty body request")
                .timestamp(LocalDateTime.now())
                .statusCode(400).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {FailedAuthenticationException.class})
    public ResponseEntity<Object> failedAuthenticationException(FailedAuthenticationException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .statusCode(401).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<Object> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .statusCode(400).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {JWTVerificationException.class})
    public ResponseEntity<Object> jwtVerificationException(JWTVerificationException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .statusCode(400).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {NoContentException.class})
    public ResponseEntity<Object> noContentException(NoContentException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message("No content found!")
                .timestamp(LocalDateTime.now())
                .statusCode(400).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {EditAnotherEntityException.class})
    public ResponseEntity<Object> editAnotherEntityException(EditAnotherEntityException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message("Trying to edit another entity")
                .timestamp(LocalDateTime.now())
                .statusCode(400).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<Object> unauthorizedException(UnauthorizedException exception){
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .message("Unauthorized action")
                .timestamp(LocalDateTime.now())
                .statusCode(401).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

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
