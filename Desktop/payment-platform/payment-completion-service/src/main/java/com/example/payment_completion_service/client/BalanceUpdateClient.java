package com.example.payment_completion_service.client;

import com.example.payment_completion_service.DTO.PaymentCompletionDTO;
import com.example.payment_completion_service.exception.PaymentCompletionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class BalanceUpdateClient {

    @Value("${update-balance.url}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<?> updateBalance(PaymentCompletionDTO paymentCompletionDTO) {
        try {

            ResponseEntity<Void> response = restTemplate.postForEntity(url, paymentCompletionDTO, Void.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new PaymentCompletionException("Balance service returned error: " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.info("Exception caught {}",e);
        }
        return ResponseEntity.ok().build();
    }
}
