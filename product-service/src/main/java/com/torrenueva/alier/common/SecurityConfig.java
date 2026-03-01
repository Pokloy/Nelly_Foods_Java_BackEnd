package com.torrenueva.alier.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.torrenueva.alier.common.jwt.JwtFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Allows you to use @PreAuthorize
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter; // We will create this below

    @Autowired
    private GatewayHeaderFilter gatewayHeaderFilter; // The "Secret Handshake"

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated() // Everything else needs a token
            );

        // 1. First, check if the request came from the Gateway
        http.addFilterBefore(gatewayHeaderFilter, UsernamePasswordAuthenticationFilter.class);
        
        // 2. Then, extract the user info from the JWT
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
