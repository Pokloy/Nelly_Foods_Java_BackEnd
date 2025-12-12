package com.torrenueva.alier.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.torrenueva.alier.model.dao.RecruitmentDao;
import com.torrenueva.alier.model.dao.entity.RecruitmentEntity;
import com.torrenueva.alier.model.dto.RecruitDto;
import com.torrenueva.alier.model.dto.UserInfoDto;
import com.torrenueva.alier.model.service.RecruitmentService;

@Service
public class RecruitmentServiceImpl implements RecruitmentService {
	@Autowired
	RecruitmentDao recruitRepository;
	
	
	@Override
	public List<RecruitDto> getAllRecruitement(){
		List<RecruitmentEntity> entityList = recruitRepository.getAllRecruitement();
	    if (entityList == null) {
	        return new ArrayList<>();
	    }
	    
	    return entityList.stream().map(entity -> {
	    	RecruitDto dto = new RecruitDto();
	    	dto.setInvitedId(entity.getInvitedId());
	    	dto.setInviterId(entity.getInviterId());
	    	dto.setReferralId(entity.getReferralId());
	    	dto.setStatus(entity.getStatus());
	    	dto.setUpdateDate(entity.getUpdateDate());
	    	return dto;
	    })
	    .toList();
	}
	
	
	@Override
	public String saveRecruit(RecruitDto recruiteDto) {
		try {
			RecruitmentEntity recuitEntity = new RecruitmentEntity();
			recuitEntity.setInvitedId(recruiteDto.getInvitedId());
			recuitEntity.setInviterId(recruiteDto.getInviterId());
			recuitEntity.setStatus(recruiteDto.getStatus());
			recuitEntity.setUpdateDate(recruiteDto.getUpdateDate());
			recruitRepository.saveAndFlush(recuitEntity);
			return "recruitment saved";
	    } catch (Exception e) {
	        // Optionally log the exception
	        e.printStackTrace();
	        return "Failed to save user: " + e.getMessage();
	    }
	}
	
	@Override
	public String deleteRecruite(UserInfoDto userInfoDto) {
		
		try {
		 int result = recruitRepository.deleteSpecificRecruitByUserId(userInfoDto.getUserId());
		 System.out.println(result);
		 return "recruitment deleted";
		} catch (Exception e) {
        // Optionally log the exception
        e.printStackTrace();
        return "Failed to delete user: " + e.getMessage();
    }
	}
	
	@Override
	public RecruitDto findSpecificRecruite(int invitedUserId) {
		RecruitDto dto = new RecruitDto();
		
		try {
			RecruitmentEntity findSpecificRecruit = recruitRepository.findSpecificRecruite(invitedUserId);
			System.out.println("entity: " +  findSpecificRecruit);
			if(findSpecificRecruit == null) {
				System.out.println("Recruitment Not Found");
				return dto;
			}
			
			dto.setReferralId(findSpecificRecruit.getReferralId());
			dto.setInvitedId(findSpecificRecruit.getInvitedId());
			dto.setInviterId(findSpecificRecruit.getInviterId());
			dto.setStatus(findSpecificRecruit.getStatus());
			dto.setUpdateDate(findSpecificRecruit.getUpdateDate());
			dto.setDeleteFlg(findSpecificRecruit.isDeleteFlag());
			return dto;
			
		} catch (Exception e) {
	        // Optionally log the exception
	        e.printStackTrace();
	        return dto;
	    }
	}
	
	@Override
	public String updateStatusByInvitedId(int invitedUserId, String status) {
		RecruitmentEntity findSpecificRecruit = recruitRepository.findSpecificRecruite(invitedUserId);
		RecruitmentEntity updatedRecruit = new RecruitmentEntity();
		
		updatedRecruit.setReferralId(findSpecificRecruit.getReferralId());
		updatedRecruit.setInviterId(findSpecificRecruit.getInviterId());
		updatedRecruit.setInvitedId(findSpecificRecruit.getInvitedId());
		updatedRecruit.setStatus(status);
		updatedRecruit.setDeleteFlag(false);
		updatedRecruit.setUpdateDate(findSpecificRecruit.getUpdateDate());
		return "User Status Updated Successfully";
	} 
}
