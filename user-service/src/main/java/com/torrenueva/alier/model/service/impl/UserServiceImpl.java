package com.torrenueva.alier.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.torrenueva.alier.model.dao.UserInfoAccountDao;
import com.torrenueva.alier.model.dao.entity.UserEntity;
import com.torrenueva.alier.model.dto.UserInfoDto;
import com.torrenueva.alier.model.kafka.UserEventProducer;
import com.torrenueva.alier.model.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserInfoAccountDao userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserEventProducer userEventProducerKafka;
	
	@Override
	public List<UserInfoDto> getAllUsers() {
	    List<UserEntity> entityList = userRepository.getAllUser();
	    if (entityList == null) {
	        return new ArrayList<>();
	    }

	    return entityList.stream()
	    		.filter(entity -> !entity.isDeleteFlag())
	            .map(entity -> {
	                UserInfoDto dto = new UserInfoDto();
	                
	                dto.setUserId(entity.getUserId());
	                dto.setAddress(entity.getAddress());
	                dto.setEmail(entity.getEmail());
	                dto.setFamilyName(entity.getFamilyName());
	                dto.setFirstName(entity.getFirstName());
	                dto.setMiddleName(entity.getMiddleName());
	                dto.setPassword(entity.getPassword());
	                dto.setPhoneNumber(entity.getPhoneNumber());
	                dto.setUserType(entity.getUserType());
	                return dto;
	            })
	            .toList();
	}

	
	@Override
	public String saveUser(UserInfoDto userDto) {
		String msg;
	    try {
	    	UserEntity findUserByEmail = userRepository.getSpecificUserByEmail(userDto.getEmail());
	        UserEntity userEntity = new UserEntity();
	        if(findUserByEmail != null) {
	        	userEntity.setUserId(findUserByEmail.getUserId());
	        	msg = "User updated successfully";
	        } else {
	        	msg = "User has been saved successfully";
	        }
	        userEntity.setFirstName(userDto.getFirstName());
	        userEntity.setMiddleName(userDto.getMiddleName());
	        userEntity.setFamilyName(userDto.getFamilyName());
	        userEntity.setEmail(userDto.getEmail());
	        userEntity.setAddress(userDto.getAddress());
	        userEntity.setPhoneNumber(userDto.getPhoneNumber());
	        userEntity.setUserType(userDto.getUserType());
	        userEntity.setPassword(encoder.encode(userDto.getPassword()));
	        UserEntity newUserId = userRepository.save(userEntity);
	        UserEntity refererUser = userRepository.getSpecificUserByEmail(userDto.getRefferEmail());
	        userDto.setUserId(newUserId.getUserId());
	        userDto.setReferedrId(refererUser.getUserId());
	        userDto.setKafkaStatsTrigger(1);
	        userEventProducerKafka.sendUserEvent(userDto);
	        return msg;
	    } catch (Exception e) {
	        // Optionally log the exception
	        e.printStackTrace();
	        return "Failed to save user: " + e.getMessage();
	    }
	}
	
	@Override
	public UserInfoDto findUserByEmail(String email) {
		UserEntity userEnt = userRepository.getSpecificUserByEmail(email);
		UserInfoDto selectedUser = new UserInfoDto();
		if(userEnt != null) {
			selectedUser.setFirstName(userEnt.getFirstName());
			selectedUser.setMiddleName(userEnt.getMiddleName());
			selectedUser.setFamilyName(userEnt.getFamilyName());
			selectedUser.setAddress(userEnt.getAddress());
			selectedUser.setEmail(userEnt.getEmail());
			selectedUser.setPassword(userEnt.getPassword());
			selectedUser.setPhoneNumber(userEnt.getPhoneNumber());
			selectedUser.setUserId(userEnt.getUserId());
			selectedUser.setUserType(userEnt.getUserType());
			return selectedUser;
		} else {
			return new UserInfoDto();
		}
		
	} 
	
	@Override
	public String deleteUserByEmail(UserInfoDto deletionDto) {
		UserEntity userEnt = userRepository.getSpecificUserByEmail(deletionDto.getEmail());
		
		deletionDto.setUserId(userEnt.getUserId());
		deletionDto.setKafkaStatsTrigger(2);
		userEventProducerKafka.sendUserEvent(deletionDto);
		
		if(userEnt != null) {
			userRepository.deleteSpecificUserByEmail(deletionDto.getEmail());
			StringBuilder sb1 = new StringBuilder();
			sb1.append("User: ");
			sb1.append(" ");
			sb1.append(userEnt.getFirstName());
			sb1.append(" ");
			sb1.append(userEnt.getMiddleName());
			sb1.append(" ");
			sb1.append(userEnt.getFamilyName());
			sb1.append(" ");
			sb1.append("has");
			sb1.append(" ");
			sb1.append("been");
			sb1.append(" ");
			sb1.append("deleted.");
			return sb1.toString();
		} else {
			return "User not found";
		}
		
	}
	
	
}
