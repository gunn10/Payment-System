package com.example.payment_verification_service.repository;

import com.example.payment_verification_service.repository.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatus,Integer> {
}
