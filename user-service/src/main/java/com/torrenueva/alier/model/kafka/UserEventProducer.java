package com.torrenueva.alier.model.kafka;

import com.torrenueva.alier.model.dto.UserInfoDto;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class UserEventProducer {
    private static final String TOPIC = "user-events";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public UserEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @CircuitBreaker(name = "kafkaProducerCB", fallbackMethod = "fallbackSendUserEvent")
    public void sendUserEvent(UserInfoDto userInfoDto) {
        kafkaTemplate.send(TOPIC, userInfoDto);
        System.out.println("✅ Sent user event to Kafka: " + userInfoDto);
    }

    public void fallbackSendUserEvent(UserInfoDto userInfoDto, Throwable ex) {
        // This runs if Kafka is down
        System.out.println("⚠️ Kafka is down. Could not send: " + userInfoDto);
        // Optionally: save to DB or retry later
    }
}
