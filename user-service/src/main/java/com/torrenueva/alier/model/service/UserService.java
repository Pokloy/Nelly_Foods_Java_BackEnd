package com.torrenueva.alier.model.service;

import java.util.List;

import com.torrenueva.alier.model.dto.UserInfoDto;

public interface UserService {
	public List<UserInfoDto> getAllUsers();
	public String saveUser(UserInfoDto userDto);
	public UserInfoDto findUserByEmail(String email);
	public String deleteUserByEmail(UserInfoDto email);
}