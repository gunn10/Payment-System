package com.example.payment_request_service.kafka.producer;

import com.example.payment_request_service.kafka.model.PaymentRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PaymentRequestProducerEvent {

    private final KafkaTemplate<String,PaymentRequestEvent>kafkaTemplate;

    @Value("kafka.topic.payment-request")
    private String topicName;

    public void sendEvent(PaymentRequestEvent paymentRequestEvent) {

        kafkaTemplate.send(topicName,paymentRequestEvent);

        System.out.println("Event produced at topic: {} with payload {}"+ topicName+ paymentRequestEvent);
    }
}
