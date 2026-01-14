package com.example.payment_completion_service.repository.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "payment_completion_status")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentCompletionStatus {

    @Id
    @GeneratedValue
    Integer id;

    Integer paymentId;

    String status;
}
