package com.torrenueva.alier.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.torrenueva.alier.model.dto.ShippingDto;
import com.torrenueva.alier.model.service.ShippingService;

@RestController
@RequestMapping("/shipping")
public class ShippingController {
	@Autowired
	private ShippingService shipService;
	
	@GetMapping
	public ResponseEntity<List<ShippingDto>> getAllShip(){
		 List<ShippingDto> result = shipService.getAllShipping();
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(result);
	}
	
	@PostMapping("/addShip")
	public ResponseEntity<String> saveShip(@RequestBody ShippingDto shipDto){
		String result = shipService.addShipping(shipDto);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(result);
	}
	
	@GetMapping("/findByUserId")
	public ResponseEntity<List<ShippingDto>> getAllShipByUserId(@RequestParam(name="userId") int userId){
		List<ShippingDto> result = shipService.allShipByUserId(userId);
		System.out.println(result);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(result);
	}
}
