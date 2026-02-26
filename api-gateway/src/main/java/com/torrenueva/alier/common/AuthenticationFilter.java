package com.torrenueva.alier.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private JwtUtils jwtUtils; // Ensure the Gateway version of JwtUtils has the 'getClaims' method

    public AuthenticationFilter() {
        super(Config.class);
    }

    public static class Config {}

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // 1. Check if the path is an Auth path (Skip validation for Login/Register)
            String path = exchange.getRequest().getPath().toString();
            if (path.contains("/api/auth") || path.contains("/users/register")) {
                return chain.filter(exchange);
            }

            // 2. Look for the Bearer Token
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
            }

            String token = authHeader.substring(7);

            try {
                // 3. Validate Token
                jwtUtils.validateToken(token);
                
                // 4. Extract info and forward to next service via Headers
                Claims claims = jwtUtils.getClaims(token);
                exchange.getRequest().mutate()
                        .header("X-User-Email", claims.getSubject())
                        .header("X-User-Roles", claims.get("roles").toString())
                        .build();

            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Token");
            }

            return chain.filter(exchange);
        };
    }
}