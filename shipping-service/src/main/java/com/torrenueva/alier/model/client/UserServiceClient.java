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

	/**
	* Calls the product-service to get a product by name.
	*/
	@CircuitBreaker(name = "userServiceCB", fallbackMethod = "fallbackGetUserById")
	public UserInfoDto getUserById(int id) {
		return webClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/users/findById")
						.queryParam("id", id)
						.build())
				.header("X-Gateway-Secret", "AlierInternalOnly123")
				.retrieve()
				.bodyToMono(UserInfoDto.class)
				.block();
	}
	
    
	/**
	* Fallback method if product-service is down or failing.
	*/
	public UserInfoDto fallbackGetUserById(int id, Throwable throwable) {
		UserInfoDto fallback = new UserInfoDto();
		fallback.setUserId(0);
		fallback.setFirstName("No Data");
		fallback.setMiddleName("No Data");
		fallback.setFamilyName("No Data");
		fallback.setEmail("No Data");
		fallback.setPhoneNumber("No Data");
		fallback.setAddress("No Data");
		fallback.setPassword("No Data");
		fallback.setUserType("No Data");
		return fallback;
	}
}
