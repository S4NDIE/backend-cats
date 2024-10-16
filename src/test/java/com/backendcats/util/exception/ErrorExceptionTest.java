package com.backendcats.util.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorExceptionTest {

    @Test
    void TestErrorExceptionWithMessage() {
        String message = "Test error message";

        ErrorException exception = new ErrorException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    void TestErrorExceptionWithMessageAndCause() {
        String message = "Test error message";
        Throwable cause = new RuntimeException("Cause of the error");

        ErrorException exception = new ErrorException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
