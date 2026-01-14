package com.example.payment_completion_service.service;

import com.example.payment_completion_service.kafka.model.PaymentVerificationEvent;
import com.example.payment_completion_service.repository.entity.PaymentCompletionStatus;

public interface PaymentCompletionService {

    void saveAndUpdateBalance(PaymentCompletionStatus paymentCompletionStatus, PaymentVerificationEvent paymentVerificationEvent);
}
