package com.example.payment_verification_service.repository.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="payment_status")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class PaymentStatus {

    @Id
    Integer paymentId;

    String status;
}
