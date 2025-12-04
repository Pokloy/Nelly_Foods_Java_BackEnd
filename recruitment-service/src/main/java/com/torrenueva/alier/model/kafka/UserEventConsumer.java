package com.torrenueva.alier.model.kafka;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.torrenueva.alier.model.client.UserServiceClient;
import com.torrenueva.alier.model.dto.RecruitDto;
import com.torrenueva.alier.model.dto.UserInfoDto;
import com.torrenueva.alier.model.service.RecruitmentService;

import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;


@Service
public class UserEventConsumer {
	private static final Logger logger = LoggerFactory.getLogger(UserEventConsumer.class);
	
	@Autowired
	UserServiceClient userServiceClient;
	
	@Autowired
	RecruitmentService recruiteService;
	
	  @KafkaListener(
			  topics = "user-events", 
			  groupId = "recruitment-group")
	    public void consumeUserEvent(UserInfoDto userInfoDto) {
    		logger.info("📦 Received User Event: {}", userInfoDto);
	        System.out.println("Received recruitement Info : " + userInfoDto);
	        
	        try {
	            if (userInfoDto.getUserId() == 0) {
	                throw new IllegalArgumentException("Invalid order data!");
	            }
	            
	            // simulate transient failure (30 % chance)
	            if (Math.random() < 0.3) {
	                throw new RuntimeException("Simulated transient failure updating user");
	            }
	            
	            
		        LocalDateTime now = LocalDateTime.now();
		        Timestamp timestamp = Timestamp.valueOf(now);
		        
		        UserInfoDto referer = userServiceClient.getUserByEmail(userInfoDto.getRefferEmail());
		        
		        RecruitDto recruiterDto = new RecruitDto();
		        recruiterDto.setInvitedId(userInfoDto.getUserId());
		        recruiterDto.setInviterId(referer.getUserId());
		        recruiterDto.setStatus("Pending");
		        recruiterDto.setUpdateDate(timestamp);
		        
		       String resulMsg = recruiteService.saveRecruit(recruiterDto);
		       
		       logger.info(resulMsg);
		       
		       System.out.println(resulMsg);
	        } catch (Exception e) {
	            logger.error("❌ Error processing order event: {}", e.getMessage());
	            throw e; // rethrow to trigger retry
	        }
	    }
    	
	
//	@KafkaListener(topics = "user-events", groupId = "recruitment-group")
//	public void consumeUserEvent(UserInfoDto userInfoDto) {
//
//	    System.out.println("Received: " + userInfoDto);
//
//	    // FAIL IMMEDIATELY for testing DLT
//	    if (userInfoDto.getUserId() == 0) {
//	        throw new RuntimeException("Forced exception for DLT test");
//	    }
//
//	    // Normal processing (will not be executed during DLT test)
//	    UserInfoDto referer = recruiteService.findUserByEmail(userInfoDto.getRefferEmail());
//
//	    RecruitDto recruiterDto = new RecruitDto();
//	    recruiterDto.setInvitedId(userInfoDto.getUserId());
//	    recruiterDto.setInviterId(referer.getUserId());
//	    recruiterDto.setStatus("Pending");
//	    recruiterDto.setUpdateDate(Timestamp.valueOf(LocalDateTime.now()));
//
//	    recruiteService.saveRecruit(recruiterDto);
//	    System.out.println("Recruitment saved successfully");
//	}

	
	
	
        // optional Dead-Letter listener
        @KafkaListener(topics = "user-events.DLT", groupId = "recruitment-group")
        public void consumeDLT(String failedMessage) {
            logger.warn("⚠️ Message moved to DLT: {}", failedMessage);
        }
}
