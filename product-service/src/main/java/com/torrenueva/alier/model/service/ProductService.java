package com.torrenueva.alier.model.service;

import java.util.List;

import com.torrenueva.alier.model.dto.ProductDto;

public interface ProductService {
	public String saveProduct(ProductDto productDto);
	public String deleteProduct(ProductDto productName);
	public List<ProductDto> getAllProduct();
	public ProductDto getSpecificProductByName(ProductDto productName);
}
