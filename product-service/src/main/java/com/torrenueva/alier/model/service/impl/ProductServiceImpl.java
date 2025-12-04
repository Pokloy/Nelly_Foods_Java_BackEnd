package com.torrenueva.alier.model.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.torrenueva.alier.model.dao.ProductDao;
import com.torrenueva.alier.model.dao.entity.ProductEntity;
import com.torrenueva.alier.model.dto.ProductDto;
import com.torrenueva.alier.model.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productRepository;
	
	@Override
	public String saveProduct(ProductDto productDto) {
		String msg;
	    try {
	    	ProductEntity specificProduct = productRepository.getSpecificProductByName(productDto.getName());
	        ProductEntity newProduct = new ProductEntity();
	        if(specificProduct != null) {
	        	newProduct.setProductId(newProduct.getProductId());
	        	msg = "User updated successfully";
	        } else {
	        	msg = "User has been saved successfully";
	        }
	        newProduct.setName(productDto.getName());
	        newProduct.setPhoto(productDto.getPhoto());
	        newProduct.setPrice(productDto.getPrice());
	        newProduct.setSpecification(productDto.getSpecification());
	        newProduct.setDescription(productDto.getDesc());
	        newProduct.setDiscount(productDto.getDiscount());

	        productRepository.saveAndFlush(newProduct);

	        return msg;
	    } catch (DataIntegrityViolationException ex) {
	        // Violated constraints (duplicate product name, NULL fields, etc.)
	        return "Failed to save product: invalid data or duplicate entry.";
	    } catch (Exception ex) {
	        // Unexpected errors
	        ex.printStackTrace(); // log it OR replace with proper logger
	        return "An unexpected error occurred while saving the product.";
	    }
	}

	
	@Override
	public String deleteProduct(ProductDto deleteDto) {
	    try {
	        String productName = deleteDto.getName();

	        int deletedCount = productRepository.deleteSpecificProductByName(productName);

	        if (deletedCount == 0) {
	            return "Deletion failed: Product '" + productName + "' does not exist.";
	        }

	        return "Product '" + productName + "' deleted successfully.";

	    } catch (Exception ex) {
	        ex.printStackTrace(); // Replace with logger in real projects
	        return "An unexpected error occurred while deleting the product.";
	    }
	}
	
	
	@Override
	public List<ProductDto> getAllProduct(){
		List<ProductEntity> entityList = productRepository.getAllProduct();
		
	    if (entityList == null) {
	        return new ArrayList<>();
	    }
	    
	    return entityList.stream()
	    		.map(entity -> {
	    			ProductDto dto = new ProductDto();
	    			dto.setName(entity.getName());
	    			dto.setPhoto(entity.getPhoto());
	    			dto.setPrice(entity.getPrice());
	    			dto.setProductId(entity.getProductId());
	    			dto.setSpecification(entity.getSpecification());
	    			dto.setDesc(entity.getDescription());
	    			dto.setDiscount(entity.getDiscount());	    			
	    			return dto;
	    		}).toList();
	}
	
	@Override
	public ProductDto getSpecificProductByName(ProductDto productName) {
	    try {
	        ProductEntity specificProduct = productRepository.getSpecificProductByName(productName.getName());

	        if (specificProduct == null) {
	            throw new NoSuchElementException("Product '" + productName + "' not found.");
	        }

	        ProductDto dto = new ProductDto();
	        dto.setName(specificProduct.getName());
	        dto.setPhoto(specificProduct.getPhoto());
	        dto.setPrice(specificProduct.getPrice());
	        dto.setProductId(specificProduct.getProductId());
	        dto.setSpecification(specificProduct.getSpecification());
	        dto.setDesc(specificProduct.getDescription());
	        dto.setDiscount(specificProduct.getDiscount());

	        return dto;

	    } catch (NoSuchElementException ex) {
	        System.err.println(ex.getMessage()); // Replace with logger in production
	        return null; // or return an empty DTO if you prefer
	    } catch (Exception ex) {
	        ex.printStackTrace(); // Log unexpected errors
	        return null;
	    }
	}


}