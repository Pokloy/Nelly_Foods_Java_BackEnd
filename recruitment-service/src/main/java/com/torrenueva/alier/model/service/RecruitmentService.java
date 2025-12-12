package com.torrenueva.alier.model.service;

import java.util.List;

import com.torrenueva.alier.model.dto.RecruitDto;
import com.torrenueva.alier.model.dto.UserInfoDto;

public interface RecruitmentService {
	public List<RecruitDto> getAllRecruitement();
	public String saveRecruit(RecruitDto recruiteDto);
	public String deleteRecruite(UserInfoDto userInfoDto);
	public RecruitDto findSpecificRecruite(int invitedUserId);
	public String updateStatusByInvitedId(int invitedUserId, String status);
}
