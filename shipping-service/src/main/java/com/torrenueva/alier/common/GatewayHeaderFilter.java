package com.torrenueva.alier.common;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
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

        // Inside User Service: GatewayHeaderFilter.java
        if ("AlierInternalOnly123".equals(secret)) {
            // Create a "System" authentication
            UsernamePasswordAuthenticationToken internalAuth = 
                new UsernamePasswordAuthenticationToken("SYSTEM_KAFKA", null, 
                    AuthorityUtils.createAuthorityList("ROLE_INTERNAL"));
            
            // Set it in the context so the JWT Filter is skipped/satisfied
            SecurityContextHolder.getContext().setAuthentication(internalAuth);
            
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
