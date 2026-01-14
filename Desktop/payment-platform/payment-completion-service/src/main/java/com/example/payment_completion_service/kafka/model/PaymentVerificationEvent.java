package com.example.payment_completion_service.kafka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class PaymentVerificationEvent {

    Integer userId;
    Integer bankId;
    Integer requestAmount;
    Integer paymentId;
}
