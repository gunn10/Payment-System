package com.example.payment_request_service.dto;


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
public class PaymentCompletionDTO {

    Integer userId;
    Integer bankId;
    Integer requestAmount;

}
