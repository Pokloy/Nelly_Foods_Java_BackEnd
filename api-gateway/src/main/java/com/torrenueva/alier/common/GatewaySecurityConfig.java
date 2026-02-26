package com.torrenueva.alier.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity // Note: This is different from EnableWebSecurity
public class GatewaySecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for your APIs
            .authorizeExchange(exchanges -> exchanges
                .anyExchange().permitAll() // Let everything pass to the Gateway Filters
            )
            .httpBasic(basic -> basic.disable()) // Disable the "Basic Auth" popup
            .formLogin(form -> form.disable());  // Disable the default login page
        
        return http.build();
    }
}
