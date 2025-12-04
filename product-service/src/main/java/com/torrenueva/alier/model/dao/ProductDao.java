package com.torrenueva.alier.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.torrenueva.alier.model.dao.entity.ProductEntity;

public interface ProductDao extends JpaRepository<ProductEntity, Integer> {
	final String GET_ALL_PRODUCTS = " SELECT e FROM ProductEntity e ";
	
	@Query(value=GET_ALL_PRODUCTS)
	public List<ProductEntity> getAllProduct() throws DataAccessException;
	
	final String DELETE_PRODUCT_BY_NAME = " DELETE FROM ProductEntity e WHERE e.name = :name";
	
	@Transactional
	@Modifying
	@Query(value=DELETE_PRODUCT_BY_NAME)
	public int deleteSpecificProductByName(@Param(value="name") String name) throws DataAccessException;

	final String GET_SPECIFIC_PRODUCT_BY_NAME = " SELECT e FROM ProductEntity e WHERE e.name = :name ";
	
	@Query(value=GET_SPECIFIC_PRODUCT_BY_NAME)
	public ProductEntity getSpecificProductByName(@Param(value = "name") String name) throws DataAccessException;
}
