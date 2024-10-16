package com.backendcats.util.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    @Value("${application.security-secret-key-jwt}")
    private String secretKey = "test-secret-key";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtUtil = new JwtUtil();
        jwtUtil.secretKey = secretKey;
    }

    @Test
    void TestGenerateToken() {
        String username = "testuser";

        String token = jwtUtil.generateToken(username);

        assertNotNull(token);
    }

    @Test
    void TestValidateToken_ValidToken() {
        String token = jwtUtil.generateToken("testuser");

        boolean isValid = jwtUtil.ValidateToken(token);

        assertTrue(isValid);
    }


    @Test
    void TestGetUserFromToken() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        String extractedUsername = jwtUtil.GetUserFromToken(token);

        assertEquals(username, extractedUsername);
    }

    @Test
    void TestIsTokenExpired() {
        String tokenNotExpired = jwtUtil.generateToken("testuser");
        assertFalse(jwtUtil.IsTokenExpired(tokenNotExpired));
    }

    @Test
    void TestGetClaimsFromToken() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        Claims extractedClaims = jwtUtil.GetClaimsFromToken(token);

        assertEquals(claims.getSubject(), extractedClaims.getSubject());
        assertEquals(claims.getExpiration(), extractedClaims.getExpiration());
    }
}
