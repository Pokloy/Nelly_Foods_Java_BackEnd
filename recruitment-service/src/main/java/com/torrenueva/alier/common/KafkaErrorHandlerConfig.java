package com.torrenueva.alier.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.kafka.support.serializer.DeserializationException;

@Configuration
public class KafkaErrorHandlerConfig {
	@Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<Object, Object> kafkaTemplate) {

        // 1st retry delay = 1 sec, multiplier = 2.0, max delay = 10 sec
        ExponentialBackOffWithMaxRetries backOff = new ExponentialBackOffWithMaxRetries(3);
        backOff.setInitialInterval(1000L);
        backOff.setMultiplier(2.0);
        backOff.setMaxInterval(10000L);

        // Define error handler with backoff and DLT publishing
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                new DeadLetterPublishingRecoverer(kafkaTemplate),
                backOff
        );

        // Optional: Skip deserialization errors (don't retry)
        errorHandler.addNotRetryableExceptions(DeserializationException.class);

        // Optional logging
        errorHandler.setRetryListeners((record, ex, deliveryAttempt) -> {
            System.out.printf("Retrying record %s due to %s (attempt %d)%n",
                    record.value(), ex.getMessage(), deliveryAttempt);
        });

        return errorHandler;
    }
}
