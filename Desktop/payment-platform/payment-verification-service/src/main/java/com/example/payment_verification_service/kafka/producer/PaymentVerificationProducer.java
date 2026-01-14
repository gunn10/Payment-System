package com.example.payment_verification_service.kafka.producer;


import com.example.payment_verification_service.kafka.model.PaymentVerificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentVerificationProducer {


    private final KafkaTemplate<String, PaymentVerificationEvent> kafkaTemplate;

    @Value("kafka.topic.payment-verification-successful")
    private String topicName;

    public void sendEvent(PaymentVerificationEvent paymentVerificationEvent){
        kafkaTemplate.send(topicName,paymentVerificationEvent);
        log.info("Event sent to completion service {}",paymentVerificationEvent);

    }


}
