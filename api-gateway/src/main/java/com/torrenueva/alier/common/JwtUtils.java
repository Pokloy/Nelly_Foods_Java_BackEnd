package com.torrenueva.alier.common;

import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    // IMPORTANT: In production, move this to application.properties
    // This string must be AT LEAST 64 characters long for HS512
    private String jwtSecret = "ECommerceSampleNellyFoods123456ByAlierGwapoKaayoTananKalibotan22u";
    private int jwtExpirationMs = 86400000; // 24 hours

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String generateToken(Authentication authentication) {
        // Extract roles from the authenticated user
        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(authentication.getName())
                .claim("roles", roles) // This is where we store ADMIN/USER info
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key()) // JJWT 0.12.x automatically chooses HS256/512 based on key size
                .compact();
    }
    
    // THE MISSING PIECE: The "Magnifying Glass"
    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key()) // This checks the signature
                .build()
                .parseSignedClaims(token)
                .getPayload(); // This returns the roles, expiry, and email
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false; // Token is expired, tampered, or invalid
        }
    }
}

