package com.torrenueva.alier.model.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.torrenueva.alier.model.dto.ProductDto;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class ProductServiceClient {

    private final WebClient webClient;

    public ProductServiceClient(WebClient.Builder webClientBuilder,
                                @Value("${product-service.base-url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    /**
     * Calls the product-service to get a product by name.
     */
    @CircuitBreaker(name = "productServiceCB", fallbackMethod = "fallbackGetProductByName")
    public ProductDto getProductByName(String name) {
        ProductDto requestDto = new ProductDto();
        requestDto.setName(name);

        return webClient.post()
                .uri("/products/find") // matches product-service controller
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block();
    }

    /**
     * Fallback method if product-service is down or failing.
     */
    public ProductDto fallbackGetProductByName(String name, Throwable throwable) {
        ProductDto fallback = new ProductDto();
        fallback.setProductId(-1);
        fallback.setName("Unknown Product");
        fallback.setDesc("Product service is currently unavailable.");
        fallback.setPrice(0);
        fallback.setPhoto(new String[]{});
        fallback.setSpecification("");
        fallback.setDiscount("");
        return fallback;
    }
}
