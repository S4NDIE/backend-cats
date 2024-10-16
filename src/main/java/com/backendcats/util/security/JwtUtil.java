package com.backendcats.util.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${application.security-secret-key-jwt}")
    public String secretKey;

    public String generateToken(String document) {
        return Jwts.builder()
                .setSubject(document)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 5))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean ValidateToken(String token) {
        return  !IsTokenExpired(token);
    }

    public String GetUserFromToken(String token) {
        return GetClaimsFromToken(token).getSubject();
    }

    public boolean IsTokenExpired(String token) {
        return GetClaimsFromToken(token).getExpiration().before(new Date());
    }

    public Claims GetClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
