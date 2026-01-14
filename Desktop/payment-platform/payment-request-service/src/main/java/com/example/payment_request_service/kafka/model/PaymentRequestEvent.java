package com.example.payment_request_service.kafka.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class PaymentRequestEvent {

    Integer userId;
    Integer bankId;
    Integer requestAmount;
    Integer paymentId;

}
