package com.torrenueva.alier.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.torrenueva.alier.model.client.UserServiceClient;
import com.torrenueva.alier.model.dto.OrderDto;
import com.torrenueva.alier.model.dto.UserDto;
import com.torrenueva.alier.model.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderServ;
	
	@Autowired
	private UserServiceClient test;
	
	@GetMapping
	public ResponseEntity<List<OrderDto>> getAllProduct(){
		List<OrderDto> getAllProduct = orderServ.getAllorder();
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(getAllProduct);
	}
	
	@PostMapping
	public ResponseEntity<String> saveOrder(@RequestBody OrderDto orderDto){
		String result = orderServ.addOrder(orderDto);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(result);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteOrder(@RequestParam(name="id") int id){
		String result = orderServ.deleteOrder(id);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(result);
	}
	
}