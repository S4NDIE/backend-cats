package com.backendcats.util.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotAuthorizedExceptionTest {
    @Test
    void TestConstructor() {
        NotAuthorizedException actualNotAuthorizedException = new NotAuthorizedException("An error occurred");
        assertNull(actualNotAuthorizedException.getCause());
        assertEquals(0, actualNotAuthorizedException.getSuppressed().length);
        assertEquals("An error occurred", actualNotAuthorizedException.getMessage());
        assertEquals("An error occurred", actualNotAuthorizedException.getLocalizedMessage());
    }

    @Test
    void TestConstructor2() {
        Throwable cause = new Throwable();
        NotAuthorizedException actualNotAuthorizedException = new NotAuthorizedException("An error occurred", cause);

        Throwable cause2 = actualNotAuthorizedException.getCause();
        assertSame(cause, cause2);
        Throwable[] suppressed = actualNotAuthorizedException.getSuppressed();
        assertEquals(0, suppressed.length);
        assertEquals("An error occurred", actualNotAuthorizedException.getLocalizedMessage());
        assertEquals("An error occurred", actualNotAuthorizedException.getMessage());
        assertNull(cause2.getLocalizedMessage());
        assertNull(cause2.getCause());
        assertNull(cause2.getMessage());
        assertSame(suppressed, cause2.getSuppressed());
    }
}

