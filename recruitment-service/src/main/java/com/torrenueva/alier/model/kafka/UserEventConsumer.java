package com.torrenueva.alier.model.kafka;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.torrenueva.alier.model.dto.RecruitDto;
import com.torrenueva.alier.model.dto.UserInfoDto;
import com.torrenueva.alier.model.service.RecruitmentService;


@Service
public class UserEventConsumer {
	@Autowired
	RecruitmentService recruiteService;
	
	  @KafkaListener(topics = "user-events", groupId = "recruitment-group")
	    public void consumeOrderEvent(UserInfoDto userInfoDto) {
	        System.out.println("Received recruitement Info : " + userInfoDto);
	        
	        LocalDateTime now = LocalDateTime.now();
	        Timestamp timestamp = Timestamp.valueOf(now);
	        
	        UserInfoDto referer = recruiteService.findUserByEmail(userInfoDto.getRefferEmail());
	        
	        RecruitDto recruiterDto = new RecruitDto();
	        recruiterDto.setInvitedId(userInfoDto.getUserId());
	        recruiterDto.setInviterId(referer.getUserId());
	        recruiterDto.setStatus("Pending");
	        recruiterDto.setUpdateDate(timestamp);
	        
	       String resulMsg = recruiteService.saveRecruit(recruiterDto);
	       
	       System.out.println(resulMsg);
	    }
}
