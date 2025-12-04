package com.torrenueva.alier.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.torrenueva.alier.model.dao.entity.UserEntity;

public interface UserInfoAccountDao extends JpaRepository<UserEntity, Integer> {
	final String GET_ALL_USERS = " SELECT e FROM UserEntity e ";
	
	@Query(value=GET_ALL_USERS)
	public List<UserEntity> getAllUser() throws DataAccessException;
	
	final String GET_SPECIFIC_USER_BY_EMAIL = " SELECT e FROM UserEntity e WHERE e.email = :mail AND e.deleteFlag = false ";
	
	@Query(value=GET_SPECIFIC_USER_BY_EMAIL)
	public UserEntity getSpecificUserByEmail(@Param(value = "mail") String mail) throws DataAccessException;
	
	final String DELETE_USER_BY_EMAIL = " UPDATE UserEntity e SET e.deleteFlag = true WHERE e.email = :mail AND e.deleteFlag = false ";
	
	@Transactional
	@Modifying
	@Query(value=DELETE_USER_BY_EMAIL)
	public int deleteSpecificUserByEmail(@Param(value = "mail") String mail) throws DataAccessException;
}
