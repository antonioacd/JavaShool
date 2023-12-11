package com.javaschool.railway.transport.company.domain.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * GlobalExceptionHandler handles exceptions globally for the entire application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles generic exceptions and returns an INTERNAL_SERVER_ERROR response.
     *
     * @param e The exception to handle.
     * @return ResponseEntity with an error message and HTTP status 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Server error: " + e.getMessage());
    }

    /**
     * Handles AccessDeniedException and returns a FORBIDDEN response.
     *
     * @param e The AccessDeniedException to handle.
     * @return ResponseEntity with an error message and HTTP status 403.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Access denied: " + e.getMessage());
    }

    /**
     * Handles MethodArgumentNotValidException and returns a BAD_REQUEST response.
     *
     * @param e The MethodArgumentNotValidException to handle.
     * @return ResponseEntity with a validation error message and HTTP status 400.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Validation error: " + e.getMessage());
    }

    /**
     * Handles ConstraintViolationException and returns a BAD_REQUEST response.
     *
     * @param e The ConstraintViolationException to handle.
     * @return ResponseEntity with a constraint violation error message and HTTP status 400.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Constraint violation: " + e.getMessage());
    }

    /**
     * Handles MethodArgumentTypeMismatchException and returns a BAD_REQUEST response.
     *
     * @param e The MethodArgumentTypeMismatchException to handle.
     * @return ResponseEntity with a method argument type mismatch error message and HTTP status 400.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Method argument type mismatch: " + e.getMessage());
    }

    /**
     * Handles BindException and returns a BAD_REQUEST response.
     *
     * @param e The BindException to handle.
     * @return ResponseEntity with a bind error message and HTTP status 400.
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> handleBindException(BindException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Bind error: " + e.getMessage());
    }
}
