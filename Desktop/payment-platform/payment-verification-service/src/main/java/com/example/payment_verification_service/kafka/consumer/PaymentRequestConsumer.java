package com.example.payment_verification_service.kafka.consumer;


import com.example.payment_verification_service.kafka.model.PaymentRequestEvent;
import com.example.payment_verification_service.mapper.EventToEntityMapper;
import com.example.payment_verification_service.repository.entity.PaymentStatus;
import com.example.payment_verification_service.service.PaymentVerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentRequestConsumer {

    private final EventToEntityMapper eventToEntityMapper;

    private final PaymentVerificationService paymentVerificationService;

    @KafkaListener(
          topics = ("kafka.topic.payment-request"),
            groupId = ("spring.kafka.consumer.group-id"),
            concurrency = "3"
    )
    @RetryableTopic(attempts = "2")

    public void consumePaymentRequest(PaymentRequestEvent paymentRequestEvent){
//
//        if(paymentRequestEvent!=null){
//            throw new RuntimeException("");
//        }

        log.info("consumed {}", paymentRequestEvent);

        PaymentStatus paymentStatus=eventToEntityMapper.map(paymentRequestEvent);

        log.info("payment status to save {}" , paymentStatus);

        paymentVerificationService.saveAndVerify(paymentStatus,paymentRequestEvent);

    }

    @DltHandler
    public void handleDeadLetterMessages(PaymentRequestEvent paymentRequestEvent){

        log.info("Message pushed to Dead Letter Queue {}", paymentRequestEvent);
    }
}
