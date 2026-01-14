//package com.example.payment_verification_service.config;
//
//import com.example.payment_verification_service.kafka.model.PaymentRequestEvent;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
//import org.springframework.kafka.support.serializer.JacksonJsonDeserializer; // New Class
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@EnableKafka
//public class KafkaConsumerConfig {
//
//    @Bean
//    public ConsumerFactory<String, PaymentRequestEvent> consumerFactory() {
//        Map<String, Object> config = new HashMap<>();
//
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, "payment-request-group");
//
//        JacksonJsonDeserializer<PaymentRequestEvent> jacksonDeserializer =
//                new JacksonJsonDeserializer<>(PaymentRequestEvent.class);
//        jacksonDeserializer.setUseTypeHeaders(false);
//        jacksonDeserializer.addTrustedPackages("*");
//
//        ErrorHandlingDeserializer<PaymentRequestEvent> errorHandlingDeserializer =
//                new ErrorHandlingDeserializer<>(jacksonDeserializer);
//
//        return new DefaultKafkaConsumerFactory<>(
//                config,
//                new StringDeserializer(),
//                errorHandlingDeserializer
//        );
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, PaymentRequestEvent> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, PaymentRequestEvent> factory
//                = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//}