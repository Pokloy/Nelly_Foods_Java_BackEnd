package com.torrenueva.alier.common;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class GatewayHeaderFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {
        
        String secret = request.getHeader("X-Gateway-Secret");

        // If the secret is missing or wrong, kill the request immediately
        if (!"AlierInternalOnly123".equals(secret)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Direct access is prohibited. Use the API Gateway.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
