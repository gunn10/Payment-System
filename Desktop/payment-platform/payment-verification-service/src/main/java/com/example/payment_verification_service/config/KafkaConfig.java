package com.example.payment_verification_service.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Value("${kafka.topic.payment-verification-successful}")
    private String topicName;

    @Bean
    public NewTopic newTopic(){
        return new NewTopic(topicName,3,(short)3);
    }
}
