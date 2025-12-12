package com.torrenueva.alier.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.torrenueva.alier.model.dto.ProductDto;
import com.torrenueva.alier.model.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductService productServ;
	
	@GetMapping("/test")
	public String test() {
		return "test products API";
	}
	
	
	@GetMapping
	public ResponseEntity<List<ProductDto>> getAllProduct(){
		List<ProductDto> getAllProduct = productServ.getAllProduct();
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(getAllProduct);
	}
	
	@PostMapping
	public ResponseEntity<String> saveProduct(@RequestBody ProductDto prodDto){
		String result = productServ.saveProduct(prodDto);	
		return ResponseEntity
				.status(HttpStatus.ACCEPTED)
				.body(result);
	} 
	
	@DeleteMapping
	public ResponseEntity<String> deleteProduct(@RequestBody ProductDto deleteDto){
		String result = productServ.deleteProduct(deleteDto);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(result);
	}
	
	@PutMapping
	public ResponseEntity<String> updateProduct(@RequestBody ProductDto updateDto){
		String result = productServ.saveProduct(updateDto);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(result);
	}
	
	@PostMapping("/find")
	public ResponseEntity<ProductDto> findProduct(@RequestBody ProductDto prodDto){
		ProductDto result = productServ.getSpecificProductByName(prodDto);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(result);
	}
}
