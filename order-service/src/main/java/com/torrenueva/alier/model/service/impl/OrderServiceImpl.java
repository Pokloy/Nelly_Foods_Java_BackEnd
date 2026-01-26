package com.torrenueva.alier.model.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.torrenueva.alier.model.client.ProductServiceClient;
import com.torrenueva.alier.model.dao.OrderDao;
import com.torrenueva.alier.model.dao.entity.OrderEntity;
import com.torrenueva.alier.model.dto.OrderDto;
import com.torrenueva.alier.model.service.OrderService;
import com.torrenueva.alier.model.dto.ProductDto;
import com.torrenueva.alier.model.dto.object.ItemObject;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDao orderRepository;
	
	@Autowired
	private ProductServiceClient productServiceClient;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Override
    public String addOrder(OrderDto orderDto) {
        LocalDateTime now = LocalDateTime.now();
        OrderEntity entity = new OrderEntity();

        // 1. Fetch data from Product-Service & Calculate Subtotals
        List<ItemObject> processedItems = orderDto.getItems().stream()
            .map(item -> {
                ProductDto product = productServiceClient.getProductById(item.getProductId());
                
                // Calculate Subtotal: Price * Quantity
                BigDecimal price = BigDecimal.valueOf(product.getPrice());
                BigDecimal subTotal = price.multiply(BigDecimal.valueOf(item.getQuantity()));
                
                item.setSubTotal(subTotal);
                return item;
            })
            .collect(Collectors.toList());

        // 2. Calculate Total Price
        BigDecimal grandTotal = processedItems.stream()
            .map(ItemObject::getSubTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 3. Convert the List to JSON String for the JSONB column
        try {
            String itemsJson = objectMapper.writeValueAsString(processedItems);
            entity.setItems(itemsJson);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert items to JSON", e);
        }

        // 4. Populate other Entity fields
        entity.setUserId(orderDto.getUserId());
        entity.setTotalPrice(grandTotal);
        entity.setStatus("Pending");
        entity.setDateOrder(now);
        entity.setUpdateDate(now);
        entity.setDeleteFlag(false);

        orderRepository.saveAndFlush(entity);
        return "Order created successfully for User: " + orderDto.getUserId();
    }
	
	@Override
	public List<OrderDto> getAllorder() {
	    List<OrderEntity> entityList = orderRepository.findAll(); // Assuming standard JPA findAll
	    
	    if (entityList == null || entityList.isEmpty()) {
	        return new ArrayList<>();
	    }

	    return entityList.stream()
	            .map(entity -> {
	                OrderDto dto = new OrderDto();
	                dto.setUserId(entity.getUserId());
	                dto.setOrderId(entity.getOrderId());
	                dto.setDateOrder(entity.getDateOrder());
	                dto.setUpdateDate(entity.getUpdateDate());
	                dto.setStatus(entity.getStatus());
	                dto.setTotalPrice(entity.getTotalPrice());

	                // --- NEW LOGIC: Convert JSON String to List<ItemObject> ---
	                try {
	                    List<ItemObject> itemList = objectMapper.readValue(
	                        entity.getItems(), 
	                        new TypeReference<List<ItemObject>>() {}
	                    );
	                    dto.setItems(itemList);
	                } catch (Exception e) {
	                    // Handle case where JSON might be corrupted or null
	                    dto.setItems(new ArrayList<>());
	                    System.err.println("Error parsing JSON for order " + entity.getOrderId() + ": " + e.getMessage());
	                }
	                
	                return dto;
	            }).toList();
	}
	
	@Override
	public String deleteOrder(int orderId) {
		OrderEntity findOrder = orderRepository.findSpecificOrder(orderId);
		
		if(findOrder == null) {
			return "No Order Found For Order ID: " + orderId;
		} 
		
		orderRepository.deleteSpecificOrder(orderId);
		return "Order ID: " + orderId + " has been deleted successfully";
	}
}
