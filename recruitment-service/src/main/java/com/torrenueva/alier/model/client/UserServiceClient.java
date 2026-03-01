package com.torrenueva.alier.model.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.torrenueva.alier.model.dto.UserInfoDto;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class UserServiceClient {

    private final WebClient webClient;

    public UserServiceClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @CircuitBreaker(name = "userServiceCB", fallbackMethod = "fallbackGetUserByEmail")
    // In Recruitment Service: UserServiceClient.java
    public UserInfoDto getUserByEmail(String email) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/users/find")
                        .queryParam("email", email)
                        .build())
                // This is the ONLY header you need for Kafka/Internal calls
                .header("X-Gateway-Secret", "AlierInternalOnly123") 
                .retrieve()
                .bodyToMono(UserInfoDto.class)
                .block();
    }

    // Update fallback signature to match
    public UserInfoDto fallbackGetUserByEmail(String email, Throwable t) {
        UserInfoDto fallback = new UserInfoDto();
        fallback.setEmail(email);
        fallback.setFirstName("Unknown");
        fallback.setFamilyName("Unknown");
        return fallback;
    }
}