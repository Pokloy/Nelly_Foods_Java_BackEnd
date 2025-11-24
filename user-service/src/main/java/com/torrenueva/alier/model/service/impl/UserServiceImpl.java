package com.torrenueva.alier.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.torrenueva.alier.model.dao.UserInfoAccountDao;
import com.torrenueva.alier.model.dao.entity.UserEntity;
import com.torrenueva.alier.model.dto.UserInfoDto;
import com.torrenueva.alier.model.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserInfoAccountDao userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public List<UserInfoDto> getAllUsers() {
		List<UserEntity> entityList = userRepository.getAllUser();
		List<UserInfoDto> dtoList = new ArrayList<>();
		for(UserEntity entity: entityList) {
			UserInfoDto dto = new UserInfoDto();
			dto.setAddress(entity.getAddress());
			dto.setEmail(entity.getEmail());
			dto.setFamilyName(entity.getFamilyName());
			dto.setFirstName(entity.getFirstName());
			dto.setMiddleName(entity.getMiddleName());
			dto.setPassword(entity.getPassword());
			dto.setPhoneNumber(entity.getPhoneNumber());
			dto.setUserType(entity.getUserType());
			dtoList.add(dto);
		}
		
		if(entityList != null) {
			return dtoList;
		} else {
			return new ArrayList<>();
		}
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
	        userRepository.save(userEntity);
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
	public String deleteUserByEmail(String email) {
		UserEntity userEnt = userRepository.getSpecificUserByEmail(email);
		if(userEnt != null) {
			userRepository.deleteSpecificUserByEmail(email);
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
