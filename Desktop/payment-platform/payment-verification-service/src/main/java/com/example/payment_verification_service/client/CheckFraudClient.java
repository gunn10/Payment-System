package com.example.payment_verification_service.client;

import com.example.payment_verification_service.exception.PaymentVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class CheckFraudClient {

    @Value("${fraud.check.url}")
    private String url;


    public Boolean isUserBank(Integer userId, Integer bankId){

        try {

            final RestTemplate restTemplate = new RestTemplate();

            return restTemplate.getForObject(
                    url,
                    Boolean.class,
                    userId,
                    bankId
            );
        }

        catch (HttpClientErrorException httpClientErrorException){
            throw new PaymentVerificationException("Fraud check can't be done");
        }

    }
}
