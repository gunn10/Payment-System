package com.example.payment_verification_service.service.impl;

import com.example.payment_verification_service.client.CheckBalanceClient;
import com.example.payment_verification_service.client.CheckFraudClient;
import com.example.payment_verification_service.exception.PaymentVerificationException;
import com.example.payment_verification_service.kafka.model.PaymentRequestEvent;
import com.example.payment_verification_service.kafka.model.PaymentVerificationEvent;
import com.example.payment_verification_service.kafka.producer.PaymentVerificationProducer;
import com.example.payment_verification_service.repository.PaymentStatusRepository;
import com.example.payment_verification_service.repository.entity.PaymentStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.payment_verification_service.service.PaymentVerificationService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentVerificationServiceImpl implements PaymentVerificationService {

    private final PaymentStatusRepository paymentStatusRepository;

    private final CheckBalanceClient checkBalanceClient;

    private final CheckFraudClient checkFraudClient;

    private final PaymentVerificationProducer paymentVerificationProducer;

    public void saveAndVerify(PaymentStatus paymentStatus,PaymentRequestEvent paymentRequestEvent){

        savePaymentStatus(paymentStatus);

        verifyPayment(paymentRequestEvent);

    }

    @Transactional
    private void savePaymentStatus(PaymentStatus paymentStatus){

        if(paymentStatus.getPaymentId()==null){
            throw new PaymentVerificationException("Payment Id is null");
        }

        paymentStatusRepository.save(paymentStatus);
    }


    private void verifyPayment(PaymentRequestEvent paymentRequestEvent){


       Boolean fraudCheck=fraudCheck(paymentRequestEvent.getUserId(),paymentRequestEvent.getBankId());

       log.info("fraud check {}",fraudCheck);

        Boolean checks=null;

        try{
       checks = fraudCheck == Boolean.TRUE ?
               balanceCheck(paymentRequestEvent.getUserId(),paymentRequestEvent.getBankId(),paymentRequestEvent.getRequestAmount())
               :Boolean.FALSE;}
        catch (PaymentVerificationException paymentVerificationException){
            log.info("Fraud check or balance check failed for {}",paymentRequestEvent.getPaymentId());
        }

       // after async checks save in db if both true

        if(Boolean.TRUE.equals(checks)){

            paymentStatusRepository.findById(paymentRequestEvent.getPaymentId())
                    .ifPresent(ps -> {
                        ps.setStatus("VERIFIED");
                        paymentStatusRepository.save(ps);
                    });
        }

        PaymentVerificationEvent paymentVerificationEvent= PaymentVerificationEvent.builder().paymentId(paymentRequestEvent.getPaymentId())
                .requestAmount(paymentRequestEvent.getRequestAmount())
                .bankId(paymentRequestEvent.getBankId())
                .userId(paymentRequestEvent.getUserId())
                .build();

        paymentVerificationProducer.sendEvent(paymentVerificationEvent);

        // make a producer for request service to consume verification-successful topic and save in db and also make successful service to consume this topic


        //implement saga to overcome if something breaks and also produce to topic verification-failure


    }

    private Boolean balanceCheck(Integer userId, Integer bankId,Integer requestAmount){
                      return  checkBalanceClient.getBalance(userId, bankId) >= requestAmount;
    }

    private Boolean fraudCheck(Integer userId, Integer bankId){
              return  checkFraudClient.isUserBank(userId,bankId);

    }
}
