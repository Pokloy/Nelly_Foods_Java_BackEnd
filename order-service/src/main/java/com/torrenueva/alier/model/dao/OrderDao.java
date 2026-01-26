package com.torrenueva.alier.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.torrenueva.alier.model.dao.entity.OrderEntity;

public interface OrderDao extends JpaRepository<OrderEntity, Integer> {
	final String GET_ALL_ORDERS = " SELECT e FROM OrderEntity e ";
	
	@Query(value=GET_ALL_ORDERS)
	public List<OrderEntity> getAllOrder() throws DataAccessException;
	
	final String DELETE_SPECIFIC_ORDER = " UPDATE OrderEntity e SET e.deleteFlag = true WHERE e.orderId = :orderId ";
	
	@Transactional
	@Modifying
	@Query(value=DELETE_SPECIFIC_ORDER)
	public int deleteSpecificOrder(@Param(value = "orderId") int orderId) throws DataAccessException;
	
	final String FIND_SPECIFIC_ORDER = " SELECT e FROM OrderEntity e WHERE e.orderId = :orderId ";
	
	@Query(value=FIND_SPECIFIC_ORDER)
	public OrderEntity findSpecificOrder(@Param(value = "orderId") int orderId) throws DataAccessException;
}
