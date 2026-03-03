package com.apigateway.jwtutil;

import io.jsonwebtoken.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import java.security.SignatureException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import java.security.Key;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token); //Decodes the JWT, verifies the signature, and gives you all the claims inside the payload.
            return claims.getExpiration().after(new Date()); // checks whether the expiration date is after now (i.e., still valid).
        } catch (Exception e) {
            return false;
        }
    }

    public Claims extractAllClaims(String token) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes()); // ✅ converts String → Key
        return Jwts.parserBuilder()
                .setSigningKey(key)   // ✅ now passing a Key, not String
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
}
