package com.torrenueva.alier.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.torrenueva.alier.model.dao.entity.RecruitmentEntity;

public interface RecruitmentDao extends JpaRepository<RecruitmentEntity, Integer> {
	final String DELETE_RECRUITMENT_BY_USER_ID = "UPDATE RecruitmentEntity e SET e.deleteFlag = true WHERE e.invitedId = :userId";
	
	@Transactional
	@Modifying
	@Query(value=DELETE_RECRUITMENT_BY_USER_ID)
	public int deleteSpecificRecruitByUserId(@Param(value = "userId") int userId) throws DataAccessException;
	
	final String GET_ALL_RECRUITMENT = " SELECT e FROM RecruitmentEntity e WHERE e.deleteFlag = false ";
	
	@Query(value=GET_ALL_RECRUITMENT)
	public List<RecruitmentEntity> getAllRecruitement() throws DataAccessException;
	
	final String GET_SPECIFIC_RECRUITMENT = " SELECT e FROM RecruitmentEntity e WHERE e.deleteFlag = false AND e.invitedId = :invitedUserId  ";
	
	@Query(value = GET_SPECIFIC_RECRUITMENT)
	public RecruitmentEntity findSpecificRecruite(@Param(value = "invitedUserId") int invitedUserId) throws DataAccessException;
}
