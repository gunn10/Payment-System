package com.example.payment_completion_service.service.impl;

import com.example.payment_completion_service.DTO.PaymentCompletionDTO;
import com.example.payment_completion_service.client.BalanceUpdateClient;
import com.example.payment_completion_service.kafka.model.PaymentCompletionStatusEvent;
import com.example.payment_completion_service.kafka.model.PaymentVerificationEvent;
import com.example.payment_completion_service.kafka.producer.PaymentCompletionStatusProducer;
import com.example.payment_completion_service.repository.PaymentCompletionStatusRepository;
import com.example.payment_completion_service.repository.entity.PaymentCompletionStatus;
import com.example.payment_completion_service.service.PaymentCompletionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PaymentCompletionServiceImpl implements PaymentCompletionService {

    private final PaymentCompletionStatusRepository paymentCompletionStatusRepository;

    private final BalanceUpdateClient balanceUpdateClient;

    private final PaymentCompletionStatusProducer paymentCompletionStatusProducer;


    @Transactional
    @Override
    public void saveAndUpdateBalance(PaymentCompletionStatus paymentCompletionStatus, PaymentVerificationEvent paymentVerificationEvent) {

         paymentCompletionStatusRepository.save(paymentCompletionStatus);

        PaymentCompletionDTO paymentCompletionDTO=PaymentCompletionDTO.builder()
                .bankId(paymentVerificationEvent.getBankId())
                .requestAmount(paymentVerificationEvent.getRequestAmount())
                .userId(paymentVerificationEvent.getUserId())
                .build();

        PaymentCompletionStatus paymentCompletionStatus1=paymentCompletionStatusRepository.getPaymentCompletionStatusByPaymentId(paymentCompletionStatus.getPaymentId());

         if(updateBalance(paymentCompletionDTO).getStatusCode()==ResponseEntity.ok().build().getStatusCode()){
             paymentCompletionStatus1.setStatus("COMPLETED");
             paymentCompletionStatusRepository.save(paymentCompletionStatus1);

         }

         PaymentCompletionStatusEvent paymentCompletionStatusEvent= PaymentCompletionStatusEvent.builder()
                 .paymentId(paymentCompletionStatus1.getPaymentId())
                 .status(paymentCompletionStatus1.getStatus())
                 .build();

         paymentCompletionStatusProducer.sendEvent(paymentCompletionStatusEvent);

    }

    private ResponseEntity<?> updateBalance(PaymentCompletionDTO paymentCompletionDTO){

       return balanceUpdateClient.updateBalance(paymentCompletionDTO);

    }
}
