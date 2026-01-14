package com.example.payment_request_service;

import com.example.payment_request_service.repository.BankRepository;
import com.example.payment_request_service.repository.UserRepository;
import com.example.payment_request_service.repository.entity.Bank;
import com.example.payment_request_service.repository.entity.User;
import com.example.payment_request_service.repository.UserBankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class PaymentRequestServiceApplication implements CommandLineRunner {

	private final UserRepository userRepository;

	private final BankRepository bankRepository;

	private final UserBankRepository userBankRepository;

	public static void main(String[] args) {
		SpringApplication.run(PaymentRequestServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


		bankRepository.saveAll((List.of(
				new Bank(1,"HDFC"),
				new Bank(2,"ICICI"),
				new Bank(3,"SBI"),
				new Bank(4,"PNB")
		)));

		userRepository.saveAll(List.of(
				new User(1,"Alice"),
				new User(2,"Bob"),
				new User(3,"David"),
				new User(4,"Charlie"),
				new User(5,"Edward"),
				new User(6, "Fiji"),
				new User(7,"Golf")

		));


	}
}
