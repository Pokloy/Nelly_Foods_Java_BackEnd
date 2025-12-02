package com.torrenueva.alier.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.torrenueva.alier.model.dao.entity.RecruitmentEntity;

public interface RecruitmentDao extends JpaRepository<RecruitmentEntity, Integer> {
	final String GET_ALL_RECRUITMENT = " SELECT e FROM RecruitmentEntity e ";
	
	@Query(value=GET_ALL_RECRUITMENT)
	public List<RecruitmentEntity> getAllRecruitement() throws DataAccessException;
}
