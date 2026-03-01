package com.torrenueva.alier.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

import com.torrenueva.alier.common.jwt.JwtFilter;

@Configuration
@EnableWebSecurity
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

    
    @Bean
    public WebClient webClient(WebClient.Builder builder, 
                               @Value("${user-service.base-url}") String baseUrl) {
        return builder
            .baseUrl(baseUrl)
            .build(); // No filters needed, we are doing it manually!
    }
	
}
