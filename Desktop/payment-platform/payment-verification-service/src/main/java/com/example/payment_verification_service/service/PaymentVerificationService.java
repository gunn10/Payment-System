package com.example.payment_verification_service.service;

import com.example.payment_verification_service.kafka.model.PaymentRequestEvent;
import com.example.payment_verification_service.repository.entity.PaymentStatus;

public interface PaymentVerificationService {

   void saveAndVerify(PaymentStatus paymentStatus, PaymentRequestEvent paymentRequestEvent);

}
