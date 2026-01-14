package com.example.payment_verification_service.client;

import com.example.payment_verification_service.exception.PaymentVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class CheckBalanceClient {

    @Value("${balance.url}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    public Integer getBalance(Integer userId, Integer bankId) {

        try {
            return restTemplate.getForObject(
                    url,
                    Integer.class,
                    userId,
                    bankId
            );
        }
        catch (HttpClientErrorException httpClientErrorException){
            throw new PaymentVerificationException("Not able to get balance");
        }
    }
}
