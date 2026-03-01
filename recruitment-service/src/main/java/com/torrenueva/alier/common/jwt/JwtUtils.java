package com.torrenueva.alier.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component // Make sure this is a component so you can @Autowire it
public class JwtUtils {

    private String jwtSecret = "ECommerceSampleNellyFoods123456ByAlierGwapoKaayoTananKalibotan22u";

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // 1. Validate the token signature and expiration
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
        	// THIS WILL PRINT THE EXACT REASON IN YOUR CONSOLE
            System.out.println("JWT Validation Failed: " + e.getMessage());
            return false;
        }
    }

    // 2. Extract the claims (the data)
    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // 3. NEW: Extract Authorities (Roles) for Spring Security
    public Collection<GrantedAuthority> extractAuthorities(String token) {
        Claims claims = getClaims(token);
        String rolesString = claims.get("roles", String.class);
        
        if (rolesString == null || rolesString.isEmpty()) {
            return java.util.Collections.emptyList();
        }

        // Convert "ROLE_ADMIN,ROLE_USER" string into a List of GrantedAuthority objects
        return Arrays.stream(rolesString.split(","))
        		.map(role -> new SimpleGrantedAuthority(role.trim())) // Force the prefix
                .collect(Collectors.toList());
    }
}