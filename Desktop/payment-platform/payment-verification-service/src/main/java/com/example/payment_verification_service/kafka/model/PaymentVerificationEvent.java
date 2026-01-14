package com.example.payment_verification_service.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentVerificationEvent {

    Integer userId;
    Integer bankId;
    Integer requestAmount;
    Integer paymentId;
}
