package com.example.payment_request_service.model;


import com.example.payment_request_service.dto.PaymentCompletionDTO;
import com.example.payment_request_service.kafka.consumer.PaymentCompletionStatusConsumer;
import com.example.payment_request_service.service.PaymentRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentRequestController {

    private final PaymentRequestService paymentRequestService;

    private final PaymentCompletionStatusConsumer paymentCompletionStatusConsumer;

    @PostMapping("/payment/{userId}/{bankId}")
    public ResponseEntity.BodyBuilder makePayment(@PathVariable Integer userId, @PathVariable Integer bankId, @RequestBody Integer requestAmount){

        paymentRequestService.createPaymentRequest(userId,bankId,requestAmount);


        return paymentCompletionStatusConsumer.consumedSuccessfully() ? ResponseEntity.accepted():
                ResponseEntity.badRequest();
    }

    @GetMapping("/balance/{userId}/{bankId}")
    public Integer getBalance(@PathVariable Integer userId,@PathVariable Integer bankId){

      return paymentRequestService.getBalance(userId,bankId);

    }

    @GetMapping("/fraud-check/{userId}/{bankId}")
    public Boolean checkFraud(@PathVariable Integer userId, @PathVariable Integer bankId){

        return paymentRequestService.checkFraud(userId,bankId);
    }

    @PostMapping("/update-balance")
    public ResponseEntity<?> updateBalance(@RequestBody PaymentCompletionDTO paymentCompletionDTO){

        return paymentRequestService.updateBalance(paymentCompletionDTO);
    }
}
