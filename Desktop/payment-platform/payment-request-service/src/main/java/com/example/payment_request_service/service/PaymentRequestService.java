package com.example.payment_request_service.service;

import com.example.payment_request_service.dto.PaymentCompletionDTO;
import com.example.payment_request_service.kafka.model.PaymentCompletionStatusEvent;
import org.springframework.http.ResponseEntity;

public interface PaymentRequestService {

    void createPaymentRequest(Integer userId, Integer bankId, Integer requestAmount);

    Integer getBalance(Integer userId, Integer bankId);

    Boolean checkFraud(Integer userId, Integer bankId);

    ResponseEntity<?> updateBalance(PaymentCompletionDTO paymentCompletionDTO);

    boolean updatePaymentStatus(PaymentCompletionStatusEvent paymentCompletionStatusEvent);
}
