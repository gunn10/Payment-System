package com.example.payment_verification_service.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestEvent {

    Integer userId;
    Integer bankId;
    Integer requestAmount;
    Integer paymentId;

}
