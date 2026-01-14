package com.example.payment_request_service.service.impl;

import com.example.payment_request_service.dto.PaymentCompletionDTO;
import com.example.payment_request_service.exception.PaymentRequestException;
import com.example.payment_request_service.kafka.model.PaymentCompletionStatusEvent;
import com.example.payment_request_service.kafka.producer.PaymentRequestProducerEvent;
import com.example.payment_request_service.kafka.model.PaymentRequestEvent;
import com.example.payment_request_service.repository.BankRepository;
import com.example.payment_request_service.repository.PaymentRepository;
import com.example.payment_request_service.repository.UserBankRepository;
import com.example.payment_request_service.repository.UserRepository;
import com.example.payment_request_service.repository.entity.Bank;
import com.example.payment_request_service.repository.entity.Payment;
import com.example.payment_request_service.repository.entity.User;
import com.example.payment_request_service.repository.entity.UserBank;
import com.example.payment_request_service.service.PaymentRequestService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentRequestServiceImpl implements PaymentRequestService {

    private final PaymentRequestProducerEvent paymentRequestProducerEvent;

    private final UserRepository userRepository;

    private final BankRepository bankRepository;

    private final PaymentRepository paymentRepository;

    private final UserBankRepository userBankRepository;

    @Transactional
    @Override
    public void createPaymentRequest(Integer userId, Integer bankId, Integer requestAmount) {

        Optional<User> user = userRepository.findById(userId);
        Optional<Bank> bank = bankRepository.findById(bankId);

        if (user.isEmpty() || bank.isEmpty()) {
            throw new PaymentRequestException("Either User or bank is invalid");
        }

        Payment payment = Payment.builder().
                user(user.get()).
                bank(bank.get()).
                verificationStatus("PENDING").
                transferStatus("PENDING")
                .build();

        System.out.println("Payment Object {}" + payment);

        paymentRepository.save(payment);

        PaymentRequestEvent paymentRequestEvent = PaymentRequestEvent.builder().
                paymentId(payment.getPaymentId()).
                requestAmount(requestAmount).
                bankId(bankId).
                userId(userId)
                .build();

        System.out.println("Event sent with Payload {}" + paymentRequestEvent);

        paymentRequestProducerEvent.sendEvent(paymentRequestEvent);
    }

    @Override
    public Integer getBalance(Integer userId, Integer bankId) {

        return userBankRepository.findBalanceByUserIdAndBankId(userId,bankId);
    }

    @Override
    public Boolean checkFraud(Integer userId, Integer bankId) {

      List<UserBank> userBankList=userRepository.findUserBanks(userId);

        log.info("user banks {}", userBankList);

      List<Integer> bankIds= userBankList.stream().map(user->user.getBank().getBankId()).toList();

      return bankIds.contains(bankId);
    }

    @Override
    public ResponseEntity<?> updateBalance(PaymentCompletionDTO paymentCompletionDTO) {

        UserBank userBank = userBankRepository
                .findByUserIdAndBankId(paymentCompletionDTO.getUserId(), paymentCompletionDTO.getBankId())
                .orElseThrow(() -> new RuntimeException("User not associated with this bank"));

        int newBalance = userBank.getBalance() - paymentCompletionDTO.getRequestAmount();
        userBank.setBalance(newBalance);

        userBankRepository.save(userBank);

        return ResponseEntity.ok().build();
    }

    @Override
    public boolean updatePaymentStatus(PaymentCompletionStatusEvent paymentCompletionStatusEvent) {
        Payment payment=paymentRepository.findById(paymentCompletionStatusEvent.getPaymentId()).get();
        payment.setTransferStatus(paymentCompletionStatusEvent.getStatus());
        boolean updatedStatus;
        paymentRepository.save(payment);
        updatedStatus=true;
        return updatedStatus;
    }
}
