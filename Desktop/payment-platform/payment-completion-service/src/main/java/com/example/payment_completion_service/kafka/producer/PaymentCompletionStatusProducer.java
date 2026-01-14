package com.example.payment_completion_service.kafka.producer;


import com.example.payment_completion_service.kafka.model.PaymentCompletionStatusEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentCompletionStatusProducer {


    @Value("kafka.topic.payment-completion-successful")
    private String topicName;

    private final KafkaTemplate<String, PaymentCompletionStatusEvent>kafkaTemplate;

    public void sendEvent(PaymentCompletionStatusEvent paymentCompletionStatusEvent){

        kafkaTemplate.send(topicName,paymentCompletionStatusEvent);
    }
}
