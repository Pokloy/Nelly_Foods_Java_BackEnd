package com.torrenueva.alier.model.service;

import java.util.List;

import com.torrenueva.alier.model.dto.RecruitDto;
import com.torrenueva.alier.model.dto.UserInfoDto;

public interface RecruitmentService {
	public List<RecruitDto> getAllRecruitement();
	public String saveRecruit(RecruitDto recruiteDto);
	//public UserInfoDto findUserByEmail(String email);
}
