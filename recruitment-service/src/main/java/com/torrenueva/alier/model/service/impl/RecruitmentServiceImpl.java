package com.torrenueva.alier.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.torrenueva.alier.model.dao.RecruitmentDao;
import com.torrenueva.alier.model.dao.entity.RecruitmentEntity;
import com.torrenueva.alier.model.dto.RecruitDto;
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
	
//	@Override
//	public UserInfoDto findUserByEmail(String email) {
//		UserInfoDto userDto = new UserInfoDto();
//		UserEntity userEntity = userRepository.getSpecificUserByEmail(email);
//		userDto.setUserId(userEntity.getUserId());
//		userDto.setFirstName(userEntity.getFirstName());
//		userDto.setMiddleName(userEntity.getMiddleName());
//		userDto.setFamilyName(userEntity.getFamilyName());
//		userDto.setEmail(userEntity.getEmail());
//		userDto.setAddress(userEntity.getAddress());
//		userDto.setPassword(userEntity.getPassword());
//		userDto.setPhoneNumber(userEntity.getPhoneNumber());
//		userDto.setUserType(userEntity.getUserType());				
//		return userDto;
//	}
	
	
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
}
