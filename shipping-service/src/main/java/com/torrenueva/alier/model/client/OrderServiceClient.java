package com.torrenueva.alier.model.client;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.torrenueva.alier.model.dto.OrderDto;
import com.torrenueva.alier.model.dto.UserInfoDto;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class OrderServiceClient {
	private final WebClient webClient;
	
	public OrderServiceClient(WebClient.Builder webClientBuilder,
			@Value("${order-service.base-url}") String baseUrl) {
		this.webClient = webClientBuilder.baseUrl(baseUrl).build();
	}
	
	@CircuitBreaker(name = "orderServiceCB", fallbackMethod = "fallbackGetOrderById")
	public OrderDto getOrderById(int id) {
		return webClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/orders/find")
						.queryParam("id", id)
						.build())
				.header("X-Gateway-Secret", "AlierInternalOnly123")
				.retrieve()
				.bodyToMono(OrderDto.class)
				.block();
						
	}
	
	public OrderDto fallbackGetOrderById(int id, Throwable throwable) {
		System.out.println(throwable);
		OrderDto fallback = new OrderDto(); 
		fallback.setOrderId(0);
		fallback.setUserId(0);
		fallback.setItems(new ArrayList<>());
		fallback.setTotalPrice(null);
		fallback.setStatus("No Order found");
		fallback.setDateOrder(null);
		fallback.setUpdateDate(null);		
		return fallback;
	}
}
