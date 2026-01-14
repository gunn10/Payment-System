package com.example.payment_completion_service.kafka.consumer;


import com.example.payment_completion_service.kafka.model.PaymentVerificationEvent;
import com.example.payment_completion_service.mapper.EventToEntityMapper;
import com.example.payment_completion_service.repository.entity.PaymentCompletionStatus;
import com.example.payment_completion_service.service.PaymentCompletionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentVerificationConsumer {

    private final PaymentCompletionService paymentCompletionService;

    private final EventToEntityMapper eventToEntityMapper;


    @KafkaListener(
            topics = ("kafka.topic.payment-verification-successful"),
            groupId = ("spring.kafka.consumer.group-id"),
            concurrency = ("2")
    )
    @RetryableTopic(attempts = "2")

    public void consumeEvent(PaymentVerificationEvent paymentVerificationEvent){

        log.info("Consumed event {}",paymentVerificationEvent);

        PaymentCompletionStatus paymentCompletionStatus=eventToEntityMapper.map(paymentVerificationEvent);

        paymentCompletionService.saveAndUpdateBalance(paymentCompletionStatus,paymentVerificationEvent);


    }


}
