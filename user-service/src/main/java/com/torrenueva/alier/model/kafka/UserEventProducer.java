package com.torrenueva.alier.model.kafka;

import com.torrenueva.alier.model.dto.UserInfoDto;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventProducer {
    private static final String TOPIC = "user-events";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public UserEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserEvent(UserInfoDto userInfoDto) {
        kafkaTemplate.send(TOPIC, userInfoDto);
        System.out.println("✅ Sent user event to Kafka: " + userInfoDto);
    }
}
