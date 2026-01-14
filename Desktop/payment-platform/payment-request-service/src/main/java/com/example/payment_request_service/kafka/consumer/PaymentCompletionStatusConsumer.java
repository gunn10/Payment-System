package com.example.payment_request_service.kafka.consumer;


import com.example.payment_request_service.kafka.model.PaymentCompletionStatusEvent;
import com.example.payment_request_service.service.PaymentRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class PaymentCompletionStatusConsumer {

    private final PaymentRequestService paymentRequestService;

    @KafkaListener(
            topics = "kafka.topic.payment-completion-successful",
            groupId = "spring.kafka.consumer.group-id",
            concurrency = "3"
    )
    public void consume(PaymentCompletionStatusEvent paymentCompletionStatusEvent){

        log.info("Consumed event {}",paymentCompletionStatusEvent);

        boolean updatedStatus= paymentRequestService.updatePaymentStatus(paymentCompletionStatusEvent);
        if(updatedStatus==Boolean.TRUE){
            consumedSuccessfully();
        }
    }

    public boolean consumedSuccessfully(){
        return true;
    }
}
