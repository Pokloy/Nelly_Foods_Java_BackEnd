package com.torrenueva.alier.model.service;

import java.util.List;

import com.torrenueva.alier.model.dto.OrderDto;

public interface OrderService {
	public String addOrder(OrderDto orderDto);
	public List<OrderDto> getAllorder();
	public String deleteOrder(int orderId);
	public OrderDto findOrderById(int orderId);
}
