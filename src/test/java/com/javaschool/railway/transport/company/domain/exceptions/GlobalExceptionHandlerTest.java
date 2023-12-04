package com.javaschool.railway.transport.company.domain.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleException_ShouldReturnInternalServerError() {
        ResponseEntity<String> response = globalExceptionHandler.handleException(new RuntimeException("Test exception"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void handleAccessDeniedException_ShouldReturnForbidden() {
        ResponseEntity<String> response = globalExceptionHandler.handleAccessDeniedException(new AccessDeniedException("Access denied"));
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Access denied: Access denied", response.getBody());
    }

    @Test
    void handleConstraintViolation_ShouldReturnBadRequest() {
        ResponseEntity<String> response = globalExceptionHandler.handleConstraintViolation(new org.hibernate.exception.ConstraintViolationException("Constraint violation", null, null));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Constraint violation: Constraint violation", response.getBody());
    }

    @Test
    void handleMethodArgumentTypeMismatch_ShouldReturnBadRequest() {
        ResponseEntity<String> response = globalExceptionHandler.handleMethodArgumentTypeMismatch(new MethodArgumentTypeMismatchException(null, null, null, null, null));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void handleBindException_ShouldReturnBadRequest() {
        // Create a dummy target object
        Object target = new Object();

        // Create a BindingResult with an error
        BindingResult bindingResult = new BeanPropertyBindingResult(target, "objectName");
        bindingResult.addError(new FieldError("objectName", "fieldName", "rejectedValue", false, null, null, "defaultMessage"));

        // Create a BindException with the BindingResult
        org.springframework.validation.BindException bindException = new org.springframework.validation.BindException(bindingResult);

        ResponseEntity<String> response = globalExceptionHandler.handleBindException(bindException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
