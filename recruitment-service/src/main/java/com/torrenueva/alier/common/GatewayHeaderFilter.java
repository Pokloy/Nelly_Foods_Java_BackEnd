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

        if ("AlierInternalOnly123".equals(secret)) {
            // We give this "System" caller the INTERNAL role and ADMIN role
            // so it can pass any @PreAuthorize check.
            var authorities = AuthorityUtils.createAuthorityList("ROLE_INTERNAL", "ROLE_ADMIN");

            UsernamePasswordAuthenticationToken systemAuth = 
                new UsernamePasswordAuthenticationToken("SYSTEM_PROCESS", null, authorities);
            
            SecurityContextHolder.getContext().setAuthentication(systemAuth);
        }

        filterChain.doFilter(request, response);
    }
}
