package com.torrenueva.alier.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.torrenueva.alier.model.dao.entity.ShippingEntity;

public interface ShippingDao extends JpaRepository<ShippingEntity, Integer> {
	final String GET_ALL_ORDER_BY_USER_ID = " SELECT e FROM  ShippingEntity e WHERE e.userId = :userId ";
	
	@Query(value=GET_ALL_ORDER_BY_USER_ID)
	public List<ShippingEntity> getAllShipByUserId(@Param(value = "userId") int userId) throws DataAccessException;
	
}
