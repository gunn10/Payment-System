package com.example.payment_completion_service.mapper;

import com.example.payment_completion_service.kafka.model.PaymentVerificationEvent;
import com.example.payment_completion_service.repository.entity.PaymentCompletionStatus;
import org.springframework.stereotype.Component;

@Component
public class EventToEntityMapper {

  public PaymentCompletionStatus map(PaymentVerificationEvent paymentVerificationEvent){
      return PaymentCompletionStatus.builder()
              .paymentId(paymentVerificationEvent.getPaymentId())
              .status("PENDING")
              .build();
  }
}
