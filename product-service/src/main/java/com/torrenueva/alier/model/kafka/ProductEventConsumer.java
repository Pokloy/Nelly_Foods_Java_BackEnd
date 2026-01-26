package com.torrenueva.alier.model.kafka;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.torrenueva.alier.model.dto.ProductDto;
import com.torrenueva.alier.model.service.ProductService;

@Service
public class ProductEventConsumer {
	private static final Logger logger = LoggerFactory.getLogger(ProductEventConsumer.class);
	
	@Autowired
	ProductService prodService;
	
	  @KafkaListener(
			  topics = "product-events", 
			  groupId = "product-group")
	    public ProductDto consumeUserEvent(ProductDto productDto) {
    		logger.info("📦 Received product Event: {}", productDto);
	        System.out.println("Received product Info : " + productDto);
	        try {
	        	System.out.println("Nakasulod sa try catch");
	            if (productDto.getProductId() == 0) {
	                throw new IllegalArgumentException("Invalid product data!");
	            }
	            
		        ProductDto product = prodService.getSpecificProductByName(productDto);
		        
		        if(product == null) {
	            	System.out.println("no product");
		        	return new ProductDto();
		        } 
		        
		        return product;
		        
	        } catch (Exception e) {
	            logger.error("❌ Error processing product event: {}", e.getMessage());
	            throw e; // rethrow to trigger retry
	        }
	    }

	
	
	
        // optional Dead-Letter listener
        @KafkaListener(topics = "product-events.DLT", groupId = "product-group")
        public void consumeDLT(String failedMessage) {
            logger.warn("⚠️ Message moved to DLT: {}", failedMessage);
        }
}
