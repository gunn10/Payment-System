package com.example.payment_verification_service.mapper;


import com.example.payment_verification_service.kafka.model.PaymentRequestEvent;
import com.example.payment_verification_service.repository.entity.PaymentStatus;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Builder
@Component
public class EventToEntityMapper {

    public PaymentStatus map(PaymentRequestEvent paymentRequestEvent){
        return PaymentStatus.builder()
                .paymentId(paymentRequestEvent.getPaymentId())
                .status("PENDING")
                .build();
    }

}
