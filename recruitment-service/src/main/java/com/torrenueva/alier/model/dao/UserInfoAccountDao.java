package com.torrenueva.alier.model.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.torrenueva.alier.model.dao.entity.UserEntity;

public interface UserInfoAccountDao extends JpaRepository<UserEntity, Integer> {
	final String GET_SPECIFIC_USER_BY_EMAIL = " SELECT e FROM UserEntity e WHERE e.email = :mail ";
	
	@Query(value=GET_SPECIFIC_USER_BY_EMAIL)
	public UserEntity getSpecificUserByEmail(@Param(value = "mail") String mail) throws DataAccessException;
	
}
