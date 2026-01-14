package com.example.payment_completion_service.repository;

import com.example.payment_completion_service.repository.entity.PaymentCompletionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentCompletionStatusRepository extends JpaRepository<PaymentCompletionStatus,Integer> {

    @Query("SELECT p FROM payment_completion_status p WHERE p.paymentId = :paymentId")
    PaymentCompletionStatus getPaymentCompletionStatusByPaymentId(@Param("paymentId") Integer paymentId);

}
