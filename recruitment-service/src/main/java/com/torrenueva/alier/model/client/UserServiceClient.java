package com.torrenueva.alier.model.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.torrenueva.alier.model.dto.UserInfoDto;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class UserServiceClient {

    private final WebClient webClient;

    public UserServiceClient(WebClient.Builder webClientBuilder,
                             @Value("${user-service.base-url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    @CircuitBreaker(name = "userServiceCB", fallbackMethod = "fallbackGetUserByEmail")
    public UserInfoDto getUserByEmail(String email) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/user-service/users/find")
                        .queryParam("email", email)
                        .build())
                .retrieve()
                .bodyToMono(UserInfoDto.class)
                .block();
    }

    public UserInfoDto fallbackGetUserByEmail(String email, Throwable t) {
        UserInfoDto fallback = new UserInfoDto();
        fallback.setEmail(email);
        fallback.setFirstName("Unknown");
        fallback.setFamilyName("Unknown");
        return fallback;
    }
}