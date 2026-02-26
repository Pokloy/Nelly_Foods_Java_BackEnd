package com.torrenueva.alier.common;

import java.util.Collections;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.torrenueva.alier.model.dao.UserInfoAccountDao;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	// 1. THE BRIDGE: Tell Spring how to find your users in YOUR database
	@Bean
    public UserDetailsService userDetailsService(UserInfoAccountDao userRepository) {
        return email -> {
            var user = userRepository.getSpecificUserByEmail(email);
            if (user == null) throw new UsernameNotFoundException("User not found: " + email);

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword()) // Must be BCrypt hashed!
                    .authorities(Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + user.getUserType().toUpperCase())
                    ))
                    .build();
        };
    }
	
	// 2. THE ENGINE: Combines the UserDetailsService and the PasswordEncoder
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter, GatewayHeaderFilter headerFilter) throws Exception {
	    http
	        // 1. Disable CSRF for REST APIs
	        .csrf(csrf -> csrf.disable()) 
	        
	        // 2. Set session management to STATELESS (No Cookies)
	        .sessionManagement(session -> 
	            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        )
	        
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/api/auth/**").permitAll() // Public
	            .requestMatchers("/users/register").permitAll() // Public
	            .requestMatchers("/users/test").permitAll() // Public
	            .anyRequest().authenticated()                // Private
	        ); 
	    
	    // THIS LINE stops direct access to 8081
	    http.addFilterBefore(headerFilter, UsernamePasswordAuthenticationFilter.class);
	    
	    // FIX: Tell Spring to run your JWT filter before the Auth filter
	    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public NewTopic userTopic(@Value("${app.kafka.topic.user-events}") String name) {
        return TopicBuilder.name(name).partitions(1).replicas(1).build();
    }

}
