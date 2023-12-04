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

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "Server error: ";
    private static final String FORBIDDEN_MESSAGE = "Access denied: ";
    private static final String VALIDATION_ERROR_MESSAGE = "Validation error: ";
    private static final String CONSTRAINT_VIOLATION_MESSAGE = "Constraint violation: ";
    private static final String METHOD_ARGUMENT_TYPE_MISMATCH_MESSAGE = "Method argument type mismatch: ";
    private static final String BIND_ERROR_MESSAGE = "Bind error: ";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(INTERNAL_SERVER_ERROR_MESSAGE + e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(FORBIDDEN_MESSAGE + e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(VALIDATION_ERROR_MESSAGE + e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CONSTRAINT_VIOLATION_MESSAGE + e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(METHOD_ARGUMENT_TYPE_MISMATCH_MESSAGE + e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> handleBindException(BindException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BIND_ERROR_MESSAGE + e.getMessage());
    }
}
