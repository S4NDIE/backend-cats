package com.backendcats.util.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void TestHandleValidationErrors2() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        ResponseEntity<Map<String, List<String>>> actualHandleValidationErrorsResult = globalExceptionHandler
                .handleValidationErrors(
                        new MethodArgumentNotValidException(null, new BindException("Target", "Object Name")));
        assertEquals(1, actualHandleValidationErrorsResult.getBody().size());
        assertTrue(actualHandleValidationErrorsResult.hasBody());
        assertTrue(actualHandleValidationErrorsResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.BAD_REQUEST, actualHandleValidationErrorsResult.getStatusCode());
    }



    @Test
    void UnauthorizedException() {
        NotAuthorizedException ex = new NotAuthorizedException("Unauthorized access");

        ResponseEntity<Map<String, List<String>>> response = globalExceptionHandler.unauthorizedException(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().containsKey("errors"));
        assertEquals(Collections.singletonList("Unauthorized access"), response.getBody().get("errors"));
    }

    @Test
    void HandleGeneralExceptions() {
        Exception ex = new Exception("General error");

        ResponseEntity<Map<String, List<String>>> response = globalExceptionHandler.handleGeneralExceptions(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().containsKey("errors"));
        assertEquals(Collections.singletonList("General error"), response.getBody().get("errors"));
    }

    @Test
    void HandleRuntimeExceptions() {
        RuntimeException ex = new RuntimeException("Runtime error");

        ResponseEntity<Map<String, List<String>>> response = globalExceptionHandler.handleRuntimeExceptions(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().containsKey("errors"));
        assertEquals(Collections.singletonList("Runtime error"), response.getBody().get("errors"));
    }
}
